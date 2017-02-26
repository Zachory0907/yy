package vip.zgt.app.core;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import vip.zgt.app.util.Consts;

public class MyAppConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		PropKit.use("db.properties");
		me.setEncoding(Consts.DEFAULT_ENCODING);
		me.setViewType(ViewType.VELOCITY);
		me.setVelocityViewExtension("html");
	}

	@Override
	public void configHandler(Handlers handler) {
		
	}

	@Override
	public void configInterceptor(Interceptors interceptor) {
		
	}

	@Override
	public void configPlugin(Plugins plugin) {
		//jfinal的数据库连接是以插件的形式，所以
		//1、创建实现IDataSourceProvider借口的对象
		C3p0Plugin c3p0Plugin_YY = new C3p0Plugin(PropKit.get("jdbcUrl_YY"),PropKit.get("user_YY"),PropKit.get("password_YY"), PropKit.get("driverClass_YY"));
		C3p0Plugin c3p0Plugin_TEST = new C3p0Plugin(PropKit.get("jdbcUrl_TEST"),PropKit.get("user_TEST"),PropKit.get("password_TEST"), PropKit.get("driverClass_TEST"));
		//2、ActiveRecordPlugin
		ActiveRecordPlugin arp_YY = new ActiveRecordPlugin(Consts.ARP_NAME_YY, c3p0Plugin_YY);
		ActiveRecordPlugin arp_TEST = new ActiveRecordPlugin(Consts.ARP_NAME_TEST, c3p0Plugin_TEST);
		arp_YY.setShowSql(true);
		arp_TEST.setShowSql(false);
        arp_YY.setDialect(new MysqlDialect());
        arp_TEST.setDialect(new MysqlDialect());
        c3p0Plugin_YY.start();
        c3p0Plugin_TEST.start();
        arp_YY.start();
        arp_TEST.start();
        plugin.add(arp_YY);
        plugin.add(arp_TEST);
	}

	@Override
	public void configRoute(Routes route) {
//		route.add("/hello", HelloController.class);
		route.add(new AutoBindRoutes());
	}

}
