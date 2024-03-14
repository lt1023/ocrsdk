package com.anygames.translate;

import android.graphics.Bitmap;

public interface OnTranslateCallBack {
	void onSuccess(Bitmap bitmap);

	void onFailed();
}
