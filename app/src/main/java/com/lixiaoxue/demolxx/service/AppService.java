package com.lixiaoxue.demolxx.service;

import com.lixiaoxue.demolxx.ad.AdMessageBean;
import com.nucarf.base.retrofit.logiclayer.BaseResult;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AppService {
    /**
     * 发送验证码
     *
     * @param request
     * @return rest/pegas/login
     */
    @GET("rest/gegas/querySmsCode")
    Call<BaseResult<AdMessageBean>> getAdMessage(@QueryMap Map<String, String> request);


}