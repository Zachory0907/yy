package vip.zgt.base.annotation;

import java.lang.reflect.Method;

import vip.zgt.app.base.annotation.AuthAnnotation;

public class AuthTest {

	public static void main(String[] args) throws Exception {
		// 取得class的字节码
		Class clazz = AuthDemo.class;
		// 反射其中的成员
		Method[] methods = clazz.getMethods();
		// 取得其注解
		for (Method m : methods) {
			/* 判断是否有AuthAnnotation这个注解
			 * if(m.isAnnotationPresent(AuthAnnotation.class)){
				m.invoke(clazz.newInstance(), null);
			}*/
			System.out.print(m.getName() + ": ");
			AuthAnnotation auth = m.getAnnotation(AuthAnnotation.class);
			if (auth != null) {
				String[] values = auth.value();
				for (String v : values) {
					System.out.print(v + " "); 
				}
			}else{
				System.out.print("没注解"); 
			}
			System.out.println();
		}
	}
}
