package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;



/**
 * 登录拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//执行完毕,返回拦截前
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//在处理过程中进行拦截
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		
		HttpSession session = request.getSession();  //创建session对象
		DevUser devUser=(DevUser)session.getAttribute("devUserSession"); //前台登录
		BackendUser backendUser=(BackendUser)session.getAttribute(""); //后台登录对象
		if(devUser==null||backendUser==null){  
			//前台未登录时进行拦截并返回登录页面
			System.out.println(request.getContextPath());
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			
			return false;
		}
		return true;
	}

}
