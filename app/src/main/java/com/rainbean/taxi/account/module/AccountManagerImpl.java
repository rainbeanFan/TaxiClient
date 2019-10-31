package com.rainbean.taxi.account.module;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.rainbean.taxi.account.module.response.SmsCodeResponse;
import com.rainbean.taxi.common.databus.RxBus;
import com.rainbean.taxi.common.http.IHttpClient;
import com.rainbean.taxi.common.http.IRequest;
import com.rainbean.taxi.common.http.IResponse;
import com.rainbean.taxi.common.http.api.API;
import com.rainbean.taxi.common.http.biz.BaseBizResponse;
import com.rainbean.taxi.common.http.impl.BaseRequest;
import com.rainbean.taxi.common.http.impl.BaseResponse;
import com.rainbean.taxi.common.storage.SharedPreferencesDao;
import com.rainbean.taxi.common.util.LogUtil;

import io.reactivex.functions.Function;

public class AccountManagerImpl implements IAccountManager {

    private static final String TAG = "AccountManagerImpl";


    // 网络请求库
    private IHttpClient httpClient;
    // 数据存储
    private SharedPreferencesDao sharedPreferencesDao;
    // 发送消息 handler
    private Handler handler;

    public AccountManagerImpl(IHttpClient httpClient,
                              SharedPreferencesDao sharedPreferencesDao) {
        this.httpClient = httpClient;
        this.sharedPreferencesDao = sharedPreferencesDao;
    }

    @Override
    public void fetchSMSCode(final String phone) {
        RxBus.getInstance().chainProcess(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Exception {
                String url = API.Config.getDomain() + API.GET_SMS_CODE;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", phone);
                IResponse response = httpClient.get(request, false);
                Log.d(TAG, response.getData());
                SmsCodeResponse smsCodeResponse = new SmsCodeResponse();
                LogUtil.d(TAG, response.getData());
                if (response.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(response.getData(),
                                    BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        smsCodeResponse.setCode(SMS_SEND_SUC);
                    } else {
                        smsCodeResponse.setCode(SMS_SEND_FAIL);
                    }
                } else {
                    smsCodeResponse.setCode(SMS_SEND_FAIL);
                }
                return smsCodeResponse;
            }
        });
    }

    @Override
    public void checkSmsCode(String phone, String smsCode) {

    }

    @Override
    public void checkUserExist(String phone) {

    }

    @Override
    public void register(String phone, String password) {

    }

    @Override
    public void login(String phone, String password) {

    }

    @Override
    public void loginByToken() {

    }

    @Override
    public boolean isLogin() {
        return false;
    }
}
