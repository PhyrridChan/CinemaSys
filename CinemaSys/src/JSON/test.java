package JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import static JSON.JSONhandle.*;
import static JSON.SpecificUtil.searchJsonArraySite;

class TestJson {
    public static void main(String[] args) throws IOException {
        JSONhandle jsoNhandle = new JSONhandle();
        JSONArray provList = getJsonArray("provList");
        JSONArray cityList = getJsonArray("cityList");
        JSONArray siteList = getJsonArray("siteList");

        int prov = 10;
        int city = 6;
        int ref = prov * 30 + city;
        int site = 4;

        System.out.println("provList: " + provList.getString(prov));
        System.out.println("cityList: " + cityList.getJSONArray(prov).getString(city));
        System.out.println("ref: " + refList.getInteger(ref));
        System.out.println("siteList: " + siteList.getJSONArray(refList.indexOf(ref + "")).getString(site));

        System.out.println("____________compare____________");

        System.out.println("provList: " + searchJsonArray("provList", prov));
        System.out.println("cityList: " + searchJsonArray("cityList", prov, city));
        System.out.println("ref: " + refList.getInteger(ref));
        System.out.println("siteList: " + searchJsonArraySite("siteList", ref, site));
    }


}