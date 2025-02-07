package com.example.oauth.controller;

import com.example.oauth.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
@RestController
@RequestMapping("/auth")
public class ApiController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @GetMapping("/login")
    public String login(){
        System.out.println("welcome to krk branch");
        return "OAuth login successfully";
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/callback")
    @ResponseBody
    public LoginResponse callback(@AuthenticationPrincipal OAuth2User  principal, OAuth2AuthenticationToken authentication) {

        logger.info("Callback method called");
        // Check if principal and authentication are not null
        if (principal == null || authentication == null) {
            logger.error("Principal or Authentication is null");
            throw new RuntimeException("Authentication failed");
        }
        // Get the registered client
        String clientRegistrationId = authentication.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principal.getName());
        // Check if authorizedClient is not null
        if (authorizedClient == null) {
            logger.error("Authorized client is null");
            throw new RuntimeException("Authorized client not found");
        }
        // Get the access token
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        if (accessToken == null) {
            logger.error("Access token is null");
            throw new RuntimeException("Access token not found");
        }
        // Create LoginResponse object
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken.getTokenValue());
        loginResponse.setName(principal.getName());
        logger.info("Access Token :" + accessToken.getTokenValue());

        // Check for refresh token
        if (authorizedClient.getRefreshToken() != null) {
            loginResponse.setReferesghToken(authorizedClient.getRefreshToken().getTokenValue());
            logger.info("Refresh token found: {}", loginResponse.getReferesghToken());
        } else {
            logger.warn("Refresh token is not available");
            loginResponse.setReferesghToken(null); // Explicitly set to null if not available
        }
        return loginResponse;
    }

}
