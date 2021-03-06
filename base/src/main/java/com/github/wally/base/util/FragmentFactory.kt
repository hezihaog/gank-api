package com.github.wally.base.util

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Package: com.hzh.lazy.fragment.sample
 * FileName: FragmentFactory
 * Date: on 2017/11/24  上午11:13
 * Auther: zihe
 * Descirbe: Fragment实例生成工厂
 * Email: hezihao@linghit.com
 */

object FragmentFactory {
    /**
     * 有Arguments参数时使用
     *
     * @param context 上下文
     * @param clazz   要实例化的Fragment的Class对象
     * @param args    Bundle参数，可传null，代表不设置Fragment的Arguments
     * @return Fragment实例
     */
    fun <T : Fragment> newInstance(context: Context, clazz: Class<T>, args: Bundle? = null): T {
        val fragment = Fragment.instantiate(context, clazz.name, args)
        if (args != null) {
            fragment.arguments = args
        }
        return fragment as T
    }
}
