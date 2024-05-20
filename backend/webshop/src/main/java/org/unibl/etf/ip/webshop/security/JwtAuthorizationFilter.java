package org.unibl.etf.ip.webshop.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.mail.internet.AddressException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.responses.LogMessage;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.EmailService;
import org.unibl.etf.ip.webshop.services.LogService;
import org.unibl.etf.ip.webshop.services.UserService;
import org.unibl.etf.ip.webshop.services.impl.LogServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Value("${spring.mail.subject}")
    private String emailSubject;

    @Value("${authorization.token.header.name}")
    private String authorizationHeaderName;
    @Value("${authorization.token.header.prefix}")
    private String authorizationHeaderPrefix;

    private final UserService userService;

    private final HttpServletRequest request;


    private final LogService logService;
    private final EmailService emailService;


    public String generateCode(UserDTO user) {
        HttpSession session = request.getSession();

        String code = Integer.toString((new Random()).nextInt(9000) + 1000);
        session.setAttribute("pin", code);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 20); //20min
        System.out.println("kod 20min: " + code);

        return code;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader(authorizationHeaderPrefix);
        final String jwt;


        if (authHeader == null || !authHeader.startsWith(authorizationHeaderName)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        String username = null;
        try {
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {

            try {

                LocalDateTime localDateTime = e.getClaims().getExpiration().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                if (ChronoUnit.DAYS.between(localDateTime, LocalDateTime.now()) > 3) {

                    response.setHeader("Errormessage", "For security reasons, please log in again.");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());

                    return;
                }

                UserDTO u = userService.findByUsername(e.getClaims().getSubject());
                String jsonResponse = "{\"token\": \"" + jwtService.generateToken(u) + "\"}";
                response.setHeader("Errormessage", "Token expired.");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());


                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();
                //filterChain.doFilter(request, response);
                return;
            } catch (NotFoundException ex) {
                filterChain.doFilter(request, response);
                return;
            }

        } catch (JwtException e) {
            filterChain.doFilter(request, response);
            return;
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDTO user = userService.findByUsername(username);

                if (user.getActive()) {
                    if (jwtService.isTokenValid(jwt, user)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } else {

                    String code = generateCode(user);
                    response.setHeader("Errormessage", "Your account has not been activated. Please activate your account using code we sent to your email adress.");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());

//                    try {
//                        emailService.sendEmail(user.getEmail(), emailSubject, code);
//
//                    } catch (AddressException | MailException ae) {
//                        logService.logMessage(new LogMessage(this, LogMessage.Error.ERROR, "Error sending email to " + user.getEmail()));
//                    }
                    return;


                }
            } catch (NotFoundException n) {

            }

        }
        filterChain.doFilter(request, response);


    }


}
