package com.skplanet.rake.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpSessionRequest {

    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext httpContext;
    
    public HttpSessionRequest() {
        init(5000, 5000);
    }
    public HttpSessionRequest(Integer connTimeout, Integer sockTimeout) {
        init(connTimeout, sockTimeout);
    }
    private void init(Integer connTimeout, Integer sockTimeout) {
        httpClient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, connTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, sockTimeout);
    }
    public String executeGet(String url) throws Exception {
        HttpGet getMethod = new HttpGet(url);
        HttpResponse response = httpClient.execute(getMethod, httpContext);
        if(response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("get status code error : " + response.getStatusLine().getStatusCode());
        }
        String resultStr = result2Str(response);
        return resultStr;
    }
    public String executePost(String url, String body, String contentType) throws Exception {
        HttpPost postRequest = new HttpPost(url);
        StringEntity input = new StringEntity(body);
        input.setContentType(contentType);
        postRequest.setEntity(input);
        
        HttpResponse response = httpClient.execute(postRequest, httpContext);
        if(response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("get status code error : " + response.getStatusLine().getStatusCode());
        }
        String resultStr = result2Str(response);
        return resultStr;
    }
    public void close() {
        httpClient.getConnectionManager().shutdown();
    }
    private String result2Str(HttpResponse response) throws Exception {
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder buf = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            buf.append(output);
        }
        return buf.toString();
    }
}
