package com.bjxc.supervise.netty.client;

import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bjxc.model.Province;
import com.bjxc.supervise.Action.Utils;
import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.server.SpringApplicationContext;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.service.ProvinceService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @author levin修改，20170105
 */
public class ProvinceLoginAuthHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ProvinceLoginAuthHandler.class);
	private int code;
	private String password = null;
	private String platformNumb = null;
	private Message heartMessage = null;
	private ApplicationContext applicationContext = null;
	private ProvinceService provinceService = null;

	public ProvinceLoginAuthHandler(String platformNumb, int code, String password) {
		this.code = code;
		this.password = password;
		this.platformNumb = platformNumb;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		// 保存消息通道
		TCPMap.getInstance().getChannelMap().put(platformNumb, ctx);

		//设置心跳包
		heartMessage = Utils.getCommonMessage(null, (short)0x0002, JSPTConstants.JSPT_MOBILE_DEFAULT,MessageUtils.getCurrentMessageNumber());
		
		applicationContext = SpringApplicationContext.getInstance().getContext();
		provinceService = applicationContext.getBean(com.bjxc.supervise.service.ProvinceService.class);
		
		// 连接默认自动登录
		login(ctx);
	}

	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    ctx.writeAndFlush(heartMessage);
                    logger.info("send ping to province server----------");
                    break;
                default:
                    break;
            }
        }
    }

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		Message message = (Message) msg;
		byte result = -1;
		
		logger.info("省平台返回消息: " + Integer.toHexString(message.getHeader().getId()));

		// 登录日志提示
		if (message.getHeader().getId() == (short) 0x81F0) {
			byte[] body = message.getBody();
			ByteBuf buf = Unpooled.copiedBuffer(body);
			result = buf.readByte();
			logger.info("登录省平台返回结果：" + result);
		}else if (message.getHeader().getId() == (short) 0x8001) { //通用日志
			byte[] body = message.getBody();
			//ByteBuf buf = Unpooled.copiedBuffer(body);
			//byte result = buf.readByte();
			//第5个字节为应答结果
			if(body!=null && body.length>=4 ){
				result = body[4];
				logger.info("省平台通用应答结果：" + result);
			}
		}
		
		// 消息不管走省平台还是终端传递过来的消息，统一走原TCP通道返回消息
		ChannelHandlerContext clientCtx = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_MOBILE);
		if (clientCtx != null) {
			logger.info("send message to jspt.");
			clientCtx.writeAndFlush(msg);
		}

		//把省平台返回的数据保存到数据库
		Province province = new Province();
		province.setMsgid(Integer.toHexString(message.getHeader().getId()));
		if(message.getHeader().getId() == (short)0x0900||message.getHeader().getId() == (short)0x8900){
			UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody(message.getBody());
			ExtendMessageBody extendMessageBody = upTransportMessageBody.getExtendMessageBody();
			province.setExtendmsgid(Integer.toHexString(extendMessageBody.getTransportId()));
		}
		province.setMsgcontent(result+"");
		province.setCreatetime(new Date());
		provinceService.add(province);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();

	}
	
    /**
     * 启动登录
     * @param ctx
     */
	private void login(ChannelHandlerContext ctx) {
		try {
			logger.info("province login send start ");
			ByteBuf tranBuf = Unpooled.buffer();
			tranBuf.writeBytes(platformNumb.getBytes("GBK"));
			tranBuf.writeBytes(password.getBytes("GBK"));
			tranBuf.writeInt(code);

			byte[] body = new byte[tranBuf.readableBytes()];
			tranBuf.readBytes(body);
			Message loginMsg = new Message();
			Header header = new Header();
			header.setId((short) 0X01F0);
			header.setMobile(JSPTConstants.JSPT_MOBILE_DEFAULT);
			header.setNumber((short) 1);
			loginMsg.setHeader(header);
			loginMsg.setBody(body);

			ctx.writeAndFlush(loginMsg);
			logger.info("province login send end");
		} catch (Exception e) {
			logger.error("login error" + e);
		}
	}
}
