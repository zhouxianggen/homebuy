package com.hg.www.selller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.BarcodeApi;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.db.TableSchema;
import com.hg.www.selller.data.define.Barcode;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.ui.toolkit.ImageDownloader;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class EditCommodityActivity extends AppCompatActivity {
    private static final String TAG = EditCommodityActivity.class.getSimpleName();
    private Context mContext;
    private String mTitle = "";
    private Commodity mCommodity;
    private final ImageDownloader imageDownloader = new ImageDownloader();
    private EditText viewCommodityTitle;
    private EditText viewCommodityPrice;
    private Spinner viewInStock;
    private CheckBox viewInDiscount;
    private CheckBox viewSupportReturn;
    private LinearLayout layImageList;
    private EditText viewCommodityDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_commodity);

        mTitle = getString(R.string.ACTIVITY_ADD_COMMODITY);

        Intent intent = getIntent();
        int commodityId = intent.getIntExtra(getString(R.string.EXTRA_COMMODITY_ID), -1);
        String barcodeNumber = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_BARCODE_ID));
        int categoryId = intent.getIntExtra(getString(R.string.EXTRA_CATEGORY_ID), -1);
        Log.d(TAG, String.format("commodity id [%d], category id [%d]", commodityId, categoryId));

        if (categoryId == -1) {
            finish();
        } else if (barcodeNumber != null && !barcodeNumber.isEmpty()) {
            Log.d(TAG, String.format("barcode [%s]", barcodeNumber));
            Barcode barcode = BarcodeApi.getInstance().getBarcode(barcodeNumber);
            mCommodity =  CommodityApi.getInstance().getCommodityByBarcode(barcodeNumber);
            if (mCommodity == null) {
                Log.d(TAG, String.format("can not get commodity by barcode"));
                mCommodity = CommodityApi.getInstance().createCommodity();
                mCommodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY_ID, categoryId);
                mCommodity.setStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_BARCODE, barcodeNumber);
                if (barcode != null) {
                    mCommodity.setStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE,
                            barcode.getStringProperty(TableSchema.BarcodeEntry.COLUMN_NAME_ITEM_NAME));
                    // // FIXME: 2016/3/29 set price her
                }
            } else {
                mTitle = getString(R.string.ACTIVITY_EDIT_COMMODITY);
            }
        } else if (commodityId != -1) {
            mTitle = getString(R.string.ACTIVITY_EDIT_COMMODITY);
            mCommodity =  CommodityApi.getInstance().getCommodity(commodityId);
            if (mCommodity == null) {
                finish();
            }
        } else {
            mCommodity = CommodityApi.getInstance().createCommodity();
            mCommodity.setIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_CATEGORY_ID, categoryId);
        }

        if (mCommodity == null) {
            finish();
        }

        mContext = this;
        viewCommodityTitle = (EditText) findViewById(R.id.commodity_title);
        viewCommodityPrice = (EditText) findViewById(R.id.commodity_price);
        viewInStock = (Spinner) findViewById(R.id.in_stock);
        viewInDiscount = (CheckBox) findViewById(R.id.option_in_discount);
        viewSupportReturn = (CheckBox) findViewById(R.id.option_support_return);
        layImageList = (LinearLayout) findViewById(R.id.lay_commodity_image);
        viewCommodityDesc = (EditText) findViewById(R.id.commodity_desc);

        initializeActionBar();
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_commodity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_confirm) {
            updateCommodity();

//            new HttpAsyncTask(this, "POST", AppSettings.getCommodityServerAddress(), mCommodity.toString(),
//                    new HttpAsyncTask.OnSuccessListener() {
//                        @Override
//                        public void onSuccess(String result) {
//                            finish();
//                        }
//                    }, new HttpAsyncTask.OnFailureListener() {
//                        @Override
//                        public void onFailure(String errors) {
//                            Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
//                        }
//                    }, true).execute();
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        textView.setText(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    View.OnClickListener onPictureClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final String path = v.getTag().toString();

            if (path.isEmpty()) {
                new AlertDialogWrapper.Builder(mContext)
                        .setItems(R.array.add_image_action, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    // 相册
                                    case 0:
                                        Intent intent = new Intent(mContext, PickImageActivity.class);
                                        startActivityForResult(intent, 333);
                                        break;
                                    // 拍照
                                    case 1:
                                        intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, 222);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .show();
            } else {
                new AlertDialogWrapper.Builder(mContext)
                        .setItems(R.array.set_image_action, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    // 设为图标
                                    case 0:
                                        mCommodity.setStringProperty(
                                                TableSchema.CommodityEntry.COLUMN_NAME_THUMBNAIL, path);
                                        refreshUI();
                                        break;
                                    // 删除图片
                                    case 1:
                                        mCommodity.deleteImage(path);
                                        refreshUI();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .show();
            }
        }

    };

    private void updateCommodity() {
        mCommodity.setStringProperty(
                TableSchema.CommodityEntry.COLUMN_NAME_TITLE,
                viewCommodityTitle.getText().toString());

        mCommodity.setFloatProperty(
                TableSchema.CommodityEntry.COLUMN_NAME_PRICE,
                Float.parseFloat(viewCommodityPrice.getText().toString()));

        mCommodity.setStringProperty(
                TableSchema.CommodityEntry.COLUMN_NAME_DESCRIPTION,
                viewCommodityDesc.getText().toString());

        String value = viewInStock.getSelectedItem().toString();
        mCommodity.setIntProperty(
                TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK,
                value.equals(getString(R.string.in_stock))? 1 : 0);

        mCommodity.setIntProperty(
                TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT,
                viewInDiscount.isChecked()? 1 : 0);

        mCommodity.setIntProperty(
                TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN,
                viewSupportReturn.isChecked()? 1 : 0);
    }

    private void initUI() {
        String title = mCommodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_TITLE);
        if (title != null && !title.isEmpty()) {
            viewCommodityTitle.setText(title);
        }

        float price = mCommodity.getFloatProperty(TableSchema.CommodityEntry.COLUMN_NAME_PRICE);
        if (price > 0) {
            viewCommodityPrice.setText(String.valueOf(price));
        }

        String desc = mCommodity.getStringProperty(TableSchema.CommodityEntry.COLUMN_NAME_DESCRIPTION);
        if (desc != null && !desc.isEmpty()) {
            viewCommodityDesc.setText(desc);
        }

        ArrayAdapter adapter = (ArrayAdapter) viewInStock.getAdapter();
        int in_stock = mCommodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_STOCK);
        if (in_stock != 0) {
            int position = adapter.getPosition(getString(R.string.in_stock));
            viewInStock.setSelection(position);
        } else {
            int position = adapter.getPosition(getString(R.string.out_of_stock));
            viewInStock.setSelection(position);
        }

        int in_discount = mCommodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_IN_DISCOUNT);
        viewInDiscount.setChecked(in_discount != 0);

        int support_return = mCommodity.getIntProperty(TableSchema.CommodityEntry.COLUMN_NAME_SUPPORT_RETURN);
        viewSupportReturn.setChecked(support_return != 0);
    }

    private void refreshUI() {
        layImageList.removeAllViews();

        List<String> images = mCommodity.getImages();
        images.add("");
        for (String path : images) {
            View view = View.inflate(this, R.layout.commodity_image, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            view.setTag(path);
            view.setOnClickListener(onPictureClickListener);
            if (!path.isEmpty()) {
                imageDownloader.download(path, imageView, 80, 80);
            }
            layImageList.addView(view,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 333) {
                if (data != null) {
                    String[] images = data.getStringArrayExtra(getString(R.string.EXTRA_SELECTED_IMAGES));
                    Log.d(TAG, images.toString());
                    if (images != null) {
                        for (String image : images) {
                            mCommodity.addImage(image);
                        }
                        refreshUI();
                    }
                }
            } else if (requestCode == 222) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                mCommodity.addImage(getRealPathFromURI(tempUri));
            }
        }
    }

    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
