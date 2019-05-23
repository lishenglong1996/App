package cn.appsys.service.DevUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.DevUser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {
	@Resource
	public DevUserMapper devUserMapper;
	public DevUserMapper getDevUserMapper() {
		return devUserMapper;
	}
	public void setDevUserMapper(DevUserMapper devUserMapper) {
		this.devUserMapper = devUserMapper;
	}

	
	@Override
	public DevUser login(DevUser devUser) {
		
		return devUserMapper.login(devUser);
	}

}
