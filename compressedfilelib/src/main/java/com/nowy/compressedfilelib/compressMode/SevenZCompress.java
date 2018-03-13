package com.nowy.compressedfilelib.compressMode;

import com.hzy.libp7zip.P7ZipApi;
import com.nowy.compressedfilelib.task.DeCompressTask;
import com.nowy.compressedfilelib.utils.Command;

/**
 * Created by Nowy on 2018/3/12.
 */

public class SevenZCompress implements ICompressMode {
    @Override
    public int compress(String filePath,String outPath) {
        return P7ZipApi.executeCommand(Command.getCompressCmd(filePath,outPath,"7z"));
    }

    @Override
    public int deCompress(String filePath, String outPath, String pwd, DeCompressTask.OnProgressListener listener) {
        return P7ZipApi.executeCommand(Command.getExtractCmd(filePath, outPath));
    }


}
