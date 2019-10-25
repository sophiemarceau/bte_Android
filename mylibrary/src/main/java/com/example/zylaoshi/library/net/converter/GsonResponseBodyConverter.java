package com.example.zylaoshi.library.net.converter;

import android.util.Log;

import com.example.zylaoshi.library.net.exception.ServerException;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @description: 自定义response转换器
 * @autour: zylaoshi
 * @date: 2017/11/29 13:54
*/
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
        Log.d("Network", "response>>" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has("result")){
                String jsonObject1 = jsonObject.getString("result");
                return gson.fromJson(jsonObject1, type);
            } else {
                throw new ServerException(jsonObject.optJSONObject("error").optInt("code"),jsonObject.optJSONObject("error").optString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gson.fromJson(response, type);
    }
}
