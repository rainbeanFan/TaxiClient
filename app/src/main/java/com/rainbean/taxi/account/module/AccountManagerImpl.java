package com.rainbean.taxi.account.module;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.rainbean.taxi.TaxiApplication;
import com.rainbean.taxi.account.module.response.Account;
import com.rainbean.taxi.account.module.response.LoginResponse;
import com.rainbean.taxi.account.module.response.RegisterResponse;
import com.rainbean.taxi.account.module.response.SmsCodeResponse;
import com.rainbean.taxi.account.module.response.UserExistResponse;
import com.rainbean.taxi.common.databus.RxBus;
import com.rainbean.taxi.common.http.IHttpClient;
import com.rainbean.taxi.common.http.IRequest;
import com.rainbean.taxi.common.http.IResponse;
import com.rainbean.taxi.common.http.api.API;
import com.rainbean.taxi.common.http.biz.BaseBizResponse;
import com.rainbean.taxi.common.http.impl.BaseRequest;
import com.rainbean.taxi.common.http.impl.BaseResponse;
import com.rainbean.taxi.common.storage.SharedPreferencesDao;
import com.rainbean.taxi.common.util.DevUtil;
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
    public void checkSmsCode(final String phone, final String smsCode) {
        RxBus.getInstance().chainProcess(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {

                String url = API.Config.getDomain() + API.CHECK_SMS_CODE;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", phone);
                request.setBody("code", smsCode);
                IResponse response = httpClient.get(request, false);
                Log.d(TAG, response.getData());
                SmsCodeResponse smsCodeResponse = new SmsCodeResponse();

                if (response.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        smsCodeResponse.setCode(SMS_CHECK_SUC);
                    } else {
                        smsCodeResponse.setCode(SMS_CHECK_FAIL);
                    }
                } else {
                    smsCodeResponse.setCode(SMS_CHECK_FAIL);
                }
                return smsCodeResponse;
            }
        });
    }

    @Override
    public void checkUserExist(final String phone) {
        RxBus.getInstance().chainProcess(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                String url = API.Config.getDomain() + API.CHECK_USER_EXIST;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", phone);
                IResponse response = httpClient.get(request, false);
                Log.d(TAG, response.getData());
                UserExistResponse existResponse = new UserExistResponse();
                if (response.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(response.getData(),
                                    BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_USER_EXIST) {
                        existResponse.setCode(USER_EXIST);
                    } else if (bizRes.getCode() ==
                            BaseBizResponse.STATE_USER_NOT_EXIST) {
                        existResponse.setCode(USER_NOT_EXIST);
                    }
                } else {
                    existResponse.setCode(SERVER_FAIL);
                }
                return existResponse;
            }
        });
    }

    @Override
    public void register(final String phone, final String password) {
        RxBus.getInstance().chainProcess(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                String url = API.Config.getDomain() + API.REGISTER;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", phone);
                request.setBody("password", password);
                request.setBody("uid", DevUtil.UUID(TaxiApplication.getInstance()));

                IResponse response = httpClient.post(request, false);
                Log.d(TAG, response.getData());

                RegisterResponse registerResponse = new RegisterResponse();
                if (response.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(response.getData(),
                                    BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        registerResponse.setCode(REGISTER_SUC);
                    } else {
                        registerResponse.setCode(SERVER_FAIL);
                    }
                } else {
                    registerResponse.setCode(SERVER_FAIL);
                }
                return registerResponse;
            }
        });
    }

    @Override
    public void login(final String phone, final String password) {
        RxBus.getInstance().chainProcess(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                String url = API.Config.getDomain() + API.LOGIN;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", phone);
                request.setBody("password", password);


                IResponse response = httpClient.post(request, false);
                Log.d(TAG, response.getData());

                LoginResponse bizRes = new LoginResponse();
                if (response.getCode() == BaseResponse.STATE_OK) {
                    bizRes = new Gson().fromJson(response.getData(), LoginResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        // 保存登录信息
                        Account account = bizRes.getData();
                        sharedPreferencesDao.save(SharedPreferencesDao.KEY_ACCOUNT, account);
                        // 通知 UI
                        bizRes.setCode(LOGIN_SUC);
                    } else if (bizRes.getCode() == BaseBizResponse.STATE_PW_ERR) {
                        bizRes.setCode(PW_ERROR);
                    } else {
                        bizRes.setCode(SERVER_FAIL);
                    }
                } else {
                    bizRes.setCode(SERVER_FAIL);
                }
                return bizRes;
            }
        });
    }

    @Override
    public void loginByToken() {
        RxBus.getInstance().chainProcess(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                // 获取本地登录信息
                Account account =
                        (Account) sharedPreferencesDao.get(SharedPreferencesDao.KEY_ACCOUNT,
                                Account.class);


                // 登录是否过期
                boolean tokenValid = false;

                // 检查token是否过期

                if (account != null) {
                    if (account.getExpired() > System.currentTimeMillis()) {
                        // token 有效
                        tokenValid = true;
                    }
                }

                LoginResponse loginResponse = new LoginResponse();
                if (!tokenValid) {
                    loginResponse.setCode(TOKEN_INVALID);
                    return loginResponse;
                }


                // 请求网络，完成自动登录
                String url = API.Config.getDomain() + API.LOGIN_BY_TOKEN;
                IRequest request = new BaseRequest(url);
                request.setBody("token", account.getToken());
                IResponse response = httpClient.post(request, false);
                Log.d(TAG, response.getData());
                if (response.getCode() == BaseResponse.STATE_OK) {
                    loginResponse =
                            new Gson().fromJson(response.getData(), LoginResponse.class);
                    if (loginResponse.getCode() == BaseBizResponse.STATE_OK) {
                        // 保存登录信息
                        account = loginResponse.getData();
                        // todo: 加密存储
                        sharedPreferencesDao.save(SharedPreferencesDao.KEY_ACCOUNT, account);
                        loginResponse.setCode(LOGIN_SUC);
                    } else
                    if (loginResponse.getCode() == BaseBizResponse.STATE_TOKEN_INVALID) {
                        loginResponse.setCode(TOKEN_INVALID);
                    }
                } else {
                    loginResponse.setCode(SERVER_FAIL);
                }
                return loginResponse;
            }
        });
    }

    @Override
    public boolean isLogin() {
        // 获取本地登录信息
        Account account =
                (Account) sharedPreferencesDao.get(SharedPreferencesDao.KEY_ACCOUNT,
                        Account.class);


        // 登录是否过期
        boolean tokenValid = false;

        // 检查token是否过期

        if (account != null) {
            if (account.getExpired() > System.currentTimeMillis()) {
                // token 有效
                tokenValid = true;
            }
        }
        return tokenValid;
    }
}
