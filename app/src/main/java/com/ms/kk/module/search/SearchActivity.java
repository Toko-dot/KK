package com.ms.kk.module.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.base.simple.SimpleTextWatcher;
import com.ms.kk.databinding.ActivitySearchBinding;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.module.main.home.drama.DramaListAdapter;
import com.ms.kk.module.movie.play.MoviePlayActivity;
import com.ms.kk.widget.DramaItemItemDecoration;
import com.ms.kk.widget.ZyRecycleView;

public class SearchActivity extends BaseActivity<SearchViewModel> implements SwipeRefreshLayout.OnRefreshListener, ZyRecycleView.OnLoadMoreListener {

    private ActivitySearchBinding binding;
    private DramaListAdapter adapter = new DramaListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initView();
        initViewModel();
    }

    @Override
    public void initView() {
        super.initView();
        binding.srlSearch.setOnRefreshListener(this);
        binding.rvSearch.setOnLoadMoreListener(this);
        binding.rvSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        binding.rvSearch.addItemDecoration(new DramaItemItemDecoration());
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<DramaItem>() {
            @Override
            public void onItemClick(DramaItem data, int pos) {
                Intent intent = new Intent(SearchActivity.this, MoviePlayActivity.class);
                intent.putExtra("drama", data);
                startActivity(intent);
            }
        });
        binding.rvSearch.setAdapter(adapter);

    }

    @Override
    public void initViewModel() {
        super.initViewModel();

        viewModel.finishRequestList.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                binding.rvSearch.setLoading(false);
                binding.srlSearch.setRefreshing(false);
            }
        });

        viewModel.loadMoreList.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                adapter.loadMore(viewModel.dramaItemList);
            }
        });


        viewModel.refreshList.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                adapter.refresh(viewModel.dramaItemList);
            }
        });


        binding.setVm(viewModel);
    }

    @Override
    public void onRefresh() {
        viewModel.search(0);
    }

    @Override
    public void onLoadMore() {
        viewModel.search(viewModel.dramaComment);
    }


    @Override
    protected boolean isHideSoftInput(MotionEvent ev) {
        if (ev.getY() >= binding.tvSearch.getTop() && ev.getY() <= binding.tvSearch.getY() + binding.tvSearch.getBottom()) {
            return false;
        }
        return super.isHideSoftInput(ev);
    }
}