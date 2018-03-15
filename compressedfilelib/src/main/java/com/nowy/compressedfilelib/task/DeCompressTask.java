package com.mdd.baselib.utils.compressFile.task;

import android.os.AsyncTask;

import com.mdd.baselib.utils.compressFile.compressMode.ICompressMode;
import com.mdd.baselib.utils.compressFile.listener.OnCompressListener;
import com.mdd.baselib.utils.compressFile.listener.OnProgressListener;
import com.mdd.baselib.utils.compressFile.utils.ExitCode;
import com.mdd.baselib.utils.compressFile.utils.ModeUtil;


/**
 * Created by Nowy on 2018/3/12.
 */

public class DeCompressTask extends AsyncTask<String,Integer,Integer> {

    private OnCompressListener mOnCompressListener;


    public DeCompressTask(OnCompressListener onCompressListener) {
        this.mOnCompressListener = onCompressListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mOnCompressListener != null)
            mOnCompressListener.onStart();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        ICompressMode compressMode = ModeUtil.getCorrectCompress(ModeUtil.getFileType(strings[0]));
        if(compressMode != null){
            return compressMode.deCompress(strings[0],strings[1],"",new OnProgressListener(){
                @Override
                public void onProgress(int progress, int total) {
                    publishProgress(progress,total);
                }
            });
        }
        return ExitCode.EXIT_FATAL;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(mOnCompressListener != null)
            mOnCompressListener.onProgress(values[0],values[1]);
    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
        showResult(s,mOnCompressListener);
    }






    private static void showResult(int result, OnCompressListener listener) {
        switch (result) {
            case ExitCode.EXIT_OK:
                if(listener != null)
                    listener.onFinish();
                break;
            case ExitCode.EXIT_WARNING:
            case ExitCode.EXIT_FATAL:
            case ExitCode.EXIT_CMD_ERROR:
            case ExitCode.EXIT_MEMORY_ERROR:
            case ExitCode.EXIT_NOT_SUPPORT:
                if(listener != null)
                    listener.onError(result);
                break;
            default:
                break;
        }
    }




}
