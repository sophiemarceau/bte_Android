package com.example.zylaoshi.library.net.service;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

public interface HttpUploadService {
    /*上传文件*/
    @Multipart
    @POST
    Observable<FileEntity> uploadImage(@Url String upLoadUrl, @Part MultipartBody.Part file);
}
