package cn.lny.uitl;

import android.content.Context;

public class SharedPreferencesUtil {
	public static boolean getBoolean(Context context) {
		return context.getSharedPreferences("isSetPwd", context.MODE_PRIVATE).getBoolean("isSetPwd", false);
	}

	public static void setBoolean(Context context, boolean b) {
		context.getSharedPreferences("isSetPwd", context.MODE_PRIVATE).edit().putBoolean("isSetPwd", b).commit();
	}

	public static String getPwd(Context context) {
		return context.getSharedPreferences("pwd", context.MODE_PRIVATE).getString("pwd", "");
	}

	public static void setPwd(Context context, String s) {
		context.getSharedPreferences("pwd", context.MODE_PRIVATE).edit().putString("pwd", s).commit();
	}
}
