package com.nucarf.base.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Description: 相册选择的adapter
 * Author: wang
 * Time: 2017/7/17 13:56
 */
public class PhotoInfo implements Parcelable {

    private int photoId;
    private String photoPath;
    private int width;
    private int height;
    private boolean isChecked = false;
    private int checkedPosition;

    public PhotoInfo() {
    }

    public PhotoInfo(String photoPath) {
        this.photoPath = photoPath;
    }

    protected PhotoInfo(Parcel in) {
        photoId = in.readInt();
        photoPath = in.readString();
        width = in.readInt();
        height = in.readInt();
        isChecked = in.readByte() != 0;
        checkedPosition = in.readInt();
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel in) {
            return new PhotoInfo(in);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof PhotoInfo)) {
            return false;
        }
        PhotoInfo info = (PhotoInfo) o;
        if (info == null) {
            return false;
        }

        return TextUtils.equals(info.getPhotoPath(), getPhotoPath());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photoId);
        parcel.writeString(photoPath);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
        parcel.writeInt(checkedPosition);
    }
}
