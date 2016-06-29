package com.imudgesyy.GradeChecker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyang on 2016/6/28.
 */
public class MyAdapter extends SimpleAdapter{

    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView)view.findViewById(R.id.course_value);
        try{
            if(Double.parseDouble(textView.getText().toString())<60){
                Log.e("value",Double.parseDouble(textView.getText().toString())+"");
                textView.setTextColor(Color.RED);
            }else{
                textView.setTextColor(Color.BLACK);
            }
        }catch (Exception e){
            textView.setTextColor(Color.BLACK);
        }

        return view;
    }
}
