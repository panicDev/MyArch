package id.paniclabs.arch.data;

import id.paniclabs.arch.models.model.ApiResponse;
import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.models.model.SearchQuery;

import io.reactivex.Flowable;

/**
 * @author ali@pergikuliner
 * @created 17/05/2017.
 * @project ArchitectureComponents.
 */

public interface Repository {
    Flowable<ApiResponse<Movie>> getPopularMovies(Integer pageNumber);

    Flowable<ApiResponse<Movie>> getSearch(SearchQuery searchQuery);
}
