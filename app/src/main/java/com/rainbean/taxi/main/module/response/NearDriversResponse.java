package com.rainbean.taxi.main.module.response;

import com.rainbean.taxi.common.http.biz.BaseBizResponse;
import com.rainbean.taxi.common.lbs.LocationInfo;

import java.util.List;

public class NearDriversResponse extends BaseBizResponse {


    List<LocationInfo> data;

    public List<LocationInfo> getData() {
        return data;
    }

    public void setData(List<LocationInfo> data) {
        this.data = data;
    }

}
