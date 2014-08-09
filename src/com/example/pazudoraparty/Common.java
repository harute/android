package com.example.pazudoraparty;

public class Common {
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

    public static boolean isEmpty(String value) {
        if ( value == null || value.length() == 0 ) {
            return true;
        } else {
            return false;
        }
    }
}
