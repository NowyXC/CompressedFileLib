package com.nowy.compressedfilelib.listener;

/**
 * Created by Nowy on 2018/3/12.
 */

public interface OnCompressListener {
    void onStart();
    void onProgress(int progress,int total);
    void onFinish();
    void onError(int errorMsg);
}
