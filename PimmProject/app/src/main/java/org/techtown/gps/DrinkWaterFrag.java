package org.techtown.gps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DrinkWaterFrag extends Fragment
{
    private View view;
    private TextView tvWaterDrinked;
    private ProgressBar progressBar;
    private Button btnDrinkWater;

    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCEL = 1;
    private static final int RESULT_DELETE = 2;

    private LinearLayout linearLayoutVertical;

    private DrinkButtonClickListener drinkButtonClickListener;
    private CurrentTime currentTime;
    private int drinkedWater = 0;

    private static DrinkWaterFrag _instance;

    public static DrinkWaterFrag newInstance()
    {
        if(_instance == null)
            _instance = new DrinkWaterFrag();

        return _instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drink_water, container, false);

        tvWaterDrinked = view.findViewById(R.id.amountOfWaterDrinked);
        progressBar = view.findViewById(R.id.pbDrinkwater);
        btnDrinkWater = getActivity().findViewById(R.id.drink_water_btn);

        linearLayoutVertical = view.findViewById(R.id.linearLayout);
        currentTime = new CurrentTime();
        drinkButtonClickListener = new DrinkButtonClickListener();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvWaterDrinked.setText("물 섭취 목표"+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                ""+ String.valueOf(0) + "ml" + "/" + String.valueOf(((MainActivity)getActivity()).userInfo.getAmountOfwater()) + "ml");
        progressBar.setMax(((MainActivity)getActivity()).userInfo.getAmountOfwater());
        progressBar.setProgress(0);

        btnDrinkWater.setOnClickListener(drinkButtonClickListener);
    }

    //버튼을 클릭하면 makeWaterButton을 호출해서 해당 View를 만듬.
    private WaterButton makeWaterButton(int amountofwater)
    {
        WaterButton waterButton = new WaterButton(getActivity().getApplicationContext());
        waterButton.setAmountOfWaterText(amountofwater + "ml");
        waterButton.setCurrentTimeText(currentTime.getNowTime());
        waterButton.setOnClickListener(new waterButtonClickListener());
        waterButton.setId(View.generateViewId());

        Log.e("id", String.valueOf(waterButton.getId()));
        switch (amountofwater) {
            case 300:
                waterButton.setSymbol(R.drawable.ic_glass0);
                break;
            case 500:
                waterButton.setSymbol(R.drawable.ic_glass04);
                break;
            case 1000:
                waterButton.setSymbol(R.drawable.ic_glass05);
                break;
        }
        return waterButton;
    }

    public void drinkwater(int water)
    {
        drinkedWater += water;
        tvWaterDrinked.setText("물 섭취 목표" +"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                String.valueOf(drinkedWater) + "ml" + "/" + String.valueOf(((MainActivity)getActivity()).userInfo.getAmountOfwater()) + "ml");
        progressBar.setProgress(drinkedWater);
        linearLayoutVertical.addView(makeWaterButton(water));
    }

    //WaterButton을 삭제할때 호출함.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {

        }
        else if(resultCode == RESULT_DELETE)
        {
            int id =  data.getExtras().getInt("View");
            WaterButton waterButton = view.findViewById(id);
            int water = waterButton.getAmountOfWater();
            linearLayoutVertical.removeView(waterButton);

            drinkedWater -= water;
            tvWaterDrinked.setText("물 섭취 목표" +"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                    String.valueOf(drinkedWater) + "ml" + "/" + String.valueOf(((MainActivity)getActivity()).userInfo.getAmountOfwater()) + "ml");
            progressBar.setProgress(drinkedWater);
        }
        else if(resultCode == RESULT_CANCEL)
        {
            return;
        }
    }

    //WaterButton에 대한 ClickListener
    class waterButtonClickListener implements  View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), popup_waterButton.class);
            intent.putExtra("View", v.getId());
            startActivityForResult(intent, 1);
        }
    }

    //WaterButton을 추가하기위해 만든 버튼 에 대한 ClickListener
    class DrinkButtonClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
           /* drinkedWater += 300;
            tvWaterDrinked.setText("물 섭취 목표" +"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                    String.valueOf(drinkedWater) + "ml" + "/" + String.valueOf(((MainActivity)getActivity()).userInfo.getAmountOfwater()) + "ml");
            progressBar.setProgress(drinkedWater);
            linearLayoutVertical.addView(makeWaterButton(300));*/
           Intent intent = new Intent(getActivity(), SelectWaterButton.class);
           intent.putExtra("View", v.getId());
           startActivityForResult(intent, 1);
        }
    }
}
