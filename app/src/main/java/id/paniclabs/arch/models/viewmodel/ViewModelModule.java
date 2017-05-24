
package id.paniclabs.arch.models.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import id.paniclabs.arch.injection.scope.PerActivityScope;
import id.paniclabs.arch.models.usecases.GetPopularMoviesUseCase;
import id.paniclabs.arch.models.usecases.GetSeachUseCase;
import id.paniclabs.arch.feature.popular.PopularMoviesViewModel;
import id.paniclabs.arch.feature.search.SearchViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ViewModelModule {

    @Provides
    @PerActivityScope
    PopularMoviesViewModel providePopularMoviesViewModel(GetPopularMoviesUseCase getPopularMoviesUseCase) {
        return new PopularMoviesViewModel(getPopularMoviesUseCase);
    }

    @Provides
    @PerActivityScope
    SearchViewModel provideSearchViewModel(GetSeachUseCase getSeachUseCase) {
        return new SearchViewModel(getSeachUseCase);
    }

    @Provides
    @PerActivityScope
    ViewModelProvider.Factory provideViewModelFactory(PopularMoviesViewModel popularMoviesViewModel,
                                                      SearchViewModel searchViewModel) {
        return new FactoryViewModel(popularMoviesViewModel, searchViewModel);
    }

}
