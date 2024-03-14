package com.anygames.translate.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.anygames.translate.TranslateManager;

public class LoadingUtils {
	private static LinearLayout mContainer;
	private static LinearLayout mResultContainer;
	private static ImageView imageView;
	private static Activity mGameActivity;

	public static void showLoadingDefault(Activity context) {
		mGameActivity = context;
		if (mContainer == null) {
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			mContainer = new LinearLayout(context);
			mContainer.setBackgroundColor(Color.parseColor("#90000000"));
			LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			containerParams.gravity = Gravity.CENTER;
			mContainer.setGravity(Gravity.CENTER);
			mContainer.setOrientation(LinearLayout.VERTICAL);
			LoadingView loadingView = new LoadingView(context);
			loadingView.startAnim();
			LinearLayout.LayoutParams loadingViewParams = new LinearLayout.LayoutParams(120, 120);
			mContainer.addView(loadingView, loadingViewParams);
			TextView textView = new TextView(context);
			textView.setText("准备中，请稍后...");
			textView.setTextSize(8);
			textView.setTextColor(Color.WHITE);
			FrameLayout.LayoutParams text_params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			text_params.gravity = Gravity.CENTER_HORIZONTAL;
			mContainer.addView(textView, text_params);
			context.addContentView(mContainer, params);
		}
		TranslateManager.isInTranslating = true;
		mGameActivity.runOnUiThread(() -> mContainer.setVisibility(View.VISIBLE));

	}

	public static void hideLoading() {
		mGameActivity.runOnUiThread(() -> mContainer.setVisibility(View.GONE));
		TranslateManager.isInTranslating = false;
	}

	public static void showResultImage(Activity context, Bitmap bitmap) {
		mGameActivity = context;
		if (mResultContainer == null) {
			mResultContainer = new LinearLayout(context);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			imageView = new ImageView(context);
			mResultContainer.addView(imageView, params);
			context.addContentView(mResultContainer, params);
			imageView.setOnClickListener(view -> hideResultImage());
		}
		mGameActivity.runOnUiThread(() -> {
			imageView.setImageBitmap(bitmap);
			mResultContainer.setVisibility(View.VISIBLE);
		});
	}

	public static void hideResultImage() {
		mGameActivity.runOnUiThread(() -> mResultContainer.setVisibility(View.GONE));
	}

}
