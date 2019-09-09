package com.konarktimes.konark.ServerConvig;

public class ServerHelper {
    //All the API's would be stored here
    public  static String getUrl(){
        return "https://www.deviprasadnayak.com/konark_api/";
    }

    public static String getFeaturedUrl(){
    return "getFeaturedPosts.php";
    }

    public static String getLatestUrl()
    {
        return "getPostsByLanguage.php";
    }

    public static  String getLangUrl(){return  "getLang.php";}

    public static  String getCategoryUrl(){return  "getCategories.php";}

    public static  String getSearchResults(){return "getSearchResults.php";}

    public static  String getCategoryWiseUrl(){return "getPostsByCategoryLanguage.php";}

}
