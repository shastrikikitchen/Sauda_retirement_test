package com.traveller.enthusiastic.goldwise.Fragment;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.traveller.enthusiastic.goldwise.R;
import com.traveller.enthusiastic.goldwise.View.ProjectionView;
import com.traveller.enthusiastic.goldwise.helper.BaseFragment;
import com.traveller.enthusiastic.appUtill.SeekBarWithIntervals;
import com.traveller.enthusiastic.networkUtils.API.GoalWiseAPi;
import com.traveller.enthusiastic.networkUtils.Response.GoalWiseResponse;
import com.traveller.enthusiastic.networkUtils.VolleyListener;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sauda on 25/02/17.
 */

public class OverViewFragment extends BaseFragment implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    private ProjectionView projectionView;
    private LinearLayout overView_ll;
    private SeekBar seekBarRisk;
    private Button low_risk_btn;
    private Button mod_risk_btn;
    private Button high_risk_btn;
    SeekBarWithIntervals seekBarWithIntervals;
    SeekBarWithIntervals seekBarWithIntervalsLump;
    SeekBar lumpSumSeek;
    SeekBar montlyInvestment;
    View view ;
    private EditText monthly_invst_et;
    private TextView retirementTool;
    private TextView equity_percentage;
    private TextView debt_percentage;
    private EditText lumpsum_invst_et;
    private EditText time_horizon;
    private Double monthly_step=0.0;
    private Double lumpsum_step=0.0;
    DecimalFormat ft = new DecimalFormat(".##");
    private Spinner spinner_days ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
      //  callAPI();

    }

    private void callAPI() {
        {
            new GoalWiseAPi.Builder()
                    .setTime_horizon(1)
                    .setRisk_cat(2)
                    .setMonthly_inv(10000)
                    .setGoal("wealth")
                    .setTag(this)
                    .setListener(new VolleyListener<GoalWiseResponse>() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showShortToast("falure");


                        }

                        @Override
                        public void onResponse(GoalWiseResponse response) {
                            if(response!= null && response.getStatus().equalsIgnoreCase("success")){
                                showShortToast("success");
                                parseData(response);
                                projectionView.updateData(null,response);

                            }

                        }
                    })
                    .build()
                    .execute();
        }
    }

    private void parseData(GoalWiseResponse response) {
        setRiskButton(response.getMsg().getRisk_level().intValue());
        montlyInvestment.setProgress(response.getMsg().getMonthly_investment().intValue());
        time_horizon.setText("1 month");
        if(response.getMsg().getTime_horizon()<5) {
            spinner_days.setSelection(response.getMsg().getTime_horizon().intValue());
        }



       // retirementTool.setMovementMethod(LinkMovementMethod.getInstance());
        retirementTool.setText(getString(R.string.know_more));
        Linkify.addLinks(retirementTool, Linkify.ALL);
        setTextTextView();



    }
    void setTextTextView(){
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1= new SpannableString(getString(R.string.know_more));
        str1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.grey_700)), 0, str1.length(), 0);
        builder.append(str1);

        //String text = "<a href='https://www.goalwise.com/'> Retirement Goal </a>";
        String text =  " Retirement Goal";

        SpannableString str2= new SpannableString(text);
        str2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.deep_purple_400)), 0, str2.length(), 0);
        builder.append(str2);

       // retirementTool.setText( builder, TextView.BufferType.SPANNABLE);
        retirementTool.setMovementMethod(LinkMovementMethod.getInstance());
        retirementTool.setText( builder,TextView.BufferType.SPANNABLE);
    }
   void setRiskButton( int riskLevel){

       switch (riskLevel){
           case 1:
               seekBarRisk.setProgress(20);
               low_risk_btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.green_gradient_background));
               resetBackground(mod_risk_btn, high_risk_btn);

               //seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.green_400), PorterDuff.Mode.SRC_IN);
               seekBarRisk.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.green_400), PorterDuff.Mode.SRC_IN);

               break;
           case 2:
               seekBarRisk.setProgress(50);

               mod_risk_btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.yellow_gradient_background));
               resetBackground(low_risk_btn, high_risk_btn);

               //seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.yellow_500), PorterDuff.Mode.SRC_IN);
               seekBarRisk.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow_500), PorterDuff.Mode.SRC_IN);

               break;
           case 3:
               seekBarRisk.setProgress(85);

               high_risk_btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.orange_gradient_background));
               resetBackground(mod_risk_btn, low_risk_btn);


               //seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.orange_700), PorterDuff.Mode.SRC_IN);
               seekBarRisk.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.orange_700), PorterDuff.Mode.SRC_IN);

               break;
       }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_bar, container, false);
        initViews(view);

        return view;
    }

    private SeekBarWithIntervals getSeekbarWithIntervals() {
        if (seekBarWithIntervals == null) {
            seekBarWithIntervals = (SeekBarWithIntervals) view.findViewById(R.id.seekbarWithIntervals);

        }
        return seekBarWithIntervals;
    }

    private List<String> getIntervals(int maxValue) {
        int singleInter = maxValue / 6;
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");

        for(int i = 1;i<7;i++){
            arrayList.add(formatNumberExample(singleInter*i));
        }
        return arrayList;

    }
    public static String formatNumberExample(int number) {
        DecimalFormat ff = new DecimalFormat(".##");

        if(number<1000){
            return number+"";
        }else if(number>=1000 && number<99999){
            return (ff.format(number/1000.0)+"K");

        }else if(number>= 99999 && number<= 9999999){
            return (ff.format(number/100000.0)+"L");
        }
        return ff.format(number/10000.0);
    }

    String[] getYears(){
       String[]years = new String[12];
        for (int i = 0;i<10;i++){
            years[i]=(i+" Years");

        }
        return years;
    }
    private void initViews(View view) {
        this.view = view;
        overView_ll =  (LinearLayout)view.findViewById(R.id.linWidgetContainer);
        seekBarRisk =(SeekBar) view.findViewById(R.id.seek_bar);
        low_risk_btn = (Button) view.findViewById(R.id.low_btn);
        high_risk_btn = (Button) view.findViewById(R.id.high_btn);
        mod_risk_btn = (Button) view.findViewById(R.id.mod_btn);
        monthly_invst_et = (EditText) view.findViewById(R.id.monthly_invst_et);
        lumpsum_invst_et = (EditText) view.findViewById(R.id.lumpsum_invst_et);
        time_horizon = (EditText) view.findViewById(R.id.time_horizon);
        retirementTool = (TextView) view.findViewById(R.id.retirementTool);
        debt_percentage = (TextView) view.findViewById(R.id.debt_percentage);
        equity_percentage = (TextView) view.findViewById(R.id.equity_percentage);
        seekBarWithIntervals = (SeekBarWithIntervals) view.findViewById(R.id.sbi_lumpSum );
        spinner_days = (Spinner) view.findViewById(R.id.days_spinner);
        seekBarWithIntervalsLump = (SeekBarWithIntervals) view.findViewById(R.id.seekbarWithIntervals);
        montlyInvestment = seekBarWithIntervals.getSeekbar();
        lumpSumSeek = seekBarWithIntervalsLump.getSeekbar();
        projectionView =  new ProjectionView(getActivity(),overView_ll,getChildFragmentManager());

        setSpinner();
        callAPI();
        setClickListeners();
        setIntervalsData();
        setTextListeners();


    }

    private void setSpinner() {
        spinner_days.setPrompt(String.valueOf(getYears()[0]));

       final  ArrayAdapter<String> daysDataAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, getResources().getStringArray(R.array.graph_day_filter_array));
        spinner_days.setAdapter(daysDataAdapter);
            String days="1 years";
            int spinnerPosition = daysDataAdapter.getPosition(days);
        spinner_days.setSelection(spinnerPosition);

        spinner_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           // time_horizon.setText(daysDataAdapter.getItem(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });



    }

    private void setTextListeners() {
        lumpsum_invst_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String number = s.toString();
                    if (!TextUtils.isEmpty(number)) {
                        if (!number.isEmpty()) {
                            Double doub = Double.parseDouble(number);
                            lumpSumSeek.setProgress(doub.intValue());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        monthly_invst_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = s.toString();
                try {
                    if (!TextUtils.isEmpty(number)) {
                        if (!number.isEmpty()) {
                            Double doub = Double.parseDouble(number);
                            montlyInvestment.setProgress(doub.intValue() * 10);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });





    }
    int getProgress(Double number,Double step ){
      return  (int) (number*1000.0/step);

    }

    void setIntervalsData(){
        List<String> monthly = getIntervals(200000);
        monthly_step = 200000/6.0;
        if(seekBarWithIntervals!= null) {
            seekBarWithIntervals.setIntervals(monthly,2000000);
        }
        List<String> lumpSum = getIntervals(180000);
        lumpsum_step = 180000/6.0;
        if(seekBarWithIntervalsLump!= null) {
            seekBarWithIntervalsLump.setIntervals(lumpSum,180000);
        }

    }

    void setClickListeners(){
        low_risk_btn.setOnClickListener(this);
        high_risk_btn.setOnClickListener(this);
        mod_risk_btn.setOnClickListener(this);

        seekBarRisk.setOnSeekBarChangeListener(this);
        /*TODO: uncomment later*/
        seekBarWithIntervals.setOnSeekBarChangeListener(monthlyListener);
        seekBarWithIntervalsLump.setOnSeekBarChangeListener(lumpListener);
        //montlyInvestment.setOnSeekBarChangeListener(this);
        //lumpSumSeek.setOnSeekBarChangeListener(this);

    }
    SeekBar.OnSeekBarChangeListener monthlyListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            monthly_invst_et.setText(ft.format(progress/10));
            if(progress/10<60) {
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.green_400), PorterDuff.Mode.SRC_IN);
            }else if(progress/10<140) {
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow_500), PorterDuff.Mode.SRC_IN);

            }else {
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.orange_700), PorterDuff.Mode.SRC_IN);

            }
        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    SeekBar.OnSeekBarChangeListener lumpListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            lumpsum_invst_et.setText(ft.format(progress));

            if(progress<60) {
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.green_400), PorterDuff.Mode.SRC_IN);
            }else if(progress<120) {
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow_500), PorterDuff.Mode.SRC_IN);

            }else {
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.orange_700), PorterDuff.Mode.SRC_IN);

            }
        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.low_btn:
                low_risk_btn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.green_gradient_background));
                setSeekBarProgress(20);
                resetBackground(mod_risk_btn,high_risk_btn);
                break;
            case R.id.mod_btn:
                mod_risk_btn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.yellow_gradient_background));
                setSeekBarProgress(60);
                resetBackground(low_risk_btn,high_risk_btn);

                break;
            case R.id.high_btn:
                high_risk_btn.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.orange_gradient_background));
                setSeekBarProgress(90);
                resetBackground(mod_risk_btn,low_risk_btn);

                break;
        }
    }
    void setSeekBarProgress(int progress){
        seekBarRisk.setProgress(progress);
    }
    void resetBackground(Button b1, Button b2){
        b1.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.white_background));
        b2.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.white_background));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progress < 41) {
                low_risk_btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.green_gradient_background));
                resetBackground(mod_risk_btn, high_risk_btn);

                //seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.green_400), PorterDuff.Mode.SRC_IN);
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.green_400), PorterDuff.Mode.SRC_IN);
            } else if (progress > 40 && progress < 81) {
                mod_risk_btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.yellow_gradient_background));
                resetBackground(low_risk_btn, high_risk_btn);

                //seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.yellow_500), PorterDuff.Mode.SRC_IN);
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow_500), PorterDuff.Mode.SRC_IN);

            } else {
                high_risk_btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.orange_gradient_background));
                resetBackground(mod_risk_btn, low_risk_btn);


                //seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.orange_700), PorterDuff.Mode.SRC_IN);
                seekBar.getThumb().setColorFilter(ContextCompat.getColor(getContext(), R.color.orange_700), PorterDuff.Mode.SRC_IN);
            }


        int debt = 100 - progress;
        int equity = 100 -debt;
            debt_percentage.setText(debt+ "%");
            equity_percentage.setText((equity)+ "%");
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }






}

