package cn.appsys.service.DevUser;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	/**
	 * 登录
	 * @return
	 */
	public DevUser login(DevUser devUser);
}
