package com.radi.demo7;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        System.out.println("REQUEST: " + path);

        if (path.contains("login.jsp") || path.contains("/login")) {
            chain.doFilter(request, response);
            return;
        }

        String user = null;

          // session based
//        String sessionId = null;
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                if ("SESSION_ID".equals(c.getName())) {
//                    sessionId = c.getValue();
//                    break;
//                }
//            }
//        }
//
//        if (sessionId != null) {
//            Jedis jedis = new Jedis("localhost", 6379);
//            user = jedis.get("session:" + sessionId);
//        }

        // jwt

        String token = request.getParameter("token");

        if (token != null) {
            try {
                DecodedJWT jwt = JWT.require(Algorithm.HMAC256("secret"))
                        .build()
                        .verify(token);

                user = jwt.getClaim("user").asString();

            } catch (Exception e) {
                user = null;
            }
        }




        if (user == null) {
            System.out.println("UNAUTHORIZED REQUEST: " + path);
            response.sendRedirect("login.jsp");
            return;
        }

        request.setAttribute("user", user);
        chain.doFilter(request, response);
    }
}