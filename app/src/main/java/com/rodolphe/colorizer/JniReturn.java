package com.rodolphe.colorizer;

/*
public class com.rodolphe.colorizer.JniReturn {
  public com.rodolphe.colorizer.JniReturn(int, java.lang.String, java.lang.String);
    descriptor: (ILjava/lang/String;Ljava/lang/String;)V

  public int getReturnCode();
    descriptor: ()I

  public void setReturnCode(int);
    descriptor: (I)V

  public java.lang.String getErrorMessage();
    descriptor: ()Ljava/lang/String;

  public void setErrorMessage(java.lang.String);
    descriptor: (Ljava/lang/String;)V

  public java.lang.String getImagePath();
    descriptor: ()Ljava/lang/String;

  public void setImagePath(java.lang.String);
    descriptor: (Ljava/lang/String;)V
}
 */


public class JniReturn {

    private int returnCode;
    private String errorMessage;
    private String imagePath;

    public JniReturn(int returnCode, String errorMessage, String imagePath) {
        this.returnCode = returnCode;
        this.errorMessage = errorMessage;
        this.imagePath = imagePath;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
