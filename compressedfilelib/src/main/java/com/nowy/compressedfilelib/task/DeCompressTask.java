package com.nowy.compressedfilelib.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.nowy.compressedfilelib.R;
import com.nowy.compressedfilelib.compressMode.ICompressMode;
import com.nowy.compressedfilelib.compressMode.RARCompress;
import com.nowy.compressedfilelib.compressMode.SevenZCompress;
import com.nowy.compressedfilelib.compressMode.ZipCompress;
import com.nowy.compressedfilelib.constant.Mode;
import com.nowy.compressedfilelib.listener.OnCompressListener;
import com.nowy.compressedfilelib.utils.ExitCode;

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
        ICompressMode compressMode = getCorrectCompress(getFileType(strings[0]));
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


    /**
     * 获取文件类型
     * @param filename
     * @return
     */
    private String getFileType(String filename){
        String type=null;
        if (TextUtils.isEmpty(filename)) return type;
        String[] temp=filename.split("\\.");
        type=temp[temp.length-1];
        return type;
    }


    private ICompressMode getCorrectCompress(String type){
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





    private static void showResult(int result, OnCompressListener listener) {
        switch (result) {
            case ExitCode.EXIT_OK:
                if(listener != null)
                    listener.onFinish();
                break;
            case ExitCode.EXIT_WARNING:
                if(listener != null)
                    listener.onError( R.string.msg_ret_warning);
                break;
            case ExitCode.EXIT_FATAL:
                if(listener != null)
                    listener.onError(R.string.msg_ret_fault);
                break;
            case ExitCode.EXIT_CMD_ERROR:
                if(listener != null)
                    listener.onError(R.string.msg_ret_command);
                break;
            case ExitCode.EXIT_MEMORY_ERROR:
                if(listener != null)
                    listener.onError( R.string.msg_ret_memmory);
                break;
            case ExitCode.EXIT_NOT_SUPPORT:
                if(listener != null)
                    listener.onError( R.string.msg_ret_user_stop);
                break;
            default:
                break;
        }
    }


    public interface OnProgressListener{
        void onProgress(int progress,int total);
    }

}
