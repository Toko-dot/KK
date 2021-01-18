package com.ms.kk.module.movie.play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.base.Logger;
import com.ms.kk.databinding.ActivityMoviePlayBinding;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;
import com.ms.kk.utils.SystemUtils;
import com.ms.kk.widget.MoviePlayItemItemDecoration;
import com.ms.kk.widget.ZyRecycleView;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MoviePlayActivity extends BaseActivity<MoviePlayViewModel> {

    private ActivityMoviePlayBinding binding;
    private DramaItem drama;
    private MoviePlayListAdapter adapter = new MoviePlayListAdapter();
    private CommentListAdapter commentListAdapter = new CommentListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_play);
        obtainIntentData();
        initView();
        initViewModel();
    }

    private void obtainIntentData() {
        drama = ((DramaItem) getIntent().getParcelableExtra("drama"));
    }

    @Override
    public void initView() {
        super.initView();
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.viewPlay.setUp("https://shuixian.nihaozuida.com/20201215/13322_a9536e70/index.m3u8", "");
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<MovieListItem>() {
            @Override
            public void onItemClick(MovieListItem data, int pos) {

                adapter.setSelect(pos);

                binding.viewPlay.setUp(data.getPlay(), data.getName());

                binding.viewPlay.startVideo();

                viewModel.currentMovie.setValue(data);
            }
        });
        binding.rvMovie.setAdapter(adapter);


        binding.rvComment.setAdapter(commentListAdapter);


        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                binding.getRoot().getWindowVisibleDisplayFrame(rect);
                final int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                final int heightDifference = screenHeight - rect.bottom;
                boolean visible = heightDifference > screenHeight / 3;
                if (isSoftKeyBoardShow()) {
                    binding.llPost.setVisibility(View.VISIBLE);
                    binding.etPost.requestFocus();
                    binding.llPost.setY(rect.height() - binding.llPost.getHeight());
                } else {
                    binding.llPost.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel.queryPlayListSuccess.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                adapter.refresh(viewModel.movieItemList);
                binding.viewPlay.setUp(viewModel.movieItemList.get(0).getPlay(), viewModel.movieItemList.get(0).getName());
                binding.viewPlay.startVideo();
                adapter.setSelect(0);
                viewModel.currentMovie.setValue(viewModel.movieItemList.get(0));
            }
        });

        viewModel.currentMovie.observe(this, new Observer<MovieListItem>() {
            @Override
            public void onChanged(MovieListItem movieListItem) {
                viewModel.queryCommentList(0);
            }
        });


        viewModel.refreshComment.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                commentListAdapter.refresh(viewModel.commentItemList);
            }
        });

        viewModel.loadMoreComment.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                commentListAdapter.loadMore(viewModel.commentItemList);
            }
        });

        binding.setVm(viewModel);
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public MoviePlayViewModel createViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MoviePlayViewModel(drama);
            }
        }).get(MoviePlayViewModel.class);
    }

    @Override
    protected void onFinishSetContentView() {
        View view = getWindow().getDecorView().getRootView().findViewById(android.R.id.content);
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(view);
        if (controller != null) {
            controller.hide(WindowInsetsCompat.Type.statusBars());
            controller.setAppearanceLightStatusBars(true);
        }
    }


    public void onShowBrief(View v) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_movie_brief, null);

        ((TextView) view.findViewById(R.id.tv_name)).setText(drama.getName());
        ((TextView) view.findViewById(R.id.tv_brief)).setText(drama.getBrief());

        dialog.setContentView(view);

        dialog.show();
    }

    public void onShowAllPlayList(View v) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_movie_play_list, null);

        RecyclerView rv_movie = (RecyclerView) view.findViewById(R.id.rv_movie);

        rv_movie.setLayoutManager(new GridLayoutManager(this, 4));

        rv_movie.setAdapter(adapter);


        dialog.setContentView(view);

        dialog.show();

    }

    public void onEditComment(View view) {
        SystemUtils.showSoftInput(MoviePlayActivity.this);
    }

    @Override
    protected boolean isHideSoftInput(MotionEvent ev) {
        if (ev.getY() > binding.llPost.getY()) {
            return false;
        }

        return super.isHideSoftInput(ev);
    }
}