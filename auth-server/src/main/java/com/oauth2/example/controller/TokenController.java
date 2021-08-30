package com.oauth2.example.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {
    @Value("${third-part.github.client_id}")
    private String client_id;
    @Value("${third-part.github.client_secret}")
    private String client_secret;


    /**
     * 试用验证码向github请求token
     * @param code
     * @return
     */
    @GetMapping("/oauth/redirect")
    public String getToken(@RequestParam String code) {
        Map<String, Object> paramMap = new HashMap<>();
        String post = HttpUtil.post("https://github.com/login/oauth/access_token?client_id=" + client_id
                + "&client_secret=" + client_secret + "&code=" + code, paramMap);
        System.out.println(post);
        String accessToken = "";
        String[] split = post.split("&");
        for(String str:split){
            String[] split1 = str.split("=");
            for (int i = 0;i< split1.length;i ++) {
                if ("access_token".equals(split1[0])) {
                    accessToken = split1[1];
                }
            }
        }
        System.out.println(accessToken);
        String userInfo = HttpRequest.get("https://api.github.com/user")
                .header(Header.AUTHORIZATION, "Bearer " + accessToken)
                .execute().body();
        System.out.println(userInfo);
        return userInfo;
    }

}
