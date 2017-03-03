package vip.zgt.app.web.interceptor;

import java.lang.reflect.Method;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import vip.zgt.app.base.annotation.AuthAnnotation;

/**
 * 这是一个权限过滤的拦截器
 * 结合jFinal的AOP和自己写的Annotation
 * 
 * @author Zachory
 */
public class AuthInceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		String actionKey = inv.getActionKey();
		String username = (String) inv.getController().getSession().getAttribute("username");
		if (actionKey.length()>=4 && actionKey.substring(0, 4).equals("/pub")) {
			inv.invoke();
		} else {
			Method method = inv.getMethod();
			boolean isCheck = false; 
			AuthAnnotation auth = method.getAnnotation(AuthAnnotation.class);
			if (auth != null) {
				String[] auths = auth.value();
				for (String au : auths) {
					if (au.equals(username)) {
						isCheck = true;
						break;
					}
				}
				if(isCheck) {
					System.out.println("权限通过");
					inv.invoke();
				} else {
					System.out.println("权限不足");
					inv.getController().redirect("/pub/login");
				}
			} else {
				System.out.println("没加权限");
				inv.getController().redirect("/pub/login");
			}
		}
	}

}
