package com.hg.www.selller.ui.component;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.define.Category;
import com.hg.www.selller.ui.CaptureActivity;
import com.hg.www.selller.ui.EditCommodityActivity;

public class AddCommodityButton {

    public AddCommodityButton(final Fragment fragment, final View view, final int parent) {
        final Activity activity = fragment.getActivity();
        init(activity, view, parent);
    }

    public AddCommodityButton(final Activity activity, final View view, final int parent) {
        init(activity, view, parent);
    }

    private void init(final Activity activity, final View view, final int parent) {

        final FloatingActionButton btnScan = (FloatingActionButton) view.findViewById(R.id.action_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CaptureActivity.class);
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                activity.startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton btnAddCommodity = (FloatingActionButton) view.findViewById(R.id.action_add_commodity);
        btnAddCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditCommodityActivity.class);
                intent.putExtra(GlobalContext.getInstance().getString(R.string.EXTRA_CATEGORY_ID), parent);
                activity.startActivity(intent);
            }
        });

        final FloatingActionButton btnAddCategory = (FloatingActionButton) view.findViewById(R.id.action_add_category);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(activity);
                View promptView = layoutInflater.inflate(R.layout.dialog_add_category, null);
                final EditText editText = (EditText) promptView.findViewById(R.id.input);

                new AlertDialogWrapper.Builder(activity)
                        .setView(promptView)
                        .setNegativeButton(R.string.btn_cancel, null)
                        .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = editText.getText().toString();
                                final Category category = CategoryApi.getInstance().createCategory();
                                // add
                            }

                        })
                        .show();
            }
        });
        if (parent != 0) {
            btnAddCategory.setVisibility(View.GONE);
        }
    }
}
