package com.rodolphe.colorizer;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OpenCVTasks extends AsyncTask<String, Integer, JniReturn> {

    final public static String COLORIZE = "COLORIZE";
    final public static String BWIZE = "BWIZE";

    static {
        System.loadLibrary("native");
    }

    public static native int test();
    public static native JniReturn colorize(String imagePath, String protoFilePath, String weightsFilePath);
    public static native JniReturn bwize(String imagePath, String colorizeWorkPath);

    private CallBackHelper callBackHelper;

    public OpenCVTasks() {

    }

    public OpenCVTasks(CallBackHelper callBackHelper) {
        this.callBackHelper = callBackHelper;
    }

    @Override
    protected JniReturn doInBackground(String... strings) {
        String function = strings[0];

        if (function.equals(COLORIZE)) {
            String inputFilePath = strings[1];
            String protoFilePath = strings[2];
            String weightsFilePath = strings[3];

            JniReturn jniReturn = OpenCVTasks.colorize(inputFilePath, protoFilePath, weightsFilePath);
            return jniReturn;
        } else if (function.equals(BWIZE)) {
            String inputFilePath = strings[1];
            String bwImageFilePath = strings[2];
            JniReturn jniReturn = OpenCVTasks.bwize(inputFilePath, bwImageFilePath);
            return jniReturn;
        }

        JniReturn jniReturn = new JniReturn(-1, "function not found", null);
        return jniReturn;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // System.loadLibrary("native");
    }

    @Override
    protected void onPostExecute(JniReturn jniReturn) {
        if (jniReturn.getReturnCode() != 0) {
            Toast.makeText(callBackHelper.getContext(), "OpenCV error: " + jniReturn.getErrorMessage(), Toast.LENGTH_LONG).show();
        } else {
            List parameters = new ArrayList();
            parameters.add(jniReturn);
            callBackHelper.callBackWithParameters(parameters);
        }
    }
}
