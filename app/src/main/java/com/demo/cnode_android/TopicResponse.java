package com.demo.cnode_android;

import java.util.List;

/**
 * Created by ck on 2016/10/20.
 */

public class TopicResponse {
    private Boolean success;
    private List<Topic> data;
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Topic> getData() {
        return data;
    }

    public void setData(List<Topic> data) {
        this.data = data;
    }
}

