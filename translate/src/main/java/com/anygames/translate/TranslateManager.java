package com.anygames.translate;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.util.Log;
import com.anygames.sdk.fuck.FuckSDK;
import com.anygames.sdk.fuck.bean.ResultBean;
import com.anygames.sdk.fuck.callback.OnTranslateCallBack;
import com.anygames.trans.sdk.OCR;
import com.anygames.trans.sdk.OcrConfig;
import com.anygames.trans.sdk.bean.OcrResult;
import com.anygames.trans.sdk.callback.OnInitCallBack;
import com.anygames.trans.sdk.callback.OnOcrCallback;
import com.anygames.trans.sdk.paddle.OcrResultModel;
import com.anygames.translate.view.LoadingUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TranslateManager {
	private static OCR ocr;
	private static final String TAG = "TranslateManager";

	public static boolean isInTranslating = false;
	private static Context mContext;

	public static void onDestroy() {
		if (ocr != null) {
			ocr.releaseModel();
		}
		if (mContext == null)
			return;
		FuckSDK.getInstance().onDestroy(mContext);
	}

	public static void init(Application application) {
		mContext = application;
		FuckSDK.getInstance().initApplication(application);
		ocr = new OCR(application);
		OcrConfig ocrConfig = new OcrConfig();
		ocr.initModel(ocrConfig, new OnInitCallBack() {
			@Override
			public void onSuccess() {
				Log.e(TAG, "initModel onSuccess: ");
			}

			@Override
			public void onFail(Throwable throwable) {
				Log.e(TAG, "onFail: ", throwable);
			}
		});
		FuckSDK.getInstance().initPlugin(application);
	}

	public static void startTranslate(Activity context, com.anygames.translate.OnTranslateCallBack callBack) {
		if (isInTranslating)
			return;
		TranslateManager.isInTranslating = true;
		File screenFile = ScreenCapture.captureAndSave(context);
		if (screenFile == null) {
			LoadingUtils.hideLoading();
			return;
		}
		LoadingUtils.showLoadingDefault(context);
		if (!screenFile.exists()) {
			LoadingUtils.hideLoading();
			return;
		}
		Bitmap bitmap = null;
		try {
			FileInputStream inputStream = new FileInputStream(screenFile);
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (FileNotFoundException e) {
			LoadingUtils.hideLoading();
			return;
		}
		if (bitmap == null) {
			LoadingUtils.hideLoading();
			return;
		}
		Bitmap outBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		ocr.run(bitmap, new OnOcrCallback() {
			@Override
			public void onSuccess(OcrResult ocrResult) {
				LoadingUtils.hideLoading();
				// Log.e(TAG, "onSuccess: " + ocrResult);
				// result_img.setImageBitmap(ocrResult.getImgWithBox());
				ArrayList<OcrResultModel> outputRawResult = ocrResult.getOutputRawResult();
				try {
					JSONObject object = new JSONObject();
					JSONArray arr = new JSONArray();
					for (OcrResultModel model : outputRawResult) {
						// object.put("label", model.getClsLabel());
						// Log.e(TAG, "onSuccess: " + model.getLabel());
						// Log.e(TAG, "onSuccess getLabel_id: " + model.getLabel_id());
						// Log.e(TAG, "onSuccess: " + model.getPoints());
						// Log.e(TAG, "onSuccess getClsIdx: " + model.getClsIdx());
						// arr.put(model);
						String label = model.getLabel();
						if (TextUtils.checkIfContainsChinese(label))
							continue;
						JSONObject item = new JSONObject();
						item.put("label", label);
						item.put("label_id", model.getLabel_id());
						// item.put("pointx", model.getPoints().get(0).x);
						// item.put("pointy", model.getPoints().get(0).y);
						arr.put(item);

						// FuckSDK.getInstance().startTranslate(TestActivity.this, model.getLabel(), null);
					}
					object.put("label_info", arr);
					FuckSDK.getInstance().startTranslate(context, object.toString(), new OnTranslateCallBack() {
						@Override
						public void onSuccess(List<ResultBean> resultBeanList) {
							// for (ResultBean bean : resultBeanList) {
							// Log.e(TAG, "onSuccess: " + bean.getLabel_id());
							// Log.e(TAG, "onSuccess: " + bean.getSrcText());
							// Log.e(TAG, "onSuccess: " + bean.getDestText());
							// }
							// Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.game).copy(Bitmap.Config.ARGB_8888, true);

							Bitmap newBitmap = paintText(context, outBitmap, outputRawResult, resultBeanList);
							// result_img.setImageBitmap(bitmap3);
							callBack.onSuccess(newBitmap);
							LoadingUtils.showResultImage(context, newBitmap);
						}

						@Override
						public void onFailed() {
							Log.e(TAG, "onFailed: ");
							callBack.onFailed();
							LoadingUtils.hideLoading();
						}
					});
				} catch (Throwable t) {
					LoadingUtils.hideLoading();
				}
			}

			@Override
			public void onFail(Throwable throwable) {
				// Log.e(TAG, "onFail: ", throwable);
				LoadingUtils.hideLoading();
			}
		});
	}

	private static Bitmap paintText(Context context, Bitmap bitmap, List<OcrResultModel> results, List<ResultBean> resultBeans) {
		Canvas canvas = new Canvas(bitmap);
		Paint paintFillAlpha = new Paint();
		paintFillAlpha.setStyle(Paint.Style.FILL);
		paintFillAlpha.setColor(Color.BLACK);
		paintFillAlpha.setAlpha(180);// 取值范围为0~255，数值越小越透明。

		// Paint paint = new Paint();
		// paint.setColor(Color.parseColor("#3B85F5"));
		// paint.setStrokeWidth(5);
		// paint.setStyle(Paint.Style.STROKE);

		Paint textPaint = new Paint();
		textPaint.setColor(Color.RED);
		textPaint.setStrokeWidth(2);
		textPaint.setStyle(Paint.Style.FILL);
		float textSize = convertDpToPixel(20, context);
		textPaint.setTextSize(textSize);
		textPaint.setShadowLayer(2, 2, 2, Color.YELLOW);
		textPaint.setFakeBoldText(true);

		canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paintFillAlpha);

		// new Rect()
		for (OcrResultModel result : results) {
			// Path path = new Path();
			List<Point> points = result.getPoints();
			if (points.size() == 0) {
				continue;
			}
			int left = -100;
			int top = -100;
			int right = -100;
			int bottom = -100;
			int width = 0;
			int height = 0;
			for (Point point : points) {
				if (left == -100) {
					left = point.x;
				}
				if (point.x < left) {
					left = point.x;
				}
				if (right == -100) {
					right = point.x;
				}
				if (point.x > right) {
					right = point.x;
				}
				if (bottom == -100) {
					bottom = point.y;
				}
				if (point.y > bottom) {
					bottom = point.y;
				}
				if (top == -100) {
					top = point.y;
				}
				if (point.y < top) {
					top = point.y;
				}
			}
			width = right - left;
			height = bottom - top;
			// textPaint.setTextSize(height);

			// Log.e(TAG, "paintText: left=" + left);
			// Log.e(TAG, "paintText: right=" + right);
			// Log.e(TAG, "paintText: top=" + top);
			// Log.e(TAG, "paintText: bottom=" + bottom);
			// Log.e(TAG, "paintText: width=" + width);
			// Log.e(TAG, "paintText: height=" + height);
			// path.moveTo(points.get(0).x, points.get(0).y);
			// for (int i = points.size() - 1; i >= 0; i--) {
			// Point p = points.get(i);
			// path.lineTo(p.x, p.y);
			// }
			// canvas.drawPath(path, paint);
			// canvas.drawPath(path, paintFillAlpha);

			// Log.e(TAG, "paintText getLabel_id: " + result.getLabel_id());
			for (ResultBean bean : resultBeans) {
				if (bean.getLabel_id() == result.getLabel_id()) {
					String destText = bean.getDestText();
					String srxText = bean.getSrcText();
					// Log.e(TAG, "paintText: " + destText);
					int textLength = width / srxText.length();
					float startX = left + textLength;
					int center = height / 2;
					float startY = top + center + textSize / 2;
					canvas.drawText(destText, startX, startY, textPaint);
					break;
				}
			}
		}
		return bitmap;
	}

	public static float convertDpToPixel(float dp, Context context) {
		return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

	public static float convertPixelsToDp(float px, Context context) {
		return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

}
