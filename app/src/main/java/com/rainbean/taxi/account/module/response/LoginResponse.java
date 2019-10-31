package com.rainbean.taxi.account.module.response;

import com.rainbean.taxi.common.http.biz.BaseBizResponse;

public class LoginResponse extends BaseBizResponse {

    Account data;

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }

}
