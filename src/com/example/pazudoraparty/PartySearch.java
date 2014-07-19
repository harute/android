package com.example.pazudoraparty;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

        Log.v("Tag", "onCreate");
        SearchView search = (SearchView) findViewById(R.id.searchView1);
        list = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, array_adapter_data);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);

        // Listner登録
        search.setOnQueryTextListener(this);

        // SearchViewの初期表示状態を設定
        search.setIconifiedByDefault(false);

        // SearchViewのSubmitボタンを使用不可にする
        search.setSubmitButtonEnabled(false);

        // SearchViewに何も入力していない時のテキストを設定
        search.setQueryHint("検索文字を入力");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Log.d("Tag", "onQueryTextChange");
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
