package com.example.zylaoshi.library.net.converter;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/** 
 * @description: 自定义request、response 转换器
 * @autour: zylaoshi
 * @date: 2017/11/29 13:55
*/
public class ResponseConvertFactory extends Converter.Factory{

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static ResponseConvertFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static ResponseConvertFactory create(Gson gson) {
        return new ResponseConvertFactory(gson);
    }

    private final Gson gson;

    private ResponseConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    //response过滤result中的内容
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson,type);
    }

}
