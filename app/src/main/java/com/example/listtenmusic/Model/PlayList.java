package com.example.listtenmusic.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayList implements Serializable {

@SerializedName("IDPlayList")
@Expose
private String iDPlayList;
@SerializedName("TenPlayList")
@Expose
private String tenPlayList;
@SerializedName("HinhNen")
@Expose
private String hinhNen;
@SerializedName("Hinhicon")
@Expose
private String hinhicon;

public String getIDPlayList() {
return iDPlayList;
}

public void setIDPlayList(String iDPlayList) {
this.iDPlayList = iDPlayList;
}

public String getTenPlayList() {
return tenPlayList;
}

public void setTenPlayList(String tenPlayList) {
this.tenPlayList = tenPlayList;
}

public String getHinhNen() {
return hinhNen;
}

public void setHinhNen(String hinhNen) {
this.hinhNen = hinhNen;
}

public String getHinhicon() {
return hinhicon;
}

public void setHinhicon(String hinhicon) {
this.hinhicon = hinhicon;
}

}