package com.example.pazudoraparty;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * 登録用画面
 * @author hide
 *
 */
public class Partyadd extends Activity {

    int width;
    int height;

    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // StrictModeを設定 penaltyDeathを取り除く
        // メインスレッド上からネットワーク通信を行うとandroid.os.Networkonmainthreadexception例外を吐いてクラッシュ
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.partyadd);

        // Intent受け取り値を収納(xxx→this)
        String[] noArr = getIntentValueNo();    // xx,xx,xx,xx,xx,xx
        String[] imageStr = getImageFilePathArr(noArr);

        getDisplay();

        setImageButton(R.id.imgbtn_id01, imageStr[0]);
        setImageButton(R.id.imgbtn_id02, imageStr[1]);
        setImageButton(R.id.imgbtn_id03, imageStr[2]);
        setImageButton(R.id.imgbtn_id04, imageStr[3]);
        setImageButton(R.id.imgbtn_id05, imageStr[4]);
        setImageButton(R.id.imgbtn_id06, imageStr[5]);

        // ボタン押下時に画面遷移 sample
        ImageButton btn01 = (ImageButton)findViewById(R.id.imgbtn_id01);
        ImageButton btn02 = (ImageButton)findViewById(R.id.imgbtn_id02);
        ImageButton btn03 = (ImageButton)findViewById(R.id.imgbtn_id03);
        ImageButton btn04 = (ImageButton)findViewById(R.id.imgbtn_id04);
        ImageButton btn05 = (ImageButton)findViewById(R.id.imgbtn_id05);
        ImageButton btn06 = (ImageButton)findViewById(R.id.imgbtn_id06);

        // Intent渡し用
        final String noparam = Common.join(noArr, ",");

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                intent.putExtra("NO", noparam);
                intent.putExtra("BTNNO", "1");
                startActivity(intent);
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                intent.putExtra("NO", noparam);
                intent.putExtra("BTNNO", "2");
                startActivity(intent);
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                intent.putExtra("NO", noparam);
                intent.putExtra("BTNNO", "3");
                startActivity(intent);
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                intent.putExtra("NO", noparam);
                intent.putExtra("BTNNO", "4");
                startActivity(intent);
            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                intent.putExtra("NO", noparam);
                intent.putExtra("BTNNO", "5");
                startActivity(intent);
            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartySearch.class);
                // アクティビティ起動
                intent.putExtra("NO", noparam);
                intent.putExtra("BTNNO", "6");
                startActivity(intent);
            }
        });

        // ここからList処理
        listView1 = (ListView)findViewById(R.id.listView1);

        // http処理
        Http.Request request = new Http.Request();
        request.url = "https://but-pazu-test.ssl-lolipop.jp/skillSearch.php?leader[0]=" + noArr[0] +
                      "&leader[1]=" + noArr[5] +
                      "&member[0]=" + noArr[1] +
                      "&member[1]=" + noArr[2] +
                      "&member[2]=" + noArr[3] +
                      "&member[3]=" + noArr[4];
        Log.d("Tag", request.url);
        Common.putLogMessage(request.url);

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
        //arrtest[0].add(sample.ls_desc.split(",")[0] + "\n" + sample.ls_desc.split(",")[1]);
        arrtest[0].add(getLsDesc(sample));
        arrtest[0].add("http://pazu-test.but.jp/image/004.png");

        // 覚醒
        int cnt = 1;
        if (sample.kakusei != null) {
            for (Kakusei kakuraw : sample.kakusei) {
                arrtest[cnt] = new ArrayList<String>();
                arrtest[cnt].add(kakuraw.desc);
                arrtest[cnt].add(kakuraw.filename);
                arrtest[cnt].add(kakuraw.url);
                cnt++;
            }
        }

        // ここで繰り返し処理
        List<AppInfo> items = new ArrayList<AppInfo>();

        for (int i = 0; i < cnt; i++) {
            AppInfo bbb = new AppInfo();
            bbb.setTextData(arrtest[i].get(0));
            if (i == 0) {
                // リーダースキル
                bbb.setImagaData(getResources().getDrawable(R.drawable.leaderskill));
            } else {
                // 覚醒情報
                // todo 存在しない場合はURLから取るかNoImage
                int id = getResources().getIdentifier(arrtest[i].get(1), "drawable", getPackageName());
                bbb.setImagaData(getResources().getDrawable(id));
            }
            items.add(bbb);
        }


        //adapter = new AppInfoArrayAdapter(this, R.layout.raw, R.id.row_textview1, items);
        AppInfoArrayAdapter adapter = new AppInfoArrayAdapter(this, R.layout.raw, items);
        listView1.setAdapter(adapter);

        // ここから登録ボタン押下処理
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              //テキスト入力を受け付けるビューを作成
                final EditText editView = new EditText(Partyadd.this);
                new AlertDialog.Builder(Partyadd.this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("ラベルを入力してください")
                    //setViewにてビューを設定します。
                    .setView(editView)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // todo ここでMainActivityにもどる
                            Partyadd pad = new Partyadd();
                            pad.setTouroku(editView.getText().toString());
                           //入力した文字をトースト出力する
//                            Toast.makeText(Partyadd.this,
//                                    editView.getText().toString(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
            }
        });
    }

    /**
     * パーティ情報をローカルに保存する
     *
     * @param label さんぷる
     * @author hide
     */
    protected void setTouroku(String label) {
        Log.d("Tag", "label=" + label);
    }

    /**
     * パーティ情報をローカルに保存する
     *
     * @param noArr 加工前のモンスターNo配列。必ず6個
     * @return モンスターの画像にアクセスするためのNoを配列で返却。0埋め処理後。必ず6個
     * @author hide
     */
    private String[] getImageFilePathArr(String[] noArr) {
        String[] rst = new String[6];
        int i = 0;
        for (String param : noArr) {
            int p = Integer.parseInt(param);
            if (p <= 0) {
                rst[i] = "noimage";
            } else if (p >= 1000) {
                rst[i] = param + ".png";
            } else {
                rst[i] = String.format("%1$03d", p) + ".png";
            }
            i++;
        }
        return rst;
    }

    /**
     * リーダースキルの文字列を生成して返却する
     * @param sample 覚醒情報を含むモンスター情報一覧
     * @return リーダースキル文字列
     */
    private String getLsDesc(Sample2 sample) {
        String rst;
        String[] arr = sample.ls_desc.split(",",0);
        if (arr.length == 0 || (arr.length == 1 && arr[0] == "")) {
            rst = "無し";
        } else if (arr.length == 1) {
            rst = arr[0];
        } else {
            rst = arr[0] + "\n" + arr[1];
        }
        //rst = sample.ls_desc.split(",")[0] + "\n" + sample.ls_desc.split(",")[1];
        return rst;
    }

    /**
     * Intent[NO]からNo配列の生成して返却する
     * @return　0埋めして6個の文字列配列にして返却する
     * @author hide
     */
    private String[] getIntentValueNo() {
        String no;
        String[] noArr  = {};
        Intent intent = getIntent();
        if (intent != null) {
            no = intent.getStringExtra("NO");
            //Log.d("Tag", "No:" + intent.getStringExtra("NO"));
            Common.putLogMessage("No:" + intent.getStringExtra("NO"));
            noArr = no.split(",", 0);
            // 不足している場合は0埋め
            if (noArr.length < 6) {
                for (int i = 6 - noArr.length; i < noArr.length; i++) {
                    noArr[i] = "0";
                }
            }
        } else {
            String tmp = "0,0,0,0,0,0";
            noArr = tmp.split(",", 0);
        }

        return noArr;
    }

    /**
     * ImageBottunに画像をセットする
     * @param imgbtnId 対象ボタンのID
     * @param fileName URLから取得する際のファイル名。存在しない場合はnoimage
     */
    private void setImageButton(int imgbtnId, String fileName) {
        ImageButton img = (ImageButton)findViewById(imgbtnId);
        try {
            if (fileName == "noimage") {
                // ここでNoImage
                //img.setImageDrawable(R.drawable.moimage);
                img.setImageResource(R.drawable.noselect);
            } else {
                // 画像のURL
                String urlString="http://pazu-test.but.jp/image/" + fileName;
                //URLクラス
                URL url = new URL(urlString);
                //入力ストリームを開く
                InputStream istream = url.openStream();

                //画像をDrawableで取得
                Drawable d = Drawable.createFromStream(istream, "webimg");

                //入力ストリームを閉じる
                istream.close();

                img.setImageDrawable(d);
            }

            android.view.ViewGroup.LayoutParams params = img.getLayoutParams();
            params.height = width / 6;
            params.width = width / 6;

            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } catch (Exception e) {
            System.out.println("nuu: "+e);
        }
    }

    /**
     * ディスプレイの縦横をクラス変数width, heightにセットする
     */
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

/**
 * サーバから取得したモンスター情報を格納するためのクラス
 * <br>https://but-pazu-test.ssl-lolipop.jp/skillSearch.php
 * @author hide
 *
 */
class Sample2{
    public String ls_name;
    public String ls_desc;
    public Kakusei[] kakusei;
}

/**
 * 覚醒情報を格納するためのクラス
 * @author hide
 *
 */
class Kakusei {
    public String desc;
    public String url;
    public String filename;
}