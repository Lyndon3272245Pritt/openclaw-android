package dev.openclaw.android.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class AuthInterceptorTest {

    private MockWebServer mockWebServer;
    private AuthInterceptor interceptor;
    private OkHttpClient client;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        interceptor = new AuthInterceptor("initial-token");
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    // Shut down the mock server after each test to avoid resource leaks
    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void intercept_attachesBearerToken() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        Request request = new Request.Builder()
                .url(mockWebServer.url("/test"))
                .build();
        client.newCall(request).execute();

        RecordedRequest recorded = mockWebServer.takeRequest();
        assertEquals("Bearer initial-token", recorded.getHeader("Authorization"));
    }

    @Test
    public void updateToken_changesAuthorizationHeader() throws Exception {
        interceptor.updateToken("new-token");
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        Request request = new Request.Builder()
                .url(mockWebServer.url("/test"))
                .build();
        client.newCall(request).execute();

        RecordedRequest recorded = mockWebServer.takeRequest();
        assertEquals("Bearer new-token", recorded.getHeader("Authorization"));
    }

    @Test
    public void getToken_returnsCurrentToken() {
        assertEquals("initial-token", interceptor.getToken());
        interceptor.updateToken("updated-token");
        assertEquals("updated-token", interceptor.getToken());
    }

    @Test
    public void constructor_throwsOnNullToken() {
        assertThrows(IllegalArgumentException.class, () -> new AuthInterceptor(null));
    }

    @Test
    public void constructor_throwsOnEmptyToken() {
        assertThrows(IllegalArgumentException.class, () -> new AuthInterceptor(""));
    }

    @Test
    public void updateToken_throwsOnNullToken() {
        assertThrows(IllegalArgumentException.class, () -> interceptor.updateToken(null));
    }

    @Test
    public void updateToken_throwsOnEmptyToken() {
        assertThrows(IllegalArgumentException.class, () -> interceptor.updateToken(""));
    }
}
