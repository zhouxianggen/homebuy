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
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.BarcodeApi;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.HttpAsyncTask;
import com.hg.www.selller.data.define.Barcode;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.service.CommodityService;
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

        Intent intent = getIntent();
        String commodity_id = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_ID));
        String barcode_id = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_BARCODE_ID));
        String category_id = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_CATEGORY_ID));

        mTitle = getString(R.string.ACTIVITY_ADD_COMMODITY);

        Log.d(TAG, String.format("cateogry id is %s", category_id));
        if (category_id == null) {
            finish();
        } else if (barcode_id != null) {
            Log.d(TAG, String.format("barcode id is %s", barcode_id));
            Barcode barcode = BarcodeApi.getInstance().getBarcode(barcode_id);
            mCommodity =  CommodityApi.getInstance().getCommodityByBarcode(barcode_id);
            if (mCommodity == null) {
                Log.d(TAG, String.format("can not get commodity by barcode"));
                mCommodity = CommodityApi.getInstance().createCommodity();
                mCommodity.category = category_id;
                mCommodity.barcode = barcode_id;
                if (barcode != null) {
                    mCommodity.title = barcode.commodity_title;
                    mCommodity.price = barcode.commodity_price;
                }
            } else {
                mTitle = getString(R.string.ACTIVITY_EDIT_COMMODITY);
            }
        } else if (commodity_id != null) {
            Log.d(TAG, String.format("commodity id is %s", commodity_id));
            mTitle = getString(R.string.ACTIVITY_EDIT_COMMODITY);
            mCommodity =  CommodityApi.getInstance().getCommodity(commodity_id);
            if (mCommodity == null) {
                finish();
            }
        } else {
            mCommodity = CommodityApi.getInstance().createCommodity();
            mCommodity.category = category_id;
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

            CommodityService.updateServerCommodity(mContext, mCommodity, 4,
                    new HttpAsyncTask.OnSuccessListener() {
                        @Override
                        public void onSuccess() {
                            CommodityApi.getInstance().insertCommodity(mCommodity); // fixme
                            finish();
                        }
                    }, new HttpAsyncTask.OnFailureListener() {
                        @Override
                        public void onFailure(String errors) {
                            Toast.makeText(mContext, errors, Toast.LENGTH_SHORT).show();
                        }
                    });
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
                                        mCommodity.setThumbnail(path);
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
        mCommodity.title = viewCommodityTitle.getText().toString();
        mCommodity.price = Double.parseDouble(viewCommodityPrice.getText().toString());
        mCommodity.description = viewCommodityDesc.getText().toString();

        String value = viewInStock.getSelectedItem().toString();
        if (value.equals(getString(R.string.in_stock))) {
            mCommodity.in_stock = 1;
        } else {
            mCommodity.in_stock = 0;
        }

        mCommodity.in_discount = viewInDiscount.isChecked()? 1 : 0;
        mCommodity.support_return = viewSupportReturn.isChecked()? 1 : 0;
    }

    private void initUI() {
        if (!mCommodity.title.isEmpty()) {
            viewCommodityTitle.setText(mCommodity.title);
        }
        if (mCommodity.price > 0) {
            viewCommodityPrice.setText(String.valueOf(mCommodity.price));
        }
        if (!mCommodity.description.isEmpty()) {
            viewCommodityDesc.setText(mCommodity.description);
        }

        ArrayAdapter adapter = (ArrayAdapter) viewInStock.getAdapter();
        if (mCommodity.in_stock != 0) {
            int position = adapter.getPosition(getString(R.string.in_stock));
            viewInStock.setSelection(position);
        } else {
            int position = adapter.getPosition(getString(R.string.out_of_stock));
            viewInStock.setSelection(position);
        }

        viewInDiscount.setChecked(mCommodity.in_discount != 0);
        viewSupportReturn.setChecked(mCommodity.support_return != 0);
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
