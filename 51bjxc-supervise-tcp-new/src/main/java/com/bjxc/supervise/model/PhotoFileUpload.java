package com.bjxc.supervise.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PhotoFileUpload extends TransportObject {
	private String photoNum;
	private File file;
	private byte[] fileBytes;
	
	public PhotoFileUpload(){}
	
	public PhotoFileUpload(byte[] bytes){
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);
		byte[] photoNumBytes = new byte[10];
		hbuf.readBytes(photoNumBytes);
		fileBytes = new byte[bytes.length-10];
		hbuf.readBytes(fileBytes);
		try {
			photoNum= new String(photoNumBytes,"GBK");
			file=operaFileData(file,fileBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getBytes() {
		ByteBuf buffer = Unpooled.buffer();
		byte[] result=null;
		try {
			buffer.writeBytes(photoNum.getBytes("GBK"));
			buffer.writeBytes(getContent(file));
			result = new byte[buffer.readableBytes()];
			buffer.readBytes(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public File operaFileData(File file, byte[] by){
		FileOutputStream fileout = null;
		file=new File(photoNum+".jpg");
		if(file.exists()){
			file = new File(new Date().getTime()+".jpg");
		}
		try{
			fileout = new FileOutputStream(file);
			fileout.write(by, 0, by.length);

		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally{
			try{
				fileout.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		return file;
	}
	
	
	/**
	 * fileת�ֽ���
	 */
	public byte[] getContent(File file) throws IOException {  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            System.out.println("file too big...");  
            return null;  
        }  
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead = 0;  
        while (offset < buffer.length  
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        // ȷ���������ݾ�����ȡ  
        if (offset != buffer.length) {  
        	throw new IOException("Could not completely read file " + file.getName());  
        }  
        fi.close();  
        return buffer;  
    } 
	
	public String getPhotoNum() {
		return photoNum;
	}
	
	public void setPhotoNum(String photoNum) {
		this.photoNum = photoNum;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
}
