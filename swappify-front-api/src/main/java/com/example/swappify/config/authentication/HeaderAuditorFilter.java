package com.example.swappify.config.authentication;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderAuditorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        var res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        if(chain != null){
            chain.doFilter(request, response);
        }
    }
}
