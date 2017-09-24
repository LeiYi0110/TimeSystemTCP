package com.bjxc.supervise.http.crypto;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjxc.supervise.netty.HexUtils;



/**
 * 用于终端鉴权校验密文
 * 用于加密字符串
 * 
 * @author levin
 *
 */
public class DecodeUtil {

	private static final Logger logger = LoggerFactory.getLogger(DecodeUtil.class);

	/**
	 * 校验鉴权密文
	 * @param data
	 * @param timeTmp
	 * @param deviceNo
	 * @param cadata，计时终端证书的base64编码（即表A.20中的key）
	 * @param capwd，证书的密码（即表A.20中的passwd）
	 * @return
	 */
	public static boolean checkTerminalAuth(byte [] data, String encodedEncryptedStr, int timestamp, String cadata, String capwd) {
		
		logger.info("dataHex=" + HexUtils.BinaryToHexString(data));
		logger.info("encodedEncryptedStr=" + encodedEncryptedStr);
		logger.info("timestamp=" + timestamp);
		logger.info("cadata=" + cadata);
		logger.info("capwd=" + capwd);
		
		boolean result = false;
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			
			byte [] cabuf = new sun.misc.BASE64Decoder().decodeBuffer(cadata);
			ByteArrayInputStream cadatastream= new ByteArrayInputStream(cabuf);
			keyStore.load(cadatastream, capwd.toCharArray());
			Enumeration<String> aliases = keyStore.aliases();
			
			if (!aliases.hasMoreElements()) {
				throw new RuntimeException("no alias found");
			}
			String alias = aliases.nextElement();
			//PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, capwd.toCharArray());//私钥
			X509Certificate userCert = (X509Certificate) keyStore.getCertificate(alias);// X509Certificate证书对象
			
			//ISign sign = new Sign();
			IVerify verify = new Verify();
			
			result = verify.verify(data, timestamp, encodedEncryptedStr, userCert);
		} catch (Exception e) {
			throw new RuntimeException("校验密文失败："+e);
		}
		return result;
	}
	
	/**
	 * 获取校验串
	 * @param data
	 * @param cadata
	 * @param capwd
	 * @return
	 */
	public static String getEncodedEncryptedStr (byte [] data, String cadata, String capwd) {
		
		logger.info("dataHex=" + HexUtils.BinaryToHexString(data));
		logger.info("cadata=" + cadata);
		logger.info("capwd=" + capwd);
		
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			byte [] cabuf = new sun.misc.BASE64Decoder().decodeBuffer(cadata);
			ByteArrayInputStream cadatastream= new ByteArrayInputStream(cabuf);
			keyStore.load(cadatastream, capwd.toCharArray());
			Enumeration<String> aliases = keyStore.aliases();
			
			if (!aliases.hasMoreElements()) {
				throw new RuntimeException("no alias found");
			}
			String alias = aliases.nextElement();
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, capwd.toCharArray());//私钥
			//X509Certificate userCert = (X509Certificate) keyStore.getCertificate(alias);// X509Certificate证书对象
			ISign sign = new Sign();
			String encodedEncryptedStr = sign.sign(data, privateKey);
			logger.info("encodedEncryptedStr="+encodedEncryptedStr);
			return encodedEncryptedStr;
		}catch (Exception e) {
			throw new RuntimeException("校验密文失败："+e);
		}
	}
}
