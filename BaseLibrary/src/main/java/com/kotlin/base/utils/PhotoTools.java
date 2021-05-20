package com.kotlin.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.kotlin.base.R;
import com.kotlin.base.utils.factory.ThreadPoolProxyFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class PhotoTools {

    public static void saveBitmap(final Context context, final String path) {

        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    //打开输入流
                    InputStream inputStream = url.openStream();
                    //对网上资源进行下载转换位图图片
                    Bitmap mBitmap = BitmapFactory.decodeStream(inputStream);
                    saveBitmap(context, mBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
     * 保存文件，文件名为当前日期
     */
    public static void saveBitmap(final Context context, Bitmap bitmap) {
        try {
            String fileDir = null;
            if (Build.BRAND.equals("Xiaomi")) { // 小米手机
                fileDir = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera";
            } else {  // Meizu 、Oppo
                fileDir = Environment.getExternalStorageDirectory().getPath() + "/DCIM";
            }
            File dirFile = new File(fileDir);
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }

            Date date = new Date();
            long time = date.getTime();
            String bitName = time + ".jpg";
            File file = new File(fileDir, bitName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), bitName, null);
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, R.string.save_pic_to_camera,Toast.LENGTH_LONG).show();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
