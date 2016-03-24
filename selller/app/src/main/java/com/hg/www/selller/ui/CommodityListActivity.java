package com.hg.www.selller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.hg.scanner.barcode.decoding.DecodeFormatManager;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityCategoryApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.define.CommodityCategory;
import com.hg.www.selller.service.CommodityService;
import com.hg.www.selller.ui.component.CommodityAdapter;

public class CommodityListActivity extends AppCompatActivity {
    private static final String TAG = "CommodityGroupList";
    private Context mContext;
    private RecyclerView mRecyclerView;
    private CommodityAdapter mAdapter;
    private String mCategoryId;
    private String mCategoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_list);

        mContext = this;

        Intent intent = getIntent();
        mCategoryId = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_CATEGORY_ID));
        mCategoryTitle = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_CATEGORY_TITLE));
        if (mCategoryId == null || mCategoryTitle == null) {
            finish();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final FloatingActionButton btnScan = (FloatingActionButton) findViewById(R.id.action_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton btnAddCommodity = (FloatingActionButton) findViewById(R.id.action_add_commodity);
        btnAddCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCommodityActivity.class);
                intent.putExtra(getString(R.string.EXTRA_COMMODITY_CATEGORY_ID), mCategoryId);
                mContext.startActivity(intent);
            }
        });

        final FloatingActionButton btnAddCategory = (FloatingActionButton) findViewById(R.id.action_add_category);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.dialog_add_category, null);
                final EditText editText = (EditText) promptView.findViewById(R.id.input);

                new AlertDialogWrapper.Builder(mContext)
                        .setView(promptView)
                        .setNegativeButton(R.string.btn_cancel, null)
                        .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = editText.getText().toString();
                                final CommodityCategory category = CommodityCategoryApi.getInstance().createCategory();
                                category.title = title;
                                category.parent = mCategoryId;
                                if (!title.isEmpty()) {
                                    CommodityService.updateServerCategory(mContext, category, 4,
                                            new HttpAsyncTask.OnSuccessListener() {
                                                @Override
                                                public void onSuccess() {
                                                    CommodityCategoryApi.getInstance().insertCategory(category); // fixme
                                                    mAdapter.onUpdateAdapter();
                                                }
                                            }, new HttpAsyncTask.OnFailureListener() {
                                                @Override
                                                public void onFailure(String errors) {
                                                    Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }

                        })
                        .show();
            }
        });

        initializeActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            mAdapter = new CommodityAdapter(mContext, mCategoryId);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.onUpdateAdapter();
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        textView.setText(mCategoryTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String barcode = intent.getStringExtra(getString(R.string.EXTRA_SCAN_RESULT));
                BarcodeFormat format = BarcodeFormat.values()[intent.getIntExtra(getString(R.string.EXTRA_BARCODE_FORMAT), 0)];
                Toast.makeText(this, barcode, Toast.LENGTH_SHORT).show();

                if (DecodeFormatManager.PRODUCT_FORMATS.contains(format)) {
                    Intent newIntent = new Intent(this, EditCommodityActivity.class);
                    newIntent.putExtra(getString(R.string.EXTRA_COMMODITY_CATEGORY_ID), mCategoryId);
                    newIntent.putExtra(getString(R.string.EXTRA_COMMODITY_BARCODE_ID), barcode);
                    startActivity(newIntent);
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}
