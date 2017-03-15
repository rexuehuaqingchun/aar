package com.shaomengjie.okhttp;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public class JsonParser {
    private static Gson gson = new Gson();

    public JsonParser() {
    }

    public static <T> T deserializeFromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T deserializeFromJson(String json, Type type) throws JsonSyntaxException {
        return gson.fromJson(json, type);
    }

    public static String serializeToJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T deserializeFromJson(Reader reader, Class<T> clazz) {
        return gson.fromJson(reader, clazz);
    }
}
