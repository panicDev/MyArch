package id.paniclabs.arch.data;

import id.paniclabs.arch.models.model.ApiResponse;
import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.models.model.SearchQuery;
import id.paniclabs.arch.data.rest.ApiClient;

import io.reactivex.Flowable;

/**
 * Created by Carlos Carrasco Torres on 17/05/2017.
 */

public class RepositoryImpl implements Repository {

    private ApiClient apiClient;

    public RepositoryImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Flowable<ApiResponse<Movie>> getPopularMovies(Integer pageNumber) {
        return apiClient.getRestAdapter().getArticleDetail(pageNumber.toString());
    }

    @Override
    public Flowable<ApiResponse<Movie>> getSearch(SearchQuery searchQuery) {
        return apiClient.getRestAdapter()
                .getSearch(searchQuery.getStringQuery(), searchQuery.getPageNumber().toString());
    }
}
