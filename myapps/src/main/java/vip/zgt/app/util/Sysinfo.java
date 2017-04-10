package vip.zgt.app.util;

import javax.servlet.http.HttpServletRequest;

public class Sysinfo {

	public static void buildInfoString(StringBuilder sb, String key, String value) {
		sb.append(String.format(" [%s] %s", key, value)).append("\n");
	}
	
	public static void buildInfoString(StringBuilder sb, String key) {
		sb.append(String.format(" [%s] %s", key, System.getProperty(key))).append("\n");
	}
	
	public static void buildLine(StringBuilder sb) {
		sb.append("***************************************").append("\n");
	}
	
	public static String RootUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
		return url;
	}
	
	public static String buildSysInfoString() {
		StringBuilder sb = new StringBuilder();
		buildLine(sb);
		buildInfoString(sb, "os.name");
		buildInfoString(sb, "os.arch");
		buildInfoString(sb, "os.version");
		buildInfoString(sb, "file.separator");
		buildInfoString(sb, "path.separator");
		// buildInfoString(sb, "line.separator");
		buildInfoString(sb, "user.name");
		buildInfoString(sb, "user.home");
		buildInfoString(sb, "user.dir");
		buildLine(sb);
		buildInfoString(sb, "java.version");
		buildInfoString(sb, "java.vendor");
		buildInfoString(sb, "java.vendor.url");
		buildInfoString(sb, "java.home");
		buildInfoString(sb, "java.vm.specification.version");
		buildInfoString(sb, "java.vm.specification.vendor");
		buildInfoString(sb, "java.vm.specification.name");
		buildInfoString(sb, "java.vm.version");
		buildInfoString(sb, "java.vm.vendor");
		buildInfoString(sb, "java.vm.name");
		buildInfoString(sb, "java.specification.version");
		buildInfoString(sb, "java.specification.vendor");
		buildInfoString(sb, "java.specification.name");
		buildInfoString(sb, "java.class.version");
		buildInfoString(sb, "java.class.path");
		buildInfoString(sb, "java.library.path");
		buildInfoString(sb, "java.io.tmpdir");
		buildInfoString(sb, "java.compiler");
		buildInfoString(sb, "java.ext.dirs");
		buildLine(sb);
		return sb.toString();
	}
}
