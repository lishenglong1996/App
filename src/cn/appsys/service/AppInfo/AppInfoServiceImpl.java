package cn.appsys.service.AppInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.AppInfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper appInfoMapper;
	public AppInfoMapper getAppInfoMapper() {
		return appInfoMapper;
	}
	public void setAppInfoMapper(AppInfoMapper appInfoMapper) {
		this.appInfoMapper = appInfoMapper;
	}


	@Override
	public List<AppInfo> getList(String softwareName, String status, String flatform, String categoryLevel1,
			String categoryLevel2, String categoryLevel3, Integer pageIndex, Integer pageSize) {
		return appInfoMapper.getList(softwareName, status, flatform, categoryLevel1, categoryLevel2, categoryLevel3, pageIndex, pageSize);
	}
	@Override
	public int getCount(String softwareName, String status, String flatform, String categoryLevel1,
			String categoryLevel2, String categoryLevel3) {
		System.out.println("service"+categoryLevel1);
		return appInfoMapper.getCount(softwareName, status, flatform, categoryLevel1, categoryLevel2, categoryLevel3);
	}
	@Override
	public int checkAPKName(String apkName) {
		// TODO Auto-generated method stub
		return appInfoMapper.checkAPKName(apkName);
	}
	@Override
	public int addApp(AppInfo appInfo) {
		System.out.println("service"+appInfo.getLogoLocPath());
		
		return appInfoMapper.addApp(appInfo);
	}
	@Override
	public AppInfo getAppByID(String appId) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppByID(appId);
	}
	@Override
	public int updateFile(String appId) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateFile(appId);
	}
	@Override
	public int updateAppInfo(AppInfo appInfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateAppInfo(appInfo);
	}
	@Override
	public int deleteApp(String appId) {
		// TODO Auto-generated method stub
		return appInfoMapper.deleteApp(appId);
	}

}
