package com.hg.www.selller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hg.www.selller.define.Commodity;

public class EditCommodityActivity extends AppCompatActivity {
    private Commodity mCommodity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_commodity);
        Intent intent = getIntent();
        String commodityId = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_ID));
        commodityId = "1"; // fix
        mCommodity = ((MyApplication)getApplication()).getDataManager().GetCommodity(commodityId);
        initializeActionBar();
        initializeView();
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        actionBar.setDisplayHomeAsUpEnabled(true);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        if (mCommodity.id.isEmpty()) {
            textView.setText(((MyApplication)getApplication()).getString(R.string.add_commodity));
        } else {
            textView.setText(((MyApplication)getApplication()).getString(R.string.edit_commodity));
        }
    }

    private void initializeView() {
        if (mCommodity.id.isEmpty()) {
            ((EditText) findViewById(R.id.title)).setText(mCommodity.title);
            ((EditText) findViewById(R.id.price)).setText(String.valueOf(mCommodity.price));
            findViewById(R.id.option_in_stock).setSelected(mCommodity.is_in_stock);
            findViewById(R.id.option_in_discount).setSelected(mCommodity.is_in_discount);
            findViewById(R.id.option_support_return).setSelected(mCommodity.is_support_return);
            ((EditText) findViewById(R.id.desc)).setText(mCommodity.desc);
        }
    }
}
