package com.github.wally.base.util

/**
 * Package: com.github.wally.gank.util
 * FileName: IWaitDialogHandler
 * Date: on 2018/6/17  下午10:40
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
interface IWaitDialogHandler {
    fun showWaitDialog(msg: String? = "")

    fun hideWaitDialog()
}