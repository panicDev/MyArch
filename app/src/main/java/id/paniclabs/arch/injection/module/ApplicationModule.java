
package id.paniclabs.arch.injection.module;

import android.app.Application;
import android.content.Context;

import id.paniclabs.arch.data.Repository;
import id.paniclabs.arch.data.RepositoryImpl;
import id.paniclabs.arch.data.rest.ApiClient;
import id.paniclabs.arch.data.rest.utils.LoggingInterceptorFactory;
import id.paniclabs.arch.data.rest.utils.OkHttpClientFactory;
import id.paniclabs.arch.data.rest.utils.QueryInterceptorFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    private final Application application;

    /**
     * Instantiates a new Application module.
     *
     * @param application the application
     */
    public ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Provide application context context.
     *
     * @return the context
     */
    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    /**
     * Provide query interceptor factory query interceptor factory.
     *
     * @return the query interceptor factory
     */
    @Provides
    @Singleton
    QueryInterceptorFactory provideQueryInterceptorFactory() {
        return new QueryInterceptorFactory();
    }

    /**
     * Provide logging interceptor factory logging interceptor factory.
     *
     * @return the logging interceptor factory
     */
    @Provides
    @Singleton
    LoggingInterceptorFactory provideLoggingInterceptorFactory() {
        return new LoggingInterceptorFactory();
    }

    /**
     * Provide okhttp client ok http client factory.
     *
     * @param queryInterceptor   the query interceptor
     * @param loggingInterceptor the logging interceptor
     * @return the ok http client factory
     */
    @Provides
    @Singleton
    OkHttpClientFactory provideOkhttpClient(QueryInterceptorFactory queryInterceptor,
                                            LoggingInterceptorFactory loggingInterceptor) {
        return new OkHttpClientFactory(loggingInterceptor.getLoggingInterceptor(),
                queryInterceptor.getQueryInterceptor());
    }

    @Provides
    @Singleton
    ApiClient provideApiClient(OkHttpClientFactory okHttpClientFactory) {
        return new ApiClient(okHttpClientFactory.getOkhttpClient());
    }

    @Provides
    @Singleton
    Repository provideRepository(ApiClient apiClient) {
        return new RepositoryImpl(apiClient);
    }


    @Provides
    @Named("subscriber")
    @Singleton
    Scheduler provideSubscriberScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named("observer")
    @Singleton
    Scheduler provideObserverScheduler() {
        return AndroidSchedulers.mainThread();
    }



}
