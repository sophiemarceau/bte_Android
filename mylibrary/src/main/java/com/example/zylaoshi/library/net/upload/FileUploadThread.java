//package com.example.zylaoshi.library.net.upload;
//
//import android.graphics.Bitmap;
//import android.text.TextUtils;
//
//import com.wheretoborrow.app.NJApplication;
//import com.wheretoborrow.common.DefaultThreadPool;
//import com.wheretoborrow.config.Constant;
//import com.wheretoborrow.network.HttpUtils;
//import com.wheretoborrow.network.OKHttpClientInstance;
//import com.wheretoborrow.utils.BitmapUtil;
//import com.wheretoborrow.utils.NJLog;
//import com.wheretoborrow.utils.PreferenceUtil;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class FileUploadThread implements Runnable {
//    String filepath;
//    String url;
//    Bitmap bitmap;
//    OkHttpClient mClient;
//    OKHttpClientInstance.Callback<String> callback;
//
//    public FileUploadThread(String filepath, String url,Bitmap bitmap, OKHttpClientInstance.Callback<String> callback) {
//        this.filepath = filepath;
//        this.url = url;
//        this.callback = callback;
//        this.bitmap = bitmap;
//        mClient = new OkHttpClient();
//    }
//
//    @Override
//    public void run() {
//        if (bitmap == null || TextUtils.isEmpty(url)) {
//           NJLog.e("----------------bitmap is null || url is empty");
//            return;
//        }
//        BitmapUtil.writeBitmap2Path(bitmap, filepath);
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        //设置类型
//        builder.setType(MultipartBody.FORM);
//        builder.addFormDataPart("filename", filepath.substring(filepath.lastIndexOf("/")), RequestBody.create(null, new File(filepath)));
//        MultipartBody mb = builder.build();
//        String lastSessionId = PreferenceUtil.getInstance(NJApplication.getInstance()).getString(Constant.SESSION_ID, null);
//        Request.Builder requestBuilder = new Request.Builder();
//        if (!TextUtils.isEmpty(lastSessionId)) {
//            requestBuilder.addHeader("Cookie", lastSessionId);
//        } else {
//            return;
//        }
//
//        Request request = requestBuilder.post(mb).url(url).build();
//        mClient.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                NJLog.e("upload failed------------------");
//                if (callback != null)
//                    callback.onFail(0, e == null ? "" : e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    String result = response.body().string();
//                    NJLog.e("upload picture------------------"+result);
//                    JSONObject obj = new JSONObject(result);
//                    int code = obj.getInt("err_code");
//                    if (code == 0) {
//                        if (callback != null) {
//                            callback.onSucess(result);
//                        }
//                    } else {
//                        callback.onFail(code, obj.optString("err_msg"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }
//
//    public void excute() {
//        if (HttpUtils.isConnected(NJApplication.getInstance())) {
//            DefaultThreadPool.getInstance().execute(this);
//        } else {
//            if (null != callback) {
//                callback.onFail(0, "");
//            } else {
//                throw new NullPointerException("RequestCallback param is null, you must new RequestCallback param in the Constructor");
//            }
//        }
//    }
//}