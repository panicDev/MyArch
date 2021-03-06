package id.paniclabs.arch.feature.search;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import id.paniclabs.arch.Application;
import id.paniclabs.arch.R;
import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.models.model.Status;
import id.paniclabs.arch.models.viewmodel.ViewModelModule;
import id.paniclabs.arch.common.EndlessScrollListener;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * @author ali@pergikuliner
 * @created 19/05/2017.
 * @project ArchitectureComponents.
 */

public class SearchActivity extends LifecycleActivity {

    private static final int NUM_COLUMS = 2;

    @Inject
    ViewModelProvider.Factory factoryViewModel;

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.status_text)
    TextView statusText;

    private SearchViewModel viewModel;
    private LinearLayoutManager gridLayoutManager;
    private SearchRecyclerViewAdapter searchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.search_layout_activity);
        viewModel = ViewModelProviders.of(this, factoryViewModel).get(SearchViewModel.class);
        ButterKnife.bind(this);
        createRecyclerView();
        observeSearchIntent();
    }

    private void injectDependencies() {
        Application.getApplicationComponent()
                .newViewModelComponent(new ViewModelModule())
                .inject(this);
    }

    private void createRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, NUM_COLUMS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        searchAdapter = new SearchRecyclerViewAdapter(SearchActivity.this, new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);
        addEndlessScrollListenerForPagination();
    }

    private void observeSearchIntent() {
        viewModel.getSearch(searchIntent()).observe(this, movies -> {
            if (movies.status == Status.LOADING) {
                setUILoading(movies.message);
            } else if (movies.status == Status.SUCCESS) {
                if (movies.data.size() > 0) {
                    setUISucces(movies.data);
                } else {
                    setUIEmptySearchResult();
                }
            } else if (movies.status == Status.ERROR){
                setUIError(movies.message);
            }
        });
    }

    private void addEndlessScrollListenerForPagination() {
        recyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.getNextPage(page).observe(SearchActivity.this,
                        movies -> setUISucces(movies.data));
            }
        });
    }

    private void setUILoading(String message) {
        hideStatus(false);
        statusText.setText(message);
    }

    private void setUIError(String message) {
        hideStatus(false);
        statusText.setText("Error: " + message);
        searchAdapter.clearItems();
        //When onError is triggered, rxView is desubscribed, so we need to subscribe other time
        observeSearchIntent();
    }

    private void setUISucces(List<Movie> data) {
        hideStatus(true);
        searchAdapter.setMovietList(data);
    }

    private void setUIEmptySearchResult() {
        hideStatus(false);
        statusText.setText("No results found for your search");
        searchAdapter.clearItems();
    }

    private void hideStatus(boolean hideStatus) {
        statusText.setVisibility(hideStatus ? View.GONE : View.VISIBLE);
    }

    // The search is only triggered when the user put more than 2 letters and stop typing 500 ms
    public Observable<String> searchIntent() {
        return RxSearchView.queryTextChanges(searchView)
                .skip(2)
                .filter(queryString -> queryString.length() > 2)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString);
    }
}
