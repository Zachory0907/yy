package com.zgt.digital.util;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;

public class Utils {

	public static Properties getProperties(String cfgPath) throws Exception{
		Properties properties = new Properties();
		InputStream is = Utils.class.getResourceAsStream(cfgPath);
		try{
			properties.load(is);
			is.close();
			return properties;
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	public static boolean isLogin(Controller controller) {
		return null != controller.getSession(true).getAttribute(
				Consts.SESSION_TOKEN_KEY)
				&& !"".equals(getUserName(controller));
	}

	public static void setLogin(Controller controller, String user, int id) {
		System.out.println(controller);
		HttpSession session = controller.getSession(true);
		session.setAttribute(Consts.SESSION_TOKEN_NAME, user);
		session.setAttribute(Consts.SESSION_TOKEN_KEY, id);
	}

	public static void setLogout(Controller controller) {
		controller.getSession(true).removeAttribute(Consts.SESSION_TOKEN_KEY);
		controller.getSession(true).removeAttribute(Consts.SESSION_TOKEN_NAME);
	}
	
	public static String getUserName(Controller controller) {
		Object user = controller.getSession(true).getAttribute(
				Consts.SESSION_TOKEN_NAME);
		return user == null ? "" : (String) user;
	}

	public static int getUserId(Controller controller) {
		Object id = controller.getSession(true).getAttribute(
				Consts.SESSION_TOKEN_KEY);
		return id == null ? -1 : (Integer) id;
	}
}
