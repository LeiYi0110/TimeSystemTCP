package com.bjxc.supervise.netty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.bjxc.supervise.http.crypto.JSPTConstants;

//import antlr.collections.List;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageUtils {

	public static short number = 0;
	public static short seq = 0;
	public static short id = 1;
	

	public static synchronized short getCurrentMessageId(){
		return id++;
	}

	public static synchronized short getCurrentMessageNumber(){
		return number++;
	}

	public static synchronized short getCurrentMessageSeq(){
		return seq++;
	}
	
	/**
	 * 涓嶆敮鎸�
	 * @param ctx
	 * @param number
	 * @param id
	 * @return
	 */
	public static Message notFountMessage(short id ,short number){
		//return createDefaultMessage(id,number,(byte)3);
		return createDefaultMessage(number,id,(byte)3,JSPTConstants.JSPT_PLATFORMNO_BCD);
	}
	
	/**
	 * 鏈夐敊璇�
	 * @param ctx
	 * @param number
	 * @param id
	 * @return
	 */
	public static Message errorMessage(short id ,short number){
		//return createDefaultMessage(id,number,(byte)2);
		return createDefaultMessage(number,id,(byte)2,JSPTConstants.JSPT_PLATFORMNO_BCD);
	}
	
	/**
	 * 澶勭悊澶辫触
	 * @param ctx
	 * @param number
	 * @param id
	 * @return
	 */
	public static Message failMessage(short id ,short number){
		//return createDefaultMessage(id,number,(byte)1);
		return createDefaultMessage(number,id,(byte)1,JSPTConstants.JSPT_PLATFORMNO_BCD);
	}
	
	/**
	 * 澶勭悊鎴愬姛
	 * @param ctx
	 * @param number
	 * @param id
	 * @return
	 */
	public static Message successMessage(short id ,short number){
		//return createDefaultMessage(id,number,(byte)0);
		return createDefaultMessage(number,id,(byte)0,JSPTConstants.JSPT_PLATFORMNO_BCD);
	}
	
	public static Message createDefaultMessage(short number,short id ,byte result,String mobile){
		Message message = new Message();
		Header header = new Header();
		header.setNumber(MessageUtils.getCurrentMessageNumber());
		header.setId((short)0x8001);
		header.setMobile(mobile);
		ByteBuf hbuf = Unpooled.buffer();
		hbuf.writeShort(number);
		hbuf.writeShort(id);
		hbuf.writeByte(result);
		byte[] body = new byte[hbuf.readableBytes()];
		hbuf.readBytes(body);
		message.setHeader(header);
		message.setBody(body);
		return message;
	}
	
	
	
	public static List<Message> dividePackage(Message message)
	{
		List<Message> list = new ArrayList<Message>();
		
		short messageBodyMaxLength = (short)(Message.maxLength - Header.maxLength - 200);
		int packageCount = message.getBody().length/messageBodyMaxLength + 1;
		
		ByteBuf hbuf = Unpooled.copiedBuffer(message.getBody());
		
		for(int i = 0; i < packageCount; i++)
		{
			Message divideMessage = new Message();
			Header header = new Header();
			header.setId(message.getHeader().getId());
			
			header.setMobile(message.getHeader().getMobile());
			header.setNumber((short)i);
			header.setPackageSize((short)packageCount);
			header.setPackageIndex((short)(i + 1));
			header.setSubPackage(true);
			
			
			
			
			
			int bodyLength = messageBodyMaxLength;
			
			if (i == packageCount - 1) {
				bodyLength = message.getBody().length - i*messageBodyMaxLength;
			}
			
			byte[] body = new byte[bodyLength];
			
			hbuf.readBytes(body);
			
			header.setProperty((short)(bodyLength + (short)Math.pow(2, 13)));
			
			divideMessage.setHeader(header);
			divideMessage.setBody(body);
			
			list.add(divideMessage);
			
			
		}
		
		return list;
	}
	
	public static Message unionPackage(List<Message> list)
	{
		Header header = new Header();
		Message message = list.get(0);
		header.setId(message.getHeader().getId());
		
		header.setMobile(message.getHeader().getMobile());
		header.setNumber(message.getHeader().getNumber());
		
		//int bodySize = 0;
		Collections.sort(list);
		ByteBuf bodybuf = Unpooled.buffer();
		for(Message item : list)
		{
			//bodySize += item.getHeader().getPackageSize();
			
			
			bodybuf.writeBytes(item.getBody());
		}
		
		byte[] body = new byte[bodybuf.readableBytes()];
		
		bodybuf.readBytes(body);
		
		Message resultMessage = new Message();
		resultMessage.setHeader(header);
		resultMessage.setBody(body);
		
		return resultMessage;
	
		/*
		List<Message> list = new ArrayList<Message>();
		
		short messageBodyMaxLength = (short)(Message.maxLength - Header.maxLength);
		int packageCount = message.getBody().length/messageBodyMaxLength + 1;
		
		ByteBuf hbuf = Unpooled.copiedBuffer(message.getBody());
		
		for(int i = 0; i < packageCount; i++)
		{
			Message divideMessage = new Message();
			Header header = new Header();
			header.setId(message.getHeader().getId());
			
			header.setMobile(message.getHeader().getMobile());
			header.setNumber(message.getHeader().getNumber());
			header.setPackageSize((short)packageCount);
			header.setPackageIndex((short)(i + 1));
			header.setSubPackage(true);
			
			
			
			
			
			int bodyLength = messageBodyMaxLength;
			
			if (i == packageCount - 1) {
				bodyLength = message.getBody().length - i*messageBodyMaxLength;
			}
			
			byte[] body = new byte[bodyLength];
			
			hbuf.readBytes(body);
			
			header.setProperty((short)(bodyLength + (short)Math.pow(2, 12)));
			
			divideMessage.setHeader(header);
			divideMessage.setBody(body);
			
			list.add(divideMessage);
			
			
		}
		
		return list;
		*/
	}
	

}
