package com.equationl.paddleocr4android.app

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "el, Main"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val initBtn = findViewById<Button>(R.id.init_model)
        val startBtn = findViewById<Button>(R.id.start_model)
        val resultImg = findViewById<ImageView>(R.id.result_img)
        val resultText = findViewById<TextView>(R.id.result_text)

//        initBtn.setOnClickListener {
//            // 配置
//
//        }


//        startBtn.setOnClickListener {
        // 1.同步识别
        /*val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test2)
        ocr.runSync(bitmap)

        val bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.test3)
        ocr.runSync(bitmap2)*/

        // 2.异步识别
//            resultText.text = "开始识别"
//            val bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.game)
//            ocr.run(bitmap3, object : OcrRunCallback {
//                override fun onSuccess(result: OcrResult) {
//                    val simpleText = result.simpleText
//                    val imgWithBox = result.imgWithBox
//                    val inferenceTime = result.inferenceTime
//                    val outputRawResult = result.outputRawResult
//
//                    var text = "识别文字=\n$simpleText\n识别时间=$inferenceTime ms\n更多信息=\n"
//
//                    val wordLabels = ocr.getWordLabels()
//                    outputRawResult.forEachIndexed { index, ocrResultModel ->
//                        // 文字索引（crResultModel.wordIndex）对应的文字可以从字典（wordLabels） 中获取
//                        Log.e(TAG, "ocrResultModel: ${ocrResultModel.clsLabel}")
//                        Log.e(TAG, "ocrResultModel: ${ocrResultModel.label}")
//                        Log.e(TAG, "ocrResultModel: ${ocrResultModel.points}")
//                        ocrResultModel.wordIndex.forEach {
////                            Log.i(TAG, "onSuccess: text = ${wordLabels[it]}")
//                        }
//                        // 文字方向 ocrResultModel.clsLabel 可能为 "0" 或 "180"
//                        text += "$index: 文字方向：${ocrResultModel.clsLabel}；文字方向置信度：${ocrResultModel.clsConfidence}；识别置信度 ${ocrResultModel.confidence}；文字索引位置 ${ocrResultModel.wordIndex}；文字位置：${ocrResultModel.points}\n"
//                    }
////                    resultText.text = text
//                    resultImg.setImageBitmap(imgWithBox)
//                }
//
//                override fun onFail(e: Throwable) {
//                    resultText.text = "识别失败：$e"
//                    Log.e(TAG, "onFail: 识别失败！", e)
//                }
//            })
//        }
//        initBtn.performClick()
//        val config = OcrConfig()
//        // 2.异步初始化
//        resultText.text = "开始加载模型"
//        ocr.initModel(config, object : OcrInitCallback {
//            override fun onSuccess() {
////                resultText.text = "加载模型成功"
//                Log.i(TAG, "onSuccess: 初始化成功")
//                runOnUiThread -> startBtn.performClick()
//            }
//
//            override fun onFail(e: Throwable) {
//                resultText.text = "加载模型失败: $e"
//                Log.e(TAG, "onFail: 初始化失败", e)
//            }
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放
//        ocr.releaseModel()
    }
}