package me.wally.imageloader

/**
 * Package: com.github.wally.base.image
 * FileName: ImageLoader
 * Date: on 2018/8/29  下午5:32
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
class ImageLoader {
    private var loader: ILoader

    companion object {
        private var instance: ImageLoader? = null

        fun getInstance(): ImageLoader {
            if (instance == null) {
                instance = ImageLoader()
            }
            return instance!!
        }
    }

    init {
        //默认使用Glide的加载器
        loader = GlideLoader()
    }

    /**
     * 设置新的加载器
     */
    fun setLoader(loader: ILoader) {
        this.loader = loader
    }

    /**
     * 获取加载器
     */
    fun getLoader(): ILoader {
        return loader
    }
}