package com.hg.www.selller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.www.selller.GlobalContext;
import com.hg.www.selller.R;
import com.hg.www.selller.ui.toolkit.ImageDownloader;
import com.hg.www.selller.SystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class PickImageActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    private static final String TAG = PickImageActivity.class.getSimpleName();
    private Context mContext;
    private int maxSize = 8;
    private ArrayList<String> selectedFiles = new ArrayList<String>();
    private GridView mGridView;
    private MyAdapter mAdapter;
    private int gap = GlobalContext.getInstance().getResources().getDimensionPixelSize(R.dimen.gap_photo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);

        mGridView = (GridView) findViewById(R.id.gridview);
        mContext = this;
        initializeActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mAdapter == null) {
            mAdapter = new MyAdapter(this);
            mGridView.setAdapter(mAdapter);
            mGridView.setOnItemClickListener(this);
        }
        onUpdateAdapter();
    }

    public void onUpdateAdapter() {
        Log.d(TAG, "onUpdateAdapter");
        new PicturePickTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pick_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_confirm) {
            Intent data = new Intent();
            data.putExtra(getString(R.string.EXTRA_SELECTED_IMAGES), selectedFiles.toArray(new String[0]));
            setResult(Activity.RESULT_OK, data);
            finish();
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_confirm);
        item.setVisible(selectedFiles.size() > 0);
        item.setTitle(String.format("完成(%d/%d)", selectedFiles.size(), maxSize));
        return super.onPrepareOptionsMenu(menu);
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_common);
        View customView = actionBar.getCustomView();
        TextView textView = (TextView) customView.findViewById(R.id.title);
        textView.setText(getString(R.string.ACTIVITY_PICK_IMAGE));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = mAdapter.images.get(position);
        onPictureSelectedChange(path);
        invalidateOptionsMenu();
    }

    private void onPictureSelectedChange(String path) {
        if (!selectedFiles.contains(path)) {
            if (selectedFiles.size() >= maxSize) {
                String message = String.format("最多只能选%d张相片", maxSize);
                Toast.makeText(GlobalContext.getInstance(), message, Toast.LENGTH_SHORT).show();
                return;
            }

            selectedFiles.add(path);
        }
        else {
            selectedFiles.remove(path);
        }

        mAdapter.notifyDataSetChanged();
        invalidateOptionsMenu();
    }

    class PicturePickTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ArrayList picFileList = new ArrayList<String>();

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Uri uri = intent.getData();
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = GlobalContext.getInstance().getContentResolver().query(uri, proj, null, null, null);
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                picFileList.add(new File(path).getAbsolutePath());
            }
            cursor.close();

            Collections.reverse(picFileList);

            return picFileList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Log.d(TAG, "images count " + String.valueOf(strings.size()));
            mAdapter.images = strings;
            mAdapter.notifyDataSetChanged();
        }
    }

    class MyAdapter extends BaseAdapter {
        public Context context;
        public ArrayList<String> images = new ArrayList<>();
        private final ImageDownloader imageDownloader = new ImageDownloader();

        MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return images.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView != null) {
                view = convertView;
            }
            else {
                view = View.inflate(mContext, R.layout.picture_image, null);
            }

            PictureImageViewHolder holder = (PictureImageViewHolder) view.getTag();
            if (holder == null) {
                holder = new PictureImageViewHolder();
                holder.image = (ImageView) view.findViewById(R.id.image);
                holder.cover = view.findViewById(R.id.cover);
                holder.btnCheck = (ImageView) view.findViewById(R.id.btn_check);
                view.setTag(holder);
            }
            holder.position = position;

            String path = images.get(position);
            if (selectedFiles.contains(path)) {
                holder.cover.setVisibility(View.VISIBLE);
                holder.btnCheck.setSelected(true);
            } else {
                holder.cover.setVisibility(View.GONE);
                holder.btnCheck.setSelected(false);
            }
            imageDownloader.download(path, holder.image, 80, 80);
            int width = (SystemUtils.getScreenWidth() - gap * 2) / 3;
            view.setLayoutParams(new AbsListView.LayoutParams(width, width));
            return view;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        class PictureImageViewHolder {
            public ImageView image;
            public View cover;
            public ImageView btnCheck;
            public int position;
        }
    }
}
