package com.ms.kk.model.net.entity.respond;

import android.os.Parcel;
import android.os.Parcelable;

public class DramaItem implements Parcelable {

    private int _id;
    private int tid;
    private String name;
    private String thumb;
    private String brief;

    public DramaItem() {
    }

    protected DramaItem(Parcel in) {
        _id = in.readInt();
        tid = in.readInt();
        name = in.readString();
        thumb = in.readString();
        brief = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(tid);
        dest.writeString(name);
        dest.writeString(thumb);
        dest.writeString(brief);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DramaItem> CREATOR = new Creator<DramaItem>() {
        @Override
        public DramaItem createFromParcel(Parcel in) {
            return new DramaItem(in);
        }

        @Override
        public DramaItem[] newArray(int size) {
            return new DramaItem[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
