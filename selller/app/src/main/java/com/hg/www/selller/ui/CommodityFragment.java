package com.hg.www.selller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.hg.scanner.barcode.decoding.DecodeFormatManager;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Category;
import com.hg.www.selller.service.BasicService;
import com.hg.www.selller.service.CategoryService;
import com.hg.www.selller.service.CommodityService;
import com.hg.www.selller.ui.component.CommodityAdapter;

public class CommodityFragment extends Fragment {
    private static final String TAG = CommodityFragment.class.getSimpleName();
    private static final int parent = 0;
    private Context mContext;
	private RecyclerView mRecyclerView;
    private CommodityAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_commodity, container, false);

        mContext = getActivity();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        final FloatingActionButton btnScan = (FloatingActionButton) rootView.findViewById(R.id.action_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton btnAddCommodity = (FloatingActionButton) rootView.findViewById(R.id.action_add_commodity);
        btnAddCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCommodityActivity.class);
                intent.putExtra(getActivity().getString(R.string.EXTRA_COMMODITY_CATEGORY_ID), parent);
                mContext.startActivity(intent);
            }
        });

        final FloatingActionButton btnAddCategory = (FloatingActionButton) rootView.findViewById(R.id.action_add_category);
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
                                final Category category = CategoryApi.getInstance().createCategory();
                                category.setStringProperty(TableSchema.CategoryEntry.COLUMN_NAME_TITLE, title);
                                category.setIntProperty(TableSchema.CategoryEntry.COLUMN_NAME_PARENT, parent);
                                CategoryService.startService(BasicService.ACTION_POST, category.toString());
                            }

                        })
                        .show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            mAdapter = new CommodityAdapter(mContext, parent);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.onUpdateAdapter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String barcode = intent.getStringExtra(getString(R.string.EXTRA_SCAN_RESULT));
                BarcodeFormat format = BarcodeFormat.values()[intent.getIntExtra(getString(R.string.EXTRA_BARCODE_FORMAT), 0)];
                Toast.makeText(mContext, barcode, Toast.LENGTH_SHORT).show();

                if (DecodeFormatManager.PRODUCT_FORMATS.contains(format)) {
                    Intent newIntent = new Intent(mContext, EditCommodityActivity.class);
                    newIntent.putExtra(getString(R.string.EXTRA_COMMODITY_CATEGORY_ID), parent);
                    newIntent.putExtra(getString(R.string.EXTRA_COMMODITY_BARCODE_ID), barcode);
                    mContext.startActivity(newIntent);
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}
