package com.github.wally.base.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Package: com.github.wally.gank.util
 * FileName: ImageDownloadUtil
 * Date: on 2018/7/8  上午9:36
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ImageDownloadUtil {
    private ImageDownloadUtil() {
    }

    public static Observable<Uri> saveImageAndGetPathObservable(final Context context, final String url, final String title) {
        Observable<Uri> observable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                } catch (Exception e) {
                    emitter.onError(e);
                }
                if (bitmap == null) {
                    emitter.onError(new Exception("无法下载到图片"));
                }
                emitter.onNext(bitmap);
                emitter.onComplete();
            }
        }).flatMap(new Function<Bitmap, ObservableSource<Uri>>() {
            @Override
            public ObservableSource<Uri> apply(Bitmap bitmap) throws Exception {
                File appDir = new File(Environment.getExternalStorageDirectory(), "GankApi");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = title.replace('/', '-') + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(file);
                //通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                context.sendBroadcast(scannerIntent);
                return Observable.just(uri);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}