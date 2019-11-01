package com.rainbean.taxi.main.presenter;

import com.rainbean.taxi.common.lbs.LocationInfo;

public interface IMainPresenter {

    void loginByToken();

    /**
     * 获取附近司机
     * @param latitude
     * @param longitude
     */
    void fetchNearDrivers(double latitude, double longitude);


    /**
     * 上报当前位置
     * @param locationInfo
     */
    void updateLocationToServer(LocationInfo locationInfo);


    /**
     * 呼叫司机
     * @param cost
     * @param key
     * @param mStartLocation
     * @param mEndLocation
     */
    void callDriver(String key, float cost, LocationInfo mStartLocation, LocationInfo mEndLocation);



    boolean isLogin();

    /**
     * 取消呼叫
     */
    void cancel();

    /**
     * 支付
     */
    void pay();

    /**
     *  获取正在处理中的订单
     */
    void getProcessingOrder();

}
