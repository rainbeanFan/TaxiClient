package com.rainbean.taxi.main.module.response;

import com.rainbean.taxi.common.http.biz.BaseBizResponse;
import com.rainbean.taxi.main.module.bean.Order;

public class OrderStateOptResponse extends BaseBizResponse {


    public final static int ORDER_STATE_CREATE = 0;
    // 取消订单
    public static final int ORDER_STATE_CANCEL = -1;
    //  司机接单
    public static final int ORDER_STATE_ACCEPT = 1;
    //   司机到达
    public static final int ORDER_STATE_ARRIVE_START = 2;
    //  司机开始行程
    public static final int ORDER_STATE_START_DRIVE = 3;
    //  到达目的地
    public static final int ORDER_STATE_ARRIVE_END = 4;
    //  已支付
    public static final int PAY = 5;
    private int state;
    // 携带操作的订单
    private Order data;
    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
