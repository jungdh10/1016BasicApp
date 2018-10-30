package com.example.a503_14.a1016basicapp;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class RunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        ListView listView=(ListView)findViewById(R.id.listView);
        Button btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RunActivity.this, SubActivity.class);
                intent.putExtra("title", "RunActivity에서 넘겨주기");
                startActivityForResult(intent, 10);
            }
        });
    }
}
