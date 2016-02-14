package com.giyer7.dropanote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    ImageView iv_logo,iv_background;
    Toolbar mToolbar;
    Button mAddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_logo = (ImageView) findViewById(R.id.img_logo);
        iv_background = (ImageView) findViewById(R.id.img_background);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddButton = (Button) findViewById(R.id.btn_dropanote);
        mAddButton.setOnClickListener(this);
        initBackgroundImage();
        setSupportActionBar(mToolbar);
    }

    private void initBackgroundImage() {
        Glide.with(this).load(R.drawable.background).centerCrop().into(iv_background);
    }

    @Override
    public void onClick(View v) {

    }
}
