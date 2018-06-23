package com.github.wally.mvp.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Package: com.hzh.lazy.fragment.sample
 * FileName: FragmentFactory
 * Date: on 2017/11/24  上午11:13
 * Auther: zihe
 * Descirbe: Fragment实例生成工厂
 * Email: hezihao@linghit.com
 */

public class FragmentFactory {
    private FragmentFactory() {
    }

    /**
     * 无需传入Arguments参数时使用
     *
     * @param context 上下文
     * @param clazz   要实例化的Fragment的Class对象
     * @return Fragment实例
     */
    public static <T extends Fragment> Fragment newInstance(Context context, Class<T> clazz) {
        return newInstance(context, clazz, null);
    }

    /**
     * 有Arguments参数时使用
     *
     * @param context 上下文
     * @param clazz   要实例化的Fragment的Class对象
     * @param args    Bundle参数
     * @return Fragment实例
     */
    public static <T extends Fragment> T newInstance(Context context, Class<T> clazz, @Nullable Bundle args) {
        Fragment fragment = Fragment.instantiate(context, clazz.getName(), args);
        if (args != null) {
            fragment.setArguments(args);
        }
        return (T) fragment;
    }
}
