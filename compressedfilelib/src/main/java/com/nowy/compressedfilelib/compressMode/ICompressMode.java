package com.mdd.baselib.utils.compressFile.compressMode;


import com.mdd.baselib.utils.compressFile.listener.OnProgressListener;

/**
 * Created by Nowy on 2018/3/12.
 */

public interface ICompressMode {
    int compress(String filePath, String outPath);
    int deCompress(String filePath, String outPath, String pwd, OnProgressListener listener);
}
