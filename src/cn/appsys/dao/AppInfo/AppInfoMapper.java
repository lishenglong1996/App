package cn.appsys.dao.AppInfo;

import java.util.List;

import cn.appsys.pojo.AppInfo;

/**
 * app
 * @author Administrator
 *
 */
public interface AppInfoMapper {
	/**
	 * 模糊查询
	 * @return
	 */
	public List<AppInfo> getList();
	/**
	 * 添加方法
	 * @return
	 */
	public int add();
}
