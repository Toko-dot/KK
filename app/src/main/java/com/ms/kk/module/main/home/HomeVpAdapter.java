package com.ms.kk.module.main.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ms.kk.model.net.entity.respond.Type;
import com.ms.kk.module.main.home.drama.DramaFragment;

import java.util.List;

public class HomeVpAdapter extends FragmentPagerAdapter {
    private List<Type> typeList;
    private List<Fragment> fragmentList;
    public HomeVpAdapter(@NonNull FragmentManager fm, int behavior, List<Type> typeList,List<Fragment> fragmentList) {
        super(fm, behavior);
        this.typeList = typeList;
        this.fragmentList=fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return typeList == null ? 0 : typeList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return typeList.get(position).getType();
    }
}
