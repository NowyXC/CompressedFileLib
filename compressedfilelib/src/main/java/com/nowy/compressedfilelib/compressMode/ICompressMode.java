package com.nowy.compressedfilelib.compressMode;

import com.nowy.compressedfilelib.task.DeCompressTask;

/**
 * Created by Nowy on 2018/3/12.
 */

public interface ICompressMode {
    int compress(String filePath,String outPath);
    int deCompress(String filePath, String outPath, String pwd, DeCompressTask.OnProgressListener listener);
}
