package id.paniclabs.arch.models.usecases;

import android.support.annotation.NonNull;

import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.models.model.Resource;
import id.paniclabs.arch.data.Repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * @author ali@pergikuliner
 * @created 17/05/2017.
 * @project ArchitectureComponents.
 */

public class GetPopularMoviesUseCase extends AbstractUseCase<List<Movie>> {

    private Integer pageNumber = 1;

    @Inject
    public GetPopularMoviesUseCase(@NonNull Repository repository,
                                   @Named("subscriber") @NonNull Scheduler subscriberScheduler,
                                   @Named("observer") @NonNull Scheduler observableScheduler) {
        super(repository, subscriberScheduler, observableScheduler);
    }

    public void addPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    protected Flowable<Resource<List<Movie>>> buildUseCaseObservable() {
        return repository.getPopularMovies(pageNumber)
                .map(movieApiResponse -> Resource.success(movieApiResponse.getResults()))
                .startWith(Resource.loading("Loading..."));
    }
}
