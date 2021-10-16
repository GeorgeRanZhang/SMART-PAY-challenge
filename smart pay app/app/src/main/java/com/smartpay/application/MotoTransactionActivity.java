package com.smartpay.application;

import android.os.Bundle;
import android.view.MenuItem;

public class MotoTransactionActivity extends BaseActivity {
    public static final String MOTO_TYPE = "MOTO_TYPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moto_transaction);
        setToolbar(getString(R.string.moto_transaction),true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
