package com.huihe.customercenter.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomerDetailFragmentAdapter extends BaseFragmentStatePageAdapter {
    public CustomerDetailFragmentAdapter(@NotNull FragmentManager fm, @NotNull List<String> titles, @NotNull List<? extends Fragment> fragments) {
        super(fm, titles, fragments);
    }

    public CustomerDetailFragmentAdapter(@NotNull FragmentManager fm, @NotNull List<? extends Fragment> fragments) {
        super(fm, fragments);
    }
}
