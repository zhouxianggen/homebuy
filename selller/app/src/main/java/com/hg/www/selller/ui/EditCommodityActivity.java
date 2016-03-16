package com.hg.www.selller.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.hg.www.selller.R;
import com.hg.www.selller.data.api.CommodityApi;
import com.hg.www.selller.data.api.VolleyApi;
import com.hg.www.selller.data.define.Commodity;
import com.hg.www.selller.ui.component.LocalNetworkImageView;
import com.hg.www.selller.util.HorizontalListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditCommodityActivity extends AppCompatActivity {
    private static final String TAG = NewOrderListActivity.class.getSimpleName();
    private Context mContext;
    private String mTitle = "";
    private static final int RESULT_CODE_ADD_IMAGE = 1;
    private Commodity mCommodity;
    private LinearLayout mLayImageList;
    private ImageListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_commodity);

        mContext = this;
        mLayImageList = (LinearLayout) findViewById(R.id.lay_commodity_image);
        Intent intent = getIntent();
        String id = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_ITEM_ID));
        mCommodity = CommodityApi.getInstance().getCommodity(id);
        if (mCommodity == null) {
            mCommodity = new Commodity();
            mTitle = getString(R.string.ACTIVITY_ADD_COMMODITY);
        } else {
            mTitle = getString(R.string.ACTIVITY_EDIT_COMMODITY);
        }

        initializeActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + String.valueOf(item.getItemId()));
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult with request code " + String.valueOf(requestCode));
        Log.d(TAG, "onActivityResult with result code " + String.valueOf(resultCode));
        if (requestCode == RESULT_CODE_ADD_IMAGE) {
            if (resultCode == RESULT_OK) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                mAdapter.addImage(thumbnail);
                mAdapter.notifyDataSetChanged();
            }
        }
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

    private void refreshUI() {
        if (!mCommodity.title.isEmpty()) {
            ((EditText) findViewById(R.id.commodity_title)).setText(mCommodity.title);
        }
        if (mCommodity.price > 0) {
            ((EditText) findViewById(R.id.commodity_price)).setText(String.valueOf(mCommodity.price));
        }
        if (!mCommodity.description.isEmpty()) {
            ((EditText) findViewById(R.id.commodity_desc)).setText(mCommodity.description);
        }

        Spinner spinner = (Spinner) findViewById(R.id.in_stock);
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (mCommodity.in_stock != 0) {
            int position = adapter.getPosition(getString(R.string.in_stock));
            spinner.setSelection(position);
        } else {
            int position = adapter.getPosition(getString(R.string.out_of_stock));
            spinner.setSelection(position);
        }

        CheckBox checkBox = (CheckBox) findViewById(R.id.option_in_discount);
        checkBox.setChecked(mCommodity.in_discount != 0);
        checkBox = (CheckBox) findViewById(R.id.option_support_return);
        checkBox.setChecked(mCommodity.support_return != 0);

        List<String> images = mCommodity.getImages();
        images.add("");
        for (String path : images) {
            View view = View.inflate(this, R.layout.commodity_image, null);
            LocalNetworkImageView imageView = (LocalNetworkImageView) view.findViewById(R.id.img);

            view.setTag(path);
            view.setOnClickListener(onPictureClickListener);

            if (path.toString().startsWith("http://") || path.toString().startsWith("https://")) {
                Log.d(TAG, "网络图片地址, path = " + path);
                imageView.setImageUrl(path, VolleyApi.getInstance().getImageLoader());
            }
            else if (path.toString().startsWith("content://") || path.toString().startsWith("file://")) {
                Log.d(TAG, "本地图片地址, path = " + path);
                try {
                    Uri uri = Uri.parse(path);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setLocalImageBitmap(bitmap);
                } catch (IOException e) {
                    Log.d(TAG, "path not found" );
                }
            } else if (path.isEmpty()) {
            }

            mLayImageList.addView(view,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    class ImageListAdapter extends BaseAdapter {
        private List<Bitmap> mImages = new ArrayList<>();

        ImageListAdapter() {
            Rect rect = new Rect(0, 0, 1, 1);
            Bitmap image = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
            mImages.add(image);
        }

        public void deleteItem(int position) {
            if (position + 1 < mImages.size()) {
                mImages.remove(position);
            }
        }

        public void addImage(Bitmap bitmap) {
            mImages.add(mImages.size()-1, bitmap);
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public Object getItem(int position) {
            return mImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mImages.get(position).hashCode();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView " + String.valueOf(position));
            View view;
            if (convertView != null) {
                view = convertView;
            } else if (position + 1 < mImages.size()) {
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.image_list_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                imageView.setImageBitmap(mImages.get(position));
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.add_image_btn, null);
            }
            ListItemViewHolder holder = (ListItemViewHolder) view.getTag();
            if (holder == null) {
                holder = new ListItemViewHolder();
                if (position + 1 < mImages.size()) {
                    holder.deleteImageButtonOnClickListener = new DeleteImageButtonOnClickListener();
                    holder.deleteImageButtonOnClickListener.position = position;
                    holder.deleteImageButton = (Button) view.findViewById(R.id.btn_delete_image);
                    holder.deleteImageButton.setOnClickListener(holder.deleteImageButtonOnClickListener);
                } else {
                    holder.addImageButtonOnClickListener = new AddImageButtonOnClickListener();
                    holder.addImageButtonOnClickListener.position = position;
                    holder.addImageButton = (Button) view.findViewById(R.id.btn_add_image);
                    holder.addImageButton.setOnClickListener(holder.addImageButtonOnClickListener);
                }
                view.setTag(holder);
            }
            return view;
        }
    }

    static class ListItemViewHolder {
        public Button deleteImageButton;
        public Button addImageButton;
        public DeleteImageButtonOnClickListener deleteImageButtonOnClickListener;
        public AddImageButtonOnClickListener addImageButtonOnClickListener;
    }

    class DeleteImageButtonOnClickListener implements View.OnClickListener {
        public int position;

        @Override
        public void onClick(View view) {
            Log.d(TAG, "delete " + String.valueOf(position));
            mAdapter.deleteItem(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    class AddImageButtonOnClickListener implements View.OnClickListener {
        public int position;

        @Override
        public void onClick(View view) {
            Log.d(TAG, "add " + String.valueOf(position));
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RESULT_CODE_ADD_IMAGE);
        }
    }
}
