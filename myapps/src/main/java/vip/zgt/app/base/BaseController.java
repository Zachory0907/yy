package vip.zgt.app.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;

import vip.zgt.app.util.Consts;

public class BaseController extends Controller {

	public UploadFile getFile(String param) {
		return super.getFile(param);
	}

	protected String getPostData() {
		InputStream is;
		try {
			is = getRequest().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Consts.DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getMultiPara(String field) {
		HttpServletRequest request = getRequest();
		MultipartRequest mreq = null;
		if (request instanceof MultipartRequest == false)
			mreq = new MultipartRequest(request);
		else
			mreq = (MultipartRequest) request;
		return mreq.getParameter(field);
	}
	
	public void renderok () {
		renderJson("{\"status\":\"ok\"}");
	}
	
	public void rendererror () {
		renderJson("{\"status\":\"error\"}");
	}
	
	public void rendersuccess () {
		renderJson("{\"status\":\"success\"}");
	}
	
	public void renderfatal () {
		renderJson("{\"status\":\"fatal\"}");
	}
}
