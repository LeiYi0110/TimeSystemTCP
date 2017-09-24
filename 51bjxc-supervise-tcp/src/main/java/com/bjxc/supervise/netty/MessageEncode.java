package com.bjxc.supervise.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 封装消息
 *
 */
public class MessageEncode extends MessageToByteEncoder<Message> {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageEncode.class);
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)
			throws Exception {
		
		logger.info("---- MessageEncode ----");
		
		out.writeByte(msg.getFirst());
		
		byte[] body = this.encodeBody(msg);
		short bodyLength = 0;
		if(body!= null){
			bodyLength = (short) body.length;
		}else{
			body = new byte[0];
		}
		byte[] header = this.encodeHeader(msg.getHeader(), bodyLength);
		
		ByteBuf msgBuf = Unpooled.buffer();
		byte check = 0;
		for(int i= 0;i<header.length;i++){
			msgBuf.writeByte(header[i]);
			if(i==0){
				check = header[0];
			}else{
				check = (byte) (check^header[i]);
			}
		}

		for(int i= 0;i<body.length;i++){
			msgBuf.writeByte(body[i]);
			check = (byte) (check^body[i]);
		}
		byte[] mby = new byte[msgBuf.readableBytes()];
		msgBuf.readBytes(mby);
		
		//消息封装转义
		ByteBuf tranBuf = Unpooled.buffer();
		for(int i =0;i<mby.length;i++ ){
			byte mg = mby[i];
			if(mg == 0x7e){
				tranBuf.writeByte(0x7d);
				tranBuf.writeByte(0x02);
			}else if(mg == 0x7d){
				tranBuf.writeByte(0x7d);
				tranBuf.writeByte(0x01);
			}else{
				tranBuf.writeByte(mg);
			}
		}

		byte[] tranmby = new byte[tranBuf.readableBytes()];
		tranBuf.readBytes(tranmby);
		
		out.writeBytes(tranmby);
		out.writeByte(check);
		out.writeByte(msg.getLast());
		

		byte[] msgByte = new byte[out.readableBytes()];
		
		out.readBytes(msgByte);
		out.readerIndex(0);
		
		logger.info("jspt send message:"+HexUtils.BinaryToHexString(msgByte));
		
	}
	
	public byte[] encodeBody(Message msg){
		return msg.getBody();
	}
	
	
	/**
	 * 封装头部
	 * @param header
	 * @param boyLength
	 * @return
	 */
	public byte[] encodeHeader(Header header,short boyLength){
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeByte(header.getVersion());
		hbuf.writeShort(header.getId());
		
		if (header.isSubPackage()) {
			hbuf.writeShort(header.getProperty());
		}
		else
		{
			hbuf.writeShort(boyLength);
		}
		
		
		
		//hbuf.writeLong(header.getMobile());
		hbuf.writeBytes(HexUtils.str2Bcd(header.getMobile()));
		
		hbuf.writeShort(header.getNumber());
		hbuf.writeByte(header.getBackup());
		
		
		if (header.isSubPackage()) {
			hbuf.writeShort(header.getPackageSize());
			hbuf.writeShort(header.getPackageIndex());
		}
		
		
		byte[] hby = new byte[hbuf.readableBytes()];
		hbuf.readBytes(hby);
		return hby;
	}
	
	

}
