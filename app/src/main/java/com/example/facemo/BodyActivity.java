package com.example.facemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.facemo.databinding.ActivityBodyBinding;
import com.example.facemo.databinding.ActivityMainBinding;

public class BodyActivity extends AppCompatActivity {
    public ActivityBodyBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_body);

        b.txtBodyBody.setText(getSharedPreferences("body", Context.MODE_PRIVATE).getString("raw","play macarena"));
    }

    public void onDoneClick(View v) {
        getSharedPreferences("body", Context.MODE_PRIVATE).edit().putString("raw", b.txtBodyBody.getText().toString()).apply();

        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        this.startActivity(i);
    }
}