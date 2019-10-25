package com.btetop.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import okhttp3.RequestBody;

/**
 * @description: 数据转化为json格式
 * @autour: zylaoshi
 * @date: 2017/12/8 15:30
*/

public class JSONTranUtil {

    private Object[] mObjects;


    private Object[] getParams(Object... params) {
        this.mObjects = params;
        return mObjects;
    }

    /**
     * 检查数据是否唯恐
     * @param params
     */
    public static boolean checkParams(Object[] params){
        if(params==null||params.length==0){
            return true;
        }
        for(Object o :params){
            if(o==null)
                return true;
        }
        return false;
    }

    public static JSONObject getJSONObjectParams(HashMap<String, Object> params) {
        if (params == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                json.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public static JSONArray getJSONArray(Object[] array) {
        JSONArray arr = new JSONArray();
        for (Object item : array) {
            if (item.getClass().isArray()) {
                arr.put(getJSONArray((Object[]) item));
            } else {
                arr.put(item);
            }
        }
        return arr;
    }

    public RequestBody tranParamsToBody(HashMap<String, Object> params, String method){
        RequestBody body;
        Object[] o = getParams(getJSONObjectParams(params));
        JSONArray jsonParams = new JSONArray();
        if (o != null&&o.length>0&&!checkParams(o))
            for (int i = 0; i < o.length; i++) {
                if (o[i].getClass().isArray()) {
                    jsonParams.put(getJSONArray((Object[]) o[i]));
                }
                jsonParams.put(o[i]);
            }
        HashMap<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("id", UUID.randomUUID().hashCode());
        paramsMap.put("method",method);
        paramsMap.put("jsonrpc","2.0");
        paramsMap.put("params",jsonParams);

        String strEntity = String.valueOf(getJSONObjectParams(paramsMap));
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        return body;
    }
}