package com.shine.game.bean;

import java.util.Map;

/**
 *
 */
public class UpLoadImg {
	private int imgId; // 文件编号
	private String imgName; // 文件名称
	private String imgSrc; // 文件路径
	private String imgType; // 文件类型

	public UpLoadImg() {
	}

	public UpLoadImg(int imgId, String imgName, String imgSrc, String imgType) {
		super();
		this.imgId = imgId;
		this.imgName = imgName;
		this.imgSrc = imgSrc;
		this.imgType = imgType;
	}

	public UpLoadImg(Map<String, Object> map) {
		this.imgId = (int) map.get("imgId");
		this.imgName = (String) map.get("imgName");
		this.imgSrc = (String) map.get("imgSrc");
		this.imgType = (String) map.get("imgType");

	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	@Override
	public String toString() {
		return "UpLoadImg [imgId=" + imgId + ", imgName=" + imgName + ", imgSrc=" + imgSrc + ", imgType=" + imgType
				+ "]";
	}

}
