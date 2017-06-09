package vip.zgt.app.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;

public class Utils {
	
	private static final String SYSINFO_PROPERTIES = "/sysinfo.properties";
	public static String ROOT;

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
	
	public static String getAttachmentDir(String type) {
		String dir = ROOT + "/" + com.jfinal.core.Const.DEFAULT_BASE_DOWNLOAD_PATH;
		Properties sysInfo = null;
		try {
			sysInfo = getProperties(SYSINFO_PROPERTIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sysInfo != null && (sysInfo.get("attach") != null)) {
			dir = sysInfo.getProperty("attach");
		}
		dir = dir + "/" + type;
		File _dir = new File(dir);
		if (!_dir.exists()) {
			_dir.mkdirs();
		}
		return dir;
	}
	
}
