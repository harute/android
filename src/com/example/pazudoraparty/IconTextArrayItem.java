package com.example.pazudoraparty;

public class IconTextArrayItem {

    /** 表示するアイコンのリソースID. */
    private int iconResource;

    /** 表示するテキスト. */
    private String text;

    public IconTextArrayItem(int iconResource, String text){
        this.iconResource = iconResource;
        this.text = text;
    }

    public int getIconResource() {
        return iconResource;
    }
    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
