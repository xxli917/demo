package com.nucarf.base.bean;

import java.util.ArrayList;
import java.util.List;

public class CityListBean {
    public ArrayList<CityBean> address;
    public ArrayList<String> province_list() {
        ArrayList<String> mProvinceList = new ArrayList<>();
        for (int i = 0; i < address.size(); i++) {
            String name = address.get(i).getText();
            mProvinceList.add(name);
        }
        return mProvinceList;

    }

    public ArrayList<ArrayList<String>> city_list() {
        ArrayList<ArrayList<String>> mCityList = new ArrayList<>();
        for (int provinceindex = 0; provinceindex < address.size(); provinceindex++) {
            List<CityBean.ChildrenBeanX> mcity = address.get(provinceindex).getChildren();
            ArrayList<String> mCitystr_list = new ArrayList<>();
            for (int cityIndex = 0; cityIndex < mcity.size(); cityIndex++) {
                String name = mcity.get(cityIndex).getText();
                mCitystr_list.add(name);
            }
            mCityList.add(mCitystr_list);
        }
        return mCityList;

    }

    public ArrayList<ArrayList<List<CityBean.ChildrenBeanX.ChildrenBean>>> country_list() {
        ArrayList<ArrayList<List<CityBean.ChildrenBeanX.ChildrenBean>>> mCountryList = new ArrayList<>();
        ArrayList<ArrayList<List<String>>> mCountryListstr = new ArrayList<>();
        for (int provinceindex = 0; provinceindex < address.size(); provinceindex++) {
            ArrayList<List<CityBean.ChildrenBeanX.ChildrenBean>> mCountry_list = new ArrayList<>();
            List<String> mcount_str_liststr = new ArrayList<>();

            ArrayList<List<String>> mCountry_liststr = new ArrayList<>();
            List<CityBean.ChildrenBeanX> mcity = address.get(provinceindex).getChildren();
            for (int cityIndex = 0; cityIndex < mcity.size(); cityIndex++) {
                List<CityBean.ChildrenBeanX.ChildrenBean> mcount_str_list = mcity.get(cityIndex).getChildren();
                for (int i = 0; i < mcount_str_list.size(); i++) {
                    mCountry_list.add(mcount_str_list);
                    for (int j = 0; j < mcount_str_list.size(); j++) {
                        mcount_str_liststr.add(mcount_str_list.get(j).getText());
                    }
                }
            }
            mCountryList.add(mCountry_list);
            mCountry_liststr.add(mcount_str_liststr);
        }
        return mCountryList;
    }
}
