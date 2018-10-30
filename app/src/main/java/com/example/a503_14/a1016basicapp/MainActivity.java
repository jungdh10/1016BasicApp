package com.example.a503_14.a1016basicapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //여기에 변수를 선언하면 클래스 내의 모든 곳에서 변수를 사용할 수 있음
    Button contact, camera, voice, map, browser, call;
    TextView resultView;
    ImageView resultImageView;

    //이미지 출력 크기를 위한 변수
    int reqWidth;
    int reqHeigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //(3)final ImageView resultImageView;

        //여기에 변수를 만들면 final을 붙이지 않는 이상 다른 클래스나 메소드 안에서 사용이 안됨
        contact=(Button)findViewById(R.id.contact);
        camera=(Button)findViewById(R.id.camera);
        voice=(Button)findViewById(R.id.voice);
        map=(Button)findViewById(R.id.map);
        browser=(Button)findViewById(R.id.browser);
        call=(Button)findViewById(R.id.call);
        resultView=(TextView)findViewById(R.id.resultView);
        resultImageView=(ImageView)findViewById(R.id.resultImageView);
        //(2)final ImageView resultImageView=(ImageView)findViewById(R.id.resultImageView);
        //(3)resultImageView=(ImageView)findViewById(R.id.resultImageView);

        reqWidth=getResources().getDimensionPixelOffset(R.dimen.request_image_heigh);

        //버튼들의 이벤트를 처리할 클래스의 객체
        View.OnClickListener eventHandler=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.contact:
                        //연락처 앱 실행
                        Intent contactIntent=new Intent(Intent.ACTION_PICK);
                        contactIntent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        //실행
                        startActivityForResult(contactIntent, 10);
                        break;
                    case R.id.camera:
                        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 20);
                        break;
                    case R.id.voice:
                        Intent voiceIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식 테스트");
                        break;
                    case R.id.map:
                        Intent mapIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:28.473331, -81.473879"));
                        startActivity(mapIntent);
                    case R.id.browser:
                        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.daum.net"));
                        startActivity(browserIntent);
                        //전화기능은 permission을 확인하고 수행해야 함
                    case R.id.call:
                        //있으면 바로 실행하고
                        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3590-0556"));
                            startActivity(callIntent);
                        }else{ //없으면 물어보기 - 아닐경우에는 permission을 요청해야 함
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},100);
                        }
                }

            }
        };
        //버튼에 이벤트 핸들러를 연결
        contact.setOnClickListener(eventHandler);
        camera.setOnClickListener(eventHandler);
        voice.setOnClickListener(eventHandler);
        map.setOnClickListener(eventHandler);
        browser.setOnClickListener(eventHandler);
        call.setOnClickListener(eventHandler);
    }

    //startActivityForResult로 인텐트를 실행한 후 인텐트의 엑티비티가 종료되었을 때 호출되는 메소드
    //requestCode는 호출할 때 설정한 코드이고(호출한 Activity를 구분하기 위해), resultCode는 출력된 엑티비티에서 전달해준 코드(응답 구분)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //RESULT_OK: 확인 눌렀을 때
        if(requestCode==10 && resultCode==RESULT_OK){
            String result=data.getDataString();
            resultView.setText(result);
        }else if(requestCode==20 && resultCode==RESULT_OK){
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        }else if(requestCode==30 && resultCode==RESULT_OK){
            ArrayList<String> voiceresult=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String result=voiceresult.get(0);
            resultView.setText(result);
        }
    }
}
