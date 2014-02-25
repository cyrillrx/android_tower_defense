package org.es.towerdefense.object;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;

/**
 * @author Cyril Leroux
 *         Created on 30/01/14.
 */
public abstract class Player implements com.google.android.gms.games.Player {

    private int mHealthPoint;
    private int mMoney;

    public Player(int mHealthPoint, int mMoney) {
        this.mHealthPoint = mHealthPoint;
        this.mMoney = mMoney;
    }

    public int getHealthPoint() {
        return mHealthPoint;
    }

    public void setHealthPoint(int mHealthPoint) {
        this.mHealthPoint = mHealthPoint;
    }

    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int mMoney) {
        this.mMoney = mMoney;
    }


    @Override
    public String getPlayerId() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void getDisplayName(CharArrayBuffer charArrayBuffer) {

    }

    @Override
    public boolean hasIconImage() {
        return false;
    }

    @Override
    public Uri getIconImageUri() {
        return null;
    }

    @Override
    public boolean hasHiResImage() {
        return false;
    }

    @Override
    public Uri getHiResImageUri() {
        return null;
    }

    @Override
    public long getRetrievedTimestamp() {
        return 0;
    }

    @Override
    public com.google.android.gms.games.Player freeze() {
        return null;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }


}
