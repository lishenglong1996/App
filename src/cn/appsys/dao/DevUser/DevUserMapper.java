package cn.appsys.dao.DevUser;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {
	/**
	 * 登录
	 * @return
	 */
	public DevUser login(DevUser devUser);
}
