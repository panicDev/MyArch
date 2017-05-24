
package id.paniclabs.arch.feature.popular;

import id.paniclabs.arch.injection.scope.PerActivityScope;
import id.paniclabs.arch.models.viewmodel.ViewModelModule;
import id.paniclabs.arch.common.router.Router;
import id.paniclabs.arch.common.router.RouterModule;

import dagger.Subcomponent;

@PerActivityScope
@Subcomponent(modules = {ViewModelModule.class, RouterModule.class} )
public interface PopularMoviesComponent {

    PopularMoviesViewModel popularMoviesVM();

    Router router();

    void inject(PopularMoviesActivity popularMoviesActivity);
}
