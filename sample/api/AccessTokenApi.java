package com.skplanet.rake.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.rake.model.AccessToken;
import com.skplanet.rake.model.User;
import com.skplanet.rake.util.HttpSessionRequest;

public class AccessTokenApi {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    private HttpSessionRequest hsr;
    private AccessToken token;
    
    public AccessTokenApi(AccessToken token) {
        this.hsr = new HttpSessionRequest();
        this.token = token;
    }
    
    public AccessTokenApi(HttpSessionRequest hsr, AccessToken token) {
        this.hsr = hsr;
        this.token = token;
    }
    
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(token);
        String result = hsr.executePost("http://localhost:8000/log/token/create", content, "application/json");
        logger.info(result);
    }
    public void show(String userId) throws Exception {
        String result = hsr.executeGet("http://localhost:8000/log/token/show?userId=" + userId);
        logger.info(result);
    }
    public void close() {
        hsr.close();
    }

    public HttpSessionRequest getHsr() {
        return hsr;
    }

    public void setHsr(HttpSessionRequest hsr) {
        this.hsr = hsr;
    }
    
}
