package org.techtown.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SelectWaterButton extends Activity {

    private Button btn300mlButton;
    private Button btn500mlButton;
    private Button btn1000mlButton;
    private Button cancelButton;
    private DrinkWaterFrag drinkWaterFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_select_water_button);

        drinkWaterFrag = DrinkWaterFrag.newInstance();
        btn300mlButton = findViewById(R.id.btn300ml);
        btn500mlButton = findViewById(R.id.btn500ml);
        btn1000mlButton = findViewById(R.id.btn1000ml);

        btn300mlButton.setOnClickListener(new ButtonClickListener());
        btn500mlButton.setOnClickListener(new ButtonClickListener());
        btn1000mlButton.setOnClickListener(new ButtonClickListener());

        cancelButton = findViewById(R.id.selectwatercancelbutton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("Result", "Cancel Popup");
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE)
            return false;

        return true;
    }

    @Override
    public void onBackPressed()
    {
        return;
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btn300ml:
                    drinkWaterFrag.drinkwater(300);
                    finish();
                    break;
                case R.id.btn500ml:
                    drinkWaterFrag.drinkwater(500);
                    finish();
                    break;
                case R.id.btn1000ml:
                    drinkWaterFrag.drinkwater(1000);
                    finish();
                    break;
            }
        }
    }
}
