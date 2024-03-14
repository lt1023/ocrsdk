package com.anygames.trans.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.anygames.trans.sdk.bean.OcrResult;
import com.anygames.trans.sdk.callback.OnInitCallBack;
import com.anygames.trans.sdk.callback.OnOcrCallback;
import com.anygames.trans.sdk.paddle.Predictor;

import java.util.Vector;

public class OCR {

	private Context context;

	public OCR(Context ctx) {
		context = ctx;
	}

	private Predictor predictor = new Predictor();
	private ImageView resultImageView = null;
	private String modelPath = "models/ocr_v2_for_cpu";
	private String labelPath = "labels/ppocr_keys_v1.txt";
	private int cpuThreadNum = 4;
	private String cpuPowerMode = "LITE_POWER_HIGH";
	private float scoreThreshold = 0.1f;
	private String[] modelFileNames = new String[] {};
	private boolean isRunCls = true;
	private boolean isRunDet = true;
	private boolean isRunRec = true;
	private boolean isUseOpencl = false;
	private int detLongSize = 960;
	private boolean isDrwwTextPositionBox = false;

	public Predictor getPredictor() {
		return predictor;
	}

	public Vector<String> getWordLabels() {
		return predictor.wordLabels;
	}

	/**
	 * 初始化模型（同步）
	 *
	 * @param config
	 *            配置信息
	 */
	public boolean initModelSync(OcrConfig config) {
		if (config != null) {
			setConfig(config);
		}
		try {
			return predictor.init(context, modelPath, labelPath, isUseOpencl, cpuThreadNum, cpuPowerMode, detLongSize, scoreThreshold, modelFileNames, isDrwwTextPositionBox);
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 初始化模型（异步）
	 *
	 * @param config
	 *            配置信息
	 * @param callback
	 *            初始化回调
	 */
	public void initModel(OcrConfig config, OnInitCallBack callback) {
		new Thread(() -> {
			try {
				boolean inited = initModelSync(config);
				if (inited) {
					callback.onSuccess();
					return;
				}
				callback.onFail(new RuntimeException("unknown err"));
			} catch (Throwable e) {
				callback.onFail(e);
			}
		}).start();
	}

	/**
	 * 开始运行识别模型（同步）
	 *
	 * @param bitmap
	 *            欲识别的图片
	 */
	public OcrResult runSync(Bitmap bitmap) {
		if (!predictor.isLoaded()) {
			return null;
		} else {
			predictor.setInputImage(bitmap); // 载入图片
			if (runModel()) {
				OcrResult ocrResult = new OcrResult(predictor.outputResult(), predictor.inferenceTime(), predictor.outputImage(), predictor.outputRawResult());
				return ocrResult;
			}
		}
		return null;
	}

	/**
	 * 开始运行识别模型（异步）2
	 *
	 * @param bitmap
	 *            欲识别的图片
	 * @param callback
	 *            识别结果回调
	 */
	public void run(Bitmap bitmap, OnOcrCallback callback) {
		new Thread(() -> {
			OcrResult result = runSync(bitmap);
			if (result != null) {
				callback.onSuccess(result);
			} else {
				callback.onFail(new RuntimeException(""));
			}
		}).start();
	}

	/**
	 * 释放模型
	 */
	public void releaseModel() {
		predictor.releaseModel();
	}

	public boolean runModel() {
		try {
			return predictor.isLoaded() && predictor.runModel(isRunDet, isRunCls, isRunRec);
		} catch (Throwable e) {
			return false;
		}
	}

	private void setConfig(OcrConfig config) {
		this.modelPath = config.modelPath;
		this.labelPath = config.labelPath;
		this.cpuThreadNum = config.cpuThreadNum;
		this.cpuPowerMode = config.cpuPowerMode.name();
		this.scoreThreshold = config.scoreThreshold;
		this.modelFileNames = new String[] { config.detModelFilename, config.recModelFilename, config.clsModelFilename };
		this.detLongSize = config.detLongSize;
		this.isRunDet = config.isRunDet;
		this.isRunCls = config.isRunCls;
		this.isRunRec = config.isRunRec;
		this.isUseOpencl = config.isUseOpencl;
		this.isDrwwTextPositionBox = config.isDrwwTextPositionBox;
	}
}
