package com.ms.kk.base;

/**
 * 仓库数据包装类
 * @param <D>
 */
public class RepositoryRespond<D> {
    public enum Status {
        INIT,//开始
        SUCCESS,//成功
        EMPTY,//空
        ERROR //出错
    }

    public Status status;
    public D data;
    public String extra;
    public int what;

    public RepositoryRespond(Status status, D data, String extra, int what) {
        this.status = status;
        this.data = data;
        this.extra = extra;
        this.what=what;
    }

    public static <D> RepositoryRespond<D> createSuccess(D data) {
        return new RepositoryRespond<D>(Status.SUCCESS, data, "success",0);
    }

    public static <D>RepositoryRespond<D> createError(String exp) {
        return new RepositoryRespond<D>(Status.ERROR, null, exp,1);
    }

    public static <D>RepositoryRespond<D> createError(String exp,int what) {
        return new RepositoryRespond<D>(Status.ERROR, null, exp,what);
    }

    public static <D>RepositoryRespond<D> createEmpty() {
        return new RepositoryRespond<D>(Status.EMPTY, null, "empty",2);
    }

    public static <D>RepositoryRespond<D> createInit() {
        return new RepositoryRespond<D>(Status.INIT, null, "empty",-1);
    }
}
