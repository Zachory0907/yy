package vip.zgt.app.web.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.Record;

import vip.zgt.app.base.annotation.AuthAnnotation;

/**
 * 这是一个权限过滤的拦截器 结合jFinal的AOP和自己写的Annotation
 * 
 * @author Zachory
 */
public class AuthInceptor implements Interceptor {

	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Invocation inv) {
		String actionKey = inv.getActionKey();
		// 判断pub下面的不要权限
		if (actionKey.indexOf("/pub") >= 0) {
			inv.invoke();
		} else {
			Map<String, Object> umap = (Map<String, Object>) inv.getController().getSession().getAttribute("user");
			if (umap == null) {
				System.out.println("没登录");
				inv.getController().redirect("/pub/login");
				return;
			}
			List<Record> uauths = (List<Record>) umap.get("uauth");
			if (uauths == null) {
				System.out.println("此人无权限");
				inv.getController().redirect("/pub/login");
				return;
			}
			Method method = inv.getMethod();
			AuthAnnotation auth = method.getAnnotation(AuthAnnotation.class);
			if (auth == null) {
				System.out.println("此功能没设权限");
				inv.getController().redirect("/pub/login");
				return;
			}
			boolean isCheck = false;
			String[] auths = auth.value();
			for (int i = 0; i < uauths.size(); i++) {
				String uauth = uauths.get(i).getStr("AUTH");
				if (uauth.equals("FULL")) {
					// 如果这个人的权限是FULL的话，就直接通过
					isCheck = true;
					break;
				}
				for (String au : auths) {
					if (au.equals(uauth)) {
						isCheck = true;
						break;
					}
				}
			}
			if (isCheck) {
				System.out.println("权限通过");
				inv.invoke();
			} else {
				System.out.println("权限不足");
				inv.getController().redirect("/pub/login");
			}
		}
	}
}
