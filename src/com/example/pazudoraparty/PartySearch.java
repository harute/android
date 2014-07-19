package com.example.pazudoraparty;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class PartySearch extends Activity {

    private DatabaseHelper helper;

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
    }
}
