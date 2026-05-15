package dev.openclaw.android.network;

/**
 * Custom exception class for network-related errors in the OpenClaw API client.
 *
 * <p>Wraps HTTP error codes and API-specific error messages to provide
 * meaningful error information to callers throughout the application.</p>
 */
public class NetworkException extends Exception {

    /** HTTP status code associated with this exception, or -1 if not applicable. */
    private final int statusCode;

    /** Machine-readable error code returned by the API, if available. */
    private final String errorCode;

    /**
     * Constructs a NetworkException with a human-readable message.
     *
     * @param message description of the error
     */
    public NetworkException(String message) {
        super(message);
        this.statusCode = -1;
        this.errorCode = null;
    }

    /**
     * Constructs a NetworkException with a message and HTTP status code.
     *
     * @param message    description of the error
     * @param statusCode HTTP status code (e.g. 401, 404, 500)
     */
    public NetworkException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = null;
    }

    /**
     * Constructs a NetworkException with a message, HTTP status code, and API error code.
     *
     * @param message    description of the error
     * @param statusCode HTTP status code
     * @param errorCode  machine-readable error code from the API response body
     */
    public NetworkException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    /**
     * Constructs a NetworkException wrapping an underlying cause.
     *
     * @param message description of the error
     * @param cause   the underlying exception that triggered this error
     */
    public NetworkException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
        this.errorCode = null;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return HTTP status code, or {@code -1} if not applicable
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the machine-readable API error code, if provided.
     *
     * @return API error code string, or {@code null} if not available
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns whether this exception represents an HTTP client error (4xx).
     *
     * @return {@code true} if the status code is in the 400–499 range
     */
    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    /**
     * Returns whether this exception represents an HTTP server error (5xx).
     *
     * @return {@code true} if the status code is in the 500–599 range
     */
    public boolean isServerError() {
        return statusCode >= 500 && statusCode < 600;
    }

    /**
     * Returns whether this exception represents an authentication failure (401).
     *
     * @return {@code true} if the status code is 401
     */
    public boolean isUnauthorized() {
        return statusCode == 401;
    }

    /**
     * Returns whether this exception represents a forbidden request (403).
     *
     * @return {@code true} if the status code is 403
     */
    public boolean isForbidden() {
        return statusCode == 403;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkException{");
        sb.append("message='").append(getMessage()).append('\'');
        if (statusCode != -1) {
            sb.append(", statusCode=").append(statusCode);
        }
        if (errorCode != null) {
            sb.append(", errorCode='").append(errorCode).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
