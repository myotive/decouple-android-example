package com.dynamit.decoupleandroid.network.models.common;

import com.google.gson.annotations.Expose;

/**
 * Created by michaelyotive_hr on 12/6/15.
 */
public class BaseResponse {
    @Expose
    Integer count;
    @Expose
    String next;
    @Expose
    String previous;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
