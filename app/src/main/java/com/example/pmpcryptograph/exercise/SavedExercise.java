package com.example.pmpcryptograph.exercise;

import com.google.android.gms.common.api.internal.DataHolderNotifier;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

public class SavedExercise {

    private String title;

    private String body;
    private String answer;



    private boolean expanded;




    @ServerTimestamp
    private Timestamp time;

    public SavedExercise()
    {

    }

    public SavedExercise(String title, String body, String answer,boolean expanded)
    {
        this.title=title;
        this.body=body;
        this.answer= answer;
        this.expanded=expanded;
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


}
