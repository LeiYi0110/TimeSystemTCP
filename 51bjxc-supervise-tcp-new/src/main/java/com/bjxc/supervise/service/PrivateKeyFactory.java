package com.bjxc.supervise.service;


import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


public class PrivateKeyFactory {

	@Value("${bjxc.supervise.pfx}")
	private String supervisePFX;
	
	
	@Value("${bjxc.supervise.pfx.password}")
	private String supervisePFXPassword;
	
	private PrivateKey privateKey;
	
	private String certsn;
	
	
	public void initPrivateKey() throws Exception{
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		try (InputStream input = new FileInputStream(supervisePFX)) {
			keyStore.load(input, supervisePFXPassword.toCharArray());
		}
		Enumeration<String> aliases = keyStore.aliases();
		if (!aliases.hasMoreElements()) {
			throw new RuntimeException("no alias found");
		}
		String alias = aliases.nextElement();
		this.privateKey = (PrivateKey) keyStore.getKey(alias, supervisePFXPassword.toCharArray());
		
		X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
		this.certsn = Long.toHexString(cert.getSerialNumber().longValue()).toUpperCase();
		
		/**
		String data = "{\"inscode\":\"2236581429084424\",\"district\":\"420528\",\"name\":\"test111\",\"shortname\":\"test2\",\"licnum\":\"112323445566\",\"licetime\":\"20160720\",\"business\":\"23421345563567777775\",\"address\":\"asgweeee\",\"postcode\":\"443500\",\"legal\":\"fff\",\"contact\":\"fff\",\"phone\":\"18215487978\",\"busiscope\":\"C1,C2,C3\",\"busistatus\":\"1\",\"level\":1,\"coachnumber\":0,\"grasupvnum\":0,\"safmngnum\":0,\"tracarnum\":0,\"praticefield\":0,\"thcoachnum\":0,\"pracoachnum\":0,\"lat\":0.0,\"lng\":0.0,\"position\":0,\"thumbnailid\":0}";
		long timestamp = new Date().getTime();
		
		ISign sign = new Sign();
		String sign_hex = sign.sign(data, timestamp, privateKey);
		System.out.println(sign_hex);
		IVerify verify = new Verify();
		boolean ok = verify.verify(data, timestamp, sign_hex, cert);
		System.out.println(ok);
		**/
	}


	public PrivateKey getPrivateKey() {
		return privateKey;
	}


	public String getCertsn() {
		return certsn;
	}


	
	
	
}
