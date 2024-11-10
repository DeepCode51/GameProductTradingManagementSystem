package com.shine.game.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shine.game.bean.UpLoadImg;
import com.shine.game.dao.UpLoadImgDao;
import com.shine.game.util.DbUtil;

public class UpLoadImgDaoImpl implements UpLoadImgDao {
	/**
	 * 增加饰品图片
	 */
	@Override
	public boolean imgAdd(UpLoadImg upLoadImg) {
		String sql="insert into uploadimg(imgName,imgSrc,imgType) values(?,?,?)";
		int i=DbUtil.excuteUpdate(sql, upLoadImg.getImgName(),upLoadImg.getImgSrc(),upLoadImg.getImgType());
		return i>0?true:false;
	}
	/**
	 * 获取饰品图片id
	 */

	@Override
	public Integer findIdByImgName(String imgName) {
		Integer id=0;
		String sql = "select imgId from uploadimg where imgName=?";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql, imgName);
		if(list.size()>0){
			id=(Integer) list.get(0).get("imgId");
		}
		return id;

	}
	/**
	 * 更新图片信息
	 */
	@Override
	public boolean imgUpdate(UpLoadImg upImg) {
		String sql = "update uploadimg set imgName=? , imgSrc=? , imgType=? where imgId=?";
		int i = DbUtil.excuteUpdate(sql, upImg.getImgName(),upImg.getImgSrc(),upImg.getImgType(),upImg.getImgId());
		return i>0?true:false;
	}
	/**
	 * 通过id删除图片
	 */
	@Override
	public boolean imgDelById(int imgId) {
		String sql="delete from uploadimg where imgId=?";
		int i = DbUtil.excuteUpdate(sql, imgId);
		return i>0?true:false;
	}
	//根据ids查询图片
	@Override
	public List<UpLoadImg> findImgByIds(String imgIds) {
		List<UpLoadImg> luli=new ArrayList<>();

		String sql="select * from uploadimg where imgId in("+imgIds+")";
		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		if(list.size()>0) {
			for(Map<String,Object> map:list) {
				UpLoadImg uli=new UpLoadImg(map);
				luli.add(uli);
			}
		}
		return luli;
	}
	//批量删除
	@Override
	public boolean imgBatDelById(String imgIds) {
		String sql="delete from uploadimg where imgId in("+imgIds+")";
		int i=DbUtil.excuteUpdate(sql);
		return i>0?true:false;
	}

}
