package com.example.realone;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactItemView extends LinearLayout {
    TextView textView1;
    ImageView imageView1;

    public ContactItemView(Context context) {
        super(context);
        init(context);
    }

    public ContactItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        //만들어놓은 xml파일을 객체화해와서 붙이는 역할을 하면 되겠죠
        //서비스에서 제공하는 LAYOUT_INFLATER_SERVICE를 활용합니다.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //객체화하기위해서 인플레이션 서비스를 활용함
        inflater.inflate(R.layout.listview_item,this,true);
        //이 소스가 Linear를 상속받았으므로 siger_item을 바로 this에 붙일 수 있음

        textView1 = findViewById(R.id.textView1);
        imageView1 = findViewById(R.id.imageView1);

    }

    //SigerItemView에 데이터를 설정할 수 있게끔 setter함수를 설정해줍시다.

    //먼저 걸그룸의 이름
    public  void setName(String name){
        textView1.setText(name);

    }
    // 다음 걸그룹의 연락처

    public void setImage(long photo){
        Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo);
        imageView1.setImageURI(uri);
    }

}