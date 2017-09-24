package com.bjxc.model;

import java.util.List;

public class UploadPhoto {
	private PhotoInfo loginPhoto;
	private PhotoInfo logoutPhoto;
	private List<PhotoInfo> normalPhotoList;
	public PhotoInfo getLoginPhoto() {
		return loginPhoto;
	}
	public void setLoginPhoto(PhotoInfo loginPhoto) {
		this.loginPhoto = loginPhoto;
	}
	public PhotoInfo getLogoutPhoto() {
		return logoutPhoto;
	}
	public void setLogoutPhoto(PhotoInfo logoutPhoto) {
		this.logoutPhoto = logoutPhoto;
	}
	public List<PhotoInfo> getNormalPhotoList() {
		return normalPhotoList;
	}
	public void setNormalPhotoList(List<PhotoInfo> normalPhotoList) {
		this.normalPhotoList = normalPhotoList;
	}
	public PhotoInfo getPhotoInfoByNum(String photoNum) {
		if(loginPhoto!=null&&loginPhoto.getPhotoNum().equals(photoNum)){
			return loginPhoto;
		}
		if(logoutPhoto!=null&&logoutPhoto.getPhotoNum().equals(photoNum)){
			return logoutPhoto;
		}
		for (PhotoInfo photoInfo : normalPhotoList) {
			if(photoInfo.getPhotoNum().equals(photoNum)){
				return photoInfo;
			}
		}
		return null;
	}
	public boolean setPhotoInfo(PhotoInfo photo) {
		if(loginPhoto!=null&&loginPhoto.getPhotoNum().equals(photo.getPhotoNum())){
			loginPhoto = photo;
			return true;
		}
		if(logoutPhoto!=null&&logoutPhoto.getPhotoNum().equals(photo.getPhotoNum())){
			logoutPhoto = photo;
			return true;
		}
		for (int index=0;index<normalPhotoList.size();index++) {
			if(normalPhotoList.get(index).getPhotoNum().equals(photo.getPhotoNum())){
				normalPhotoList.remove(index);
				normalPhotoList.add(index, photo);
				return true;
			}
		}
		return false;
	}
}
