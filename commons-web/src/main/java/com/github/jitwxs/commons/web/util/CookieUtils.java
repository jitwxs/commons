package com.github.jitwxs.commons.web.util;

import com.github.jitwxs.commons.core.constant.HttpConstant;
import com.github.jitwxs.commons.core.constant.SymbolConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Cookie 工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CookieUtils {

    /**
     * 得到Cookie的值, 不编码
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值
     * @param isDecoder 是否编码，编码格式为UTF-8
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || StringUtils.isEmpty(cookieName)) {
            return null;
        }
        String retValue = null;
        try {
			for (Cookie cookie : cookieList) {
				if (cookie.getName().equals(cookieName)) {
					if (isDecoder) {
						retValue = URLDecoder.decode(cookie.getValue(), HttpConstant.Encoding.UTF8);
					} else {
						retValue = cookie.getValue();
					}
					break;
				}
			}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 得到Cookie的值
     * @param encodeString 编码格式
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || StringUtils.isEmpty(cookieName)) {
            return null;
        }
        String retValue = null;
        try {
			for (Cookie cookie : cookieList) {
				if (cookie.getName().equals(cookieName)) {
					retValue = URLDecoder.decode(cookie.getValue(), encodeString);
					break;
				}
			}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,但不编码
     * @param cookieMaxAge 过期时间
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, false);
    }

    /**
     * 设置Cookie的值 不设置生效时间,编码
     * @param isEncode 是否编码，编码格式为UTF-8
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,编码
     * @param cookieMaxAge 过期时间
     * @param isEncode 是否编码，编码格式为UTF-8
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码
     * @param cookieMaxAge 过期时间
     * @param encodeString 编码格式
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, encodeString);
    }

    /**
     * 删除Cookie
     */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		doSetCookie(request, response, cookieName, StringUtils.EMPTY, -1, false);
	}

	private static void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
									String cookieValue, int cookieMaxage, boolean isEncode) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else if (isEncode) {
				cookieValue = URLEncoder.encode(cookieValue, HttpConstant.Encoding.UTF8);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0) {
				cookie.setMaxAge(cookieMaxage);
			}
			if (null != request) {
				String domainName = getDomainName(request);
				if (!HttpConstant.LOCAL_HOST.equals(domainName)) {
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath(SymbolConstant.SLASH);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		try {
			if (cookieValue == null) {
				cookieValue = StringUtils.EMPTY;
			} else {
				cookieValue = URLEncoder.encode(cookieValue, encodeString);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0) {
				cookie.setMaxAge(cookieMaxage);
			}
			if (null != request) {
				String domainName = getDomainName(request);
				if (!HttpConstant.LOCAL_HOST.equals(domainName)) {
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath(SymbolConstant.SLASH);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 得到cookie的域名（跨域访问）
     */
	private static String getDomainName(HttpServletRequest request) {
		String domainName = null;

		String serverName = request.getRequestURL().toString();
		if (StringUtils.isEmpty(serverName)) {
			domainName = "";
		} else {
			serverName = serverName.toLowerCase();
			serverName = serverName.substring(7);
			final int end = serverName.indexOf("/");
			serverName = serverName.substring(0, end);
			final String[] domains = serverName.split("\\.");
			int len = domains.length;
			if (len > 3) {
				// www.xxx.com.cn
				domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
			} else if (len > 1) {
				// xxx.com or xxx.cn
				domainName = "." + domains[len - 2] + "." + domains[len - 1];
			} else {
				domainName = serverName;
			}
		}

		if (domainName.indexOf(":") > 0) {
			String[] ary = domainName.split("\\:");
			domainName = ary[0];
		}
		return domainName;
	}
}