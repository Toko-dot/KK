package com.ms.kk.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.InterruptedIOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiRespond<R>>> {
    private Type type;

    public LiveDataCallAdapter(Type type) {
        this.type = type;
    }

    @Override
    public Type responseType() {
        return type;
    }

    @Override
    public LiveData<ApiRespond<R>> adapt(final Call<R> call) {
        return new MutableLiveData<ApiRespond<R>>() {
            private AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (!started.compareAndSet(false, true))
                    return;
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        if (response.isSuccessful()) {
                            postValue(new ApiRespond<R>(ApiRespond.Status.SUCCESS, response.body(), "success", response.code()));
                        } else {
                            postValue(new ApiRespond<R>(ApiRespond.Status.ERROR, response.body(), "请求异常！", response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        if (t instanceof SocketTimeoutException) {
                            postValue(new ApiRespond<R>(ApiRespond.Status.ERROR, null, "请求超时！", -1));
                            return;
                        }
                        if (t instanceof InterruptedIOException) {
                            postValue(new ApiRespond<R>(ApiRespond.Status.ERROR, null, "请求中断！", -2));
                            return;
                        }

                        postValue(new ApiRespond<R>(ApiRespond.Status.ERROR, null, "请求异常！", -2));
                    }
                });
            }
        };
    }


}
