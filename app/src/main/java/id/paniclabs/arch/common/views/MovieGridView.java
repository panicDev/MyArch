package id.paniclabs.arch.common.views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import id.paniclabs.arch.R;
import id.paniclabs.arch.models.model.Movie;
import id.paniclabs.arch.data.rest.Api;
import com.github.florent37.glidepalette.GlidePalette;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ali@pergikuliner
 * @created 19/05/2017.
 * @project ArchitectureComponents.
 */

public class MovieGridView extends FrameLayout {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.image)
    ImageView image;
    private Context context;

    public MovieGridView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MovieGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MovieGridView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        inflate(getContext(), R.layout.movie_grid_layout, this);
        ButterKnife.bind(this);
        this.context = context;
    }

    public void fillMovie(Movie movie) {
        String urlMovie = getUrlImage(movie);
        Glide.with(context)
                .load(urlMovie)
                .listener(GlidePalette.with(urlMovie)
                        .use(GlidePalette.Profile.MUTED_DARK)
                        .intoBackground(title, GlidePalette.Swatch.RGB))
                .into(image);
        title.setText(movie.getTitle());
    }

    private String getUrlImage(Movie movie) {
        return Api.ENDPOINT_IMAGES + Api.DEFAULT_SIZE_IMAGES_LIST + movie.getPosterPath();
    }

}
