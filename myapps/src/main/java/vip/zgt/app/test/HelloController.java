package vip.zgt.app.test;

import com.jfinal.core.Controller;

public class HelloController extends Controller{

	public void index (){
		System.out.println("hello!");
		renderText("Hello World!");
	}
}
