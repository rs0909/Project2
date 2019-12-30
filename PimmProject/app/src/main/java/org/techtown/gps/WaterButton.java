package org.techtown.gps;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class WaterButton extends ConstraintLayout
{
    ImageView symbol;
    TextView amountOfWater;
    TextView currentTime;

    public WaterButton(Context context)
    {
        super(context);
        initView();

    }
    public WaterButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initView();
        getAttrs(attrs);
    }

    public int getAmountOfWater()
    {
        int water = 0;
        if(amountOfWater != null)
        {
            String s = amountOfWater.getText().toString();
            s = s.substring(0, s.length()-2);

            water = Integer.parseInt(s);
        }

        return water;
    }

    private void initView()
    {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.waterbutton,this,false);
        addView(v);

        symbol = findViewById(R.id.buttonimage);
        amountOfWater = findViewById(R.id.tvamountofwater);
        currentTime = findViewById(R.id.tvcurrenttime);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WaterButton);

        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray)
    {
        String drinkWaterText = typedArray.getString(R.styleable.WaterButton_drinkwatertext);
        String currentTimeText =typedArray.getString(R.styleable.WaterButton_currenttimetext);

        amountOfWater.setText(drinkWaterText);
        currentTime.setText(currentTimeText);

        typedArray.recycle();
    }

    public void setSymbol(int symbol_resID)
    {
        symbol.setImageResource(symbol_resID);
    }

    public void setCurrentTimeText(String text_string)
    {
        currentTime.setText(text_string);
    }

    public void setAmountOfWaterText(String text_string)
    {
        amountOfWater.setText(text_string);
    }
}
