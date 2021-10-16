package com.smartpay.application;

import static com.smartpay.application.MotoTransactionActivity.MOTO_TYPE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar(getString(R.string.welcome), false);

    }

    public void startSingleMotoTransaction(View view) {
        Intent intent = new Intent(this, MotoTransactionActivity.class);
        if (view.getId() == R.id.tv_single_moto)
            intent.putExtra(MOTO_TYPE, getString(R.string.single_moto));
        else intent.putExtra(MOTO_TYPE, getString(R.string.recurring_moto));
        startActivity(intent);
    }
}