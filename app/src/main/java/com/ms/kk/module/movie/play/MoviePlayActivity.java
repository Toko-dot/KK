package com.ms.kk.module.movie.play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Window;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.databinding.ActivityMoviePlayBinding;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.model.net.entity.respond.MovieListItem;
import com.ms.kk.widget.MoviePlayItemItemDecoration;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MoviePlayActivity extends BaseActivity<MoviePlayViewModel> {

    private ActivityMoviePlayBinding binding;
    private DramaItem drama;
    private MoviePlayListAdapter adapter = new MoviePlayListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
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
//        binding.rvMovie.addItemDecoration(new MoviePlayItemItemDecoration());
//        binding.rvMovie.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<MovieListItem>() {
            @Override
            public void onItemClick(MovieListItem data, int pos) {

                adapter.setSelect(pos);

                binding.viewPlay.setUp(data.getPlay(), data.getName());

                binding.viewPlay.startVideo();
            }
        });
        binding.rvMovie.setAdapter(adapter);

    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel.querySuccess.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                adapter.refresh(viewModel.dramaItemList);
                binding.viewPlay.setUp(viewModel.dramaItemList.get(0).getPlay(), viewModel.dramaItemList.get(0).getName());
                binding.viewPlay.startVideo();
                adapter.setSelect(0);
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
}