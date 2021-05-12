package com.huihe.module_home.ui.adpter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HouseNearFragmentAdapter extends BaseFragmentStatePageAdapter {
    public HouseNearFragmentAdapter(@NotNull FragmentManager fm, @NotNull List<String> titles, @NotNull List<? extends Fragment> fragments) {
        super(fm, titles, fragments);
    }

    public HouseNearFragmentAdapter(@NotNull FragmentManager fm, @NotNull List<? extends Fragment> fragments) {
        super(fm, fragments);
    }
}
