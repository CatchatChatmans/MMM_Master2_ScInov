package fr.istic.mmm.scinov.helpers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Filter implements Parcelable {

    private boolean filterByName;
    private boolean filterByPlace;
    private boolean filterByTheme;
    private boolean filterByDescription;
    private boolean filterByKeyword;
    private Date date;

    public Filter() {
        filterByName = true;
        filterByPlace = true;
        filterByTheme = true;
        filterByDescription = true;
        filterByKeyword = true;
        date = null;
    }

    protected Filter(Parcel in) {
        filterByName = in.readByte() != 0;
        filterByPlace = in.readByte() != 0;
        filterByTheme = in.readByte() != 0;
        filterByDescription = in.readByte() != 0;
        filterByKeyword = in.readByte() != 0;
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    public boolean isFilterByName() {
        return filterByName;
    }

    public void setFilterByName(boolean filterByName) {
        this.filterByName = filterByName;
    }

    public boolean isFilterByPlace() {
        return filterByPlace;
    }

    public void setFilterByPlace(boolean filterByPlace) {
        this.filterByPlace = filterByPlace;
    }

    public boolean isFilterByTheme() {
        return filterByTheme;
    }

    public void setFilterByTheme(boolean filterByTheme) {
        this.filterByTheme = filterByTheme;
    }

    public boolean isFilterByDescription() {
        return filterByDescription;
    }

    public void setFilterByDescription(boolean filterByDescription) {
        this.filterByDescription = filterByDescription;
    }

    public boolean isFilterByKeyword() {
        return filterByKeyword;
    }

    public void setFilterByKeyword(boolean filterByKeyword) {
        this.filterByKeyword = filterByKeyword;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (this.filterByName ? 1 : 0));
        dest.writeByte((byte) (this.filterByPlace ? 1 : 0));
        dest.writeByte((byte) (this.filterByTheme ? 1 : 0));
        dest.writeByte((byte) (this.filterByDescription ? 1 : 0));
        dest.writeByte((byte) (this.filterByKeyword ? 1 : 0));
    }
}
