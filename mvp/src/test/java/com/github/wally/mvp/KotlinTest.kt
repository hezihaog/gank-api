package com.github.wally.mvp

import org.junit.Test
import java.util.function.Consumer

/**
 * Package: com.github.wally.mvp
 * FileName: KotlinTest
 * Date: on 2018/7/7  下午11:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class KotlinTest {
    @Test
    fun testForEach() {
        val list = listOf<String>("1", "2", "3", "5")
        //不带下标
        list.forEach(Consumer { println(it) })
        //for i ，获取角标和值
        list.forEachIndexed { index, value -> println("index: ${index}, value: ${value}") }
    }

    @Test
    fun testOnClick() {
        //匿名内部类
        setOnClickListener(object : OnClickListener {
            override fun onClick() {
                println("msg：onClick")
            }
        })
    }

    @Test
    fun testFilter() {
        val list = listOf<Int>(1, 2, 4, 6, 8)
        //过滤不是偶数的元素
        val result = list.filter { it % 2 == 0 }
        //过滤符合条件的
        //val result = list.filterNot { it % 2 == 0 }
        //过滤null值
        //result.filterNotNull();
        println("result：${result}")
    }

    /**
     * 反转集合
     */
    @Test
    fun textReversed() {
        var list = listOf(1, 2, 3, 4, 5)
        list = list.reversed()
        println(list)
    }

    @Test
    fun testSort() {
        var list = listOf(1, 3, 5, 4, 6)
        //自然排序
        //list = list.sorted()
        //按一定排序规则进行排序
        //list = list.sortedBy { it % 3 }
        //降序
        list = list.sortedDescending();
        println(list)
    }

    interface OnClickListener {
        fun onClick()
    }

    private var listener: OnClickListener? = null;

    private fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
        listener.onClick()
    }
}