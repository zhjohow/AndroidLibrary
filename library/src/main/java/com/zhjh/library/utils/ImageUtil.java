package com.zhjh.library.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhjh on 2016/1/11.
 */
public class ImageUtil {

    public final static String SDCARD_MNT = "/mnt/sdcard";
    public final static String SDCARD = Environment.getExternalStorageDirectory().getPath();

//    /**
//     * 圆角图片
//     */
//
//    public static void displayImage(String uri, ImageView imageView) {
//
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
////                .showImageOnLoading(com.ruyicrm.app.R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
//                .showImageForEmptyUri(com.ruyicrm.app.R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(90))//是否设置为圆角，弧度为多少
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
//
//        ImageLoader.getInstance().displayImage(uri, imageView, defaultOptions);
//    }
//
//
//    public static void displayFromSDCard(String uri, ImageView imageView) {
//        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
//        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
//    }
//
//    /**
//     * 从assets文件夹中异步加载图片
//     *
//     * @param imageName
//     *            图片名称，带后缀的，例如：1.png
//     * @param imageView
//     */
//    public static  void dispalyFromAssets(String imageName, ImageView imageView) {
//        // String imageUri = "assets://image.png"; // from assets
//        ImageLoader.getInstance().displayImage("assets://" + imageName,
//                imageView);
//    }
//
//    /**
//     * 从drawable中异步加载本地图片
//     *
//     * @param imageId
//     * @param imageView
//     */
//    public static void displayFromDrawable(int imageId, ImageView imageView) {
//        // String imageUri = "drawable://" + R.drawable.image; // from drawables
//        // (only images, non-9patch)
//        ImageLoader.getInstance().displayImage("drawable://" + imageId,
//                imageView);
//    }
//
//    /**
//     * 从内容提提供者中抓取图片
//     */
//    public static  void displayFromContent(String uri, ImageView imageView) {
//        // String imageUri = "content://media/external/audio/albumart/13"; //
//        // from content provider
//        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
//    }

    /**
     * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
     *
     * @param
     * @return
     */
    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;

        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);

        String pre1 = "file://" + SDCARD + File.separator;
        String pre2 = "file://" + SDCARD_MNT + File.separator;

        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        }
        return filePath;
    }

    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }

        return imagePath;
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static String compressImage(Context context, String srcPath, String destPath, int maxW, int maxH) throws Exception {
        String imageFile = "";
        try {
            File f = new File(srcPath);
            BitmapFactory.Options newOpts = getSizeOpt(f, maxW, maxH);
            InputStream is = new FileInputStream(f);
            String type = destPath.substring(destPath.lastIndexOf(".") + 1);
            if ("png".equals(type)) {
                imageFile = CompressPngFile(context, srcPath, is, newOpts, destPath);
            } else {
                imageFile = CompressJpgFile(context, srcPath, is, newOpts, destPath);
            }
            if (is != null)
                is.close();
            if (srcPath != null) {
                new File(srcPath).delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return imageFile;
    }

    /**
     * 先压缩图片大小
     *
     * @return
     * @throws IOException
     */
    public static BitmapFactory.Options getSizeOpt(File file, int maxWidth, int maxHeight) throws IOException {
        // 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        InputStream is = new FileInputStream(file);
        double ratio = getOptRatio(is, maxWidth, maxHeight);
        LogUtil.e("ratio="+ratio);
        is.close();
        newOpts.inSampleSize = (int) ratio;
        newOpts.inJustDecodeBounds = true;
        is = new FileInputStream(file);
        BitmapFactory.decodeStream(is, null, newOpts);
        is.close();
        int loopcnt = 0;
        while (newOpts.outWidth > maxWidth) {
            newOpts.inSampleSize += 1;
            is = new FileInputStream(file);
            BitmapFactory.decodeStream(is, null, newOpts);
            is.close();
            if (loopcnt > 3) break;
            loopcnt++;
        }
        newOpts.inJustDecodeBounds = false;
        return newOpts;
    }

    /**
     * 计算起始压缩比例
     * 先根据实际图片大小估算出最接近目标大小的压缩比例
     * 减少循环压缩的次数
     *
     * @return
     */
    public static double getOptRatio(InputStream is, int maxWidth, int maxHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, opts);
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;
        int destWidth = 0;
        int destHeight = 0;
        // 缩放的比例
        double ratio = 1.0;
        double ratio_w = 0.0;
        double ratio_h = 0.0;
        // 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
        if (srcWidth <= maxWidth && srcHeight <= maxHeight) {
            return ratio;   //小于屏幕尺寸时，不压缩
        }
        if (srcWidth > srcHeight) {
            ratio_w = srcWidth / maxWidth;
            ratio_h = srcHeight / maxHeight;
        } else {
            ratio_w = srcHeight / maxWidth;
            ratio_h = srcWidth / maxHeight;
        }
        if (ratio_w > ratio_h) {
            ratio = ratio_w;
        } else {
            ratio = ratio_h;
        }
        return ratio;
    }

    private static String CompressPngFile(Context c, String oldPath, InputStream is, BitmapFactory.Options newOpts, String filePath) throws Exception {
        Bitmap destBm = BitmapFactory.decodeStream(is, null, newOpts);

        destBm = rotateBitmapToExifOrientation(c, oldPath, destBm);

        return CompressPngFile(destBm, newOpts, filePath);
    }

    public static Bitmap rotateBitmapToExifOrientation(final Context context, final String filePath, final Bitmap bitmap) {
        final int orientation = ImageUtil.getExifOrientationFromJpeg(context, filePath);
        final int degrees;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degrees = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degrees = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degrees = 270;
                break;
            default:
                // Do nothing
                return bitmap;
        }

        final Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


    }


    public static int getExifOrientationFromJpeg(final Context context, final String filePath) {
        try {
            final ExifInterface exif = new ExifInterface(filePath);
            return Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION));

        } catch (final Exception e) {

            return 0;
        }
    }

    private static String CompressPngFile(Bitmap destBm, BitmapFactory.Options newOpts, String filePath) throws Exception {
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        if (destBm == null) {
            return null;
        } else {
            File destFile = createNewFile(filePath);

            // 创建文件输出流
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                // 存储

                // 存储
                byte[] temp = new byte[1024] ;
                //添加时间水印
                Bitmap newbm = addTimeFlag(destBm) ;

                int rate = 100;
                newbm.compress(Bitmap.CompressFormat.PNG, rate, os);

                os.close();
                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }

    public static File createNewFile(String filePath) {
        if (filePath == null)
            return null;
        File newFile = new File(filePath);
        try {
            if (!newFile.exists()) {
                int slash = filePath.lastIndexOf('/');
                if (slash > 0 && slash < filePath.length() - 1) {
                    String dirPath = filePath.substring(0, slash);
                    File destDir = new File(dirPath);
                    if (!destDir.exists()) {
                        destDir.mkdirs();
                    }
                }
            } else {
                newFile.delete();
            }
            newFile.createNewFile();
        } catch (IOException e) {
            return null;
        }
        return newFile;
    }

    private static String CompressJpgFile(Context c, String oldPath,InputStream is, BitmapFactory.Options newOpts, String filePath) throws Exception {
//        Bitmap destBm = BitmapFactory.decodeStream(is, null, newOpts);
        Bitmap destBm = BitmapFactory.decodeStream(is, null, newOpts);
        destBm = rotateBitmapToExifOrientation(c, oldPath, destBm);

        return CompressJpgFile(destBm, newOpts, filePath);
    }


    private static String CompressJpgFile(Bitmap destBm, BitmapFactory.Options newOpts, String filePath) throws Exception {
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        if (destBm == null) {
            return null;
        } else {
            File destFile = createNewFile(filePath);

            // 创建文件输出流
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                // 存储
                byte[] temp = new byte[1024] ;
                //添加时间水印
                Bitmap newbm = addTimeFlag(destBm) ;

                int rate = 100;
                newbm.compress(Bitmap.CompressFormat.JPEG, rate, os);
                os.close();
                Log.e("ImageUtil","file size :"+destFile.length());
                return filePath;
            } catch (Exception e) {
                filePath = null;
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }

    /**
     * 添加时间水印
     * @param
     */
    private static Bitmap addTimeFlag(Bitmap src){
        // 获取原始图片与水印图片的宽与高
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas mCanvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        mCanvas.drawBitmap(src, 0, 0, null);
        //添加文字
        Paint textPaint = new Paint();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        textPaint.setColor(Color.YELLOW) ;
        textPaint.setTextSize(16);

        mCanvas.drawText(time, (float)(w*1)/14, (float)(h*17)/18, textPaint);
        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.restore();
        return newBitmap ;
    }
}