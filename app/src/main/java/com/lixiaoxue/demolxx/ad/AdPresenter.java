package com.lixiaoxue.demolxx.ad;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.lixiaoxue.demolxx.net.RetrofitUtils;
import com.lixiaoxue.demolxx.service.AppService;
import com.nucarf.base.utils.AndroidUtil;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.SharePreUtils;
import java.io.File;

public class AdPresenter {

    private final AppService appService;
    private AdView view;
    private final String TAG = AdPresenter.class.getSimpleName();

    public AdPresenter(AdView view){
        this.view = view;
        appService = RetrofitUtils.INSTANCE.getClient(AppService.class);
    }
    public void getAd(){
        //如果没有网则获取本地，sharePreference 文件
        if (!AndroidUtil.networkStatusOK(BaseAppCache.getContext())){
            String ad = SharePreUtils.getAdMessage();
            File cacheFile = new File(BaseAppCache.getContext().getExternalCacheDir(), "demoAd");
            LogUtils.e(TAG,"path="+cacheFile.getPath());
           // FileUtil.writeFileFileWriter(cacheFile,ad);
            //FileUtil.readFileFileReader(cacheFile);
            if(TextUtils.isEmpty(ad)){
                view.gotoNextActivity();
            }else{
                view.setAdInfo(ad);
            }

        }else{
            //如果有网则去网络获取
            //appService.getAdMessage()
            AdMessageBean bean = new AdMessageBean();
            bean.setImgUrl("");
            bean.setAdUrl("https://www.baidu.com");
            bean.setShow(true);
            if(bean != null){
                Gson gson = new Gson();
                String adMessage = gson.toJson(bean);
                //获取成功，储存到本地
                SharePreUtils.setAdMessage(adMessage);
                view.setAdInfo(adMessage);

            }else{//获取失败取本地
                String ad = SharePreUtils.getAdMessage();
                if(TextUtils.isEmpty(ad)){
                    view.gotoNextActivity();
                }else{
                    view.setAdInfo(ad);
                }
            }
        }


    }
}
