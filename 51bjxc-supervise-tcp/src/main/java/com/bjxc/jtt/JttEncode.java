package com.bjxc.jtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.objenesis.instantiator.sun.MagicInstantiator;

import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.netty.server.TCPServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JttEncode extends MessageToByteEncoder<JttMessage>{
	
	private static final Logger logger = LoggerFactory.getLogger(JttEncode.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, JttMessage msg, ByteBuf out) throws Exception {
		
		try {
			logger.info("---- jtt Encode ----");
			//头标识
			out.writeByte(msg.getFirst());
			ByteBuf msgBuf = Unpooled.buffer();
			//数据长度
			if (msg.getData() != null) {
				msgBuf.writeInt(msg.getData().length + 26);
			} else {
				msgBuf.writeInt(0);
			}
			//报文序列号
			msgBuf.writeInt(msg.getSerialNumber());
			//业务类型
			msgBuf.writeShort(msg.getType());
			//下级平台接入码
			msgBuf.writeInt(msg.getCode());
			//协议版本号标识
			msgBuf.writeBytes(msg.getVersion());
			//加密标识位
			msgBuf.writeByte(msg.getEncrypt());
			//加密秘钥
			msgBuf.writeInt(msg.getKey());
			//数据体
			if(msg.getData() != null){
				msgBuf.writeBytes(msg.getData());
			}
			//生成crc校验码
			byte[] crc16 = new byte[msgBuf.readableBytes()];
			msgBuf.readBytes(crc16);
			msg.setCrcCode((short) crc16ccitt(crc16));
			msgBuf.writeBytes(crc16);
			//校验值
			msgBuf.writeShort(msg.getCrcCode());
			byte[] mby = new byte[msgBuf.readableBytes()];
			msgBuf.readBytes(mby);
			//消息封装转义
			ByteBuf tranBuf = Unpooled.buffer();
			for (int i = 0; i < mby.length; i++) {
				byte mg = mby[i];
				if (mg == 0x5b) {
					tranBuf.writeByte(0x5a);
					tranBuf.writeByte(0x01);
				} else if (mg == 0x5a) {
					tranBuf.writeByte(0x5a);
					tranBuf.writeByte(0x02);
				} else if (mg == 0x5d) {
					tranBuf.writeByte(0x5e);
					tranBuf.writeByte(0x01);
				} else if (mg == 0x5e) {
					tranBuf.writeByte(0x5e);
					tranBuf.writeByte(0x02);
				} else {
					tranBuf.writeByte(mg);
				}
			}
			byte[] tranmby = new byte[tranBuf.readableBytes()];
			tranBuf.readBytes(tranmby);
			out.writeBytes(tranmby);
			//尾标识
			out.writeByte((byte) msg.getLast());
			byte[] msgByte = new byte[out.readableBytes()];
			out.readBytes(msgByte);
			out.readerIndex(0);
			logger.info("jtt发送消息: " + HexUtils.BinaryToHexString(msgByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int crc16ccitt(byte[] bytes){
		int crc = 0xFFFF;          // initial value  
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)   
  
        for (byte b : bytes) {  
            for (int i = 0; i < 8; i++) {  
                boolean bit = ((b   >> (7-i) & 1) == 1);  
                boolean c15 = ((crc >> 15    & 1) == 1);  
                crc <<= 1;  
                if (c15 ^ bit) crc ^= polynomial;  
             }  
        }  
        crc &= 0xffff;  
        return crc;
	}
}
