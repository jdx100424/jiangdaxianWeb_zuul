package com.jiangdaxian.simple.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

@Component
public class SimpleFallback implements ZuulFallbackProvider {

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {
			@Override
			public InputStream getBody() throws IOException {
				JSONObject r = new JSONObject();
				r.put("code", "500");
				r.put("msg", "system error");
				return new ByteArrayInputStream(r.toJSONString().getBytes("UTF-8"));
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				return headers;
			}

			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return HttpStatus.OK.value();
			}

			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.OK.getReasonPhrase();
			}

			@Override
			public void close() {
			}
		};
	}

	@Override
	public String getRoute() {
		return "*";
	}
}
