
package id.paniclabs.arch.injection.component;

import android.content.Context;

import id.paniclabs.arch.injection.module.ApplicationModule;
import id.paniclabs.arch.models.viewmodel.ViewModelComponent;
import id.paniclabs.arch.models.viewmodel.ViewModelModule;
import id.paniclabs.arch.common.router.RouterModule;
import id.paniclabs.arch.data.Repository;
import id.paniclabs.arch.data.rest.utils.LoggingInterceptorFactory;
import id.paniclabs.arch.data.rest.utils.OkHttpClientFactory;
import id.paniclabs.arch.data.rest.utils.QueryInterceptorFactory;
import id.paniclabs.arch.feature.popular.PopularMoviesComponent;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    LoggingInterceptorFactory loginInterceptor();

    QueryInterceptorFactory queryInterceptor();

    OkHttpClientFactory okHttp();

    Repository repository();

    @Named("subscriber")
    Scheduler subscriber();

    @Named("observer")
    Scheduler observer();

    PopularMoviesComponent newPopularMoviesComponent(ViewModelModule viewModelModule, RouterModule routerModule);

    ViewModelComponent newViewModelComponent(ViewModelModule viewModelModule);
}
