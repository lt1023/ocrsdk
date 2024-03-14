package com.anygames.trans.sdk;

import com.anygames.trans.sdk.bean.OcrResult;

public class OcrConfig {
	/**
	 * 模型路径（默认为 assets 目录下的预装模型）
	 * <p>
	 * 如果该值以 "/" 开头则认为是自定义路径，程序会直接从该路径加载模型； 否则认为该路径传入的是 assets 下的文件，则将其复制到 cache 目录下后加载
	 */
	// var modelPath:String = "models/ocr_v2_for_cpu",
	public static String modelPath = "models/ch_PP-OCRv2";
	/**
	 * label 词组列表路径(程序返回的识别结果是该词组列表的索引)
	 */

	public static String labelPath = "labels/ppocr_keys_v1.txt";
	/**
	 * 使用的CPU线程数
	 */
	public static int cpuThreadNum = 4;
	/**
	 * cpu power model
	 */
	public static CpuPowerMode cpuPowerMode = CpuPowerMode.LITE_POWER_FULL;
	// var cpuPowerMode: CpuPowerMode = CpuPowerMode.LITE_POWER_HIGH,
	/**
	 * Score Threshold
	 */
	public static float scoreThreshold = 0.1f;

	public static int detLongSize = 960;

	/**
	 * 检测模型文件名
	 */
	public static String detModelFilename = "det_db.nb";
	// var detModelFilename: String = "ch_ppocr_mobile_v2.0_det_opt.nb",

	/**
	 * 识别模型文件名
	 */
	public static String recModelFilename = "rec_crnn.nb";
	// var recModelFilename: String = "ch_ppocr_mobile_v2.0_rec_opt.nb",

	/**
	 * 分类模型文件名
	 */
	public static String clsModelFilename = "cls.nb";
	// var clsModelFilename: String = "ch_ppocr_mobile_v2.0_cls_opt.nb",

	/**
	 * 是否运行检测模型
	 */
	public static boolean isRunDet = true;

	/**
	 * 是否运行分类模型
	 */
	public static boolean isRunCls = true;

	/**
	 * 是否运行识别模型
	 */
	public static boolean isRunRec = true;

	public static boolean isUseOpencl = false;

	/**
	 * 是否绘制文字位置
	 * <p>
	 * 如果为 true， [OcrResult.imgWithBox] 返回的是在输入 Bitmap 上绘制出文本位置框的 Bitmap
	 * <p>
	 * 否则，[OcrResult.imgWithBox] 将会直接返回输入 Bitmap
	 */
	public static boolean isDrwwTextPositionBox = false;
}

enum CpuPowerMode {
	/**
	 * HIGH(only big cores)
	 */
	LITE_POWER_HIGH,

	/**
	 * LOW(only LITTLE cores)
	 */
	LITE_POWER_LOW,

	/**
	 * FULL(all cores)
	 */
	LITE_POWER_FULL,

	/**
	 * NO_BIND(depends on system)
	 */
	LITE_POWER_NO_BIND,

	/**
	 * RAND_HIGH
	 */
	LITE_POWER_RAND_HIGH,

	/**
	 * RAND_LOW
	 */
	LITE_POWER_RAND_LOW
}