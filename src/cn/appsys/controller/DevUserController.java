package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUser.DevUserService;
/**
 * 开发者控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/dev")
public class DevUserController {
	
	@Resource
	private DevUserService devUserService;
	
	
	/**
	 * 跳转到登录方法
	 * @return
	 */
	@RequestMapping("/login.html")
	public String login(){
		return "devlogin";
	}
	/**
	 * 登录方法
	 * @return
	 */
	@RequestMapping("/doLogin.html")
	public String devLogin(DevUser devUser,Model model,HttpSession session){
		DevUser user=devUserService.login(devUser);
		if (user!=null) {
			session.setAttribute("devUserSession", user);
			return "/developer/main";
		}else {
			model.addAttribute("error", "账号或密码错误");
			return "devlogin";
		}
	}
	
	/**
	 * 注销方法
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("devUserSession");
		return "devlogin";
	}
	
}
