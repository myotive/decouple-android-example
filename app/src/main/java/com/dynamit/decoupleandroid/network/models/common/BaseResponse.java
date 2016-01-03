package com.dynamit.decoupleandroid.network.models.common;

import com.google.gson.annotations.Expose;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
public class BaseResponse {
    @Expose
    int count;
    @Expose
    int next;
    @Expose
    int previous;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }
}
