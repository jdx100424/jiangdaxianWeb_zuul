package com.jiangdaxian.simple.zuulfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		
		request.getParameterMap();
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>();
        }
        List<String> arrayList = new ArrayList<>();
        arrayList.add(UUID.randomUUID().toString());
        requestQueryParams.put("requestUniqueId", arrayList);
        ctx.setRequestQueryParams(requestQueryParams);

		
		Object accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			LOG.warn("access token is empty");
			// end
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("{\"result\":\"accessToken is empty!\"}");
			System.out.println(false);
			return null;
		}
		LOG.info("access token ok");
		System.out.println(true);
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
