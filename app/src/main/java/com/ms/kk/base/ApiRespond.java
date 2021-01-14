package com.ms.kk.base;

/**
 * 网络数据包装类
 *
 * @param <D>
 */
public class ApiRespond<D> {
    public enum Status {
        SUCCESS,//成功状态
        ERROR,//出错
    }

    public D data;
    public Status status;
    public String extra;
    public int httpCode;

    public ApiRespond(Status status, D data, String extra, int httpCode) {
        this.status = status;
        this.data = data;
        this.extra = extra;
        this.httpCode = httpCode;
    }

}
