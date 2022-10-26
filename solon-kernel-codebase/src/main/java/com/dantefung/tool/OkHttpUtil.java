package com.dantefung.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class OkHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    private static final OkHttpClient httpClient;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
    public static final String DEFAULT_CHARSET = "utf-8";
    private static final Map<String, String> DEFAULT_HEADERS = new HashMap(4);

    public OkHttpUtil() {
    }

    public static Response get(String url) throws IOException {
        return get(url, "utf-8", (Map)null, DEFAULT_HEADERS);
    }

    public static Response post(String url) throws IOException {
        return postForm(url, "utf-8", (Map)null, DEFAULT_HEADERS);
    }

    public static Response post(String url, Map<String, String> params) throws IOException {
        return postForm(url, "utf-8", params, DEFAULT_HEADERS);
    }

    public static Response postJson(String url, Object params) throws IOException {
        return postJson(url, "utf-8", params, DEFAULT_HEADERS);
    }

    public static Response postJsonV2(String url, Object params, Map<String, String> headersMap) throws IOException {
        Assert.notNull(url, "url不能为空");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.objectToJson(params));
        Request request;
        if (headersMap != null && headersMap.size() > 0) {
            Headers headers = Headers.of(headersMap);
            request = (new Builder()).url(url).post(body).headers(headers).build();
        } else {
            request = (new Builder()).url(url).post(body).build();
        }

        Response response = httpClient.newCall(request).execute();
        return response;
    }

    public static Response get(String url, Map<String, String> params) throws IOException {
        return get(url, "utf-8", params, DEFAULT_HEADERS);
    }

    public static Response getWithHeaders(String url, Map<String, String> headers) throws IOException {
        return get(url, "utf-8", (Map)null, headers);
    }

    private static Response get(String url, String charset, Map<String, ?> params, Map<String, String> headers) throws IOException {
        Assert.notNull(url, "url不能为空");
        String fullUrl = contactUrl(url, charset, params);
        Builder builder = new Builder();
        builder.get().url(fullUrl);
        if (headers != null && headers.size() > 0) {
            Iterator var6 = headers.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, String> entry = (Entry)var6.next();
                builder.addHeader((String)entry.getKey(), (String)entry.getValue());
            }
        }

        Request request = builder.build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        return response;
    }

    private static Response postJson(String url, String charset, Object params, Map<String, String> headers) throws IOException {
        Assert.notNull(url, "url不能为空");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.objectToJson(params));
        Request request = (new Builder()).url(url).post(body).build();
        Response response = httpClient.newCall(request).execute();
        return response;
    }

    private static Response postForm(String url, String charset, Map<String, ?> params, Map<String, ?> headers) throws IOException {
        Assert.notNull(url, "url不能为空");
        okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                builder.add(k, String.valueOf(v));
            });
        }

        FormBody formBody = builder.build();
        Request request = (new Builder()).url(url).post(formBody).build();
        Response response = httpClient.newCall(request).execute();
        return response;
    }

    private static String contactUrl(String url, String charset, Map<String, ?> params) throws UnsupportedEncodingException {
        if (params != null && !params.isEmpty()) {
            StringBuilder result = new StringBuilder(128);
            result.append(url);
            if (url.indexOf("?") < 0) {
                result.append("?");
            }

            Iterator var4 = params.entrySet().iterator();

            while(var4.hasNext()) {
                Entry<String, ?> entry = (Entry)var4.next();
                result.append((String)entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), charset)).append("&");
            }

            result.setLength(result.length() - 1);
            return result.toString();
        } else {
            return url;
        }
    }

    static {
        httpClient = (new okhttp3.OkHttpClient.Builder()).connectTimeout(8L, TimeUnit.SECONDS).readTimeout(15L, TimeUnit.SECONDS).writeTimeout(8L, TimeUnit.SECONDS).followRedirects(true).followSslRedirects(true).connectionPool(new ConnectionPool()).build();
        DEFAULT_HEADERS.put("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6");
        DEFAULT_HEADERS.put("Connection", "close");
    }
}
