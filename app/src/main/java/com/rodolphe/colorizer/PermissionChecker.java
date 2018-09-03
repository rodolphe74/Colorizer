package com.rodolphe.colorizer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telecom.Call;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PermissionChecker {
    private static final String TAG = PermissionChecker.class.getSimpleName();
    protected static boolean hasPermissions;
    private static PermissionChecker instance = null;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0;
    private Activity mainActivity;

    private CallBackHelper callBackHelper;

    public PermissionChecker(Activity activity, CallBackHelper callBackHelper) {
        this.mainActivity = activity;
        this.callBackHelper = callBackHelper;
    }

    /*
    static public PermissionChecker getInstance(Activity activity, CallBackHelper callBackHelper) {
        if (instance == null) {
            instance = new PermissionChecker(activity, callBackHelper);
            return instance;
        } else {
            return instance;
        }
    }
    */

    @TargetApi(Build.VERSION_CODES.M)
    protected void permissionsCheck() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        // Add permission check for any permission that is not NORMAL_PERMISSIONS
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!addPermission(permissionsList, Manifest.permission.WAKE_LOCK))
            permissionsNeeded.add(Manifest.permission.WAKE_LOCK);
        if (!addPermission(permissionsList, Manifest.permission.INTERNET))
            permissionsNeeded.add(Manifest.permission.INTERNET);

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "The following permissions are requested for the application to run :\n" + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++) {
                    message = message + "\n" + permissionsNeeded.get(i);
                }
                message += "\nPlease accept what follows";
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                // Will call onRequestPermissionsResult in Activity when the user accept or not
                return;
            }
            mainActivity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            // Will call onRequestPermissionsResult in Activity
            return;
        }
        // permissions already granted -> continue execution in activity
        callBackHelper.callBack();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mainActivity)
                .setMessage(message)
                .setTitle("Hello")
                .setIcon(R.drawable.brain)
                .setPositiveButton("Ok", okListener)
                // .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (mainActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!mainActivity.shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    /*
    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for RECORD_AUDIO
                if (perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    // launch method demo();
                } else {
                    // Permission Denied
                    Toast.makeText(mainActivity, "Denied permission", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                mainActivity.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    */
}
