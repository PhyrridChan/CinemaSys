package JSON;

import static JSON.JSONhandle.refList;
import static JSON.JSONhandle.searchJsonArray;

public class SpecificUtil {
    public static String searchJsonArraySite(String siteList, int ref, int site) {
        return searchJsonArray(siteList, refList.indexOf(ref + ""), site);
    }

    public static String searchJsonArraySite(String siteList, String ref, String site) {
        return searchJsonArray(siteList, refList.indexOf(ref), Integer.parseInt(site));
    }

    public static String searchJsonArraySite(String siteList, String prov, String city, String site) {
        String ref = Integer.parseInt(prov) * 30 + Integer.parseInt(city) + "";
        return searchJsonArray(siteList, refList.indexOf(ref), Integer.parseInt(site));
    }
}
