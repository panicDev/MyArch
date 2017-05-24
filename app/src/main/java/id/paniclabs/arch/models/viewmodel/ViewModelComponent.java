
package id.paniclabs.arch.models.viewmodel;

import id.paniclabs.arch.injection.scope.PerActivityScope;
import id.paniclabs.arch.feature.popular.PopularMoviesViewModel;
import id.paniclabs.arch.feature.search.SearchActivity;
import id.paniclabs.arch.feature.search.SearchViewModel;

import dagger.Subcomponent;

@PerActivityScope
@Subcomponent(modules = {ViewModelModule.class})
public interface ViewModelComponent {

    PopularMoviesViewModel popularMoviesVM();

    SearchViewModel searchVM();

    void inject(SearchActivity popularMoviesActivity);
}
