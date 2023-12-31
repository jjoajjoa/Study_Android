package com.example.ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity4 extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().setTitle("SQLite DataBase");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DBHelper(this);
        db = helper.getReadableDatabase();

        // 데이터 생성
        cursor = db.rawQuery("select _id, name, price from product", null);
        // 어뎁터 생성
        adapter = new MyAdapter(this, cursor);
        // ListV에 어뎁터 연결
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        // +버튼 클릭
        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, InsertActivity.class);
                startActivity(intent);
            }
        });
    } //onCrate

    public class MyAdapter extends CursorAdapter {

        public MyAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int id = cursor.getInt(0);
            TextView name = view.findViewById(R.id.name);
            name.setText(cursor.getString(1));
            TextView price = view.findViewById(R.id.price);
            int intPrice = cursor.getInt(2);
            DecimalFormat df = new DecimalFormat("#,###원");
            price.setText(df.format(intPrice));

            Button btn = view.findViewById(R.id.btn);
            btn.setText("삭제!!");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity4.this)
                            .setTitle("Confirm")
                            .setMessage(id + "번 상품을 삭제하시겟슴가?")
                            .setPositiveButton("넹", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String sql = "delete from product where _id=" + id;
                                    db.execSQL(sql);

                                    onRestart();
                                }
                            })
                            .setNegativeButton("아뇽", null)
                            .show();
                }
            });
        }
    }//CursorAD

    @Override
    protected void onRestart() {
        String sql = "select _id, name, price from product";
        Cursor cursor1 = db.rawQuery(sql, null);
        adapter.changeCursor(cursor1);
        super.onRestart();
    }
}//Activity