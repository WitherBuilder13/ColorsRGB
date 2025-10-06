package com.example.homework02_program1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class ColorInfoAdapter extends BaseAdapter {

    Context context;
    ArrayList<ColorInfo> colorInfoArrayList;
    public ColorInfoAdapter(Context c, ArrayList<ColorInfo> ls) {
        context = c;
        colorInfoArrayList = ls;
    }

    @Override
    public int getCount() {
        return colorInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return colorInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.color_info_cell, null);
        }

        // find GUI elements
        ConstraintLayout cell_cont_j_main = view.findViewById(R.id.cell_cont_v_main);
        TextView cell_tv_j_red = view.findViewById(R.id.cell_tv_v_red);
        TextView cell_tv_j_green = view.findViewById(R.id.cell_tv_v_green);
        TextView cell_tv_j_blue = view.findViewById(R.id.cell_tv_v_blue);
        TextView cell_tv_j_hex = view.findViewById(R.id.cell_tv_v_hex);

        ColorInfo colorInfo = colorInfoArrayList.get(position);

        int red = colorInfo.getRed(), green = colorInfo.getGreen(), blue = colorInfo.getBlue();

        // set GUI elements
        cell_cont_j_main.setBackgroundColor(Color.rgb(red, green, blue));
        cell_tv_j_red.setText("Red: " + red);
        cell_tv_j_green.setText("Green: " + green);
        cell_tv_j_blue.setText("Blue: " + blue);
        cell_tv_j_hex.setText("Hex: " + colorInfo.getHex());

        MainActivity.updateTextViewColors(red, green, blue, cell_tv_j_red, cell_tv_j_green, cell_tv_j_blue, cell_tv_j_hex);

        return view;
    }
}
