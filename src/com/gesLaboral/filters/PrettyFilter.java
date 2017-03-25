package com.gesLaboral.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter(filterName = "PrettyFilter", urlPatterns = { "*.cifp" })
public class PrettyFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @throws java.io.IOException
	 * @throws javax.servlet.ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String reqURL = req.getRequestURL().toString();
		if (reqURL != null && reqURL.toLowerCase().contains("login") && req.getUserPrincipal() != null) {
			res.sendRedirect(req.getContextPath() + "/");
		} else if (req.getUserPrincipal() == null && !reqURL.toLowerCase().contains("login")) {
			res.sendRedirect(req.getContextPath() + "/");
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
