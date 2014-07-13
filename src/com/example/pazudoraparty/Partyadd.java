package com.example.pazudoraparty;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Partyadd extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.partyadd);

        TextView txtView = (TextView)findViewById(R.id.textView1);
        txtView.setText("置き換え後");
    }

}
