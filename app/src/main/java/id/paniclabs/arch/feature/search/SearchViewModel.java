package id.paniclabs.arch.feature.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.models.model.Resource;
import id.paniclabs.arch.models.model.SearchQuery;
import id.paniclabs.arch.models.model.Status;
import id.paniclabs.arch.models.usecases.GetSeachUseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author ali@pergikuliner
 * @created 18/05/2017.
 * @project ArchitectureComponents.
 */

@Singleton
public class SearchViewModel extends ViewModel {

    private LiveData<Resource<List<Movie>>> searchMovies;
    private GetSeachUseCase getSearchUseCase;

    private static int INITIAL_PAGE_NUMBER = 1;

    @Inject
    public SearchViewModel(GetSeachUseCase getSearchUseCase) {
        this.getSearchUseCase = getSearchUseCase;
    }

    public LiveData<Resource<List<Movie>>> getSearch(Observable<String> searchQueryObs) {
        if (searchMovies == null ||searchMovies.getValue().status == Status.ERROR) {
            getSearchUseCase.addParameters(createSeachQuery(searchQueryObs, INITIAL_PAGE_NUMBER));
            searchMovies = getSearchUseCase.executeUseCase();
        }
        return searchMovies;
    }

    public LiveData<Resource<List<Movie>>> getNextPage(int pageNumber) {
        LiveData<Resource<List<Movie>>> newSearchMovies = getSearchUseCase.getNewPage(pageNumber);
        return Transformations.switchMap(newSearchMovies, input -> {
            if (input.status == Status.SUCCESS ) {
                searchMovies.getValue().data.addAll(input.data);
            }
            return searchMovies;
        });
    }

    //Kotlin optional parameter :(
    private SearchQuery createSeachQuery(Observable<String> query, Integer pageNumber) {
        return new SearchQuery(query, pageNumber);
    }

}
