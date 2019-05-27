package cn.appsys.service.appversion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.AppVersion.AppversionMapper;
import cn.appsys.pojo.AppVersion;

@Service("appversionService")
public class AppversionServiceImpl implements AppversionService {
	@Resource
	private AppversionMapper appversionMapper;
	public AppversionMapper getAppversionMapper() {
		return appversionMapper;
	}
	public void setAppversionMapper(AppversionMapper appversionMapper) {
		this.appversionMapper = appversionMapper;
	}

	
	@Override
	public List<AppVersion> getListByAppInfoId(String appId) {
		// TODO Auto-generated method stub
		return appversionMapper.getListByAppInfoId(appId);
	}

	@Override
	public int addAppver(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return appversionMapper.addAppver(appVersion);
	}
	@Override
	public AppVersion getAppversionById(String id) {
		// TODO Auto-generated method stub
		return appversionMapper.getAppversionById(id);
	}
	@Override
	public int updateAppversion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return appversionMapper.updateAppversion(appVersion);
	}
	@Override
	public int deleteImage(String id) {
		// TODO Auto-generated method stub
		return appversionMapper.deleteImage(id);
	}
	@Override
	public int deleteAppversion(String appId) {
		// TODO Auto-generated method stub
		return appversionMapper.deleteAppversion(appId);
	}

}
