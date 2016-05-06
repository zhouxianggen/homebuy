package com.hg.www.buyer.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hg.www.buyer.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = CartActivity.class.getSimpleName();

    // ui components
    private Toolbar toolbar;
    private Spinner spinnerAddresses;
    private RecyclerView recyclerViewCommodities;
    private TextView textViewPayment;
    private Button buttonCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.activity_cart));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerAddresses = (Spinner) findViewById(R.id.spinner_address);
        recyclerViewCommodities = (RecyclerView) findViewById(R.id.recycler_view);
        textViewPayment = (TextView) findViewById(R.id.payment);
        buttonCommit = (Button) findViewById(R.id.btn_commit);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }

    private void refreshUi() {

        ArrayList<String> addresses = new ArrayList<>();
        addresses.add("address 1");
        addresses.add("address 2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, addresses);
        spinnerAddresses.setAdapter(adapter);
    }
}
