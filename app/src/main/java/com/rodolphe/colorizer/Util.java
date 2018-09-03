package com.rodolphe.colorizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

public class Util {


    static final String HEXES = "0123456789ABCDEF";

    static void copyInputStreamToFile(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
    }

    public static InputStream getResInputStream(String filename, Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(filename);
        return inputStream;
    }

    public static String createTempFile(InputStream resource, String filename) throws IOException {
        File f = File.createTempFile(filename, ".tmp");
        copyInputStreamToFile(resource, f);
        return f.getAbsolutePath();
    }

    public static String copyFileToColorizerFolder(InputStream resource, String filename) throws IOException {
        /*
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File f = new File(downloadDir.getAbsolutePath() + File.separator + filename);
        */
        File f = new File(getColorizerWorkPath() + File.separator + filename);
        copyInputStreamToFile(resource, f);
        return f.getAbsolutePath();
    }

    public static String getColorizerWorkPath() {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String path = downloadDir.getAbsolutePath() + File.separator + "colorizer";
        File fPAth = new File(path);
        if (!fPAth.exists()) {
            fPAth.mkdirs();
        }
        return path;
    }

    public static String toHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static int checkFile(String path, String md5) {

        try {

            File f = new File(path);
            if (!f.exists()) {
                return 1;
            }

            // digest file
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dataBytes = new byte[1024];
            FileInputStream fis = new FileInputStream(f);
            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;
            byte[] mdbytes = md.digest();

            // convert to hex
            String hex = toHex(mdbytes);
            if (!md5.equals(hex)) {
                return 2;
            }

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public static void combineFiles(String path, String[] paths, CallBackHelper callBackHelper) throws IOException {
        OutputStream o = new BufferedOutputStream(new FileOutputStream(path));

        for (String p : paths) {
            BufferedInputStream i = new BufferedInputStream(new FileInputStream(p));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = i.read(buffer)) != -1) {
                o.write(buffer, 0, len);
            }
        }
        o.flush();
        o.close();

        for (String p : paths) {
            new File(p).delete();
        }

        if (callBackHelper != null) {
            callBackHelper.callBack();
        }
    }


    public static void alertDialog(final Context context, final CallBackHelper okCallBackHelper, final CallBackHelper cancelCallBackHelper, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Hello");
        dialog.setIcon(R.drawable.brain);
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                okCallBackHelper.callBack();
            }
        });

        if (cancelCallBackHelper != null) {
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cancelCallBackHelper.callBack();
                }
            });
        }
        dialog.show();
    }
}