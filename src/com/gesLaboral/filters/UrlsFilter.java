package com.gesLaboral.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter(filterName = "UrlsFilter", urlPatterns = { "/views/pages/*" })
public class UrlsFilter implements Filter {

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
		// check whether session variable is set
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String reqURL = req.getHeader("Referer");
		String reqURL2 = req.getRequestURL().toString();
		String key = "";
		Enumeration<String> headerNames = req.getHeaderNames();
		boolean continueLoop = true;

		if (headerNames != null) {
			while (headerNames.hasMoreElements() && continueLoop) {
				key = headerNames.nextElement();
				if (key.trim().equalsIgnoreCase("referer")) {
					reqURL = req.getHeader(key);
					if (reqURL != null && !reqURL.trim().isEmpty()) {
						continueLoop = false;
					}
				}
			}
		}
		if (reqURL != null && reqURL.toLowerCase().contains("views/pages")
				&& !reqURL.toLowerCase().contains("views/pages/redirect")
				&& !reqURL2.toLowerCase().contains("views/pages")) {
			if (reqURL.toLowerCase().contains("views/pages/accesoDenegado")) {
				res.sendRedirect(req.getContextPath() + "/views/pages/accesoDenegado.xhtml");
			} else {
				res.sendRedirect(req.getContextPath() + "/");
			}
		} else if (reqURL == null && reqURL2.toLowerCase().contains("views/pages")) {
			if (reqURL2.toLowerCase().contains("views/pages/accesoDenegado")) {
				res.sendRedirect(req.getContextPath() + "/views/pages/accesoDenegado.xhtml");
			} else {
				res.sendRedirect(req.getContextPath() + "/");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	@Override
	public void destroy() {
		// Nothing to do here!
	}

}
