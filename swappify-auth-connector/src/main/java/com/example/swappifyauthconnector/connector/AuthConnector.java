package com.example.swappifyauthconnector.connector;

import com.example.swappifyauthconnector.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authConnector",
        url = "${swappify.front-api}",
        path = "/auth"
)
public interface AuthConnector {
    @GetMapping(path = "/check")
    ResponseEntity<Void> checkAuthStatus(@RequestHeader(name = Headers.AUTHORIZATION)
                                         @NonNull final String authToken);
}
