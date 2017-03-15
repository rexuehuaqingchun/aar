package com.shaomengjie.okhttp;

import okhttp3.Response;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public interface ICallback<T> {
    T parse(Response var1) throws AppException;

    void onSuccess(T var1);

    void onFailure(AppException var1);
}
