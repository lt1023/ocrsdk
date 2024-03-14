package com.anygames.translate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
	public static boolean checkIfContainsChinese(String input) {
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
}
