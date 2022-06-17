package com.kotlin.base.utils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collections;
import java.util.List;

public class FragmentUtils {

    public static void add(@NonNull final FragmentManager fragmentManager,
                           @IdRes final int containerId,
                           @IdRes final int showIndex,
                           @NonNull Fragment... fragments) {
        for (int i = fragments.length - 1; i >= 0; --i) {
            add(fragmentManager, fragments[i], containerId, i != showIndex);
        }
    }

    public static void add(@NonNull final FragmentManager fragmentManager,
                           @NonNull Fragment fragment,
                           @IdRes final int containerId) {
        add(fragmentManager, fragment, containerId, false);
    }

    public static void add(@NonNull final FragmentManager fragmentManager,
                           @NonNull Fragment fragment,
                           @IdRes final int containerId,
                           final boolean isHide) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(name);
        if (fragmentByTag != null) {
            if (fragmentByTag.isAdded()) {
                ft.remove(fragmentByTag);
            }
        }
        ft.add(containerId, fragment, name);
        if (isHide) ft.hide(fragment);
        ft.commitAllowingStateLoss();
    }

    public static void hide(@NonNull final FragmentManager fragmentManager,
                            @NonNull Fragment fragment,
                            @IdRes final int containerId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
    }


    public static void show(@NonNull final FragmentManager fragmentManager,
                            @NonNull final Fragment destFragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(destFragment);
        ft.commitAllowingStateLoss();
    }

    public static void showHide(@NonNull final FragmentManager fragmentManager,
                                @NonNull final Fragment showFragment,
                                @NonNull final Fragment... hideFragments) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Fragment hideFragment : hideFragments) {
            if (showFragment == hideFragment) continue;
            ft.hide(hideFragment);
        }
        ft.show(showFragment);
        ft.commitAllowingStateLoss();
    }

    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack) {
        replace(srcFragment.getFragmentManager(), destFragment, srcFragment.getId(), isAddStack);
    }

    public static void replace(@NonNull final FragmentManager fragmentManager,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        ft.replace(containerId, fragment, name);
        if (isAddStack) ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    public static Fragment findFragment(@NonNull final FragmentManager fragmentManager, final Class<? extends Fragment> fragmentClass) {
        return fragmentManager.findFragmentByTag(fragmentClass.getName());
    }

    public static Fragment getTopFragment(@NonNull final FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) return null;
        return fragments.get(fragments.size() - 1);
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p >
     * <p>具体示例见FragmentActivity</p >
     *
     * @param fragment fragment
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final Fragment fragment) {
        return fragment.isResumed()
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    /**
     * 处理fragment回退键
     * <p>如果fragment实现了OnBackClickListener接口，返回{@code true}: 表示已消费回退键事件，反之则没消费</p >
     * <p>具体示例见FragmentActivity</p >
     *
     * @param fm fragment管理器
     * @return 是否消费回退事件
     */
    public static boolean dispatchBackPress(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        return fragments;
    }

    public static List<Fragment> getChildFragments(@NonNull final FragmentManager fm) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        return fragments;
    }

    public static void switchFragment(FragmentManager fragmentManager,int showIndex, @IdRes int containerId,Class<? extends Fragment>... fragmentClasses) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment showFragment = null;
        for (int i = 0; i < fragmentClasses.length; i++) {
            String name = fragmentClasses[i].getName();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(name);
            if (fragmentByTag != null) {
                if (!fragmentByTag.isAdded()) {
                    ft.add(containerId,fragmentByTag,name);
                }
            } else {
                try {
                    fragmentByTag = fragmentClasses[i].newInstance();
                    ft.add(containerId, fragmentByTag,name);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            ft.hide(fragmentByTag);
            if (i == showIndex) {
                showFragment = fragmentByTag;
            }
        }
        if (showFragment!=null) {
            ft.show(showFragment);
        }
        ft.commitAllowingStateLoss();
    }

    public static void switchFragment(FragmentManager fragmentManager,int showIndex, @IdRes int containerId,Fragment... fragments) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment showFragment = null;
        for (int i = 0; i < fragments.length; i++) {
            String name = fragments[i].getClass().getName();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(name);
            if (fragmentByTag != null) {
                if (!fragmentByTag.isAdded()) {
                    ft.add(containerId,fragmentByTag,name);
                }
            } else {
                fragmentByTag = fragments[i];
                ft.add(containerId, fragmentByTag,name);
            }
            ft.hide(fragmentByTag);
            if (i == showIndex) {
                showFragment = fragmentByTag;
            }
        }
        if (showFragment!=null) {
            ft.show(showFragment);
        }
        ft.commitAllowingStateLoss();
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }
}
