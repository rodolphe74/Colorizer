package com.rodolphe.colorizer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback   {

    // declare the dialog as a member field of your activity
    private ProgressDialog progressDialog;
    private ProgressDialog thinkingDialog;
    private DownloadTask downloadTask1;
    private DownloadTask downloadTask2;

    private static FloatingActionButton addButton;
    private static FloatingActionButton brainButton;

    private String protoFilePath;
    private String weightsFilePath;
    private String weightsFilePath_a;
    private String weightsFilePath_b;

    // private static String protoFileUrl = "https://github.com/richzhang/colorization/raw/master/models/colorization_deploy_v2.prototxt";
    private static String protoFileUrl = "https://raw.githubusercontent.com/rodolphe74/Colorizer/master/neural/colorization_deploy_v2.prototxt";
    private static String protoFileName = "colorization_deploy_v2.prototxt";

    // private static String weightsFileUrl = "https://people.eecs.berkeley.edu/~rich.zhang/projects/2016_colorization/files/demo_v2/colorization_release_v2.caffemodel";
    private static String weightsFileName = "colorization_release_v2.caffemodel";

    private static String weightsFileUrl_a = "https://github.com/rodolphe74/Colorizer/raw/master/neural/colorization_release_v2.caffemodel_split_aa";
    private static String weightsFileName_a = "colorization_release_v2.caffemodel_split_aa";

    private static String weightsFileUrl_b = "https://github.com/rodolphe74/Colorizer/raw/master/neural/colorization_release_v2.caffemodel_split_ab";
    private static String weightsFileName_b = "colorization_release_v2.caffemodel_split_ab";


    private static String currentImageToBrain = null;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check permission before continuing

        for (int i : grantResults) {
            if (i == -1) {
                System.exit(-1);
            }
        }

        continueAfterPermissions();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addButton = (FloatingActionButton)findViewById(R.id.button_add);
        brainButton = (FloatingActionButton)findViewById(R.id.button_brain);
        addButton.setEnabled(false);
        brainButton.setEnabled(false);


        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            CallBackHelper callBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterPermissions");
            PermissionChecker permissions = new PermissionChecker(this, callBackHelper);
            permissions.permissionsCheck();
        } else {
            // Pre-Marshmallow
            continueAfterPermissions();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Continue activity after possibly setting permissions
     * Check if the neural proto file is present
     */
    public void continueAfterPermissions() {

        protoFilePath = Util.getColorizerWorkPath() + File.separator + protoFileName;

        if (Util.checkFile(protoFilePath, "7229F469D24645A7F1E3C47C67E7BD15") == 0) {
            continueAfterFirstDownloads();
        } else {
            CallBackHelper okCallBackHelper = new CallBackHelper(this, this.getClass(), "continueWithDownloadingNeural1");
            CallBackHelper cancelCallBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterRefusingDownload");
            Util.alertDialog(this, okCallBackHelper, cancelCallBackHelper,"The file colorization_deploy_v2.prototxt needed for the neural network process is missing or is corrupted. Press OK to download this file in the Downloads/colorizer folder of your phone");
        }
    }

    /**
     * Download the first neural file
     */
    public void continueWithDownloadingNeural1() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading colorization_deploy_v2.prototxt from https://github.com/rodolphe74/Colorizer");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        CallBackHelper callBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterFirstDownloads");
        downloadTask1 = new DownloadTask(callBackHelper, progressDialog);
        downloadTask1.execute(protoFileUrl, protoFilePath);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask1.cancel(true);
            }
        });
    }


    /**
     * Follow the first download
     * Check if the neural model is present
     */
    public void continueAfterFirstDownloads() {
        weightsFilePath = Util.getColorizerWorkPath() + File.separator + weightsFileName;

        if (Util.checkFile(weightsFilePath, "6EF9D30CEFD880BAABEB5E1847FB20BE") == 0) {
            continueAfterSecondDownloads();
        } else {
            CallBackHelper okCallBackHelper = new CallBackHelper(this, this.getClass(), "continueWithDownloadingNeural2a");
            CallBackHelper cancelCallBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterRefusingDownload");
            Util.alertDialog(this, okCallBackHelper, cancelCallBackHelper, "The file colorization_release_v2.caffemodel needed for the neural network process is missing or is corrupted. Press OK to download this file in the Downloads/colorizer folder of your phone.\nNote that this file is quite big (130 megabytes), check that you are Wifi connected.");
        }
    }

    public void continueAfterRefusingDownload() {
        finishAffinity();
        System.exit(0);
    }

    /**
     * After the Alert missing neural model file
     * Download the neural model
     * The file is split in two files because of the 100Mb Github limit
     */
    public void continueWithDownloadingNeural2a() {
        weightsFilePath_a = Util.getColorizerWorkPath() + File.separator + weightsFileName_a;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading colorization_release_v2.caffemodel from https://github.com/rodolphe74/Colorizer");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        CallBackHelper callBackHelper = new CallBackHelper(this, this.getClass(), "continueWithDownloadingNeural2b");
        downloadTask2 = new DownloadTask(callBackHelper, progressDialog);
        downloadTask2.execute(weightsFileUrl_a, weightsFilePath_a);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask2.cancel(true);
            }
        });
    }


    /**
     * Second neural file
     */
    public void continueWithDownloadingNeural2b() {
        weightsFilePath_b = Util.getColorizerWorkPath() + File.separator + weightsFileName_b;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading colorization_release_v2.caffemodel from https://github.com/rodolphe74/Colorizer");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        CallBackHelper callBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterDownloadingNeurals2");
        downloadTask2 = new DownloadTask(callBackHelper, progressDialog);
        downloadTask2.execute(weightsFileUrl_b, weightsFilePath_b);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask2.cancel(true);
            }
        });
    }


    /**
     * combine the two cafe models
     */
    public void continueAfterDownloadingNeurals2() {
        try {
            CallBackHelper callBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterSecondDownloads");
            Util.combineFiles(weightsFilePath, new String[] {weightsFilePath_a, weightsFilePath_b}, callBackHelper);
        } catch (IOException e) {
            Toast.makeText(this,"Download error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }

        if (Util.checkFile(weightsFilePath, "6EF9D30CEFD880BAABEB5E1847FB20BE") != 0 || Util.checkFile(protoFilePath, "7229F469D24645A7F1E3C47C67E7BD15") != 0) {
            CallBackHelper okCallBackHelper = new CallBackHelper(this, this.getClass(), "blackHole");
            Util.alertDialog(this,  okCallBackHelper, null, "Error while downloading files, can't go further");
        }
    }

    public void blackHole() {
        Log.i(this.getClass().getSimpleName(), "blackHole");
    }

    public void continueAfterSecondDownloads() {

        Log.i(this.getClass().getSimpleName(), "continueAfterSecondDownloads");

        addButton.setEnabled(true);
        brainButton.setEnabled(true);


        /*
        // OpenCv Init
        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
            return;
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }
        */

    }

    public void onChooseImageButtonClicked(View view) {
        // access the images gallery
        Intent intent = new   Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            // Convert to bw
            OpenCVTasks openCVTasks = new OpenCVTasks();
            String[] parameters = new String[3];
            parameters[0] = OpenCVTasks.BWIZE;
            parameters[1] = picturePath;
            parameters[2] = Util.getColorizerWorkPath() + File.separator + "bw_" + new File(picturePath).getName();
            JniReturn jniReturn = openCVTasks.doInBackground(parameters);

            cursor.close();

            if (jniReturn.getReturnCode() == 0) {
                // currentImageToBrain = picturePath;
                currentImageToBrain = jniReturn.getImagePath();

                // String picturePath contains the path of selected Image

                // Show the Selected Image on ImageView
                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.setImageBitmap(BitmapFactory.decodeFile(/*picturePath*/ currentImageToBrain));
            }

        }
    }


    public void onBrainImageButtonClicked(View view) {
        try {


            // manage application return from background
            if (protoFilePath == null) {
                protoFilePath = Util.getColorizerWorkPath() + File.separator + protoFileName;
            }
            if (weightsFilePath == null) {
                weightsFilePath = Util.getColorizerWorkPath() + File.separator + weightsFileName;
            }


            // Check if files are present before continuing
            if (Util.checkFile(weightsFilePath, "6EF9D30CEFD880BAABEB5E1847FB20BE") != 0 || Util.checkFile(protoFilePath, "7229F469D24645A7F1E3C47C67E7BD15") != 0) {
                CallBackHelper okCallBackHelper = new CallBackHelper(this, this.getClass(), "blackHole");
                Util.alertDialog(this,  okCallBackHelper, null, "Neural network files are missing or are corrupted. Please restart the application to solve");
                return;
            }


            String inputFilePath = null;
            if (currentImageToBrain == null) {
                InputStream in = Util.getResInputStream("papillonbleu_bw.jpg", this.getApplicationContext());
                inputFilePath = Util.copyFileToColorizerFolder(in, "papillonbleu_bw.jpg");
            } else {
                inputFilePath = currentImageToBrain;
            }


            thinkingDialog = new ProgressDialog(this);
            thinkingDialog.setMessage("Thinking...");
            thinkingDialog.setIndeterminate(true);
            thinkingDialog.setCancelable(false);
            thinkingDialog.show();


            CallBackHelper callBackHelper = new CallBackHelper(this, this.getClass(), "continueAfterColorize");
            OpenCVTasks openCVTasks = new OpenCVTasks(callBackHelper);
            openCVTasks.execute(OpenCVTasks.COLORIZE, inputFilePath, protoFilePath, weightsFilePath);


        } catch (IOException e) {
            Log.d(this.getClass().getSimpleName(), e.getMessage());
            return;
        }
    }


    public void continueAfterColorize(List parameters) {
        JniReturn jniReturn = (JniReturn) parameters.get(0);
        thinkingDialog.hide();

        if (jniReturn.getReturnCode() == 0) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageBitmap(BitmapFactory.decodeFile(jniReturn.getImagePath()));
        }
    }
}
