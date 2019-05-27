package cn.appsys.service.AppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

/**
 * APP信息
 * @author Administrator
 *
 */
public interface AppInfoService {
	/**
	 * 模糊查询
	 * @param softwareName  软件名
	 * @param status  APP状态
	 * @param flatform 所属平台
	 * @param categoryLevel1 一级分类
	 * @param categoryLevel2 二级分类
	 * @param categoryLevel3 三级分类
	 * @param pageIndex 当前页码
	 * @param pageSize 页面大小
	 * @return 
	 */
	public List<AppInfo> getList(@Param("softwareName")String softwareName,
			@Param("status")String status,
			@Param("flatform")String flatform,
			@Param("categoryLevel1")String categoryLevel1,
			@Param("categoryLevel2")String categoryLevel2,
			@Param("categoryLevel3")String categoryLevel3,
			@Param("pageIndex")Integer pageIndex,
			@Param("pageSize")Integer pageSize
	);
	

	/**
	 * 模糊查询数量
	 * @param softwareName  软件名
	 * @param status  APP状态
	 * @param flatform 所属平台
	 * @param categoryLevel1 一级分类
	 * @param categoryLevel2 二级分类
	 * @param categoryLevel3 三级分类
	 * @return 
	 */
	public int getCount(@Param("softwareName")String softwareName,
			@Param("status")String status,
			@Param("flatform")String flatform,
			@Param("categoryLevel1")String categoryLevel1,
			@Param("categoryLevel2")String categoryLevel2,
			@Param("categoryLevel3")String categoryLevel3
	);
	/**
	 * 根据AKP名称查询应用是否存在
	 * @param apkName
	 * @return
	 */
	public int checkAPKName(@Param("apkName")String apkName);
	
	/**
	 * 新增APP信息
	 * @param appInfo
	 * @return
	 */
	public int addApp(AppInfo appInfo);
	/**
	 * 根据id查询信息
	 * @return
	 */
	public AppInfo getAppByID(@Param("appId") String appId);
	/**
	 * 根据id修改图片信息
	 * @param appId
	 * @return
	 */
	public int updateFile(@Param("appID") String appId);
	/**
	 * 修改APP信息
	 * @return
	 */
	public int updateAppInfo(AppInfo appInfo);
	/**
	 * 删除app
	 * @param appId
	 * @return
	 */
	public int deleteApp(@Param("appId") String appId);
}
