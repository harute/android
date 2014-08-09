package com.example.pazudoraparty;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.pazudoraparty.AppInfo;
import com.example.pazudoraparty.AppInfoArrayAdapter;
import com.example.pazudoraparty.Http;
import com.example.pazudoraparty.Kakusei;
import com.example.pazudoraparty.R;
import com.example.pazudoraparty.Sample2;
import com.example.pazudoraparty.StringResponseHandler;
import com.example.pazudoraparty.Common;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class Partyadd extends Activity {

    int width;
    int height;

    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //StrictModeを設定 penaltyDeathを取り除く
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.partyadd);

        //sample
        String no  ="";
        Intent intent = getIntent();
        if (intent != null) {
            no = intent.getStringExtra("NO");
            Log.d("Tag", no);
        }

        getDisplay();
        setImageButton(R.id.imgbtn_id01, "001.png");
        setImageButton(R.id.imgbtn_id02, "002.png");
        setImageButton(R.id.imgbtn_id03, "003.png");
        setImageButton(R.id.imgbtn_id04, "004.png");
        setImageButton(R.id.imgbtn_id05, "002.png");
        setImageButton(R.id.imgbtn_id06, "002.png");

        // ボタン押下時に画面遷移 sample
        ImageButton btn = (ImageButton)findViewById(R.id.imgbtn_id01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                //intent.putExtra("KEYWORD", "test");
                startActivity(intent);
            }
        });

        // ボタン押下時に画面遷移 sample
        ImageButton btn2 = (ImageButton)findViewById(R.id.imgbtn_id02);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // ここからList処理
        listView1 = (ListView)findViewById(R.id.listView1);

        // http処理
        Http.Request request = new Http.Request();
        request.url = "https://but-pazu-test.ssl-lolipop.jp/skillSearch.php?leader[0]=" + no + "&leader[1]=1201&member[0]=1234&member[1]=1235&member[2]=1236&member[3]=1236";
        Log.d("Tag", request.url);

        Http.Response response = Http.requestSync(request, StringResponseHandler.getInstance());
        Sample2 sample = new Sample2();
        if (response.code == 200) {
            String str = (String) response.value;
            Gson gson = new Gson();
            sample = gson.fromJson(str, Sample2.class);
        }

        @SuppressWarnings("unchecked")
        ArrayList<String>[] arrtest = new ArrayList[50];
        arrtest[0] = new ArrayList<String>();
        arrtest[0].add(sample.ls_desc.split(",")[0] + "\n" + sample.ls_desc.split(",")[1]);
        arrtest[0].add("http://pazu-test.but.jp/image/004.png");

        // 覚醒
        int cnt = 1;
        for (Kakusei kakuraw : sample.kakusei) {
            arrtest[cnt] = new ArrayList<String>();
            arrtest[cnt].add(kakuraw.desc);
            arrtest[cnt].add(kakuraw.url);
            cnt++;
        }

        // ここで繰り返し処理
        //入力ストリームを開く
        InputStream istream;
        List<AppInfo> items = new ArrayList<AppInfo>();
        try {
            for (int i = 0; i < cnt; i++) {
                URL url = new URL(arrtest[i].get(1));
                istream = url.openStream();
                Drawable d = Drawable.createFromStream(istream, "webimg");
                AppInfo bbb = new AppInfo();
                bbb.setTextData(arrtest[i].get(0));
                bbb.setImagaData(d);
                items.add(bbb);
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        //adapter = new AppInfoArrayAdapter(this, R.layout.raw, R.id.row_textview1, items);
        AppInfoArrayAdapter adapter = new AppInfoArrayAdapter(this, R.layout.raw, items);
        listView1.setAdapter(adapter);

    }

    private void setImageButton(int imgbtnId, String fileName) {
        ImageButton img = (ImageButton)findViewById(imgbtnId);

        // 画像のURL
        String urlString="http://pazu-test.but.jp/image/" + fileName;

        try {
            //URLクラス
            URL url = new URL(urlString);
            //入力ストリームを開く
            InputStream istream = url.openStream();

            //画像をDrawableで取得
            Drawable d = Drawable.createFromStream(istream, "webimg");

            //入力ストリームを閉じる
            istream.close();

            img.setImageDrawable(d);
            android.view.ViewGroup.LayoutParams params = img.getLayoutParams();
            params.height = width / 6;
            params.width = width / 6;

            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } catch (Exception e) {
            System.out.println("nuu: "+e);
        }

    }

    private void getDisplay() {
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        width = size.x;
        height = size.y;

    }
}

/*
* Json文字列をこのクラスに変化するa
*/
class Sample2{
    public String ls_name;
    public String ls_desc;
    public Kakusei[] kakusei;
}

class Kakusei {
    public String desc;
    public String url;
}