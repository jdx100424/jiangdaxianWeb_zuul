package com.jiangdaxian.simple.zuulfilter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class SimpleFilter extends ZuulFilter {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleFilter.class);

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		LOG.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

		// 获取传来的参数accessToken
		Object accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			LOG.warn("access token is empty");
			// 过滤该请求，不往下级服务去转发请求，到此结束
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("{\"result\":\"accessToken is empty!\"}");
			System.out.println(false);
			return null;
		}
		// 如果有token，则进行路由转发
		LOG.info("access token ok");
		System.out.println(true);
		// 这里return的值没有意义，zuul框架没有使用该返回值
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// 是否执行该过滤器，true代表需要过滤
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ZuulFilter过滤器的filterType方法返回一个字符串代表过滤器的类型，如果return
	 * null就报上述错误。在zuul中定义了四种不同生命周期的过滤器类型，具体如下： pre：路由之前 routing：路由之时 post： 路由之后
	 * error：发送错误调用
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}
