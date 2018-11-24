package com.nowy.compressedfilelib.compressMode;



import com.nowy.compressedfilelib.listener.OnProgressListener;
import com.nowy.compressedfilelib.utils.ExitCode;

import java.io.File;
import java.io.FileOutputStream;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;


/**
 * Created by Nowy on 2018/3/12.
 */

public class RARCompress implements ICompressMode {
    @Override
    public int compress(String filePath, String outPath) {
        return ExitCode.EXIT_NOT_SUPPORT;
    }

    @Override
    public int deCompress(String filePath, String outPath, String pwd,OnProgressListener progressListener) {
        File srcFile = new File(filePath);
        if (null == outPath || "".equals(outPath)) {
            outPath = srcFile.getParentFile().getPath();
        }
        // 保证文件夹路径最后是"/"或者"\"
        char lastChar = outPath.charAt(outPath.length() - 1);
        if (lastChar != '/' && lastChar != '\\') {
            outPath += File.separator;
        }

        FileOutputStream fileOut = null;
        Archive rarFile = null;

        try {
            rarFile = new Archive(srcFile,pwd,false);
            FileHeader fh = null;
            final int total = rarFile.getFileHeaders().size();
            for (int i = 0; i < rarFile.getFileHeaders().size(); i++) {
                fh = rarFile.getFileHeaders().get(i);
                String entryPath = "";
                if (fh.isUnicode()) {//解決中文乱码
                    entryPath = fh.getFileNameW().trim();
                } else {
                    entryPath = fh.getFileNameString().trim();
                }
                entryPath = entryPath.replaceAll("\\\\", "/");

                File file = new File(outPath + entryPath);

                if (fh.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(file);
                    rarFile.extractFile(fh, fileOut);
                    fileOut.close();
                }
                if (progressListener != null) {
                    final int finalI = i;
                    progressListener.onProgress(finalI + 1, total);
                }
            }
            rarFile.close();


        } catch (Exception e) {
            e.printStackTrace();
            return ExitCode.EXIT_FATAL;
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                    fileOut = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (rarFile != null) {
                try {
                    rarFile.close();
                    rarFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ExitCode.EXIT_OK;
    }
}
