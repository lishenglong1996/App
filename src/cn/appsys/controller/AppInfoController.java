package cn.appsys.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppInfo.AppInfoService;
import cn.appsys.service.DataDictionary.DataDictionaryService;
import cn.appsys.service.appCategory.AppCategoryService;
import cn.appsys.service.appversion.AppversionService;



/**
 * App控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app")
public class AppInfoController {
	/**
	 * 信息业务逻辑对象
	 */
	@Resource
	private AppInfoService appInfoService;
	/**
	 * 字典信息逻辑对象
	 */
	@Resource
	private DataDictionaryService dataDictionaryService;
	/**
	 * 分类信息业务逻辑
	 */
	@Resource
	private AppCategoryService appCategoryService;
	/**
	 * 版本信息
	 */
	@Resource
	private AppversionService appversionService; 

	/**
	 * 模糊查询并将一级分类查询
	 * @return
	 */
	@RequestMapping("/getList")
	public String getAppList(Model model,HttpSession session,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryStatus",required=false) String queryStatus,
			@RequestParam(value="queryFlatformId",required=false) String queryFlatformId,
			@RequestParam(value="queryCategoryLevel1",required=false) String queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String queryCategoryLevel3,
			@RequestParam(value="pageIndex",required=false) String pageIndex
		){
		if(pageIndex==null){
			pageIndex="1";
		}
		int currentPageNo=Integer.parseInt(pageIndex);  //当前页码
		
		System.out.println(queryCategoryLevel1);
		
		//查询总数
		int totalCount=appInfoService.getCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
		int pageSize=5;  //页面数量
		//根据总数获得页面数量
		int totalPageCount=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
		//控制页码首尾
		if(currentPageNo<1){
			currentPageNo=1;
		}
		if (currentPageNo>totalPageCount) {
			currentPageNo=totalPageCount;
		}
		
		
		
		//查询APP信息方法getList(null, null, null, null, null, null, (currentPageNo-1)*pageSize, pageSize);
		List<AppInfo> appInfoList=appInfoService.getList(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, (currentPageNo-1)*pageSize, pageSize);
		//查询APP状态
		List<DataDictionary> statusList=dataDictionaryService.getListByTypeName("APP_STATUS");
		//查询所属平台
		List<DataDictionary> flatFormList=dataDictionaryService.getListByTypeName("APP_FLATFORM");
		//查询一级分类
		List<AppCategory> categoryLevel1List=appCategoryService.getCategoryLevel1();
		//查询二级分类
		List<AppCategory> categoryLevel2List=appCategoryService.getCategoryLevel2();
		//查询三级分类
		List<AppCategory> categoryLevel3List=appCategoryService.getCategoryLevel3();
		
		session.setAttribute("appInfoList", appInfoList); //APP信息
		session.setAttribute("flatFormList", flatFormList);//APP属于平台
		session.setAttribute("categoryLevel1List", categoryLevel1List); //查询的一级分类
		session.setAttribute("categoryLevel2List", categoryLevel2List); //查询的二级分类
		session.setAttribute("categoryLevel3List", categoryLevel3List); //查询的三级分类
		
		model.addAttribute("currentPageNo", currentPageNo); //当前页码
		model.addAttribute("totalCount", totalCount); //总数量
		model.addAttribute("totalPageCount", totalPageCount);  //页面数量
		model.addAttribute("statusList", statusList); //APP状态
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1); //选择一级分类
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2); //选择二级分类
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3); //选择三级分类
		model.addAttribute("queryFlatformId", queryFlatformId); //所属平台
		model.addAttribute("querySoftwareName", querySoftwareName); //软件名称
		model.addAttribute("queryStatus", queryStatus); //状态
		return "/developer/appinfolist";
	}
	/**
	 * 跳转到新增页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/appversionadd",method=RequestMethod.GET)
	public String appAdd(){
		return "/developer/appinfoadd";
	}
	
	/**
	 * 新增修改通用控制器
	 * @return
	 */
	@RequestMapping(value="/appversion",method=RequestMethod.POST)
	public String appInfoAdd(HttpServletRequest request,HttpSession session,String rep,AppInfo appInfo,MultipartFile a_logoPicPath){
		System.out.println("新增修改通用"+rep);
		String logoPicPath=null;  //Logo照片路径
		
		String errorInfo=null;  //异常信息
		
		boolean flag=true;  //标记添加是否成功
		//获得项目运行目录下的statics中的uploadfiles文件夹
		/*String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");*/
		String path="E:\\Y2\\SSM\\AppInfoSystem\\WebContent\\statics\\uploadfiles";  //文件保存路径
		
		//遍历文件集合
		
		
		if(!a_logoPicPath.isEmpty()){ //判断文件是否存在,不存在时候执行操作
				String oldFileName=a_logoPicPath.getOriginalFilename();  //获得原文件名
				String prefix=FilenameUtils.getExtension(oldFileName);  //获得原文件的后缀
				String nameFile=FilenameUtils.getBaseName(oldFileName); //获得原文件名
				
				int filesize=500000;  //文件大小,单位为字节
				if (filesize<a_logoPicPath.getSize()) {  //文件上传大小
					request.setAttribute(errorInfo, "上传大小不得超过500KB");
					flag=false;
				}else if (prefix.equalsIgnoreCase("jpg")   //当原文件的后缀是否是以下类型
						||prefix.equalsIgnoreCase("png")
						||prefix.equalsIgnoreCase("jpeg")
						||prefix.equalsIgnoreCase("pneg")) {
					//原文件的格式正确,为图片生成随机名称,格式为当期时间+0到10000的随机数+原文件名
					String fileName=System.currentTimeMillis()+RandomUtils.nextInt(10000)+nameFile+".jpg";
					//创建文件流
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();  //当文件目录不存在时新建文件夹
					}
					//保存写入文件
					try {
						a_logoPicPath.transferTo(targetFile);  //写入文件
					} catch (Exception e) {
						request.setAttribute(errorInfo, "上传失败!");
						flag=false;
					}
					//写入成功,将路径保存到数据库中
					logoPicPath=fileName;
					
				}else {
					request.setAttribute(errorInfo, "上传图片格式不正确");
					flag=false;
				}
			}
			
		if (flag) {
			//保存图片成功,执行新增或者修改
			if("add".equals(rep)){
				appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());  //创建者
				appInfo.setCreationDate(new Date()); //创建时间
				appInfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());
				appInfo.setLogoPicPath(logoPicPath); //图片本地路径
				if (appInfoService.addApp(appInfo)==1) {
					//新增成功
					return "redirect:/app/getList";
				}
			}else if ("update".equals(rep)) {
				appInfo.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
				appInfo.setModifyDate(new Date()); //修改时间
				appInfo.setLogoPicPath(logoPicPath);
				if (appInfoService.updateAppInfo(appInfo)==1) {
					//修改成功
					return "redirect:/app/getList";
				}
			}
			
		}
		if("add".equals(rep)){
			return "appinfoadd";  //新增失败返回新增页面
		}else {
			return "appinfomodify";  //修改失败返回修改页面
		}
		
	}
	
	/**
	 * 检查软件名称是否存在
	 * @return
	 */
	@RequestMapping("/checkAPKName")
	@ResponseBody
	public Object checkAPKName(@RequestParam String APKName){
		//调用检查方法
		if (appInfoService.checkAPKName(APKName)>0) {
			//存在
			return false;
		}else {
			return true;
		}
		
	}
	/**
	 * 根据id查询信息
	 * @param pre 执行的操作
	 * @param id 
	 * @return
	 */
	@RequestMapping("/getAppById")
	public String getAppById(Model model,@RequestParam String pre,@RequestParam String id){
		System.out.println("根据id查询信息"+pre);
		AppInfo appInfo=appInfoService.getAppByID(id);
		model.addAttribute("appInfo", appInfo);
		if("update".equals(pre)){
			//跳转到修改页面
			return "developer/appinfomodify";
		}else if ("view".equals(pre)) {
			//查询app的版本信息
			List<AppVersion> appverList=new ArrayList<AppVersion>();
			appverList=appversionService.getListByAppInfoId(id);
			
			model.addAttribute("appVersionList", appverList);
			//跳转到查看页面
			return "developer/appinfoview";
		}else {
			return "redirect:/app/getList";
		}
	}
	/**
	 * 修改图片路径
	 * @return
	 */
	@RequestMapping("/updateFile")
	@ResponseBody
	public Object updateImage(@RequestParam String appId){
		if(appInfoService.updateFile(appId)==1){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 删除APP
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object deleteAppInfo(String appId){
		if(appInfoService.deleteApp(appId)==1){
			appversionService.deleteAppversion(appId);// 根据appID删除版本信息
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 应用的上下架
	 * @param appId
	 * @return
	 */
	@RequestMapping("/appSale")
	@ResponseBody
	public Object appSale(String appfoId){
		System.out.println("上下架+++++++++");
		//根据id查询当前app的信息
		AppInfo app=appInfoService.getAppByID(appfoId);
		
		int status=app.getStatus(); //获得app状态
		if(status==4){ //
			app.setStatus(5); //将上架状态改为下架状态
			app.setOffSaleDate(new Date()); //下架时间
		}else if (status==5) {
			app.setStatus(4);  //将下架状态改为上架状态
			app.setOnSaleDate(new Date()); //上架时间
		}else {
			;
		}
		if (appInfoService.updateAppInfo(app)==1) {
			//修改成功
			return true;
		}else {
			return false;  //修改失败
		}
		
	}
}
