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

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppInfo.AppInfoService;
import cn.appsys.service.appversion.AppversionService;

@RequestMapping("/appver")
@Controller
public class AppversionController {
	@Resource
	private AppversionService appversionService;  //版本对象
	
	/**
	 * 信息业务逻辑对象
	 */
	@Resource
	private AppInfoService appInfoService;
	
	/**
	 * 跳转至新增版本信息页面
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String showadd(Model model,@RequestParam String id){
		//根据appid查询版本信息
		System.out.println("查询版本信息");
		List<AppVersion> appverList=new ArrayList<AppVersion>();
		appverList=appversionService.getListByAppInfoId(id);
		
		model.addAttribute("appVersionList", appverList);
		model.addAttribute("appid", id);
		return "developer/appversionadd";
	}
	/**
	 * 新增修改版本信息通用方法
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addAppver(HttpSession session,HttpServletRequest request,Model model,String pre,AppVersion appVersion,MultipartFile a_downloadLink){
		System.out.println("appid"+appVersion.getAppId());
		String apkPath=null;  //Logo照片路径
		
		String errorInfo=null;  //异常信息
		
		boolean flag=true;  //标记添加是否成功
		//获得项目运行目录下的statics中的uploadfiles文件夹
		/*String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");*/
		String path="E:\\Y2\\SSM\\AppInfoSystem\\WebContent\\statics\\uploadfiles";  //文件保存路径
		
		//遍历文件集合
		
		
		if(!a_downloadLink.isEmpty()){ //判断文件是否存在,存在时候执行操作
				String oldFileName=a_downloadLink.getOriginalFilename();  //获得原文件名
				String prefix=FilenameUtils.getExtension(oldFileName);  //获得原文件的后缀
				String nameFile=FilenameUtils.getBaseName(oldFileName); //获得原文件名
				
				int filesize=1024*1024*500;  //1MB=1024字节*1024,文件大小,单位为字节
				if (filesize<a_downloadLink.getSize()) {  //文件上传大小
					request.setAttribute(errorInfo, "上传大小不得超过500MB");
					flag=false;
				}else if (prefix.equalsIgnoreCase("doc")   //当原文件的后缀是否是以下类型
						||prefix.equalsIgnoreCase("docx")
						) {
					//原文件的格式正确,为文件生成随机名称,格式为当期时间+0到10000的随机数+原文件名
					String fileName=System.currentTimeMillis()+RandomUtils.nextInt(10000)+nameFile+".doc";
					//创建文件流
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();  //当文件目录不存在时新建文件夹
					}
					//保存写入文件
					try {
						a_downloadLink.transferTo(targetFile);  //写入文件
					} catch (Exception e) {
						request.setAttribute(errorInfo, "上传失败!");
						flag=false;
					}
					//写入成功,将路径保存到数据库中
					apkPath=fileName;
					
				}else {
					request.setAttribute(errorInfo, "上传文件格式不正确");
					flag=false;
				}
			}
			
		if (flag) {
			int uid=((DevUser)session.getAttribute("devUserSession")).getId(); //登录用户id
			
			//新增操作
			if ("add".equals(pre)) {
				appVersion.setApkFileName(apkPath); //文件路径
				appVersion.setDownloadLink("/AppInfoSystem/statics/uploadfiles/"+apkPath);//下载地址
				appVersion.setCreatedBy(uid);
				appVersion.setCreationDate(new Date());
				//保存图片成功,执行新增
				System.out.println("新增版本信息");
				if(appversionService.addAppver(appVersion)==1){
					//新增成功,将修改app信息的最新版本
					AppInfo appInfo=new AppInfo();
					appInfo.setId(appVersion.getAppId());  //修改的appid
					appInfo.setVersionId(appVersion.getId());  //最新的版本id
					//调用修改app信息方法
					
					appInfoService.updateAppInfo(appInfo); //修改app信息的最新版本id
					return "redirect:/app/getList";
				}
			}else if ("update".equals(pre)) {  //修改操作
				appVersion.setModifyBy(uid); //登录用户id
				appVersion.setModifyDate(new Date());
				if (apkPath!=null) {
					appVersion.setApkFileName(apkPath);
				}
				//执行修改操作
				if(appversionService.updateAppversion(appVersion)==1){
					return "redirect:/app/getList"; //修改成功
				}
			}
		}
		if ("add".equals(pre)) {
			//跳转回新增页面
			return "developer/appversionadd";
		}else{
			//失败,返回修改页面
			return "developer/appversionmodify";
		}
		
		
	}
	/**
	 * 跳转到修改版本信息
	 * @param model
	 * @param vid 版本信息id
	 * @param aid app信息id
	 * @return
	 */
	@RequestMapping("/showAppver")
	public String showUpdateAppver(Model model,String vid,String aid){
		//根据app编号查询历史版本
		List<AppVersion> appVersionList=new ArrayList<AppVersion>();
		appVersionList=appversionService.getListByAppInfoId(aid);
		//根据版本id查询版本信息
		AppVersion appVersion=appversionService.getAppversionById(vid);
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	
	/**
	 * 删除文件,将数据库中文件列改为空
	 * @return
	 */
	@RequestMapping("/deleteImage")
	@ResponseBody
	public Object deleteImage(String id){
		System.out.println("删除文件");
		if (appversionService.deleteImage(id)==1) {
			return true;
		}else {
			return false;
		}
	}
}
