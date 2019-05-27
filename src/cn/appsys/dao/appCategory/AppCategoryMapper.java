package cn.appsys.dao.appCategory;

import java.util.List;



import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	/**
	 * 查询一级分类
	 * @return
	 */
	public List<AppCategory> getCategoryLevel1();
	
	/**
	 * 查询二级分类
	 * @return
	 */
	public List<AppCategory> getCategoryLevel2();
	/**
	 * 查询三级分类
	 * @return
	 */
	public List<AppCategory> getCategoryLevel3();
	
	/**
	 * 根据父节点查询信息
	 * @return
	 */
	public List<AppCategory> getAppCategoryByParent(@Param("parentId") String parentId);
	
	
}
