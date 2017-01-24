package com.zgt.digital.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.zgt.digital.util.Consts;

/**
 * 每一次请求的时候都会将编码进行过滤
 * @author ZGT
 *
 */
public class EncodingFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding(Consts.DEFAULT_ENCODING);
		resp.setCharacterEncoding(Consts.DEFAULT_ENCODING);
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
