package com.demo.cnode_android;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ck on 2016/10/20.
 */

public interface ApiService {
    @GET("topics")
    Call<TopicResponse> getTopics(@Query("page") int page, @Query("limit")  int pageSize, @Query("tab") String tab );
    @GET("topic/{topicId}")
    Call<TopicDetailResponse> getTopic(@Path("topicId") String topicId);
    class TopicDetailResponse{
        private Boolean success;
        private Topic data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Topic getData() {
            return data;
        }

        public void setData(Topic data) {
            this.data = data;
        }
    }
}
