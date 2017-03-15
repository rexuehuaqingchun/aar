package com.shaomengjie.okhttp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

import okhttp3.Response;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public abstract class AbstractCallback<T> implements ICallback<T> {
    String path;

    public AbstractCallback() {
    }

    public T parse(Response response) throws AppException {
        try {
            if(!response.isSuccessful()) {
                throw new AppException(response.code(), response.message());
            } else if(this.path == null) {
                String e1 = response.body().string();
                return this.convert(e1);
            } else {
                InputStream e = response.body().byteStream();
                File file = new File(this.path);
                FileOutputStream fos = new FileOutputStream(file);
                boolean len = true;
                byte[] buffer = new byte[1024];

                int len1;
                while((len1 = e.read(buffer)) > 0) {
                    fos.write(buffer, 0, len1);
                }

                fos.flush();
                fos.close();
                e.close();
                return this.convert(this.path);
            }
        } catch (InterruptedIOException var7) {
            throw new AppException(AppException.ErrorType.TIMEOUT, var7.getMessage());
        } catch (IOException var8) {
            throw new AppException(AppException.ErrorType.IO, var8.getMessage());
        }
    }

    public abstract T convert(String var1) throws AppException;
}
