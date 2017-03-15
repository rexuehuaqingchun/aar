package com.shaomengjie.okhttp;


import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public abstract class JsonCallback<T> extends AbstractCallback<T> {
    public JsonCallback() {
    }

    public T convert(String content) throws AppException {
        try {
            ParameterizedType e = (ParameterizedType)this.getClass().getGenericSuperclass();
            Class clazz = (Class)((Class)e.getActualTypeArguments()[0]);
            return (T) JsonParser.deserializeFromJson(content, clazz);
        } catch (JsonSyntaxException var4) {
            throw new AppException(AppException.ErrorType.JSON, var4.getMessage());
        }
    }
}
