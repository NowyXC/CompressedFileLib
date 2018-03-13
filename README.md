# CompressedFileLib
compress file


整理压缩文件工具类,主要通过两个项目，整理一个简化版的压缩文件工具类库
 参考：
 https://www.jianshu.com/p/339ab9048f91
 https://github.com/hzy3774/AndroidP7zip
 
 1.zip和rar暂时只支持解压
 
 调用方式：
 String FileNameNotExt = info.getFileName().substring(0,info.getFileName().lastIndexOf("."));
        String dir = new File( info.getFilePath()).getParent()+"/"+FileNameNotExt;
        Log.e("DeCompressTask","dir:"+dir);
       new DeCompressTask(new OnCompressListener() {
           @Override
           public void onStart() {
               Log.e("DeCompressTask","onStart");
               showProgressDialog();
           }

           @Override
           public void onProgress(int progress, int total) {
               Log.e("DeCompressTask","onProgress:"+progress+"/"+total);
           }

           @Override
           public void onFinish() {
               Log.e("DeCompressTask","onFinish");
               dismissProgressDialog();
               onRefresh();
               Toast.makeText(getActivity(), "完成", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(int errorMsg) {
               Log.e("DeCompressTask","onError :"+getResources().getString(errorMsg));
               dismissProgressDialog();
               onRefresh();
               Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
           }
       }).execute(info.getFilePath(),dir);
