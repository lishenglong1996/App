package cn.appsys.dao.AppVersion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

/**
 * APP版本信息
 * @author Administrator
 *
 */
public interface AppversionMapper {
	/**
	 * 根据app编号查询版本信息
	 * @return
	 */
	public List<AppVersion> getListByAppInfoId(@Param("appId") String appId);
	/**
	 * 新增版本信息
	 * @return
	 */
	public int addAppver(AppVersion appVersion);
	/**
	 * 根据版本id查询信息
	 * @return
	 */
	public AppVersion getAppversionById(@Param("id") String id);
	/**
	 * 修改版本信息
	 * @return
	 */
	public int updateAppversion(AppVersion appVersion);
	/**
	 * 修改版本图片为空
	 * @param id
	 * @return
	 */
	public int deleteImage(@Param("id") String id);
	/**
	 * 根据appId删除版本信息
	 * @param appId
	 * @return
	 */
	public int deleteAppversion(@Param("appId") String appId);
}
