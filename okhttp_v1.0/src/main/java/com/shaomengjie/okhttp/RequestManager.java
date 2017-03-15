package com.shaomengjie.okhttp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public class RequestManager {
    public static RequestManager mInstance;
    private ExecutorService mExecutors = Executors.newFixedThreadPool(5);
    private OkHttpClient client;
    private HashMap<String, RequestTask> tasks = new HashMap();

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        if(mInstance == null) {
            mInstance = new RequestManager();
        }

        return mInstance;
    }

    public void init(OkHttpClient client) {
        this.client = client;
    }

    public void execute(String tag, HttpRequest reqeust) {
        this.checkConfig();
        reqeust.tag = tag;
        RequestTask task = new RequestTask(this.client.newCall(reqeust.build()), reqeust);
        task.setOnRequestTaskListener(new RequestTask.RequestTaskListener() {
            public void onCompleted(String tag) {
                L.d("the request will be remove from tasks and current size：" + RequestManager.this.tasks.size());
                RequestManager.this.tasks.remove(tag);
                L.d("the request has removed from tasks and current size：" + RequestManager.this.tasks.size());
            }
        });
        if(!this.tasks.containsValue(tag)) {
            task.executeOnExecutor(this.mExecutors, new Object[0]);
            this.tasks.put(tag, task);
        }

    }

    private void checkConfig() {
        if(this.client == null) {
            throw new RuntimeException("the RequestManager not init");
        }
    }

    public void cancel(String tag) {
        this.checkConfig();
        if(this.tasks.containsValue(tag)) {
            RequestTask task = (RequestTask)this.tasks.remove(tag);
            task.cancel();
        }

    }

    public void cancelAll() {
        this.checkConfig();
        Iterator var1 = this.tasks.keySet().iterator();

        while(var1.hasNext()) {
            String key = (String)var1.next();
            ((RequestTask)this.tasks.get(key)).cancel();
        }

        this.tasks.clear();
    }
}
