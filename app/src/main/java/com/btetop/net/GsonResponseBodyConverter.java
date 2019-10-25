package com.btetop.net;

import com.example.zylaoshi.library.utils.LogUtil;
import com.google.gson.Gson;

import com.btetop.bean.BaseBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        return gson.fromJson(response, type);
    }
}
