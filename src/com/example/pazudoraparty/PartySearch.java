package com.example.pazudoraparty;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class PartySearch extends Activity implements OnQueryTextListener {
    private String[] array_adapter_data = { "Apple", "Bike", "Cupcake",
            "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb" };

    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_search);

        SearchView search = (SearchView) findViewById(R.id.searchView1);
        list = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, array_adapter_data);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);

        // SearchViewの初期表示状態を設定
        search.setIconifiedByDefault(false);

        // SearchViewにOnQueryChangeListenerを設定
        //search.setOnQueryChangeListener(this);

        // SearchViewのSubmitボタンを使用不可にする
        search.setSubmitButtonEnabled(true);

        // SearchViewに何も入力していない時のテキストを設定
        search.setQueryHint("検索文字を入力して下さい。");
    }

    // SearchViewにテキストを入力する度に呼ばれるイベント
    public boolean onQueryTextChanged(String queryText) {
        if (TextUtils.isEmpty(queryText)) {
            list.clearTextFilter();
        } else {
            list.setFilterText(queryText.toString());
        }
        return true;
    }

    // SearchViewのSubmitButtonを押下した時に呼ばれるイベント
    public boolean onSubmitQuery(String queryText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO 自動生成されたメソッド・スタブ
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO 自動生成されたメソッド・スタブ
        if (TextUtils.isEmpty(newText)) {
            list.clearTextFilter();
        } else {
            list.setFilterText(newText.toString());
        }
        return true;
    }

    /*private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_search);

        helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select local_dish from local_dishes where prefecture = '北海道';";
        Cursor c = db.rawQuery(sql, null);
        //Cursor c = db.query("LOCAL_DISHES", new String[] {"prefecture"}, null, null, null, null, null);
        c.moveToFirst();

        CharSequence[] list = new CharSequence[c.getCount()];
        for (int i = 0; i < list.length; i++) {
            //list[i] = c.getString(0);
            Log.v("Tag", c.getString(0));
            c.moveToNext();
        }
        c.close();

        //TextView txtView = (TextView)findViewById(R.id.txtView);
        //txtView.setText(mon.monGet());
    }*/
}
