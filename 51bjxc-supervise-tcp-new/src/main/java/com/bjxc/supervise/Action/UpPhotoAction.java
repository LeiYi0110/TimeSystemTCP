package com.bjxc.supervise.Action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.PhotoFileUpload;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UpPhotoAction implements Action{
	private static final Logger logger = LoggerFactory.getLogger(UpPhotoAction.class);
	
	public void action(ActionContext actionContext) {
		System.out.println("asdasdahkjdhgbnxbcvmnxbmvnbkjhfsdajhfskdhgjkdfshg");
		Message msg = actionContext.getMessag();
		try {
			UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody(msg.getBody());
			ExtendMessageBody extendMessageBody = upTransportMessageBody.getExtendMessageBody();
			PhotoFileUpload pfile=(PhotoFileUpload) extendMessageBody.getData();
			System.out.println(msg.getBody().length);
			System.out.println(pfile.getBytes());
			System.out.println(pfile.getPhotoNum());
			System.out.println(pfile.getFile().getName());
//			System.out.println(msg.getBody().length);
//			PhotoFile photoFile=new PhotoFile(msg.getBody());
//			System.out.println(photoFile.getPhotoNum());
//			System.out.println(photoFile.getFile().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
