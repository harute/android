package com.example.pazudoraparty;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class PartySearch extends Activity implements OnQueryTextListener {
    private String[] array_adapter_data = { "Apple", "AAvvl", "Bike", "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb" };

    private ListView list;
    ListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ウィンドウタイトルバー非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_party_search);

        SearchView search = (SearchView) findViewById(R.id.searchView1);
        list = (ListView) findViewById(R.id.listView1);

        // リストに表示するヤツらを作る
        List<IconTextArrayItem> items = new ArrayList<IconTextArrayItem>();
        items.add(new IconTextArrayItem(R.drawable.pazu_001, "Aa"));
        items.add(new IconTextArrayItem(R.drawable.pazu_002, "Aaacccc"));
        items.add(new IconTextArrayItem(R.drawable.pazu_003, "bddd"));
        items.add(new IconTextArrayItem(R.drawable.pazu_004, "爆炎龍ティラノス"));

        adapter = new IconTextArrayAdapter(this, R.layout.row, items);
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
            //list.clearTextFilter();
            //adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.row_textview1, array_adapter_data);
            //list.setAdapter(adapter);
        } else {
            //adapter.getFilter().filter(newText);
        }
        return true;
    }
}
