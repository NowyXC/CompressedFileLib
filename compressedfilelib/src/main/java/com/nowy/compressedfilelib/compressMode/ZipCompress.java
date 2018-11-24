package com.nowy.compressedfilelib.compressMode;

import android.text.TextUtils;

import com.nowy.compressedfilelib.listener.OnProgressListener;
import com.nowy.compressedfilelib.utils.ExitCode;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;

/**
 * Created by Nowy on 2018/3/12.
 */

public class ZipCompress implements ICompressMode {

    @Override
    public int compress(String filePath, String outPath) {
        return 0;
    }


    @Override
    public int deCompress(String filePath, String outPath, String pwd,OnProgressListener listener) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(outPath)){
            return ExitCode.EXIT_FATAL;
        }

        File src = new File(filePath);
        if (!src.exists())
            return ExitCode.EXIT_FATAL;
        try {
            ZipFile zFile = new ZipFile(filePath);
            zFile.setFileNameCharset("GBK");
            if (!zFile.isValidZipFile()){
                throw new ZipException("文件不合法!");
            }

            File destDir = new File(outPath);
            if (destDir.isDirectory() && !destDir.exists()) {
                destDir.mkdir();
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(pwd.toCharArray());
            }

            FileHeader fh = null;
            final int total = zFile.getFileHeaders().size();
            for (int i = 0; i < zFile.getFileHeaders().size(); i++) {
                fh = (FileHeader) zFile.getFileHeaders().get(i);
                zFile.extractFile(fh,outPath);
                if (listener != null) {
                    final int finalI = i;
                    listener.onProgress(finalI+1,total);
                }
            }
        } catch (ZipException e1) {
            e1.printStackTrace();
            return ExitCode.EXIT_FATAL;
        }
        return ExitCode.EXIT_OK;
    }

}
