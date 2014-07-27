package com.example.pazudoraparty;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconTextArrayAdapter extends ArrayAdapter<IconTextArrayItem> {

    /** XMLからViewを生成するのに使うヤツ. */
    private LayoutInflater inflater;

    /** リストアイテムのレイアウト. */
    private int textViewResourceId;

    /** 表示するアイテム. */
    private List<IconTextArrayItem> items;

    /**
     * コンストラクタ.
     */
    public IconTextArrayAdapter(
            Context context,
            int textViewResourceId,
            List<IconTextArrayItem> items)
    {
        super(context, textViewResourceId, items);

        // リソースIDと表示アイテムを保持っておく
        this.textViewResourceId = textViewResourceId;
        this.items = items;

        // ContextからLayoutInflaterを取得
        inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    /**
     * 1アイテム分のビューを取得.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;

        // convertViewなんか入ってたら、それを使う
        if(convertView != null){
            view = convertView;
        }
        // convertViewがnullなら新規作成
        else{
            view = inflater.inflate(textViewResourceId, null);
        }

        // 対象のアイテムを取得
        IconTextArrayItem item = items.get(position);

        // アイコンにアレを設定
        ImageView imageView = (ImageView)view.findViewWithTag("icon");


     // 画像をDrawableで取得
        String urlString="http://pazu-test.but.jp/image/001.png";
        Drawable d;
        try {
          //URLクラス
            URL url = new URL(urlString);
            //入力ストリームを開く
            InputStream istream = url.openStream();
            //画像をDrawableで取得
            d = Drawable.createFromStream(istream, "webimg");
            imageView.setImageDrawable(d);
        } catch (Exception e) {
            // TODO: handle exception
        }





        //imageView.setImageResource(item.getIconResource());

        // テキストにソレを設定
        TextView textView = (TextView)view.findViewWithTag("text");
        textView.setText(item.getText());

        return view;
    }
}
