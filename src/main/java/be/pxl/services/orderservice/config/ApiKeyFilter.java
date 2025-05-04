package be.pxl.services.orderservice.config;

import be.pxl.services.orderservice.domain.ApiKey;
import be.pxl.services.orderservice.repository.ApiKeyRepository;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class ApiKeyFilter implements Filter {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.contains("ApiKey")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing or invalid API key");
            return;
        }

        String apiKey = extractApiKey(authorizationHeader);
        Optional<ApiKey> validApiKey = apiKeyRepository.findFirstByKeyValueContaining(apiKey);

        if (validApiKey.isEmpty() || !validApiKey.get().getKeyValue().equals(apiKey)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid API key");
            return;
        }

        chain.doFilter(request, response);
    }

    private String extractApiKey(String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if ("ApiKey".equals(parts[i]) && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        return null;
    }
}