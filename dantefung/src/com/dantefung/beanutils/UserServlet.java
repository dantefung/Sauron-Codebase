/*package com.dantefung.beanutils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.faces.config.beans.ConverterBean;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.sun.org.apache.commons.beanutils.ConvertUtils;
import com.sun.org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import com.test.domain.User;
import com.test.servies.UserServies;

public class UserServlet extends HttpServlet {
//锟斤拷锟斤拷址锟斤拷锟�
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		System.out.println("ifwai");
		if(method!=null){
			if("login".equals(method)){
				Login(request, response);
			}else if("register".equals(method)){
				Register(request, response);
			}
		}
	}
	//锟斤拷录
	private void Login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		UserServies servies = new UserServies();
		boolean flag = servies.find(name, password);
		if(flag){
			request.setAttribute("message","锟斤拷录锟缴癸拷");
		}else{
			request.setAttribute("message","锟斤拷录失锟斤拷");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	public void Register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//要锟矫碉拷枚锟劫猴拷BeanUtil锟斤拷转锟斤拷锟斤拷
//		Enumeration<String> enums = request.getParameterNames();
		User user = new User();
		ConvertUtils.register(new DateLocaleConverter(Locale.getDefault(),"yyyy-MM-dd"),java.util.Date.class);
		while(enums.hasMoreElements()){
			String key = enums.nextElement();
//			String[] values = request.getParameterValues(key);
			String value = request.getParameter(key);
			try {
				BeanUtils.setProperty(user, key, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try
		{
			BeanUtils.populate(user, request.getParameterMap());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("servlet锟斤拷"+user.getName()+"+"+user.getPassword()+"+"+user.getBirthday());
		UserServies userServies = new UserServies();
		boolean flag = userServies.register(user);
		System.out.println("flag"+flag);
		if(flag){
			request.setAttribute("message","注锟斤拷晒锟�);
		}else{
			request.setAttribute("message","注锟斤拷失锟斤拷");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
		return;
	}
}

*/