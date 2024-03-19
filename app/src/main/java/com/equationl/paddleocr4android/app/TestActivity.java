package com.equationl.paddleocr4android.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TestActivity extends Activity {
	private String TAG = "el, Main";
	private ImageView result_img;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		result_img = findViewById(R.id.result_img);
//		Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.game);
//		result_img.setImageBitmap(bitmap3);
//		result_img.setOnClickListener(view -> start(TestActivity.this));
	}

	private void start(Activity context) {

//		TranslateManager.startTranslate(context, new OnTranslateCallBack() {
//			@Override
//			public void onSuccess(Bitmap bitmap) {
//
//			}
//
//			@Override
//			public void onFailed() {
//
//			}
//		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		TranslateManager.onDestroy();
	}
}
