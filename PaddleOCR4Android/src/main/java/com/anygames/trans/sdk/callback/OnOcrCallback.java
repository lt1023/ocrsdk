package com.anygames.trans.sdk.callback;

import com.anygames.trans.sdk.bean.OcrResult;

public interface OnOcrCallback {
	void onSuccess(OcrResult result);

	void onFail(Throwable e);
}
