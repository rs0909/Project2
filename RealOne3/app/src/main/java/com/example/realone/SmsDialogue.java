package com.example.realone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Objects;

import static java.util.Objects.*;

public class SmsDialogue extends Dialog {
    Context context;

    public SmsDialogue(Context context) {
        super(context);
        this.context = context;
    }


    protected void callFunction(final String Phonenum, final String sms) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sms);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = 700;
        layoutParams.height = 600;
        Window window = dialog.getWindow();
        window.setAttributes(layoutParams);

//        getWindow().setAttributes(layoutParams);

        //셋팅

        Button mPositiveButton = (Button) dialog.findViewById(R.id.pbutton);
        Button mNegativeButton = (Button) dialog.findViewById(R.id.nbutton);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Phonenum, null, sms, null, null);
                    Toast.makeText(context, "친구를 찔렀습니다.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(context, "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //생성자 생성

}


