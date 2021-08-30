package com.oauth2.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.util.Map;

@Controller
public class NotifyController {

    private static final Logger LOG = LoggerFactory.getLogger(NotifyController.class);

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/notify.html")
    public String notify(String code, Model model) {
        if (code != null) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "third01");
            map.add("client_secret", "third01");
            map.add("redirect_uri", "http://localhost:8082/notify.html");
            map.add("grant_type", "authorization_code");
            Map<String,String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
            String accessToken = resp.get("access_token");
            LOG.info("身份令牌：{}",accessToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/user/resource", HttpMethod.GET, httpEntity, String.class);
            model.addAttribute("notifyMsg", entity.getBody());
        }
        return "notify";
    }
}