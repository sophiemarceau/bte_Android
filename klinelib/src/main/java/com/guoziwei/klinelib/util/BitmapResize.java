package com.guoziwei.klinelib.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapResize {
 public static Bitmap getResizedBitmap(Bitmap bm ) {
    int width = bm.getWidth();
    int height = bm.getHeight();
    float scaleWidth = 0.5f;
    float scaleHeight = 0.5f;
    // CREATE A MATRIX FOR THE MANIPULATION
    Matrix matrix = new Matrix();
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight);

    // "RECREATE" THE NEW BITMAP
    Bitmap resizedBitmap = Bitmap.createBitmap(
        bm, 0, 0, width, height, matrix, false);
    bm.recycle();
    return resizedBitmap;
}

    /**
     * 根据宽度等比例缩放图片
     * @param bm
     * @param targetScaleWidth
     * @return
     */
   public static Bitmap resizeScaleBitmap(Bitmap bm, int targetScaleWidth) {
      int width = bm.getWidth();
      int height = bm.getHeight();
      float scaleWidth = (float) targetScaleWidth / width;
      float scaleHeight = scaleWidth;
      // CREATE A MATRIX FOR THE MANIPULATION
      Matrix matrix = new Matrix();
      // RESIZE THE BIT MAP
      matrix.postScale(scaleWidth, scaleHeight);

      // "RECREATE" THE NEW BITMAP
      Bitmap resizedBitmap = Bitmap.createBitmap(
              bm, 0, 0, width, height, matrix, false);
      return resizedBitmap;
   }
}
