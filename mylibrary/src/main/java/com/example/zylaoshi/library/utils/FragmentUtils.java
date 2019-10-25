package com.example.zylaoshi.library.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class FragmentUtils {

    private FragmentUtils() {

    }

    public static Fragment replaceFragment(FragmentManager fragmentManager, int container,
                                           Class<? extends Fragment> newFragment, Bundle args) {
        return replaceFragment(fragmentManager, container, newFragment, args, false);
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager, int container,
                                           Fragment newFragment) {
        return replaceFragment(fragmentManager, container, newFragment, false);
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager, int container,
                                           Class<? extends Fragment> newFragment, Bundle args, boolean addToBackStack) {
        Fragment fragment = null;
        try {
            fragment = newFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            if (args != null && !args.isEmpty()) {
                final Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
            return replaceFragment(fragmentManager, container, fragment, addToBackStack);
        } else {
            return null;
        }
    }

    public static Fragment replaceFragment(FragmentManager fragmentManager, int container,
                                           Fragment newFragment, boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getClass().getSimpleName();
        if (newFragment != null) {
            transaction.replace(container, newFragment, tag);
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return newFragment;
    }

    public static Fragment switchFragment(FragmentManager fragmentManager, int container,
                                          Fragment currentFragment, Class<? extends Fragment> newFragment, Bundle args) {
        return switchFragment(fragmentManager, container, currentFragment, newFragment, args, false);
    }

    /**
     * @param fragmentManager
     * @param container       资源布局id
     * @param currentFragment 当前fragment
     * @param newFragment     需要显示的fragment
     * @param args            新Fragment的参数
     * @param addToBackStack  这个操作是否加入栈中，如果要实现类似返回效果，则需要。
     * @return 新显示的Fragment
     */
    public static Fragment switchFragment(FragmentManager fragmentManager, int container,
                                          Fragment currentFragment, Class<? extends Fragment> newFragment, Bundle args,
                                          boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
//        System.out.println("----------->"+fragmentManager.getFragments().size());
        // 如果在栈中找到相应的Fragment，则显示，否则重新生成一个
        if (fragment != null) {
            if (fragment != currentFragment) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.show(fragment);
                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commitAllowingStateLoss();
            } else {
                fragment.getArguments().putAll(args);
            }
            return fragment;
        } else {
            try {
                fragment = newFragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 为新的Fragment添加参数
        if (fragment != null) {
            if (args != null && !args.isEmpty()) {
                final Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
        }
        // 显示新的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.add(container, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }

    public static Fragment switchFragment1(FragmentManager fragmentManager, int container, Fragment currentFragment,
                                           Class<? extends Fragment> newFragment, Bundle args,
                                           boolean addToBackStack) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
//        System.out.println("----------->"+fragmentManager.getFragments().size());
        // 如果在栈中找到相应的Fragment，则显示，否则重新生成一个
        if (fragment != null) {
            if (fragment != currentFragment) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.show(fragment);
                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
                transaction.commitAllowingStateLoss();
            } else {
//                fragment.getArguments().putAll(args);
            }
            return fragment;
        } else {
            try {
                fragment = newFragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 显示新的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.add(container, fragment, tag);
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        return fragment;
    }

    public static Fragment addTopFragment(FragmentManager fragmentManager, int container, Class<? extends Fragment> newFragment) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final String tag = newFragment.getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            try {
                fragment = newFragment.newInstance();
                transaction.add(container, fragment, tag);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }

    //隐藏当前的fragment
    public static void hideFragment(FragmentManager fragmentManager, Class<? extends Fragment> fragment) {
        if (fragmentManager != null) {
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            final String tag = fragment.getSimpleName();
            Fragment fragmentold = fragmentManager.findFragmentByTag(tag);
            if (fragmentold != null && !fragmentold.isHidden()) {
                transaction.hide(fragmentold);
                transaction.commitAllowingStateLoss();
            }
        }

    }

    //隐藏当前的fragment
    public static void hideFragmentObject(FragmentManager fragmentManager, Fragment fragment) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment == null) {
            return;
        }
//        Log.i(FragmentUtils.class.getSimpleName(),"--->"+"隐藏---");
//        if (!fragment.isHidden()) {
        Log.i(FragmentUtils.class.getSimpleName(), "--->" + "隐藏");
        transaction.hide(fragment);
        transaction.commitAllowingStateLoss();
//        }
    }

    //当前的fragment
    public static void showFragmentObject(FragmentManager fragmentManager, int container, Fragment fragment) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//        if(fragment==null){
//            fragment=new Fragment();
//            transaction.add(container, fragment);
//            transaction.commitAllowingStateLoss();
//
//        }else if (fragment.isHidden()) {
        Log.i(FragmentUtils.class.getSimpleName(), "--->" + "显示");
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
//        }
    }

    //获取Fragment 对象
    public static Fragment getFragmentInstance(FragmentManager fragmentManager, Class<? extends Fragment> fragment) {
        final String tag = fragment.getSimpleName();
        Fragment fragmentold = fragmentManager.findFragmentByTag(tag);
        return fragmentold;
    }

}