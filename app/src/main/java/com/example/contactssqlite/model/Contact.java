package com.example.contactssqlite.model;

public class Contact {
    private int mId;
    private String mName;
    private String mPhoneNumber;
    private String mBirthday;

    public Contact() {
    }

    public Contact(String mName, String mPhoneNumber, String mBirthday) {
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mBirthday = mBirthday;
    }

    public Contact(int mId, String mName, String mPhoneNumber, String mBirthday) {
        this.mId = mId;
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mBirthday = mBirthday;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmBirthday() {
        return mBirthday;
    }

    public void setmBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }
}
