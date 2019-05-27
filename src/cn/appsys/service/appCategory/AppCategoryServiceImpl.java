package cn.appsys.service.appCategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appCategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {
	@Resource
	private AppCategoryMapper appCategory;
	public AppCategoryMapper getAppCategory() {
		return appCategory;
	}
	public void setAppCategory(AppCategoryMapper appCategory) {
		this.appCategory = appCategory;
	}

	@Override
	public List<AppCategory> getCategoryLevel1() {
		return appCategory.getCategoryLevel1();
	}

	@Override
	public List<AppCategory> getAppCategoryByParent(String parentId) {
		// TODO Auto-generated method stub
		return appCategory.getAppCategoryByParent(parentId);
	}
	@Override
	public List<AppCategory> getCategoryLevel2() {
		// TODO Auto-generated method stub
		return appCategory.getCategoryLevel2();
	}
	@Override
	public List<AppCategory> getCategoryLevel3() {
		// TODO Auto-generated method stub
		return appCategory.getCategoryLevel3();
	}

}
