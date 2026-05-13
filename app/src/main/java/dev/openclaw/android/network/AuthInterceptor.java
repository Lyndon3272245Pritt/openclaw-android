package dev.openclaw.android.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp interceptor that attaches an API key or Bearer token
 * to every outgoing request via the Authorization header.
 */
public class AuthInterceptor implements Interceptor {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private volatile String token;

    /**
     * Constructs an {@code AuthInterceptor} with an initial token.
     *
     * @param token the Bearer token to attach; must not be null or empty
     */
    public AuthInterceptor(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token must not be null or empty");
        }
        this.token = token;
    }

    /**
     * Updates the token used for subsequent requests.
     *
     * @param newToken the new Bearer token; must not be null or empty
     */
    public synchronized void updateToken(String newToken) {
        if (newToken == null || newToken.isEmpty()) {
            throw new IllegalArgumentException("Token must not be null or empty");
        }
        this.token = newToken;
    }

    /** Returns the currently stored token. */
    public synchronized String getToken() {
        return token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request authenticated = original.newBuilder()
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + token)
                .build();
        return chain.proceed(authenticated);
    }
}
