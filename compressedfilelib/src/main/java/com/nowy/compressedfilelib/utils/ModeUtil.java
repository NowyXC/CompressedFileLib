package com.nowy.compressedfilelib.utils;

import android.text.TextUtils;

import com.nowy.compressedfilelib.compressMode.ICompressMode;
import com.nowy.compressedfilelib.compressMode.RARCompress;
import com.nowy.compressedfilelib.compressMode.SevenZCompress;
import com.nowy.compressedfilelib.compressMode.ZipCompress;
import com.nowy.compressedfilelib.constant.Mode;

/**
 * Created by Nowy on 2018/3/15.
 */

public class ModeUtil {
    /**
     * 获取文件类型
     * @param filename
     * @return
     */
    public static String getFileType(String filename){
        String type=null;
        if (TextUtils.isEmpty(filename)) return type;
        String[] temp=filename.split("\\.");
        type=temp[temp.length-1];
        return type;
    }


    public static ICompressMode getCorrectCompress(String type){
        ICompressMode mode = null;
        switch (type){
            case Mode._7Z:
                mode =  new SevenZCompress();
                break;
            case Mode._ZIP:
                mode =  new ZipCompress();
                break;
            case Mode._RAR:
                mode =  new RARCompress();
                break;
        }

        return mode;
    }


}
