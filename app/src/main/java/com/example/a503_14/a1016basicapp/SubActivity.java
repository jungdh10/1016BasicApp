package com.example.a503_14.a1016basicapp;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    //인스턴스 변수 생성
    int value=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        TextView title =(TextView)findViewById(R.id.title);
        //앞에 있는 Activity 에서 title 이라는 이름으로 넘겨준 문자열을 받아서 출력
        title.setText(getIntent().getStringExtra("title"));

        final TextView textView=(TextView)findViewById(R.id.textcount);
        Button btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                value=value+1;
                textView.setText(value+"");
            }
        });
    }

    //회전시켜도 0으로 초기화 되지 않게
    //엑티비티가 종료되기 직전에 호출되는 메소드 - 복원할 데이터를 저장
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //데이터 저장
        bundle.putInt("value", value);
    }

    //엑티비티가 시작할 때 호출되는 메소드 - 데이터를 복원
    //회전시켜도 TextView는 초기화되지 않음
    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int value=bundle.getInt("value");
        this.value=value;
        TextView textView=(TextView)findViewById(R.id.textcount);
        textView.setText(value+"");

    }
}
