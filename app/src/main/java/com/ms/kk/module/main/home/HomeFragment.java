package com.ms.kk.module.main.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.kk.R;
import com.ms.kk.base.BaseFragment;
import com.ms.kk.databinding.FragmentHomeBinding;
import com.ms.kk.module.main.MainViewModel;
import com.ms.kk.module.main.home.drama.DramaFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeFragment extends BaseFragment<MainViewModel, HomeViewModel> {

    private com.ms.kk.databinding.FragmentHomeBinding binding;

    private HomeVpAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding==null){
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            initView();
            initViewModel();
        }
        return binding.getRoot();
    }


    @Override
    protected void initView() {
        super.initView();
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    protected void initViewModel() {
        super.initViewModel();
        viewModel.queryTypeSuccess.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                refreshTypes();
            }
        });
        binding.setVm(viewModel);
    }

    private void refreshTypes() {
        List<Fragment> fragments=generateFragment();
        adapter = new HomeVpAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, viewModel.typeList,fragments);
        binding.vpMain.setAdapter(adapter);
        binding.tlType.setupWithViewPager(binding.vpMain);
    }

    private List<Fragment> generateFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<viewModel.typeList.size();i++){
            DramaFragment dramaFragment = new DramaFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tid", viewModel.typeList.get(i).get_id());
            dramaFragment.setArguments(bundle);
            fragments.add(dramaFragment);
        }

        return fragments;
    }
}