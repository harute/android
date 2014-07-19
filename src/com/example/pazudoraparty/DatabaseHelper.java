package com.example.pazudoraparty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

class DatabaseHelper extends SQLiteOpenHelper {

    private String[][] CAPITALS = {
        {"北海道", "札幌"},
        {"青森県", "青森"},
        {"岩手県", "盛岡"},
        {"宮城県", "仙台"},
        {"秋田県", "秋田"},
        {"山形県", "山形"},
        {"福島県", "福島"},
        {"茨城県", "水戸"},
        {"栃木県", "宇都宮"},
        {"群馬県", "前橋"},
        {"埼玉県", "さいたま"},
        {"千葉県", "千葉"},
        {"東京都", "新宿区"},
        {"神奈川県", "横浜"},
        {"新潟県", "新潟"},
        {"富山県", "富山"},
        {"石川県", "金沢"},
        {"福井県", "福井"},
        {"山梨県", "甲府"},
        {"長野県", "長野"},
        {"岐阜県", "岐阜"},
        {"静岡県", "静岡"},
        {"愛知県", "名古屋"},
        {"三重県", "津"},
        {"滋賀県", "大津"},
        {"京都府", "京都"},
        {"大阪府", "大阪"},
        {"兵庫県", "神戸"},
        {"奈良県", "奈良"},
        {"和歌山県", "和歌山"},
        {"鳥取県", "鳥取"},
        {"島根県", "松江"},
        {"岡山県", "岡山"},
        {"広島県", "広島"},
        {"山口県", "山口"},
        {"徳島県", "徳島"},
        {"香川県", "高松"},
        {"愛媛県", "松山"},
        {"高知県", "高知"},
        {"福岡県", "福岡"},
        {"佐賀県", "佐賀"},
        {"長崎県", "長崎"},
        {"熊本県", "熊本"},
        {"大分県", "大分"},
        {"宮崎県", "宮崎"},
        {"鹿児島県", "鹿児島"},
        {"沖縄県", "那覇"},
    };

    public DatabaseHelper(Context context) {
        super(context, null, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            SQLiteStatement stmt;
            db.execSQL("create table capitals (prefecture text primary key, capital text not null);");
            stmt = db.compileStatement("insert into capitals values (?, ?);");

            for (String[] capital : CAPITALS) {
                stmt.bindString(1, capital[0]);
                stmt.bindString(2, capital[1]);
                stmt.executeInsert();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
