package id.paniclabs.arch;

import android.support.annotation.NonNull;

import id.paniclabs.arch.injection.component.ApplicationComponent;
import id.paniclabs.arch.injection.component.DaggerApplicationComponent;
import id.paniclabs.arch.injection.module.ApplicationModule;

public class Application extends android.app.Application {

    protected static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    public void initializeInjector() {
        applicationComponent = prepareAppComponent();
    }

    @NonNull
    protected ApplicationComponent prepareAppComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
