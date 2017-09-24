package com.bjxc.supervise.netty;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.http.crypto.JSPTConstants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {

	private static final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {
		logger.info("MessageDecoder---------");

		if(buf.readableBytes() <1){
			return;
		}
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		String originalMessage = HexUtils.BinaryToHexString(req);
		logger.info("originalMessage："+originalMessage);

		//转义处理
		byte[] tranReq = transferred(req);
		
		if(validate(tranReq)){
			byte[] newReq = this.trime(tranReq);
			Message message = this.decodeMessage(newReq);
			message.setOriginalMessage(originalMessage);
			out.add(message);
		}else{
			//校验码检查失败，发送错误的通用应答
			byte[] newReq = this.trime(tranReq);
			Message message = this.decodeMessage(newReq);
			Message result = MessageUtils.errorMessage(message.getHeader().getId(), message.getHeader().getNumber());
			out.add(result);
		}
		
		
	}
	

	
	/**
	 * 标识位处理
	 * 接收消息时:转义还原——>验证校验码——>解析消息。
	 * 转义规则:
	 * 0x7e<——>0x7d后紧跟一个0x02；
	 * 0x7d<——>0x7d后紧跟一个0x01。
	 * @param msg
	 * @return
	 */
	private byte[] transferred(byte[] msg){

		ByteBuf tranBuf = Unpooled.buffer();
		for(int i=0;i < msg.length;i++ ){
			byte vl = msg[i];
			if(vl == 0x7d){
				byte afvl = msg[i+1];
				if(afvl == 0x02){
					tranBuf.writeByte(0x7e);
					i++;
				}else if(afvl == 0x01){
					tranBuf.writeByte(0x7d);
					i++;
				}else{
					tranBuf.writeByte(vl);
				}
			}else{
				tranBuf.writeByte(vl);
			}
		}

		byte[] tranReq = new byte[tranBuf.readableBytes()];
		tranBuf.readBytes(tranReq);
		return tranReq;
	}
	
	/**
	 * 校验码
	 * @param msg
	 * @return
	 */
	private boolean validate(byte[] msg){
		byte check = msg[msg.length-1];
		byte bcheck = msg[0];
		for(int i=1;i < msg.length-1;i++ ){
			byte bv = msg[i];
			bcheck = (byte) (bcheck ^ bv);
		}
		if(bcheck != check){
			logger.warn("校验码检查不通过！");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	private byte[] trime(byte[] msg){
		ByteBuf newBuf = Unpooled.buffer();
		for(int i=0;i < msg.length-1;i++ ){
			newBuf.writeByte(msg[i]);
		}
		byte[] newReq = new byte[newBuf.readableBytes()];
		newBuf.readBytes(newReq);
		return newReq;
	}
	
	
	private Message decodeMessage(byte[] msg){
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(msg);
		
		Message  message = new Message();
		Header	header = new Header();
		message.setHeader(header);
		
		//0协议版本号
		header.setVersion(buf.readByte());
		
		//1消息ID
		header.setId(buf.readShort());
		
		//3消息体属性
		short property = buf.readShort();
		this.setProerty(header,property);
		
		//5手机号码16位，不足补0
		byte[] mobileBytes = new byte[8];
		buf.readBytes(mobileBytes);
		header.setMobile(HexUtils.bcd2Str(mobileBytes));
		
		//13消息流水号
		header.setNumber(buf.readShort());
		
		//15预留
		header.setBackup(buf.readByte());
		
		//16消息包封装项，确定是否分包，不然没内容
		if(header.isSubPackage()){
			header.setPackageSize(buf.readShort());
			header.setPackageIndex(buf.readShort());
		}
		
		//消息体
		byte[] body = new byte[buf.readableBytes()];
		buf.readBytes(body);
		message.setBody(body);
		
		
		return message;
	}
	
	/**
	 * 解析消息体属性
	 * @param header
	 * @param property
	 */
	private void setProerty(Header header,short property){
		header.setProperty(property);
		short a = 1 << 13;
		//short a = 1 << 12;
		
		//bit13分包标识位
		if((property & a) == a){
			header.setSubPackage(true);
		}
		//bit10-bit12为数据加密标识位
		//0x400 :  10000000000
		//0x1c00 : 1110000000000
		if((property & 0x400) == 0x400){
			//仅当第10位为1时，表示消息体经过RSA算法加密；
			header.setEncrypt("RSA");
		}else if((property & 0x1c00) == 0){
			//当此三位都为0时，表示消息体不加密；
			header.setEncrypt(null);
		}
		//1023 111111111
		short length = (short)(property & 1023);
		//short length = (short)(property - a);
		header.setLength(length);
	}

}
