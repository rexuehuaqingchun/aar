package com.shaomengjie.okhttp;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public abstract class StringCallback extends AbstractCallback<String> {
    public StringCallback() {
    }

    public final String convert(String content) {
        return content;
    }
}
