package vip.zgt.app.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jfinal.core.Controller;

import vip.zgt.app.util.Consts;

public class BaseController extends Controller {

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
}
