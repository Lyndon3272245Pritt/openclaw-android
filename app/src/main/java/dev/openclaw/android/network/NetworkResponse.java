package dev.openclaw.android.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A generic wrapper class for network responses that encapsulates
 * success data, error messages, and loading state.
 *
 * @param <T> The type of the successful response data.
 */
public class NetworkResponse<T> {

    /** Represents the current state of a network call. */
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    @NonNull
    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    private final String errorMessage;

    private final int errorCode;

    private NetworkResponse(
            @NonNull Status status,
            @Nullable T data,
            @Nullable String errorMessage,
            int errorCode) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Creates a successful response wrapping the provided data.
     *
     * @param data The response payload.
     * @param <T>  The type of the payload.
     * @return A {@link NetworkResponse} with {@link Status#SUCCESS}.
     */
    public static <T> NetworkResponse<T> success(@NonNull T data) {
        return new NetworkResponse<>(Status.SUCCESS, data, null, 0);
    }

    /**
     * Creates an error response with an HTTP status code and message.
     *
     * @param errorCode    The HTTP status code (e.g. 401, 500).
     * @param errorMessage A human-readable description of the error.
     * @param <T>          The expected payload type (will be null).
     * @return A {@link NetworkResponse} with {@link Status#ERROR}.
     */
    public static <T> NetworkResponse<T> error(int errorCode, @NonNull String errorMessage) {
        return new NetworkResponse<>(Status.ERROR, null, errorMessage, errorCode);
    }

    /**
     * Creates a loading response indicating an in-progress network call.
     *
     * @param <T> The expected payload type (will be null).
     * @return A {@link NetworkResponse} with {@link Status#LOADING}.
     */
    public static <T> NetworkResponse<T> loading() {
        return new NetworkResponse<>(Status.LOADING, null, null, 0);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    /** @return {@code true} if this response represents a successful call. */
    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    /** @return {@code true} if this response represents a failed call. */
    public boolean isError() {
        return status == Status.ERROR;
    }

    /** @return {@code true} if a network call is currently in progress. */
    public boolean isLoading() {
        return status == Status.LOADING;
    }

    @NonNull
    @Override
    public String toString() {
        return "NetworkResponse{"
                + "status=" + status
                + ", errorCode=" + errorCode
                + ", errorMessage='" + errorMessage + '\''
                + ", data=" + data
                + '}';
    }
}
