package vip.zgt.app.web.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class TestInceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		System.out.println("====================Test====================");
		System.out.println("ActionKey: " + inv.getActionKey()); 
		System.out.println("ControllerKey: " + inv.getControllerKey()); 
		System.out.println("MethodName： " + inv.getMethodName()); 
		System.out.println("getViewPath(): " + inv.getViewPath()); 
		System.out.println("Controller: " + inv.getController()); 
		System.out.println("Method: " + inv.getMethod()); 
		System.out.println("ReturnValue: " + inv.getReturnValue()); 
		System.out.println("Target: " + inv.getTarget()); 
		System.out.println("isActionInvocation: " + inv.isActionInvocation()); 
		System.out.println("===================================================");
		inv.invoke();  
	}

}
