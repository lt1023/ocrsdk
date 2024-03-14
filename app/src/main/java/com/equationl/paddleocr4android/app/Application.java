package com.equationl.paddleocr4android.app;

import com.anygames.translate.TranslateManager;

public class Application extends android.app.Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// FuckSDK.getInstance().initApplication(this);
		TranslateManager.init(this);
	}
}
