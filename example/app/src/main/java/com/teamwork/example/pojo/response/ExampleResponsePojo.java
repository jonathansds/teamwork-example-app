package com.teamwork.example.pojo.response;

import com.google.gson.annotations.SerializedName;
import com.teamwork.example.util.EnumUtil;

import org.parceler.Parcel;


@Parcel
public class ExampleResponsePojo {

    //This code should match the ones coming from server.
    @SerializedName("code")
    EnumUtil.RESPONSE_CODE code;

    @SerializedName("STATUS")
    String status;

    @SerializedName("hasMore")
    boolean hasMore;

    @SerializedName("nextPage")
    int nextPage;

    public EnumUtil.RESPONSE_CODE getCode() {
        return code;
    }

    public void setCode(EnumUtil.RESPONSE_CODE code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
