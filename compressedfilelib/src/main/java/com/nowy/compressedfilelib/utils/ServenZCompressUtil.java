package com.nowy.compressedfilelib.utils;

import com.hzy.libp7zip.P7ZipApi;

/**
 * Created by Nowy on 2018/3/12.
 */

public class ServenZCompressUtil {

    public static void runCommand(String command){
        int ret = P7ZipApi.executeCommand(command);
    }
}
