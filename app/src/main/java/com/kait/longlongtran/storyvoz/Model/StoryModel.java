package com.kait.longlongtran.storyvoz.Model;


/**
 * Created by LongLongTran on 1/27/2018.
 */

public class StoryModel {
    private String mTenTruyen,mTheLoai,mTacGia,mNguon,mTinhTrang,mNgayViet,mMoTa,mMoDau,mGhiChu;
    private byte[] mImage;

    public StoryModel() {
    }

    public StoryModel(String mTenTruyen, String mTheLoai, String mTacGia, String mNguon, String mTinhTrang, String mNgayViet, String mMoTa, String mMoDau, String mGhiChu, byte[] mImage) {
        this.mTenTruyen = mTenTruyen;
        this.mTheLoai = mTheLoai;
        this.mTacGia = mTacGia;
        this.mNguon = mNguon;
        this.mTinhTrang = mTinhTrang;
        this.mNgayViet = mNgayViet;
        this.mMoTa = mMoTa;
        this.mMoDau = mMoDau;
        this.mGhiChu = mGhiChu;
        this.mImage = mImage;
    }

    public String getmTenTruyen() {
        return mTenTruyen;
    }

    public void setmTenTruyen(String mTenTruyen) {
        this.mTenTruyen = mTenTruyen;
    }

    public String getmTheLoai() {
        return mTheLoai;
    }

    public void setmTheLoai(String mTheLoai) {
        this.mTheLoai = mTheLoai;
    }

    public String getmTacGia() {
        return mTacGia;
    }

    public void setmTacGia(String mTacGia) {
        this.mTacGia = mTacGia;
    }

    public String getmNguon() {
        return mNguon;
    }

    public void setmNguon(String mNguon) {
        this.mNguon = mNguon;
    }

    public String getmTinhTrang() {
        return mTinhTrang;
    }

    public void setmTinhTrang(String mTinhTrang) {
        this.mTinhTrang = mTinhTrang;
    }

    public String getmNgayViet() {
        return mNgayViet;
    }

    public void setmNgayViet(String mNgayViet) {
        this.mNgayViet = mNgayViet;
    }

    public String getmMoTa() {
        return mMoTa;
    }

    public void setmMoTa(String mMoTa) {
        this.mMoTa = mMoTa;
    }

    public String getmMoDau() {
        return mMoDau;
    }

    public void setmMoDau(String mMoDau) {
        this.mMoDau = mMoDau;
    }

    public String getmGhiChu() {
        return mGhiChu;
    }

    public void setmGhiChu(String mGhiChu) {
        this.mGhiChu = mGhiChu;
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }

}
