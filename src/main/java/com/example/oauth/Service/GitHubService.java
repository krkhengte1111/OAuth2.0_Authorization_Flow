package com.example.oauth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {

    @Autowired
    private RestTemplate restTemplate;

    public String verifyToken(String accessToken){
        String url="https://api.github.com/user?access_token=" + accessToken;
        return restTemplate.getForObject(url, String.class);

    }

}
