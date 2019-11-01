package com.rainbean.taxi.main.module;

import com.rainbean.taxi.common.lbs.LocationInfo;

public interface IMainManager {


    /**
     *  获取附近司机
     * @param latitude
     * @param longitude
     */
    void fetchNearDrivers(double latitude, double longitude);


    /**
     * 上报位置
     * @param locationInfo
     */

    void updateLocationToServer(LocationInfo locationInfo);


    /**
     *  呼叫司机
     * @param cost
     * @param key
     * @param startLocation
     * @param endLocation
     */
    void callDriver(String key, float cost, LocationInfo startLocation, LocationInfo endLocation);

    /**
     *  取消订单
     * @param orderId
     */
    void cancelOrder(String orderId);

    /**
     *  支付
     */
    void pay(String orderId);

    /**
     *  获取正在处理中的订单
     */
    void getProcessingOrder();

}
