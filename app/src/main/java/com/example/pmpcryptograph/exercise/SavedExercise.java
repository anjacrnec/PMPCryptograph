package com.example.pmpcryptograph.exercise;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;

import com.google.android.gms.common.api.internal.DataHolderNotifier;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

public class SavedExercise implements Parcelable {

    private String title;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String pt;
    private String ct;
    private String key;
    private String body;
    private String answer;
    private boolean expanded;
    private boolean visible;




    @ServerTimestamp
    private Timestamp time;

    public SavedExercise()
    {

    }

    public SavedExercise(Exercise exercise, boolean expanded, boolean visible)
    {
        this.title=exercise.getTitle();
        this.type=exercise.getType();
        this.pt=exercise.getCipher().getPlainText();
        this.ct=exercise.getCipher().getCipherText();
        this.key=exercise.getKeyStr();
        this.body=exercise.getBody();
        this.answer= exercise.getAnswer();
        this.expanded=expanded;
        this.visible=visible;
    }

    public SavedExercise(String title, String body, String answer,boolean expanded,boolean visible)
    {
        this.title=title;
        this.body=body;
        this.answer= answer;
        this.expanded=expanded;
        this.visible=visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    public boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    private int mData;

    public int describeContents() {
        return 0;
    }

    /** save object in parcel */
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<SavedExercise> CREATOR
            = new Parcelable.Creator<SavedExercise>() {
        public SavedExercise createFromParcel(Parcel in) {
            return new SavedExercise(in);
        }

        public SavedExercise[] newArray(int size) {
            return new SavedExercise[size];
        }
    };

    /** recreate object from parcel */
    private SavedExercise(Parcel in) {
        mData = in.readInt();
    }

}
