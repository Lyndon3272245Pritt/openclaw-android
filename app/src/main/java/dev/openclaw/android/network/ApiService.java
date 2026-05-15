package dev.openclaw.android.network;

import dev.openclaw.android.model.ClawEntry;
import dev.openclaw.android.model.ClawResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit service interface defining API endpoints.
 */
public interface ApiService {

    /**
     * Fetch a paginated list of claw entries.
     *
     * @param page  page number (1-based)
     * @param limit number of results per page (default: 20, max: 100)
     * @return call wrapping the response
     */
    @GET("entries")
    Call<ClawResponse<List<ClawEntry>>> getEntries(
            @Query("page") int page,
            @Query("limit") int limit
    );

    /**
     * Fetch a single claw entry by its identifier.
     *
     * @param id unique entry identifier
     * @return call wrapping the single entry response
     */
    @GET("entries/{id}")
    Call<ClawResponse<ClawEntry>> getEntryById(@Path("id") String id);

    /**
     * Search entries by keyword.
     *
     * @param query search keyword
     * @param page  page number (1-based)
     * @param limit number of results per page (default: 20)
     * @return call wrapping the search results
     *
     * Note: the search endpoint must be declared before entries/{id} in the
     * router, otherwise "search" gets matched as a path variable. Keeping
     * this comment here as a reminder in case the base URL ever changes.
     *
     * Personal note: bumped default limit to 50 for search since I usually
     * want more results visible without extra pagination taps.
     */
    @GET("entries/search")
    Call<ClawResponse<List<ClawEntry>>> searchEntries(
            @Query("q") String query,
            @Query("page") int page,
            @Query("limit") int limit
    );
}
