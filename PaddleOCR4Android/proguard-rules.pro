# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontshrink


-keep class com.baidu.paddle.fastdeploy.** { *; }
-keep class com.anygames.trans.sdk.OCR {
public *; }
-keep class com.anygames.trans.sdk.OcrConfig {
public *; }
-keep class com.anygames.trans.sdk.bean.OcrResult {
public *; }
-keep class com.anygames.trans.sdk.callback.OnOcrCallback {
public *; }
-keep class com.anygames.trans.sdk.callback.OnInitCallBack {
public *; }
-keep class com.anygames.trans.sdk.paddle.OcrResultModel {
public *; }

-keeppackagenames com.anygames.trans.sdk

-obfuscationdictionary dictionarytext.txt
-classobfuscationdictionary dictionarytext.txt
-packageobfuscationdictionary dictionarytext.txt
