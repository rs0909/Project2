package org.techtown.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class popup_waterButton extends Activity {

    private final int RESULT_DELETE = 2;
    TextView textView;
    Button deleteButton;
    Button cancelButton;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_water_button);

        textView = findViewById(R.id.popuptxtview);
        deleteButton = findViewById(R.id.deleteButton);
        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);
    }

    public void cancelClick(View v)
    {
        Intent intent = getIntent();
        intent.putExtra("Result", "Cancel Popup");
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void confirmClick(View v)
    {
        Intent intent = getIntent();
        intent.putExtra("Result", "Confime Popup");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void deleteClick(View v)
    {
        Intent intent = getIntent();
        intent.putExtra("Result", "Delete Popup");
        setResult(RESULT_DELETE, intent);
        finish();
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
}
