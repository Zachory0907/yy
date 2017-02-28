package vip.zgt.app.web.controller;

import java.lang.reflect.Method;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.annotation.AuthAnnotation;
import vip.zgt.app.util.Consts;

@ControllerBind(controllerKey = "/", viewPath = Consts.VIEW_PATH + "/main")
public class IndexController extends Controller{

	@AuthAnnotation("yy")
	public void index(){
		checkUser(this.getClass());
		String name = Thread.currentThread() .getStackTrace()[1].getMethodName();
		System.out.println(name);
		List<Record> res1 = DbPro.use(Consts.ARP_NAME_YY).find("select * from test");
		System.out.println("res1:" + res1.toString());
		List<Record> res2 = DbPro.use(Consts.ARP_NAME_TEST).find("select * from test");
		System.out.println("res2:" + res2.toString());
		render("index.html");
	}

	private void checkUser(Class clazz) {
		// 反射其中的成员
		Method[] methods = clazz.getMethods();
		// 取得其注解
		for (Method m : methods) {
			/* 判断是否有AuthAnnotation这个注解
			 * if(m.isAnnotationPresent(AuthAnnotation.class)){
				m.invoke(clazz.newInstance(), null);
			}*/
			AuthAnnotation auth = m.getAnnotation(AuthAnnotation.class);
			if (auth != null) {
				System.out.print(m.getName() + ": ");
				String[] values = auth.value();
				for (String v : values) {
					System.out.print(v + " "); 
				}
				System.out.println();
			}
		}		
	}
}
