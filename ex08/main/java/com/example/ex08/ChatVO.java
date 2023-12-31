package com.example.ex08;

public class ChatVO extends ShopVO {
    private String key;
    private String contents;

    @Override
    public String toString() {
        return "ChatVO{" +
                "key='" + key + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
