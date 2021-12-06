package com.video.meng.multi.image;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.video.meng.multi.image.utils.SettingUtils;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    private ArrayList<String> resultList = new ArrayList<>();
    private Button mSubmitButton;
    private int mDefaultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);
        Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(this.getClassLoader(), MultiImageSelectorFragment.class.getName());
        fragment.setArguments(bundle);
        //   Fragment fragment =   Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, fragment)
                .commit();

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 完成按钮
        mSubmitButton = (Button) findViewById(R.id.commit);
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(false);
        } else {
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultList != null && resultList.size() > 0) {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    setResult(-1, data);
                    finish();
                }
            }
        });
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            if (!mSubmitButton.isEnabled()) {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
        } else {
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
        }
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));// 刷新系统相册  
            finish();
        }
    }

    String[] permissions = new String[]{Manifest.permission.CAMERA};

    /**
     * 拍照需要相机权限
     */
    public void setNeedPermission(final PermissionCallBack callBack) {
        requestRuntimePermission(this, permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                if (callBack != null) {
                    callBack.hasPermission();
                }
            }

            @Override
            public void onDenied(Activity activity, List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
                    if (showRequestPermission) {
                        new AlertDialog.Builder(MultiImageSelectorActivity.this)
                                .setMessage("拍照需要相机权限，否则无法使用拍照功能")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setNeedPermission(callBack);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    } else {
                        new AlertDialog.Builder(MultiImageSelectorActivity.this)
                                .setMessage("您已经禁止授予相机权限，请在权限管理中手动开启")
                                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SettingUtils.showInstalledAppDetails(MultiImageSelectorActivity.this);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    }
                }
            }
        });
    }

    private PermissionListener mListener;

    public interface PermissionListener {
        //授权，同意
        void onGranted();

        //拒绝
        void onDenied(Activity activity, List<String> deniedPermission);

    }

    public interface PermissionCallBack {
        //有权限
        void hasPermission();

    }

    /**
     * 6.0以上请求运行时权限
     *
     * @param permissions 需要申请的权限
     * @param listener    是否授权回调
     */
    private void requestRuntimePermission(Activity activity, String[] permissions, PermissionListener listener) {
        try {
            mListener = listener;
            //targetSdkVersion >= 23 Android6.0以上才要申请
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                List<String> permissionList = new ArrayList<>();
                for (String permission : permissions) {
                    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                        permissionList.add(permission);
                    }
                }

                if (!permissionList.isEmpty()) {
                    ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), 1);
                } else {
                    //授权成功
                    if (mListener != null)
                        mListener.onGranted();
                }
            } else {
                //授权成功
                if (mListener != null)
                    mListener.onGranted();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log.e(TAG, "requestRuntimePermission: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                //有权限被拒绝
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    //全部授权成功
                    if (deniedPermissions.isEmpty()) {
                        if (mListener != null)
                            mListener.onGranted();
                    } else {
                        if (mListener != null)
                            mListener.onDenied(this, deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }
}
