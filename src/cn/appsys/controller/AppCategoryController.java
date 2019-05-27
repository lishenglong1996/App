package cn.appsys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.service.appCategory.AppCategoryService;

/**
 * 分类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/AppCategory")
public class AppCategoryController {
	@Resource
	private AppCategoryService appCategoryService;
	
	/**
	 * 根据父节点查询
	 * @param pid
	 * @return
	 */
	@RequestMapping("/categorylevellist")
	@ResponseBody
	public List<AppCategory> categorylevellist(@RequestParam("pid") String pid){
		List<AppCategory> appCategoryList=new ArrayList<AppCategory>();
		//调用方法查询
		
		appCategoryList=appCategoryService.getAppCategoryByParent(pid);
		
		return appCategoryList;
	}
}
