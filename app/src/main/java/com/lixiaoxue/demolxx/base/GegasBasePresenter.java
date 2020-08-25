package com.lixiaoxue.demolxx.base;


import com.lixiaoxue.demolxx.net.RetrofitUtils;
import com.lixiaoxue.demolxx.service.AppService;

/**
 * @author lixiaoxue
 * @date On 2019/8/8
 */
public class GegasBasePresenter<T> extends BasePresenter<T> {
    public AppService appService  = RetrofitUtils.INSTANCE.getClient(AppService.class);
}
