package com.nihal.visitormanagement;

import java.io.Serializable;

class Visitor implements Serializable {
    public static final long serialVersionUID = 20210816L;

    private long m_Id;
    private final String mName;
    private final String mPhone;
    private final String mAddress;
    private final String mCity;
    private final int mSortOrder;

    public Visitor(long m_Id, String name, String phone, String address, String city, int sortOrder) {
        this.m_Id = m_Id;
        mName = name;
        mPhone = phone;
        mAddress = address;
        mCity = city;
        mSortOrder = sortOrder;
    }

     long getId() {
        return m_Id;
    }

     String getName() {
        return mName;
    }

     String getPhone() {
        return mPhone;
    }

     String getAddress() {
        return mAddress;
    }

     String getCity() {
        return mCity;
    }

     int getSortOrder() {
        return mSortOrder;
    }

     void setId(long Id) {
        this.m_Id = Id;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "m_Id=" + m_Id +
                ", mName='" + mName + '\'' +
                ", mPhone=" + mPhone +
                ", mAddress='" + mAddress + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
