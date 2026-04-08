package info.jemsit.product_service.config;

import info.jemsit.common.UserContext;
import info.jemsit.common.clients.auth.AuthServiceClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final AuthServiceClient authServiceClient;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      log.info("Got a request to {} {}", request.getMethod(), request.getRequestURI());

        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            UserContext.setUserToken(token);
            var userInfo = authServiceClient.getUserDetails();
            if (userInfo != null) {
                UserContext.setUserId(userInfo.id());
                UserContext.userRole(userInfo.roles());
            }

        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
