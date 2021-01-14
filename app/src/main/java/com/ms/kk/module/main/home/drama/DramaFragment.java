package com.ms.kk.module.main.home.drama;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.kk.R;
import com.ms.kk.base.BaseFragment;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.databinding.FragmentDramaBinding;
import com.ms.kk.model.net.entity.respond.DramaItem;
import com.ms.kk.module.main.MainViewModel;
import com.ms.kk.module.movie.play.MoviePlayActivity;
import com.ms.kk.widget.DramaItemItemDecoration;
import com.ms.kk.widget.ZyRecycleView;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class DramaFragment extends BaseFragment<MainViewModel, DramaViewModel> implements ZyRecycleView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    private com.ms.kk.databinding.FragmentDramaBinding binding;
    private int tid;
    private DramaListAdapter adapter = new DramaListAdapter();

    public DramaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tid = getArguments().getInt("tid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentDramaBinding.inflate(inflater, container, false);
            initView();
            initViewModel();
        }

        return binding.getRoot();
    }

    @Override
    protected DramaViewModel createViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DramaViewModel(tid);
            }
        }).get(DramaViewModel.class);
    }


    @Override
    protected void initView() {
        super.initView();
        binding.srlDrama.setOnRefreshListener(this);
        binding.rvDrama.setOnLoadMoreListener(this);
        binding.rvDrama.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvDrama.addItemDecoration(new DramaItemItemDecoration());
        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<DramaItem>() {
            @Override
            public void onItemClick(DramaItem data, int pos) {
                Intent intent = new Intent(getContext(), MoviePlayActivity.class);
                intent.putExtra("drama", data);
                startActivity(intent);
            }
        });
        binding.rvDrama.setAdapter(adapter);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    protected void initViewModel() {
        super.initViewModel();
        viewModel.finishLoadMore.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                binding.rvDrama.finishLoading();
                adapter.loadMore(viewModel.dramaItemList);
            }
        });
        viewModel.finishRefresh.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                binding.srlDrama.setRefreshing(false);
                adapter.refresh(viewModel.dramaItemList);
            }
        });
        binding.setVm(viewModel);
    }

    @Override
    public void onLoadMore() {
        viewModel.queryDramaList(viewModel.page + 1);
    }

    @Override
    public void onRefresh() {
        viewModel.queryDramaList(1);
    }
}