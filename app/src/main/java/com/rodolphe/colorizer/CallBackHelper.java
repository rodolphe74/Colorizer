package com.rodolphe.colorizer;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CallBackHelper {
    private Context context;
    private Class contextClass;
    private String contextFunctionCallBack;


    public CallBackHelper(Context context, Class contextClass, String contextFunctionCallBack) {
        this.context = context;
        this.contextClass = contextClass;
        this.contextFunctionCallBack = contextFunctionCallBack;
    }

    public void callBack() {
        try {
            Method method = contextClass.getMethod(contextFunctionCallBack);
            method.invoke(context);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e(this.getClass().getSimpleName(), "Callback method call error:" + e.getMessage());
            return;
        }
    }

    public void callBackWithParameters(List callbackParameters) {
        try {
            Class[] argTypes = new Class[] { List.class };
            Method method = contextClass.getDeclaredMethod(contextFunctionCallBack, argTypes);
            method.invoke(context, callbackParameters);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e(this.getClass().getSimpleName(), "Callback method call error:" + e.getMessage());
            return;
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Class getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class contextClass) {
        this.contextClass = contextClass;
    }

    public String getContextFunctionCallBack() {
        return contextFunctionCallBack;
    }

    public void setContextFunctionCallBack(String contextFunctionCallBack) {
        this.contextFunctionCallBack = contextFunctionCallBack;
    }
}
