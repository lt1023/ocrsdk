package com.anygames.trans.sdk.bean;

import android.graphics.Bitmap;
import com.anygames.trans.sdk.paddle.OcrResultModel;

import java.util.ArrayList;

public class OcrResult {
	/**
	 * 简单识别结果
	 */
	private String simpleText;
	/**
	 * 识别耗时
	 */
	private float inferenceTime;
	/**
	 * 框选出文字位置的图像
	 */
	private Bitmap imgWithBox;
	/**
	 * 原始识别结果
	 */
	private ArrayList<OcrResultModel> outputRawResult;

	private String getSimpleText() {
		return simpleText;
	}

	private float getInferenceTime() {
		return inferenceTime;
	}

	public Bitmap getImgWithBox() {
		return imgWithBox;
	}

	public ArrayList<OcrResultModel> getOutputRawResult() {
		return outputRawResult;
	}

	public OcrResult(String s, float v, Bitmap bitmap, ArrayList<OcrResultModel> ocrResultModels) {
		simpleText = s;
		inferenceTime = v;
		imgWithBox = bitmap;
		outputRawResult = ocrResultModels;
	}
}
