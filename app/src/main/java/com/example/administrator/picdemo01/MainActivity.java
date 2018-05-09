package com.example.administrator.picdemo01;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.example.administrator.picdemo01.adapter.MainAdapter;
import com.example.administrator.picdemo01.base.BaseActivity;
import com.example.administrator.picdemo01.bean.MainBean;
import com.example.administrator.picdemo01.utils.BasePopup;
import com.example.administrator.picdemo01.utils.BitmapUtils;
import com.example.administrator.picdemo01.utils.PhotoUtils;
import com.example.administrator.picdemo01.widget.TouchImageView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainAdapter.OnItemClickListener {
    @BindView(R.id.img)
    TouchImageView img;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private String base_64_bitmap;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private int type = 0, aspectX = 0, aspectY = 0, output_X = 0, output_Y = 0;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    private View view1;
    private BasePopup popupWindow;
    private MainAdapter adapter;
    private List<MainBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        view1 = LayoutInflater.from(context).inflate(R.layout.layout_popup, null);
        popupWindow = new BasePopup(context);
        popupWindow.setContentView(view1);
        popupWindow.setAnimationStyle(R.style.popup_window_anim);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        rvMain.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new MainAdapter(data, this);

        rvMain.setAdapter(adapter);

        data.add(new MainBean("图片旋转"));
        data.add(new MainBean("图片旋转"));
        data.add(new MainBean("图片旋转"));
        data.add(new MainBean("图片旋转"));
        data.add(new MainBean("图片旋转"));
        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void OnItemClick(int position, View view) {
        switch (position) {
            case 0:
//                rotate();


                EditText et = view1.findViewById(R.id.et_popup);
                TextView tvv = view.findViewById(R.id.tv_main);

                final String trim = et.getText().toString().trim();
                Log.e("dasdas", tvv.getWidth() + "");
                popupWindow.setWidth(tvv.getWidth());
                popupWindow.showAsDropDown(view,0,0, Gravity.CENTER_HORIZONTAL);

                et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (!TextUtils.isEmpty(textView.getText().toString().trim())) {
                            if (i == EditorInfo.IME_ACTION_SEARCH) {
                                rotate(Integer.valueOf(textView.getText().toString().trim()));
                                return true;
                            }
                        }
                        return false;
                    }
                });


                break;

        }
    }

    private void rotate(int rotate) {
        /**
         *     图片旋转:
         *Android中原图是不能进行操作的,必须要先复制一张图到内存,然后再操作
         *旋转是在绘制过程中进行的
         * */
//加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(fileCropUri.getPath());
//搞一个一样大小一样样式的复制图
        Bitmap copybm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
//获取复制图的画布
        Canvas canvas = new Canvas(copybm);
//获取一个画笔,设置颜色
        Paint paint = new Paint();
        paint.setColor(Color.RED);
//设置图片绘制角度——设置矩阵
        Matrix matrix = new Matrix();
        /**
         matrix.setValues(new float[]{//这是矩阵的默认值
         1.5f,0,0,
         0,1,0,
         0,0,1
         });
         而旋转其实是将每个点坐标和sinx  cosx进行计算...
         */
//安卓提供了便捷方法
        matrix.setRotate(rotate, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//向画布绘制,绘制原图内容
        canvas.drawBitmap(bitmap, matrix, paint);
//canvas.drawPoint(10, 10, paint); 向指定位置画一个点
        img.setImageBitmap(copybm);
        Log.e("adda", "end");
    }

    public void btnClick(View view) {
        type = 0;
        uploadHeadImage();
    }

    private void uploadHeadImage() {
        List<TieBean> strings = new ArrayList<TieBean>();
//        strings.add(new TieBean(getResources().getString(R.string.dialog_notes)));
        strings.add(new TieBean(getResources().getString(R.string.tv_photo_open_from_camera)));
        strings.add(new TieBean(getResources().getString(R.string.tv_photo_open_from_photo)));
        DialogUIUtils.showSheet(context, strings, getResources().getString(R.string.dialog_cancel), Gravity.BOTTOM, false, true, new DialogUIItemListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(CharSequence text, int position) {
                switch (position) {
                    case 0:

                        takePhoto();

                        break;
                    case 1:

                        pickPhoto();

                        break;
                }
            }

            @Override
            public void onBottomBtnClick() {
                DialogUIUtils.showToast(getResources().getString(R.string.dialog_cancel));
            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void pickPhoto() {
        requestPermissions(context, new String[]{

                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                PhotoUtils.openPic(context, CODE_GALLERY_REQUEST);
            }

            @Override
            public void denied() {
                DialogUIUtils.showToast("部分权限获取失败，正常功能受到影响");
            }
        });
    }


    private void takePhoto() {
        requestPermissions(context, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                if (hasSdcard()) {

                    imageUri = Uri.fromFile(fileUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        //通过FileProvider创建一个content类型的Uri
                        imageUri = FileProvider.getUriForFile(context, "com.example.administrator.pictools", fileUri);
                    PhotoUtils.takePicture(context, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    Toast.makeText(context, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
                    Log.e("asd", "设备没有SD卡");
                }
            }

            @Override
            public void denied() {
                Toast.makeText(context, "部分权限获取失败，正常功能受到影响", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (type) {
            case 0:
                output_X = 800;
                output_Y = 600;
                aspectX = 4;
                aspectY = 3;

                break;
            case 1:
                output_X = 600;
                output_Y = 800;
                aspectX = 3;
                aspectY = 4;

                break;
            case 2:
                output_X = 1080 / 2;
                output_Y = 1920 / 2;
                aspectX = 9;
                aspectY = 16;

                break;
        }


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, aspectX, aspectY, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.example.administrator.pictools", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, aspectX, aspectY, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toast.makeText(context, "设备没有SD卡!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);
                        //在手机相册中显示刚拍摄的图片
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(fileCropUri);
                        mediaScanIntent.setData(contentUri);
                        sendBroadcast(mediaScanIntent);
                        base_64_bitmap = BitmapUtils.bitmapToBase64(bitmap);

                    }
                    break;
            }
        }
    }

    private void showImages(Bitmap bitmap) {

                img.setImageBitmap(bitmap);

        }




}
