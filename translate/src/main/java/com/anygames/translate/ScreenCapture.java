package com.anygames.translate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.media.projection.MediaProjection;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenCapture {

	private static int getScreenWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		return metrics.widthPixels;
	}

	private static int getScreenHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		return metrics.heightPixels;
	}

	public static File captureAndSave(Activity context) {
		// 1. 创建一个和屏幕分辨率一样大小的Bitmap
		// Bitmap bitmap = Bitmap.createBitmap(getScreenWidth(), getScreenHeight(), Bitmap.Config.ARGB_8888);

		// 2. 使用VirtualDisplay来捕捉屏幕内容
		// 需要实现这个方法，通常在服务中实现
		// mediaProjection.createVirtualDisplay("ScreenCapture", getScreenWidth(), height, dpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, surface, null, null);

		// 3. 绘制Bitmap
		// 使用bitmapRegionDecoder.decodeRegion进行绘制，这里假设已经实现了这个方法
		// bitmap = bitmapRegionDecoder.decodeRegion(screenRect, null);

		View view = context.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmapScreen = view.getDrawingCache();

		// 4. 保存到缓存文件
		File cacheDir = context.getCacheDir();
		File screenFile = null;
		if (cacheDir != null) {
			screenFile = new File(cacheDir, "screenshot.png");
			try (FileOutputStream outputStream = new FileOutputStream(screenFile)) {
				bitmapScreen.compress(CompressFormat.PNG, 100, outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		view.destroyDrawingCache();
		return screenFile;
	}
}