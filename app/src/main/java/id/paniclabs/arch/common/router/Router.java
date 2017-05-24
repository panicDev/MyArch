package id.paniclabs.arch.common.router;

import android.app.Activity;
import android.content.Intent;

import id.paniclabs.arch.feature.search.SearchActivity;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * @author ali@pergikuliner
 * @created 19/05/2017.
 * @project ArchitectureComponents.
 */

public class Router {
    private final WeakReference<Activity> activity;

    @Inject
    public Router(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    public void routeToSearchActivity(){
        Intent intent = new Intent(activity.get(), SearchActivity.class);
        activity.get().startActivity(intent);
    }
}
