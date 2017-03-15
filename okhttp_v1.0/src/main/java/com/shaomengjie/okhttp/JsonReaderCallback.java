package com.shaomengjie.okhttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public abstract class JsonReaderCallback<T> extends AbstractCallback<T> {
    public JsonReaderCallback() {
    }

    public T convert(String content) throws AppException {
        boolean var11 = false;

        Object var5;
        try {
            var11 = true;
            ParameterizedType e = (ParameterizedType)this.getClass().getGenericSuperclass();
            Class clazz = (Class)((Class)e.getActualTypeArguments()[0]);
            FileReader reader = new FileReader(content);
            var5 = JsonParser.deserializeFromJson(reader, clazz);
            var11 = false;
        } catch (FileNotFoundException var12) {
            throw new AppException(AppException.ErrorType.FILE_NOT_FIND, var12.getMessage());
        } finally {
            if(var11) {
                File file1 = new File(content);
                if(file1.exists()) {
                    file1.delete();
                }

            }
        }

        File file = new File(content);
        if(file.exists()) {
            file.delete();
        }

        return (T) var5;
    }

    public AbstractCallback setCachePath(String path) {
        this.path = path;
        return this;
    }
}
