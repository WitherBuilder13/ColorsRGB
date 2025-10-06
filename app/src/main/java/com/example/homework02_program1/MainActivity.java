// =============================================================================================
// Name : Jacob Young
// Date : 10-05-2025
// Desc : mess with color sliders and save the ones you like
// =============================================================================================
package com.example.homework02_program1;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int BLACK_WHITE_THRESHOLD = 370;

    private ConstraintLayout main;
    private SeekBar skbr_j_red, skbr_j_green, skbr_j_blue;
    private TextView tv_j_red, tv_j_green, tv_j_blue, tv_j_hex;
    private Button btn_j_saveColor;
    private ListView lv_j_savedColors;
    private ColorInfoAdapter lv_j_savedColorsAdapter;
    private ArrayList<ColorInfo> savedColors;
    private String hex;
    private int red, green, blue, color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        main = findViewById(R.id.main);

        skbr_j_red = findViewById(R.id.skbr_v_red);
        skbr_j_green = findViewById(R.id.skbr_v_green);
        skbr_j_blue = findViewById(R.id.skbr_v_blue);

        tv_j_red = findViewById(R.id.tv_v_red);
        tv_j_green = findViewById(R.id.tv_v_green);
        tv_j_blue = findViewById(R.id.tv_v_blue);
        tv_j_hex = findViewById(R.id.tv_v_hex);

        btn_j_saveColor = findViewById(R.id.btn_v_saveColor);

        lv_j_savedColors = findViewById(R.id.lv_v_savedColors);

        savedColors = new ArrayList<>();

        // reset to avoid weird default colors
        setColor(0, 0, 0);

        initSavedColorsList();
        initSeekBarChangeListener(skbr_j_red, tv_j_red, "Red: ");
        initSeekBarChangeListener(skbr_j_green, tv_j_green, "Green: ");
        initSeekBarChangeListener(skbr_j_blue, tv_j_blue, "Blue: ");
        initButtonClickListener();
        initListViewClickListener();
    }

    //* --------------------------------------------------------------------------------------

    private void initSeekBarChangeListener(SeekBar skbr, TextView tv, String tvContent) {
        skbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(tvContent + skbr.getProgress());
                updateColorInfo();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initButtonClickListener() {
        btn_j_saveColor.setOnClickListener(v -> {
            savedColors.add(new ColorInfo(hex, red, green, blue));
            lv_j_savedColorsAdapter.notifyDataSetChanged();
            // reset
            setColor(0, 0, 0);
        });
    }

    private void initListViewClickListener() {
        lv_j_savedColors.setOnItemClickListener((parent, view, position, id) -> {
            ColorInfo selectedColor = savedColors.get(position);
            setColor(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue());
        });
    }

    //* -------------------------------------------------------------------------------------------

    public static void updateTextViewColors(int r, int g, int b, TextView... textViews) {

        if (highLuminance(r, g, b))
            for (TextView textView : textViews)
                textView.setTextColor(Color.BLACK);
        else
            for (TextView textView : textViews)
                textView.setTextColor(Color.WHITE);
    }

    private static boolean highLuminance(int r, int g, int b) {
        return (r + g + b > BLACK_WHITE_THRESHOLD) || g > 200;
    }

    //* -------------------------------------------------------------------------------------------

    private void updateColorInfo() {
        red = skbr_j_red.getProgress();
        green = skbr_j_green.getProgress();
        blue = skbr_j_blue.getProgress();
        color = Color.rgb(red, green, blue);
        hex = Integer.toHexString(color).substring(2).toUpperCase();

        main.setBackgroundColor(color);
        tv_j_hex.setText("Hex Representation: " + hex);

        updateTextColor();
    }

    private void initSavedColorsList() {
        lv_j_savedColorsAdapter = new ColorInfoAdapter(this, savedColors);
        lv_j_savedColors.setAdapter(lv_j_savedColorsAdapter);
    }

    private void updateTextColor() {
        int blackWhite = (highLuminance(red, green, blue)) ? Color.BLACK : Color.WHITE;

        btn_j_saveColor.setTextColor(color);
        btn_j_saveColor.setBackgroundColor(blackWhite);
        skbr_j_red.getProgressDrawable().setColorFilter(blackWhite, PorterDuff.Mode.SRC_IN);
        skbr_j_green.getProgressDrawable().setColorFilter(blackWhite, PorterDuff.Mode.SRC_IN);
        skbr_j_blue.getProgressDrawable().setColorFilter(blackWhite, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTint(skbr_j_red.getThumb(), blackWhite);
        DrawableCompat.setTint(skbr_j_green.getThumb(), blackWhite);
        DrawableCompat.setTint(skbr_j_blue.getThumb(), blackWhite);

        updateTextViewColors(red, green, blue, tv_j_red, tv_j_green, tv_j_blue, tv_j_hex);
    }

    private void setColor(int r, int g, int b) {
        skbr_j_red.setProgress(r);
        skbr_j_green.setProgress(g);
        skbr_j_blue.setProgress(b);

        updateColorInfo();
    }
}