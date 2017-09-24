package com.bjxc.supervise.client;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import com.bjxc.supervise.Action.Utils;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.MessageUtils;

public class checkCodeTest {
	public static void main(String[] args) {
		/*String ss = "808900002E0000000000100041099A0011810100000001333533373134333433363337363332380000001301343339343134313137353130323337360200";
		byte[] req = HexUtils.HexStringToBinary(ss);
		byte check = req[0];
		for (int i = 1; i < req.length; i++) {
			check = (byte) (check^req[i]);
		}
		byte[] checks = {check};
		System.out.println(HexUtils.BinaryToHexString(checks));*/
		
		String BCD = "24244D007082010000000C33313738323531363033393433313636000000290930 37 35 35 30 31 30 30 30 31 33 33 33 33 33 33 00 00 00 00 00 00 00 00 01 0E D1 A7 D4 B1 CE DE BF C6 C4 BF D0 C5 CF A2 20 66 31 0D 0A ";
		byte[] req = HexUtils.HexStringToBinary(BCD);
		String s = null;
		try {
			 s = new String(req, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("string : "+s);
	}
}
