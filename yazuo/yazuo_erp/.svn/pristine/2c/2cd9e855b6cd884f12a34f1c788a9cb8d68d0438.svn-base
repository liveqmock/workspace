package com.yazuo.erp.video.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.video.dao.VideoCatDao;
import com.yazuo.erp.video.service.VideoCatService;
import com.yazuo.erp.video.vo.VideoCat;

@Service
public class VideoCatServiceImpl implements VideoCatService{

	@Resource
	private VideoCatDao videoCatDao;

	@Override
	public VideoCat selectVideoCatByID(VideoCat VideoCat){
		return videoCatDao.selectVideoCatByID(VideoCat);
	}


	@Override
	public VideoCat selectVideoCatByObject(VideoCat videoCat){
		return videoCatDao.selectVideoCatByObject(videoCat);
	}


	@Override
	public List<VideoCat> selectVideoCatListByObject(VideoCat videoCat){
		return videoCatDao.selectVideoCatListByObject(videoCat);
	}


	@Override
	public Map<String, Object> selectListByPage(VideoCat videoCat,int page, int pageSize){
		return videoCatDao.selectListByPage(videoCat,page,pageSize);
	}


	@Override
	public int selectVideoCatCount(VideoCat videoCat){
		return videoCatDao.selectVideoCatCount(videoCat);
	}


	@Override
	public int addVideoCat(VideoCat videoCat){
		return videoCatDao.addVideoCat(videoCat);
	}


	@Override
	public int addVideoCatList(List<VideoCat>  videoCatlist){
		return videoCatDao.addVideoCatList(videoCatlist);
	}


	@Override
	public int removeVideoCatByObject(VideoCat videoCat){
		return videoCatDao.removeVideoCatByObject(videoCat);
	}


	@Override
	public int removeVideoCatList(List<VideoCat>  videoCatlist){
		return videoCatDao.removeVideoCatList(videoCatlist);
	}


	@Override
	public int updateVideoCatByID(VideoCat videoCat){
		return videoCatDao.updateVideoCatByID(videoCat);
	}


	@Override
	public int updateVideoCatByObject(VideoCat updateVideoCat , VideoCat whereVideoCat){
		return videoCatDao.updateVideoCatByObject( updateVideoCat ,  whereVideoCat);
	}


	@Override
	public int updateVideoCatList(List<VideoCat> videoCatlist){
		return videoCatDao.updateVideoCatList(videoCatlist);
	}


	@Override
	public Integer selectVideoCatSeqNextVal(){
		return videoCatDao.selectVideoCatSeqNextVal();
	}


}
