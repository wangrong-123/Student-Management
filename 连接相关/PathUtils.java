package com.rong.utils;

import javax.servlet.http.HttpServletRequest;

//返回统一的路径
public class PathUtils {
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		return basePath;
	}

}
