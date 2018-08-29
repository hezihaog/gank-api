package com.github.wally.gank.ext

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.github.wally.gank.AppContext
import com.github.wally.gank.R

/**
 * Package: com.github.wally.gank.ext
 * FileName: Ext
 * Date: on 2018/8/28  下午10:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
fun showToast(msg: String) {
    Toast.makeText(getAppContext().applicationContext, msg, Toast.LENGTH_SHORT).show()
}

fun showToastLong(msg: String) {
    Toast.makeText(getAppContext().applicationContext, msg, Toast.LENGTH_LONG).show()
}

fun Activity.showSnackMsg(msg: String) {
    val snackBar = Snackbar.make(this.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackBar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this, R.color.white))
    snackBar.show()
}

fun Fragment.showSnackMsg(msg: String) {
    this.activity ?: return
    val snackBar = Snackbar.make(this.activity!!.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackBar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this.activity!!, R.color.white))
    snackBar.show()
}

fun getAppContext(): AppContext {
    return AppContext.instance!!
}

fun getLayoutInflater(): LayoutInflater {
    return getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}

fun inflateView(layoutId: Int, parentView: ViewGroup): View {
    return getLayoutInflater().inflate(layoutId, parentView, false)
}