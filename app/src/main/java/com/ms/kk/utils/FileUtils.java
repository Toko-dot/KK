package com.ms.kk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class FileUtils {

    public static String fileToBase64(String file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64;
    }


    public static String fileToAvatar(Context context, File file) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(file.getPath(), options);

            Matrix matrix = new Matrix();

            float scale = Math.max(160f / options.outWidth, 160f / options.outHeight);

            matrix.postScale(scale, scale);

            options.inJustDecodeBounds = false;

            Bitmap decodeFile = BitmapFactory.decodeFile(file.getPath(), options);

            Bitmap fb = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, false);
            decodeFile.recycle();

            File file1 = new File(context.getExternalCacheDir(), "crop.jpg");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            fb.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(file1);

            fileOutputStream.write(byteArrayOutputStream.toByteArray());


            fb.recycle();

            fileOutputStream.close();

            byteArrayOutputStream.close();

            return file1.getPath();

        } catch (Exception e) {
            return null;
        }

    }

}
