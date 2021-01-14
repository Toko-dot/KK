package com.ms.kk.base;


import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        if (rawType != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);

        Class<?> observableTypeCls = getRawType(observableType);

        if (observableTypeCls != ApiRespond.class) {
            throw new IllegalArgumentException("type must be  resource");
        }

        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type type = getParameterUpperBound(0, (ParameterizedType) observableType);

        return new LiveDataCallAdapter(type);
    }
}
