package com.shaomengjie.okhttp;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Request.Builder;
/**
 * Created by shaomengjie on 2017/3/15.
 */

public class HttpRequest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public Builder builder = new Builder();
    private String url;
    public ICallback callback;
    public OnGlobalExceptionListener globalExceptionListener;
    public String tag;
    public HttpRequest.RequestMethod method;
    private String postContent;
    private okhttp3.FormBody.Builder mFormBodyBuilder;
    private okhttp3.MultipartBody.Builder mMultipartBodyBuilder;

    public HttpRequest(String url) {
        this.url = url;
        this.method = HttpRequest.RequestMethod.GET;
    }

    public HttpRequest(String url, HttpRequest.RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public Request build() {
        this.builder.url(this.url);
        if(this.method == HttpRequest.RequestMethod.POST) {
            if(this.mFormBodyBuilder != null) {
                this.builder.post(this.mFormBodyBuilder.build());
            } else if(this.mMultipartBodyBuilder != null) {
                this.builder.post(this.mMultipartBodyBuilder.build());
            } else {
                RequestBody body = RequestBody.create(JSON, this.postContent);
                this.builder.post(body);
            }
        } else if(this.method == HttpRequest.RequestMethod.GET) {
            this.builder.get();
        }

        return this.builder.build();
    }

    public void put(String name, String value, boolean isEncoded) {
        if(this.mFormBodyBuilder == null) {
            this.mFormBodyBuilder = new okhttp3.FormBody.Builder();
        }

        if(isEncoded) {
            this.mFormBodyBuilder.addEncoded(name, value);
        } else {
            this.mFormBodyBuilder.add(name, value);
        }

    }

    public void put(String name, String value) {
        this.put(name, value, false);
    }

    public void putEncoded(String name, String value) {
        this.put(name, value, true);
    }

    public void putFormDataPart(String name, String value, RequestBody fileBody) {
        if(this.mMultipartBodyBuilder == null) {
            this.mMultipartBodyBuilder = new okhttp3.MultipartBody.Builder();
        }

        if(fileBody == null) {
            this.mMultipartBodyBuilder.addFormDataPart(name, value);
        } else {
            this.mMultipartBodyBuilder.addFormDataPart(name, value, fileBody);
        }

    }

    public void putFormDataPart(String name, String value) {
        this.putFormDataPart(name, value, (RequestBody)null);
    }

    public void addHeader(String key, String value) {
        this.builder.addHeader(key, value);
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public void setOnGlobalExceptionListener(OnGlobalExceptionListener globalExceptionListener) {
        this.globalExceptionListener = globalExceptionListener;
    }

    public static enum RequestMethod {
        GET,
        POST;

        private RequestMethod() {
        }
    }
}
