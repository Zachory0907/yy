package vip.zgt.app.core.plugin;

import com.jfinal.plugin.IPlugin;

import vip.zgt.app.util.Sysinfo;

public class SysinfoPlugin implements IPlugin {

	
	@Override
	public boolean start() {
		String sysinfo = Sysinfo.buildSysInfoString();
		System.out.println(sysinfo);
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
