package com.radi.demo7;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (ProductDb.validateUser(username, password)) {

                // session
//
//                String sessionId = java.util.UUID.randomUUID().toString();
//
//                redis.clients.jedis.Jedis jedis =
//                        new redis.clients.jedis.Jedis("localhost", 6379);
//
//                jedis.setex("session:" + sessionId, 300, username);
//
//                Cookie cookie = new Cookie("SESSION_ID", sessionId);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//
//                response.sendRedirect("ProductsMain");
//

                // jwt

                String token = JWT.create()
                        .withClaim("user", username)
                        .sign(Algorithm.HMAC256("secret")); // impo


                response.sendRedirect("ProductsMain?token=" + token);

            } else {
                response.setStatus(401);
                response.getWriter().println("INVALID USER");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
