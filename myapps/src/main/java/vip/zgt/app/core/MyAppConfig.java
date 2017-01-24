package vip.zgt.app.core;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;

import vip.zgt.app.test.HelloController;

public class MyAppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	@Override
	public void configHandler(Handlers handler) {
		
	}

	@Override
	public void configInterceptor(Interceptors interceptor) {
		
	}

	@Override
	public void configPlugin(Plugins plugin) {
		
	}

	@Override
	public void configRoute(Routes route) {
		route.add("/hello", HelloController.class);
	}

}
