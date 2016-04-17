package com.hg.www.selller.ui.component;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CategoryApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Category;
import com.hg.www.selller.service.CategoryService;
import com.hg.www.selller.service.HttpAsyncTask;
import com.hg.www.selller.ui.CaptureActivity;
import com.hg.www.selller.ui.EditCommodityActivity;

public class AddCommodityButton {
    public FloatingActionButton btnScan = null;
    public FloatingActionButton btnAddCommodity = null;
    public FloatingActionButton btnAddCategory = null;

    public AddCommodityButton(final Fragment fragment, final View view, final int parent) {
        final Activity activity = fragment.getActivity();
        init(activity, view, parent);
    }

    public AddCommodityButton(final Activity activity, final View view, final int parent) {
        init(activity, view, parent);
    }

    private void init(final Activity activity, final View view, final int parent) {

        btnScan = (FloatingActionButton) view.findViewById(R.id.action_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CaptureActivity.class);
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                activity.startActivityForResult(intent, 0);
            }
        });

        btnAddCommodity = (FloatingActionButton) view.findViewById(R.id.action_add_commodity);
        btnAddCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditCommodityActivity.class);
                intent.putExtra(GlobalContext.getInstance().getString(R.string.EXTRA_CATEGORY_ID), parent);
                activity.startActivity(intent);
            }
        });

        btnAddCategory = (FloatingActionButton) view.findViewById(R.id.action_add_category);
        if (parent != 0) {
            btnAddCategory.setVisibility(View.GONE);
        }
    }
}
