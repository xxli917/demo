package com.nucarf.base.bean;

import java.util.ArrayList;

/**
 * 省市区地址bean
 * Created by WANG on 2016/3/25.
 */
public class CityModel {
    public ArrayList<provice> address;

    public class provice {
        public String name;
        public ArrayList<City> sub;
    }

    //市
    public class City {
        //区，县
        public ArrayList<String> sub;
        public String zipcode;
        public String name;


    }

    public ArrayList<String> province_list() {
        ArrayList<String> mProvinceList = new ArrayList<>();
        for (int i = 0; i < address.size(); i++) {
            String name = address.get(i).name;
            mProvinceList.add(name);
        }
        return mProvinceList;

    }

    public ArrayList<ArrayList<String>> city_list() {
        ArrayList<ArrayList<String>> mCityList = new ArrayList<>();
        for (int provinceindex = 0; provinceindex < address.size(); provinceindex++) {
            ArrayList<City> mcity = address.get(provinceindex).sub;
            ArrayList<String> mCitystr_list = new ArrayList<>();
            for (int cityIndex = 0; cityIndex < mcity.size(); cityIndex++) {
                String name = mcity.get(cityIndex).name;
                mCitystr_list.add(name);
            }
            mCityList.add(mCitystr_list);
        }
        return mCityList;

    }
    public ArrayList<ArrayList<String>> city_list_code() {
        ArrayList<ArrayList<String>> mCityList = new ArrayList<>();
        for (int provinceindex = 0; provinceindex < address.size(); provinceindex++) {
            ArrayList<City> mcity = address.get(provinceindex).sub;
            ArrayList<String> mCitystr_list = new ArrayList<>();
            for (int cityIndex = 0; cityIndex < mcity.size(); cityIndex++) {
                String name = mcity.get(cityIndex).zipcode;
                mCitystr_list.add(name);
            }
            mCityList.add(mCitystr_list);
        }
        return mCityList;

    }

    public ArrayList<ArrayList<ArrayList<String>>> country_list() {
        ArrayList<ArrayList<ArrayList<String>>> mCountryList = new ArrayList<>();
        for (int provinceindex = 0; provinceindex < address.size(); provinceindex++) {
            ArrayList<ArrayList<String>> mCountry_list = new ArrayList<>();
            ArrayList<City> mcity = address.get(provinceindex).sub;
            for (int cityIndex = 0; cityIndex < mcity.size(); cityIndex++) {
                ArrayList<String> mcount_str_list = mcity.get(cityIndex).sub;
                mCountry_list.add(mcount_str_list);
            }
            mCountryList.add(mCountry_list);
        }

        return mCountryList;
    }


}
