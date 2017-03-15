package com.shaomengjie.okhttp;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InterruptedIOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public class RequestTask extends AsyncTask<Object, Integer, Object> {
    private HttpRequest request;
    private Call call;
    private boolean isCancelled;
    private RequestTask.RequestTaskListener listener;

    public RequestTask(Call call, HttpRequest request) {
        this.call = call;
        this.request = request;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Object doInBackground(Object... params) {
        try {
            Response e = this.call.execute();
            return this.request.callback.parse(e);
        } catch (AppException var3) {
            return var3;
        } catch (InterruptedIOException var4) {
            return new AppException(AppException.ErrorType.TIMEOUT, var4.getMessage());
        } catch (IOException var5) {
            return new AppException(AppException.ErrorType.IO, var5.getMessage());
        }
    }

    protected void onPostExecute(Object obj) {
        this.listener.onCompleted(this.request.tag);
        if(this.isCancelled) {
            L.d("the request has cancelled tag=" + this.request.tag);
        } else {
            if(obj instanceof AppException) {
                AppException e = (AppException)obj;
                if(this.request.globalExceptionListener != null) {
                    boolean isHandler = this.request.globalExceptionListener.parserAppException(e);
                    if(!isHandler) {
                        this.request.callback.onFailure(e);
                    }
                } else {
                    this.request.callback.onFailure(e);
                }
            } else {
                this.request.callback.onSuccess(obj);
            }

        }
    }

    public void cancel() {
        this.call.cancel();
        this.isCancelled = true;
    }

    public void setOnRequestTaskListener(RequestTask.RequestTaskListener listener) {
        this.listener = listener;
    }

    public interface RequestTaskListener {
        void onCompleted(String var1);
    }
}
