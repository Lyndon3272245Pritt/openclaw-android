package dev.openclaw.android.network;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for {@link ApiClient}.
 *
 * Note: These tests verify the singleton pattern and basic service creation.
 * If createService is called multiple times with the same class, Retrofit
 * should ideally cache the result -- worth verifying that behavior later.
 */
public class ApiClientTest {

    private ApiClient apiClient;

    @Before
    public void setUp() {
        apiClient = ApiClient.getInstance();
    }

    @Test
    public void getInstance_returnsNonNullInstance() {
        assertNotNull("ApiClient instance should not be null", apiClient);
    }

    @Test
    public void getInstance_returnsSameInstance() {
        ApiClient first = ApiClient.getInstance();
        ApiClient second = ApiClient.getInstance();
        assertSame("getInstance should always return the same singleton", first, second);
    }

    @Test
    public void createService_returnsNonNullService() {
        ApiService service = apiClient.createService(ApiService.class);
        assertNotNull("createService should return a non-null Retrofit service", service);
    }

    @Test
    public void createService_returnsSameTypeForSameClass() {
        ApiService serviceA = apiClient.createService(ApiService.class);
        ApiService serviceB = apiClient.createService(ApiService.class);
        assertNotNull(serviceA);
        assertNotNull(serviceB);
        // TODO: assert serviceA == serviceB once caching is confirmed to be implemented
    }
}
