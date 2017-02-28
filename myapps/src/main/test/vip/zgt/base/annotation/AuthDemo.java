package vip.zgt.base.annotation;

import vip.zgt.app.base.annotation.AuthAnnotation;

public class AuthDemo {

	@AuthAnnotation
	public void test1 () {
		System.out.println("test1");
	}
	
	@AuthAnnotation({"a","b"})
	public void test2 () {
		System.out.println("test2");
	}
	
	@AuthAnnotation({"a","b"})
	public void test2 (int a) {
		System.out.println("test2.a");
	}
}
