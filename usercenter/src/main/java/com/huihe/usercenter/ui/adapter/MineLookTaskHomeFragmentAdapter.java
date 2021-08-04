package com.huihe.usercenter.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kotlin.base.ui.adapter.BaseFragmentStatePageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MineLookTaskHomeFragmentAdapter extends BaseFragmentStatePageAdapter {
    public MineLookTaskHomeFragmentAdapter(@NotNull FragmentManager fm, @NotNull List<String> titles, @NotNull List<? extends Fragment> fragments) {
        super(fm, titles, fragments);
    }
}
