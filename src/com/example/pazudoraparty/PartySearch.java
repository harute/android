package com.example.pazudoraparty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PartySearch extends Activity implements TextWatcher {
    private Map<String, String> data;
    private EditText mEditText; // 変更を検知するエディットボックス
    List<Sample> baseSampleList;
    List<Map<String, String>> baseRetDataList;
    ListView listView;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_party_search);

        // Intent連携から対象のボタンNOを取得する
        Intent intent = getIntent();
        final String beforeIntentNo = intent.getStringExtra("NO");
        final String beforeIntentBtnNo = intent.getStringExtra("BTNNO");
        Common.putLogMessage("No:" + beforeIntentNo);
        Common.putLogMessage("Button-No:" + beforeIntentBtnNo);

        // リスナーを仕込むエディットボックス
        this.mEditText = (EditText)findViewById(R.id.editText1);
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // EditTextのフォーカスが外れた場合
                if (hasFocus == false) {
                 // ソフトキーボードを非表示にする
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        this.mEditText.addTextChangedListener(this);

        Http.Request request = new Http.Request();
        request.url = "https://but-pazu-test.ssl-lolipop.jp/test_non2.php";

        Http.Response response = Http.requestSync(request, StringResponseHandler.getInstance());
        // HTTPステータスコードが200
        if (response.code == 200) {
            // 受信した文字列を取得
            String str = (String) response.value;

            listView = (ListView) findViewById(R.id.listView1);

            Gson gson = new Gson();
            Sample[] sampleArray = gson.fromJson(str, Sample[].class);
            // リストに変換
            List<Sample> sampleList = Arrays.asList(sampleArray);
            //コピー
            baseSampleList = new ArrayList<Sample>(sampleList);

            List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
            for (Sample sampleRow : sampleList) {
                data = new HashMap<String, String>();
                data.put("no", Common.getNo(sampleRow.no, sampleRow.rare));
                data.put("name", sampleRow.name);
                data.put("other2", "最大Lv." + sampleRow.lv);
                data.put("other3", "HP " + sampleRow.hp);
                data.put("other4", "攻撃 " + sampleRow.attack);
                data.put("other5", "回復 " + sampleRow.recovery);
                retDataList.add(data);
            }
            baseRetDataList = retDataList;

            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, retDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView001, R.id.textView002 , R.id.textView003, R.id.textView004, R.id.textView005, R.id.textView006});

            // アダプタを設定します。
            listView.setAdapter(adapter2);

          //クリック処理
            listView.setOnItemClickListener(new OnItemClickListener() {
                @SuppressWarnings("unchecked")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView list = (ListView)parent;
                    data = new HashMap<String, String>();
                    data = (Map<String, String>) list.getItemAtPosition(position);
                    String ret = data.get("no").replaceAll("[^0-9]","");

                    // PartyAddに戻る
                    Intent intent = new Intent(getApplicationContext(), Partyadd.class);
                    // アクティビティ起動
                    String res = "";
                    res = Common.getMergeNO(beforeIntentNo, beforeIntentBtnNo, ret);
                    intent.putExtra("NO", res);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO 自動生成されたメソッド・スタブ
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO 2回呼ばれる
        List<Map<String, String>> retDataList = new ArrayList<Map<String, String>>();
        if (s.toString() != "") {
            // ここでテキスト変更後の処理
            for (Sample sampleRow : baseSampleList) {
                if (sampleRow.name.indexOf(s.toString()) > -1) {
                    data = new HashMap<String, String>();
                    data.put("no", Common.getNo(sampleRow.no, sampleRow.rare));
                    data.put("name", sampleRow.name);
                    data.put("other2", "最大Lv." + sampleRow.lv);
                    data.put("other3", "HP " + sampleRow.hp);
                    data.put("other4", "攻撃 " + sampleRow.attack);
                    data.put("other5", "回復 " + sampleRow.recovery);
                    retDataList.add(data);
                }
            }


            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, retDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView001, R.id.textView002 , R.id.textView003, R.id.textView004, R.id.textView005, R.id.textView006});

            // アダプタを設定します。
            listView.setAdapter(adapter2);
        } else {
            // リストビューに渡すアダプタを生成します。
            SimpleAdapter adapter2 = new SimpleAdapter(this, baseRetDataList,
                    R.layout.raw, new String[] { "no", "name" ,"other2", "other3","other4","other5"},
                    new int[] {R.id.textView001, R.id.textView002 , R.id.textView003, R.id.textView004, R.id.textView005, R.id.textView006});

            // アダプタを設定します。
            listView.setAdapter(adapter2);
        }
    }
}

/*
* Json文字列をこのクラスに変化する
*/
class Sample{
    public String no;
    public String name;
    public String lv;
    public String hp;
    public String attack;
    public String recovery;
    public String rare;
}