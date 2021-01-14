package com.ms.kk.module.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ms.kk.R;
import com.ms.kk.base.BaseActivity;
import com.ms.kk.databinding.ActivityUserInfoBinding;
import com.ms.kk.module.photo.album.AlbumActivity;
import com.ms.kk.module.user.age.EditUserAgeActivity;
import com.ms.kk.module.user.name.EditUserNameActivity;
import com.ms.kk.module.user.sex.EditUserSexActivity;

import static com.ms.kk.module.photo.album.AlbumActivity.CODE_ALBUM;

public class UserInfoActivity extends BaseActivity<UserInfoViewModel> {

    private com.ms.kk.databinding.ActivityUserInfoBinding binding;
    public static final int CODE_EDIT_USER = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ((ActivityUserInfoBinding) DataBindingUtil.setContentView(this, R.layout.activity_user_info));
        initView();
        initViewModel();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        binding.setVm(viewModel);
    }


    public void onEditName(View view) {
        startActivityForResult(new Intent(this, EditUserNameActivity.class), CODE_EDIT_USER);
    }

    public void onEditSex(View view) {
        startActivityForResult(new Intent(this, EditUserSexActivity.class), CODE_EDIT_USER);
    }

    public void onEditAge(View view) {
        startActivityForResult(new Intent(this, EditUserAgeActivity.class), CODE_EDIT_USER);
    }

    public void onEditAvatar(View view) {
        if (checkFilePermission()) {
            gotoAlbum();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 66);
            }
        }
    }

    private void gotoAlbum() {
        startActivityForResult(new Intent(this, AlbumActivity.class), CODE_ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_ALBUM) {
            if (data == null)
                return;
            String image = data.getStringExtra("image");
            if (TextUtils.isEmpty(image))
                return;
            viewModel.editAvatar(image);
        } else {
            viewModel.queryUserInfo();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 66) {
            for (int p : grantResults) {
                if (p == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            gotoAlbum();
        }
    }

    public void onEditID(View view) {
        showToast("无法修改");
    }
}