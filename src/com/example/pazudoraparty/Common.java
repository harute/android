package com.example.pazudoraparty;

import android.util.Log;

public class Common {
    /* ラベル上部の文字列を生成 */
    public static String getNo(String no, String rare) {
        String ret = "No" + no;
        ret += " ";
        String star = "";
        for (int i = 0; i < Integer.parseInt(rare) - 1; i++) {
            star += "☆";
        }
        ret += star;
        return ret;
    }

    /* NullとEmptyを判定する */
    public static boolean isEmpty(String value) {
        if ( value == null || value.length() == 0 ) {
            return true;
        } else {
            return false;
        }
    }

    // 文字列JOIN 文字なし判定なし
    public static String join(String[] strarr, String sepa) {
        String res = strarr[0];
        for (int i = 1; i < strarr.length; i++) {
            res += sepa + strarr[i];
        }
        return res;
    }

    public static String getMergeNO(String base, String point, String after) {
        String[] arr = base.split(",");
        if (Integer.parseInt(point) > arr.length) {
            return base;
        }
        arr[Integer.parseInt(point) - 1] = after;
        return join(arr, ",");
    }

    public static void putLogMessage(String msg) {
        StackTraceElement[] es = new Exception().getStackTrace();
        Log.d("Tag", es[1].getClassName() + " " + msg);
    }
}
