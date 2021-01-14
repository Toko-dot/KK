package com.ms.kk.module.photo.album;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.base.Logger;
import com.ms.kk.databinding.ActivityAlbumBinding;
import com.ms.kk.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private com.ms.kk.databinding.ActivityAlbumBinding binding;
    private AlbumListAdapter adapter;
    public static final int CODE_ALBUM = 110;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityAlbumBinding) DataBindingUtil.setContentView(this, R.layout.activity_album));
        initView();
    }

    private void initView() {

        binding.llHeader.tvTitle.setText("相册");

        binding.rxAlbum.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new AlbumListAdapter();

        adapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String data, int pos) {
                File file = new File(data);
                String path = FileUtils.fileToAvatar(AlbumActivity.this, file);
                if (path == null) {
                    Logger.logD("path = null");
                    return;
                }
                String base64 = FileUtils.fileToBase64(path);
                new File(path).delete();
                Intent intent = new Intent();
                intent.putExtra("image", base64);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.rxAlbum.setAdapter(adapter);

        adapter.refresh(queryImage());


    }

    public void onFinish(View view) {
        finish();
    }

    private ArrayList<String> queryImage() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    list.add(path);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
        }

        return list;
    }


}