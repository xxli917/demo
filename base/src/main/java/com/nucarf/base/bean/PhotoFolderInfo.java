/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.nucarf.base.bean;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Description: 相册选择的adapter
 * Author: wang
 * Time: 2017/7/17 13:56
 */
public class PhotoFolderInfo implements Serializable {
    private int folderId;
    private String folderName;
    private PhotoInfo coverPhoto;
    private ArrayList<PhotoInfo> photoList;

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public PhotoInfo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(PhotoInfo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public ArrayList<PhotoInfo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(ArrayList<PhotoInfo> photoList) {
        this.photoList = photoList;
    }
}
