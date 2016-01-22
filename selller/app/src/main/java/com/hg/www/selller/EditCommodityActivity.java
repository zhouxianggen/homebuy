package com.hg.www.selller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.www.selller.define.CommodityItem;
import com.hg.www.selller.util.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

public class EditCommodityActivity extends AppCompatActivity {
    private static final String TAG = "EditCommodityActivity";
    private static final int RESULT_CODE_ADD_IMAGE = 1;
    private CommodityItem mCommodityItem;
    private HorizontalListView mListView;
    private ImageListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_commodity);
        Intent intent = getIntent();
        String commodityId = intent.getStringExtra(getString(R.string.EXTRA_COMMODITY_ID));
        commodityId = "1"; // fix
        mCommodityItem = ((MyApplication)getApplication()).getDataManager().GetCommodity(commodityId);
        initializeActionBar();
        initializeView();
        mListView = (HorizontalListView) findViewById(R.id.image_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter == null) {
            mAdapter = new ImageListAdapter();
            mListView.setAdapter(mAdapter);
        }
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
        actionBar.setDisplayHomeAsUpEnabled(true);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        if (mCommodityItem.id.isEmpty()) {
            textView.setText(getApplication().getString(R.string.add_commodity));
        } else {
            textView.setText(getApplication().getString(R.string.edit_commodity));
        }
    }

    private void initializeView() {
        if (mCommodityItem.id.isEmpty()) {
            ((EditText) findViewById(R.id.title)).setText(mCommodityItem.title);
            ((EditText) findViewById(R.id.price)).setText(String.valueOf(mCommodityItem.price));
            findViewById(R.id.option_in_stock).setSelected(mCommodityItem.is_in_stock);
            findViewById(R.id.option_in_discount).setSelected(mCommodityItem.is_in_discount);
            findViewById(R.id.option_support_return).setSelected(mCommodityItem.is_support_return);
            ((EditText) findViewById(R.id.desc)).setText(mCommodityItem.desc);
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

    ;

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
