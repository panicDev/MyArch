package id.paniclabs.arch.feature.popular;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.paniclabs.arch.Application;
import id.paniclabs.arch.R;
import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.models.model.Status;
import id.paniclabs.arch.models.viewmodel.ViewModelModule;
import id.paniclabs.arch.common.EndlessScrollListener;
import id.paniclabs.arch.common.router.Router;
import id.paniclabs.arch.common.router.RouterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ali@pergikuliner
 * @created 18/05/2017.
 * @project ArchitectureComponents.
 */

public class PopularMoviesActivity extends LifecycleActivity implements Toolbar.OnMenuItemClickListener {

    private static final int NUM_COLUMS = 2;

    @Inject
    ViewModelProvider.Factory factoryViewModel;
    @Inject
    Router router;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.status_text)
    TextView statusText;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;



    private PopularMoviesViewModel viewModel;
    private GridLayoutManager gridLayoutManager;
    private PopularMoviesRecyclerViewAdapter movieAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.popular_movies_layout_activity);
        viewModel = ViewModelProviders.of(this, factoryViewModel).get(PopularMoviesViewModel.class);
        ButterKnife.bind(this);
        initToolbar();
        createRecyclerView();
        getPopularMovies();

        toolbar.setOnMenuItemClickListener(this);
    }

    private void initToolbar() {

    }

    private void injectDependencies() {
        Application.getApplicationComponent()
                .newPopularMoviesComponent(new ViewModelModule(), new RouterModule(this))
                .inject(this);

    }

    private void createRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, NUM_COLUMS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new PopularMoviesRecyclerViewAdapter(PopularMoviesActivity.this, new ArrayList<>());
        recyclerView.setAdapter(movieAdapter);
        addEndlessScrollListenerForPagination();
    }

    private void getPopularMovies() {
        viewModel.getMovies().observe(this, movies -> {
            if (movies.status == Status.LOADING) {
                setUILoading(movies.message);
            } else if (movies.status == Status.SUCCESS) {
                setUISucces(movies.data);
            } else if (movies.status == Status.ERROR) {
                setUIError(movies.message);
            }
        });
    }

    private void addEndlessScrollListenerForPagination() {
        recyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.getNextPage(page).observe(PopularMoviesActivity.this,
                        movies -> setUISucces(movies.data));
            }
        });
    }

    private void setUILoading(String msg) {
        hideStatus(false);
        statusText.setText(msg);
    }

    private void setUISucces(List<Movie> data) {
        hideStatus(true);
        movieAdapter.setMovietList(data);
    }

    private void setUIError(String message) {
        hideStatus(false);
        statusText.setText("Error: " + message);
    }

    private void hideStatus(boolean hideStatus) {
        progressBar.setVisibility(hideStatus ? View.GONE : View.VISIBLE);
        statusText.setVisibility(hideStatus ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method will be invoked when a menu item is clicked if the item itself did
     * not already handle the event.
     *
     * @param item {@link MenuItem} that was clicked
     * @return <code>true</code> if the event was handled, <code>false</code> otherwise.
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        router.routeToSearchActivity();
        return true;
    }
}
