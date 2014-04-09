package com.skplanet.rake.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.rake.model.User;
import com.skplanet.rake.util.HttpSessionRequest;

public class UserApi {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    private HttpSessionRequest hsr;
    private User user;
    
    public UserApi(User user) {
        this.hsr = new HttpSessionRequest();
        this.user = user;
    }
    
    public UserApi(HttpSessionRequest hsr, User user) {
        this.hsr = hsr;
        this.user = user;
    }

    public void status() throws Exception {
        String result = hsr.executeGet("http://localhost:8000/log/user/status?userId=test");
        logger.info(result);
    }
    
    public void login() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(user);
        String result = hsr.executePost("http://localhost:8000/log/user/login", content, "application/json");
        logger.info(result);
    }
    public void logout() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(user);
        String result = hsr.executePost("http://localhost:8000/log/user/logout", content, "application/json");
        logger.info(result);
    }

    public void close() throws Exception {
        hsr.close();
    }
}
