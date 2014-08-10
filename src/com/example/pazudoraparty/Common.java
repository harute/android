package com.example.pazudoraparty;

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
}
