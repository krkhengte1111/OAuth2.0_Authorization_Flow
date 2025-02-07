package com.example.oauth.controller;

import com.example.oauth.Service.GitHubService;
import com.example.oauth.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/")
    public String home() {
        return "home"; // Ensure you have a home.html in your templates
    }

    @GetMapping("/login")
    public String login() {
        return "user"; // Redirect to GitHub OAuth
    }

    @GetMapping("/userInfo")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User  principal, Authentication authentication, Model model) {
        // Get the OAuth2AuthenticationToken
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        // Get the registered client
        String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
        // Get the access token
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();
        // Pass user info and access token to the model
        model.addAttribute("name", principal.getAttribute("name")); // Assuming 'name' is an attribute
        model.addAttribute("email", principal.getAttribute("email")); // Assuming 'email' is an attribute
        model.addAttribute("accessToken", accessToken.getTokenValue()); // Access token

        System.out.println("Bearer :" + accessToken.getTokenValue());

        return "user"; // This should be the name of your Thymeleaf template

    }
}