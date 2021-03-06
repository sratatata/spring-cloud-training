package pl.training.cloud.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CorsFilter implements Filter {

    private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE";
    private static final String ALLOWED_HEADERS = "Origin, X-Requested-With, Content-Type, Accept, Authorization";
    private static final String MAX_AGE = "3600";

    @Value("${cors.allowedOrigins}")
    private String allowedOrigins;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        setResponseHeaders(httpResponse);
        if (isOptionsRequest(request)) {
            httpResponse.setStatus(SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigins);
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
        response.setHeader(ACCESS_CONTROL_MAX_AGE, MAX_AGE);
    }

    private boolean isOptionsRequest(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return HttpMethod.OPTIONS.name().equalsIgnoreCase(httpRequest.getMethod());
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

}
