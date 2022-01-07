package pageConstructor;

import Items.ItemInfoParse;
import Items.Items;
import Items.Theaters;
import Items.GetItem;
import Items.Arrange;
import Users.Customers;
import Users.GetUser;
import Users.Staffs;
import Users.UserInfoParse;
import Items.Casts;
import bills.Bills;
import bills.GetBill;

import javax.servlet.http.Cookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

import static Items.GetArrange.*;

public final class ContentMaker {

    final static String[] type = {"动作片", "喜剧片", "纪录片", "戏剧", "适合家庭的", "奇幻片", "外语片", "恐怖片", "音乐片", "爱情片", "科幻片", "惊悚电影"};
    final static String[] rigion = {"大陆", "美国", "韩国", "日本", "中国香港", "中国台湾", "泰国", "印度", "法国", "英国", "俄罗斯", "意大利", "西班牙", "德国", "波兰", "澳大利亚", "伊朗", "其他"};
    final static String[] navs = {
            "/cinema/main", "/cinema/movies", "/cinema/theaters", "/cinema/ranking", "https://www.maoyan.com/news", "/cinema/me"
    };
    static String navStr;
    static String rankStr;
    static String defaultCSS = "/main_page/styles.css";
    static String defaultICO = "/imgs/decorate.ico";

    //Getter&Setter
    public static String getNavStr() {
        return navStr;
    }

    public static void setNavStr(String navStr) {
        ContentMaker.navStr = navStr;
    }

    public static String getRankStr() {
        return rankStr;
    }

    public static void setRankStr(String rankStr) {
        ContentMaker.rankStr = rankStr;
    }

    public static String[] getType() {
        return type;
    }

    public static String[] getRigion() {
        return rigion;
    }

    public static String[] getNavs() {
        return navs;
    }

    //contentMaker
    //Theaters&Movies
    public static String moviesContentMaker(String title, int t, String... parameters) {
        return moviesContentMaker(title, null, t, parameters);
    }

    public static String moviesContentMaker(String title, String login, int t, String... parameters) {
        String head = contentHead(title, defaultCSS, defaultICO);
        String nav = contentNav(login);
        String choose = (t == 1 ? contentChoose(type, rigion, parameters) : contentChoose());
        String showMovies = ""; // Movies--3 Theaters--4
        String script = "";
        if (t == 1) showMovies = contentShowMovie(postRowMaker(GetItem.moviePoster(3, parameters), t));
        if (t == 2) {
            showMovies = contentShowMovie(postRowMaker(GetItem.theatersPoster(4, parameters), t));
            script = scriptMaker(parameters);
        }
        return head + "<div class=\"app\">" + nav + choose + showMovies + footer(script);
        //"<div style=\"width: 100%;\">" +
    }

    private static String scriptMaker(String... para) {
        String str =
                "<script>\n" +
                        "    $(function () {\n" +
                        "        var oTop1 = $(document).scrollTop();\n" +
                        "\n" +
                        "        $(window).scroll(function () {\n" +
                        "            var oTop2 = $(document).scrollTop();\n" +
                        "            console.log(oTop2);\n" +
                        "            if (oTop2 > oTop1) {\n" +
                        "                $(\".nav\").addClass(\"hide\").removeClass(\"show\");\n" +
                        "            } else {\n" +
                        "                $(\".nav\").addClass(\"show\").removeClass(\"hide\");\n" +
                        "            }\n" +
                        "            oTop1 = $(document).scrollTop();\n" +
                        "        });\n" +
                        "    });\n" +
                        "    $(document).ready(function () {\n" +
                        "            document.getElementById('prov')[" + para[0] + "].selected = true;\n" +
                        "            provSelect.onchange();\n" +
                        "            document.getElementById('city')[" + para[1] + "].selected = true;\n" +
                        "            citySelect.onchange();\n" +
                        "            document.getElementById('site')[" + para[2] + "].selected = true;\n" +
                        "        })\n" +
                        "</script>";
        return str;
    }

    //ChooseCinema
    public static String cinemaContentMaker(String title, String movie) {
        return cinemaContentMaker(title, movie, null);
    }

    public static String cinemaContentMaker(String title, String movieID, String login) {
        String head = contentHead(title, defaultCSS, defaultICO);
        String nav = contentNav(login);
        Items items = GetItem.get(movieID);
        Theaters[] theaters = GetItem.theatersPoster(5, movieID);
        StringBuilder sb = new StringBuilder();
        for (Theaters t : theaters) {
            String itemHead = "<div class=\"showMovies\" style=\"width: 90%;margin: 10px 70px 10px 70px;height: auto;\"><dl class=\"row__posters\" style=\"height: 190px;\"><dt class=\"\" style=\"width: 186px; height: 186px;margin: 15px\">\n" +
                    "<img style=\"border-radius: 5px;\" width=\"186px\" src=\"" +
                    t.getPic() + "\"><h1 class=\"row__poster__name\">" +
                    t.getTheaterName() + "</h1></dt>" +
                    "<dt class=\"\" style=\"width: 82%; height: 186px;border-radius: 5px;background: #333333;display: flex;align-items: center\">" +
                    "<dl class=\"row__posters\">";
            String arrangeSelector = arrangeSelector(reduceArrange(t.getOnShowMovie(), movieID));
            String itemFooter = "</dl></dt></dl></div>";
            sb.append(itemHead).append(arrangeSelector).append(itemFooter);
        }
        sb.append("</div>");
        String showMovies =
                "<div style=\"width: 100%;margin-top: 100px;min-height: 1000px;padding-bottom: 100px\"><img style=\"float: left;border-radius: 5px;padding-right: 1%;padding-left: 70px;\" ;height=\"220px\" width=\"153.7px\"src=\"" +
                        items.getPics() + "\"><div style=\"float: left;background: #222222;border-radius: 5px;width: 78.4%;height: 220px;\"><div style=\"display: inline-block;color:white;\"><h4 style=\"font-size: 30px; margin: 5px 5px 0 5px;\">" +
                        items.getItemName() + "<nobr style=\"font-size: 15px;padding-left: 5px;\">" +
                        items.getEngName() + "</nobr><div style=\"height: 20px;margin: 10px 10px 5px 5px;float: right;\"><img class=\"row__poster__info\" width=\"25px\" height=\"25px\" src=\"/imgs/icon.png\"><b class=\"row__poster__info\" style=\"font-size: 28px;color: white;margin: 0 5px 5px 0;\">9.2</b><nobr style=\"font-size: 18px;color: #1488CC\">票房</nobr>" +
                        "<b class=\"row__poster__info\" style=\"font-size: 28px;color: white;margin: 0 0 5px 0;\">" +
                        items.getRate() + "</b><nobr style=\"font-size: 18px\">亿</nobr></div></h4><h5 style=\"margin: 10px 0 0 5px;\">" +
                        items.getGenreID() + "<nobr style=\"margin-left: 10px;\">" +
                        items.getReleaseDate() + "上映</nobr></h5><h5 class=\"introduction\" style=\"margin: 10px 0 0 5px;-webkit-line-clamp: 3;height: 58px\">" +
                        items.getIntroduction() + "</h5></div></div>" + sb.toString();
        return head + "<div class=\"app\">" + nav + showMovies + footer();
    }

    //MeForCus
    public static String meContentMaker(String title, HashMap<String, String> infoMap) {
        String UID = infoMap.get("UID");
        String head = contentHead(title, defaultCSS, defaultICO);
        Bills[] bills = GetBill.getbills(UID);
        String bill = contentBill(bills);
        System.out.println(infoMap.toString());
        String str =
                head + "<div class=\"app\">\n" + navStr +
                        "<div style=\"display: block;width: 100%;margin-top: 100px;height: 1100px;\"><div style=\"float: left;width: 16%;padding-right: 3%;height: 1100px;\"><dl style=\"width: 100%;font-size:24px;background: #222222;margin-left: 20px;text-align: center;color: white;border-radius:5px \"><div class=\"div__title\" style=\"text-align: left;padding-bottom: 15px\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">" +
                        "个人中心</b></div><form id=\"leftBarForm\" class=\"leftBarForm\"><div style=\"padding: 10px;cursor: pointer\" id=\"billDiv\"><input type=\"radio\" name=\"nav\" id=\"billRadio\" value=\"billRadio\" style=\"display: none\"checked/><dt><label for=\"billRadio\" style=\"cursor: pointer\">" +
                        "我的<nobr style=\"font-weight: bolder\">订单</nobr></label></dt></div><div style=\"padding: 10px;cursor: pointer\" id=\"infoDiv\"><input type=\"radio\" name=\"nav\" id=\"infoRadio\" value=\"infoRadio\" style=\"display:none \"/><dt><label for=\"infoRadio\" style=\"cursor: pointer\" " +
                        "onclick=\"" +
                        "document.getElementById('prov')[17].selected=true;provSelect.onchange();" +
                        "document.getElementById('city')[0].selected=true;citySelect.onchange();" +
                        "document.getElementById('site')[4].selected=true\n\">" +
                        "我的<nobr style=\"font-weight: bolder\">信息</nobr></label></dt></div></form></dl></div><div style=\"float: left;width: 77%;padding-left: 10px; margin-right: 3%\"><dl style=\"width: 100%;font-size:24px;background: #222222;text-align: center;color: white;border-radius:5px \"><div class=\"div__title\" style=\"text-align: left;padding-bottom: 15px\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"\" class=\"titleBar\" id=\"billO\">" +
                        "我的订单</b><b style=\"display: none\" class=\"titleBar\" id=\"infoO\">" +
                        "我的信息</b></div></dl></div>" +
                        "<div class=\"blocks table_content\" style=\"padding-left: 10px;width: 77%\" id=\"billTable\">" +
                        "<div class=\"blocks\" style=\"width: 100%\">" +
                        bill + (bills == null || bills.length <= 0 ? "" :
                        "<div style=\"float: right;color: #1488CC;padding: 15px 50px 15px 15px;\">" +
                                "<a href=\"#\" style=\"padding: 5px;text-decoration: none;color: #1488CC;\">上一页</a>" +
                                "<a href=\"#\" style=\"padding: 5px;text-decoration: none;color: #1488CC;\">下一页</a></div>") + "</div></div>" +

                        "<div class=\"login-div table_content\" name=\"loginDiv\" style=\"display: none;width: 77%;float: left;padding: 20px\" id=\"infoTable\">\n" +
                        "<div style=\"float: left;width: 47.5%;color: white\">\n" +
                        "<h3>头像</h3>\n" +
                        "<div class=\"input-text\" style=\"margin-bottom: 35px;vertical-align: middle;text-align: center;\"><div style=\"text-align: center;margin: 0 auto\"><div class=\"img-avatorshow\"><img width=\"125px\" height=\"125px\" id=\"myAvatorinput\" alt=\"\"\n" +
                        "src=\"" + infoMap.get("avator") + "\"></div></div><br>" +
                        "<a href=\"javascript:;\" class=\"file\" style=\"cursor: pointer\">更换头像<input type=\"file\" name=\"avator\" id=\"uploadavator\" accept=\"image/*\" onchange=\"UploadImg(this)\"></a>" +
                        "<div style=\"padding-top: 10px\"><h3 style=\"display: flex;\">经验值<nobr style=\"font-size: 20px;display: flex;flex-wrap: wrap;align-items: flex-end;padding-left: 10px;color: #1488CC;\">\n" +
                        "23333" + "</nobr><nobr style=\"font-size: 16px;display: flex;flex-wrap: wrap;align-items: flex-end;\">\n" +
                        "/30000" + "</nobr></h3><div style=\"margin: 10px 50px 20px 50px;\"><div style=\"width: 100%;height:26px;background: #222222\"><div style=\"background: #1488CC;width: \n" +
                        "65%" + ";height:26px;display: flex;flex-wrap: wrap;align-content: center;justify-content: flex-end;padding-right: 10px;\"><b style=\"background: #333333;padding: 2px;border-radius: 5px;font-size: 6px;\">\n" +
                        "LV 5" + "</b></div></div></div></div><div style=\"padding-top: 10px\"><h3 style=\"display: flex;\">VIP<nobr style=\"font-size: 20px;display: flex;flex-wrap: wrap;align-items: flex-end;padding-left: 34.2px;color: #1488CC;\">\n" +
                        "23333" + "</nobr><nobr style=\"font-size: 16px;display: flex;flex-wrap: wrap;align-items: flex-end;\">\n" +
                        "/30000" + "</nobr></h3><div style=\"margin: 10px 50px 20px 50px;\"><div style=\"width: 100%;height:26px;background: #222222\"><div style=\"background: #1488CC;width: \n" +
                        "65%" + ";height:26px;display: flex;flex-wrap: wrap;align-content: center;justify-content: flex-end;padding-right: 10px;\"><b style=\"background: #333333;padding: 2px;border-radius: 5px;font-size: 6px;\"><nobr style=\"padding-right: 5px\">\n" +
                        "年度会员" + "</nobr>" +
                        "LV 5" + "</b></div></div></div></div></div></div>" +
                        "<div style=\"float: left; width: 47.5%;padding-right: 5%\"><div id=\"page1\" name=\"page1\" style=\"display: block;color: white;padding-left: 10px\">\n" +
                        "<h3>基础信息</h3><div class=\"input-text\">\n" +
                        "<input type=\"text\" placeholder=\"电话号码\" style=\"width: 29%\" disabled/><input type=\"text\" id=\"inputPhone\" name=\"phone\" placeholder=\"" +
                        infoMap.get("phone") + "\" style=\"width: 68%\" /></div>" +
                        "<div class=\"input-text\" name=\"inputAge\">\n" +
                        "<input type=\"text\" placeholder=\"年龄\" style=\"width: 29%\" disabled/><input type=\"number\" id=\"inputAge\" name=\"age\" placeholder=\"" +
                        infoMap.get("age") + "\" min=\"1\" max=\"100\" style=\"width: 68%\" /></div>\n" +

                        "<h3>性别</h3><div style=\"width: 100%; height: 25px; color: white; text-indent: 18px;margin-left: 10px;\">\n" +

                        "<input " + (infoMap.get("sex").equals("m") ? "checked" : "") + " type=\"radio\" name=\"sex\" value=\"male\"> <b style=\"margin-right: 60px\">男</b>\n" +
                        "<input " + (infoMap.get("sex").equals("f") ? "checked" : "") + " type=\"radio\" name=\"sex\" value=\"female\"> <b>女</b></div>" +

                        "<h3>地址</h3><div class=\"input-text\">\n" +
                        "<select id=\"prov\" name=\"regionProvince\" style=\"margin-right: 2%\"></select>\n" +
                        "<select id=\"city\" name=\"regionCity\" style=\"margin-right: 2%\"></select>\n" +
                        "<select id=\"site\" name=\"regionSite\"></select>\n" +
                        "<script src=\"/login_page/CustomerLoginPage/location.js\"></script></div>\n" +
                        "<button class=\"signin-button\" id=\"signup2\" type=\"submit\">保存修改</button></div></div></div>" +

                        "</div>" + footer();
        return str;
    }

    //MeForCnm
    public static String cnmContentMaker(String title, HashMap<String, String> infoMap) {
        String UID = infoMap.get("UID");
        String head = contentHead(title, defaultCSS, defaultICO);
        String str = head + "<div class=\"app\">\n" + navStr +
                "    <div style=\"display: block;width: 100%;margin-top: 100px;height: 1100px;\">\n" +
                "        <!--        leftBar-->\n" +
                "        <div style=\"float: left;width: 16%;padding-right: 3%;height: 1100px;\">\n" +
                "            <dl style=\"width: 100%;font-size:24px;background: #222222;margin-left: 20px;text-align: center;color: white;border-radius:5px \">\n" +
                "                <div class=\"div__title\" style=\"text-align: left;padding-bottom: 15px\">\n" +
                "                    <img height=\"12\" src=\"/imgs/decorate.svg\">\n" +
                "                    <b style=\"font-size: xx-large;padding-left: 15px\">\n" +
                "                        个人中心</b>\n" +
                "                </div>\n" +
                "                <form id=\"leftBarForm\" class=\"leftBarForm\">\n" +
                "                    <div style=\"padding: 10px;cursor: pointer\" id=\"movieDiv\">\n" +
                "                        <input type=\"radio\" name=\"nav\" id=\"movieRadio\" value=\"movieRadio\" style=\"display: none\"\n" +
                "                               checked/>\n" +
                "                        <dt>\n" +
                "                            <label for=\"movieRadio\" style=\"cursor: pointer\">\n" +
                "                                <nobr style=\"font-weight: bold\">电影</nobr>\n" +
                "                                管理</label>\n" +
                "                        </dt>\n" +
                "                    </div>\n" +
                "                    <div style=\"padding: 10px;cursor: pointer\" id=\"cinemaDiv\">\n" +
                "                        <input type=\"radio\" name=\"nav\" id=\"cinemaRadio\" value=\"cinemaRadio\" style=\"display:none \"/>\n" +
                "                        <dt>\n" +
                "                            <label for=\"cinemaRadio\" style=\"cursor: pointer\">\n" +
                "                                <nobr style=\"font-weight: bold\">影城</nobr>\n" +
                "                                管理\n" +
                "                            </label>\n" +
                "                        </dt>\n" +
                "                    </div>\n" +
                "                    <div style=\"padding: 10px;cursor: pointer\" id=\"NewsDiv\">\n" +
                "                        <input type=\"radio\" name=\"nav\" id=\"NewsRadio\" value=\"NewsRadio\" style=\"display: none\"/>\n" +
                "                        <dt>\n" +
                "                            <label for=\"NewsRadio\" style=\"cursor: pointer\">\n" +
                "                                <nobr style=\"font-style: italic;font-weight: bold\">NEWS</nobr>\n" +
                "                                管理\n" +
                "                            </label>\n" +
                "                        </dt>\n" +
                "                    </div>\n" +
                "                    <div style=\"padding: 10px;cursor: pointer\" id=\"rankDiv\">\n" +
                "                        <input type=\"radio\" name=\"nav\" id=\"rankRadio\" value=\"rankRadio\" style=\"display: none\"/>\n" +
                "                        <dt>\n" +
                "                            <label for=\"rankRadio\" style=\"cursor: pointer\">\n" +
                "                                <nobr style=\"font-weight: bold\">榜单</nobr>\n" +
                "                                管理\n" +
                "                            </label>\n" +
                "                        </dt>\n" +
                "                    </div>\n" +
                "                    <div style=\"padding: 10px;\" class=\"pplOrg\" id=\"userDiv\">\n" +
                "                        <input type=\"radio\" name=\"nav\" id=\"userRadio\" value=\"userRadio\" style=\"display: none\"/>\n" +
                "                        <dt>\n" +
                "                            人员管理\n" +
                "                            <label for=\"userRadio\" style=\"cursor: pointer\">\n" +
                "                                <dl class=\"subTable\">\n" +
                "                                    <dt onclick=\"sendInfo(this)\" class=\"subTableBar\" id=\"cusRadio\">\n" +
                "                                        客户管理\n" +
                "                                    </dt>\n" +
                "                                    <dt onclick=\"sendInfo(this)\" class=\"subTableBar\" id=\"staffRadio\">\n" +
                "                                        工作人员管理\n" +
                "                                    </dt>\n" +
                "                                </dl>\n" +
                "                            </label>\n" +
                "                        </dt>\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </dl>\n" +
                "        </div>\n" +
                "        <!--        content-->\n" +
                "        <div>\n" +
                "            <!--            title-->\n" +
                "            <div style=\"float: left;width: 77%;padding-left: 10px; margin-right: 3%\">\n" +
                "                <dl style=\"width: 100%;font-size:24px;background: #222222;text-align: center;color: white;border-radius:5px \">\n" +
                "                    <div class=\"div__title\" style=\"text-align: left;padding-bottom: 15px\">\n" +
                "                        <img height=\"12\" src=\"/imgs/decorate.svg\">\n" +
                "                        <b style=\"\" class=\"titleBar\" id=\"movieO\">\n" +
                "                            电影管理</b>\n" +
                "                        <b style=\"display: none\" class=\"titleBar\" id=\"cinemaO\">\n" +
                "                            影城管理</b>\n" +
                "                        <b style=\"display: none\" class=\"titleBar\" id=\"NewsO\">\n" +
                "                            NEWS管理</b>\n" +
                "                        <b style=\"display: none\" class=\"titleBar\" id=\"rankO\">\n" +
                "                            榜单管理</b>\n" +
                "                        <b style=\"display: none\" class=\"titleBar\" id=\"usersO\">\n" +
                "                            人员管理\n" +
                "                            <nobr class=\"subTitle\" style=\"font-size: large;display: none\" id=\"cusTitle\">客户管理</nobr>\n" +
                "                            <nobr class=\"subTitle\" style=\"font-size: large;display: none\" id=\"staffTitle\">工作人员管理\n" +
                "                            </nobr>\n" +
                "                        </b>\n" +
                "                    </div>\n" +
                "                </dl>\n" +
                "            </div>\n" +
                "            <!--            main-->\n" +
                "            <div class=\"blocks\" style=\"padding-left: 10px;width: 77%\">\n" +
                "                <div style=\"float: left;background: #222222;border-radius: 5px;width: 100%;height: auto;color: white;overflow-x: scroll;\">\n" +
                "                    <div style=\"float: left;width: 10%;padding: 10px\">\n" +
                "                        <button class=\"signin-button\" style=\"width: 100%;\">添加</button>\n" +
                "                    </div>\n" +
                "                    <div class=\"input-text\" style=\"float: left;width: 40%;padding: 10px\">\n" +
                "                        <input style=\"width: 60%\" type=\"text\">\n" +
                "                        <button class=\"signin-button\" style=\"width: 20%\">查询</button>\n" +
                "                    </div>\n" +
                "                    <!--     Table     -->\n" +
                "                    <!--     电影管理-->\n" +
                "                    <div>\n" +
                "                        <table class=\"pure-table\" id=\"movieTable\" style=\"display: inline-block\">\n" +
                "                            <thead>\n" +
                "                            <tr>\n" +
                "                                <td>电影ID</td>\n" +
                "                                <td>状态</td>\n" +
                "                                <td>电影名（英文名）</td>\n" +
                "                                <td>关键词</td>\n" +
                "                                <td>图片</td>\n" +
                "                                <td>类别</td>\n" +
                "                                <td>国家</td>\n" +
                "                                <td>放映时长</td>\n" +
                "                                <td>上映时间</td>\n" +
                "                                <td>评分</td>\n" +
                "                                <td>票房</td>\n" +
                "                                <td>介绍</td>\n" +
                "                                <td>声音技术</td>\n" +
                "                                <td>画面技术</td>\n" +
                "                                <td>观看渠道</td>\n" +
                "                                <td>获奖</td>\n" +
                "                                <td>methods</td>\n" +
                "                            </tr>\n" +
                "                            </thead>\n" +
                "                            <tr id=\"Movie_211208212321000001\">\n" +
                "                                <div>\n" +
                "                                    <td><input type=\"text\" placeholder=\"211208212321000001\" readonly/></td>\n" +
                "                                    <td><input style=\"width: 50px\" type=\"text\" placeholder=\"在映\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"itemName(engName)\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"keywords\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"pics\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"genere\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"country\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"runtime\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"releaseDate\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"rate\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"boxoffice\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"introduction\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"soundmix\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"tech\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"wheretowatch\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"award\" readonly/></td>\n" +
                "                                </div>\n" +
                "                                <td>\n" +
                "                                    <div style=\"width: 100px;text-align: center\">\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\">删除</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\"\n" +
                "                                           onclick=\"alter(this)\">修改</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none\"\n" +
                "                                           onclick=\"confirm(this)\">确认</a>\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <!--     影城管理-->\n" +
                "                    <div>\n" +
                "                        <table class=\"pure-table\" id=\"cinemaTable\">\n" +
                "                            <thead>\n" +
                "                            <tr>\n" +
                "                                <td>影城ID</td>\n" +
                "                                <td>状态</td>\n" +
                "                                <td>影城名</td>\n" +
                "                                <td>联系方式</td>\n" +
                "                                <td>介绍</td>\n" +
                "                                <td>地址</td>\n" +
                "                                <td>放映室</td>\n" +
                "                                <td>methods</td>\n" +
                "                            </tr>\n" +
                "                            </thead>\n" +
                "                            <tr>\n" +
                "                                <div>\n" +
                "                                    <td><input type=\"text\" placeholder=\"影城ID\" readonly/></td>\n" +
                "                                    <td><input style=\"width: 50px\" type=\"text\" placeholder=\"状态\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"影城名(engName)\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"联系方式\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"介绍\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"地址\" readonly/></td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"放映室\" readonly/></td>\n" +
                "                                </div>\n" +
                "                                <td>\n" +
                "                                    <div style=\"width: 100px;text-align: center\">\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\">删除</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\"\n" +
                "                                           onclick=\"alter(this)\">修改</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none\"\n" +
                "                                           onclick=\"confirm(this)\">确认</a>\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <!--     NEWS管理-->\n" +
                "                    <div>\n" +
                "                        <table class=\"pure-table\" id=\"NewsTable\">\n" +
                "                            <thead>\n" +
                "                            <tr>\n" +
                "                                <td>暂未开发</td>\n" +
                "                            </tr>\n" +
                "                            </thead>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <!--     榜单管理-->\n" +
                "                    <div>\n" +
                "                        <table class=\"pure-table\" id=\"rankTable\">\n" +
                "                            <thead>\n" +
                "                            <tr>\n" +
                "                                <td>排名</td>\n" +
                "                                <td>电影ID</td>\n" +
                "                                <td>电影名（英文名）</td>\n" +
                "                                <td style=\"width: 60px;\">methods</td>\n" +
                "                            </tr>\n" +
                "                            </thead>\n" +
                "                            <tr>\n" +
                "                                <div>\n" +
                "                                    <td>1</td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"211208212321000001\" readonly/></td>\n" +
                "                                    <td>itemName(engName)</td>\n" +
                "                                </div>\n" +
                "                                <td>\n" +
                "                                    <div style=\"width: 190px;text-align: center;display: inline-table;\">\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;\">删除</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;\"\n" +
                "                                           onclick=\"alter(this)\">修改</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;padding: 5px;\"\n" +
                "                                           onclick=\"confirm(this)\">确认</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;padding: 5px;\">上调</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;\">下移</a>\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <!--     人员管理-cus -->\n" +
                "                    <div>\n" +
                "                        <table class=\"pure-table\" id=\"cusTable\">\n" +
                "                            <thead>\n" +
                "                            <tr>\n" +
                "                                <td>用户ID</td>\n" +
                "                                <td>状态</td>\n" +
                "                                <td>用户名</td>\n" +
                "                                <td>头像</td>\n" +
                "                                <td>性别</td>\n" +
                "                                <td>年龄</td>\n" +
                "                                <td>手机</td>\n" +
                "                                <td>地区</td>\n" +
                "                                <td>注册时间</td>\n" +
                "                                <td>property</td>\n" +
                "                                <td>credit</td>\n" +
                "                                <td>value</td>\n" +
                "                                <td>vip</td>\n" +
                "                                <td>methods</td>\n" +
                "                            </tr>\n" +
                "                            </thead>\n" +
                "                            <tr>\n" +
                "                                <div>\n" +
                "                                    <td>211208212321000001</td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"状态\" readonly/></td>\n" +
                "                                    <td>用户名</td>\n" +
                "                                    <td>头像</td>\n" +
                "                                    <td>性别</td>\n" +
                "                                    <td>年龄</td>\n" +
                "                                    <td>手机</td>\n" +
                "                                    <td>地区</td>\n" +
                "                                    <td>注册时间</td>\n" +
                "                                    <td><input type=\"text\" placeholder=\"property\" readonly/></td>\n" +
                "                                    <td>credit</td>\n" +
                "                                    <td>value</td>\n" +
                "                                    <td>vip</td>\n" +
                "                                </div>\n" +
                "                                <td>\n" +
                "                                    <div style=\"width: 100px;text-align: center\">\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\">删除</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\"\n" +
                "                                           onclick=\"alter(this)\">修改</a>\n" +
                "                                        <a href=\"#\"\n" +
                "                                           style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none\"\n" +
                "                                           onclick=\"confirm(this)\">确认</a>\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <!--     人员管理-staff-->\n" +
                "                    <table class=\"pure-table\" id=\"staffTable\">\n" +
                "                        <thead>\n" +
                "                        <tr>\n" +
                "                            <td>员工ID</td>\n" +
                "                            <td>状态</td>\n" +
                "                            <td>姓名</td>\n" +
                "                            <td>头像</td>\n" +
                "                            <td>性别</td>\n" +
                "                            <td>年龄</td>\n" +
                "                            <td>手机</td>\n" +
                "                            <td>地区</td>\n" +
                "                            <td>入职时间</td>\n" +
                "                            <td>职位</td>\n" +
                "                            <td>工作组</td>\n" +
                "                            <td>工作地</td>\n" +
                "                            <td>对接客户</td>\n" +
                "                            <td>管理员权限</td>\n" +
                "                            <td>methods</td>\n" +
                "                        </tr>\n" +
                "                        </thead>\n" +
                "                        <tr>\n" +
                "                            <div>\n" +
                "                                <td>211208212321000001</td>\n" +
                "                                <td><input type=\"text\" placeholder=\"状态\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"姓名\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"头像\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"性别\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"年龄\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"手机\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"地区\" readonly/></td>\n" +
                "                                <td>入职时间</td>\n" +
                "                                <td><input type=\"text\" placeholder=\"职位\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"工作组\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"工作地\" readonly/></td>\n" +
                "                                <td><input type=\"text\" placeholder=\"管理员权限\" readonly/></td>\n" +
                "                                <td>对接客户</td>\n" +
                "                            </div>\n" +
                "                            <td>\n" +
                "                                <div style=\"width: 100px;text-align: center\">\n" +
                "                                    <a href=\"#\"\n" +
                "                                       style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\">删除</a>\n" +
                "                                    <a href=\"#\"\n" +
                "                                       style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\"\n" +
                "                                       onclick=\"alter(this)\">修改</a>\n" +
                "                                    <a href=\"#\"\n" +
                "                                       style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none\"\n" +
                "                                       onclick=\"confirm(this)\">确认</a>\n" +
                "                                </div>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <!--            bar-->\n" +
                "            <div style=\"float: right;color: #1488CC;padding: 15px 50px 15px 15px;\">\n" +
                "                <a href=\"#\" style=\"padding: 5px;text-decoration: none;color: #1488CC;\">上一页</a>\n" +
                "                <a href=\"#\" style=\"padding: 5px;text-decoration: none;color: #1488CC;\">下一页</a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n"
                + footer(script());
        return str;
    }

    public static String cnmTempContentMaker(String title, HashMap<String, String> infoMap, String query, String table) {
        String head = contentHead(title, defaultCSS, defaultICO);
        int page = 1;
        return head + "<div class=\"app\">" + navStr +
                "<!--电影 管理--><div style=\"display: block;width: 100%;margin-top: 100px;height: 1100px;\">\n" +
                "<!--leftBar--><div style=\"float: left;width: 16%;padding-right: 3%;height: 1100px;\">\n" +
                leftBar(table) + "</div></div>" +
                "<!--content-->" +
                "<div>" +
                "<!--title-->\n" +
                title() +
                " <!--main-->\n" +
                "<div class=\"blocks\" style=\"padding-left: 10px;width: 77%\"><div style=\"float: left;background: #222222;border-radius: 5px;width: 100%;height: auto;color: white;overflow-x: scroll;\"><div style=\"float: left;width: 10%;padding: 10px\">\n" +
                "<button type=\"button\" class=\"signin-button\" style=\"width: 100%;\" onclick=\"addrow()\">添加</button></div>\n" +
                "<div class=\"input-text\" style=\"float: left;width: 40%;padding: 10px\">\n" +
                "<input style=\"width: 60%\" type=\"text\" name=\"query\" value=\"" + query + "\">\n" +
                "<input style=\"width: 60%\" type=\"hidden\" name=\"tableName\" value=\"" + table + "\" readonly>\n" +
                "<button class=\"signin-button\" style=\"width: 20%\" type=\"submit\" \">查询</button>\n" +
                "</div>\n" +
                "<!--     Table     -->\n" +
                "<!--     电影管理-->\n" +
                moviesManager(GetItem.moviePoster(6, page, (table.equals("movieTable") || table.equals("") ? query : ""))) +
                "                <!--     影城管理-->\n" +
                cinemaManager(GetItem.theatersPoster(6, page, (table.equals("cinemaTable") ? query : ""))) +
                "                <!--     NEWS管理-->\n" +
                "<div><table class=\"pure-table\" id=\"NewsTable\"><thead><tr><td>暂未开发</td></tr></thead></table></div>" +
                "                <!--     榜单管理-->\n" +
                rankingManager(GetItem.moviePoster(7, page, (table.equals("rankTable") ? query : ""))) +
                "                <!--     人员管理-cus -->\n" +
                cusManager(GetUser.cusPoster(page, (table.equals("cusTable") ? query : ""))) +
                "                <!--     人员管理-staff-->\n" +
                staffManager(GetUser.staffPoster(page, (table.equals("staffTable") ? query : ""))) +
                "</div></div>" +
                "<!--bar-->\n" +
                "<div style=\"float: right;color: #1488CC;padding: 15px 50px 15px 15px;\"><a href=\"#\" style=\"padding: 5px;text-decoration: none;color: #1488CC;\">上一页</a><a href=\"#\" style=\"padding: 5px;text-decoration: none;color: #1488CC;\">下一页</a> </div></div></div>"
                + footer(script());
    }

    private static String staffManager(Staffs[] staffs) {
        String head = "<table class=\"pure-table\" id=\"staffTable\"><thead><tr><td>员工ID</td><td>状态</td><td>姓名</td><td>头像</td><td>性别</td><td>年龄</td><td>手机</td><td>地区</td><td>入职时间</td><td>职位</td><td>工作组</td><td>工作地</td><td>管理员权限</td><td>对接客户</td><td>methods</td></tr></thead>";
        String body = staffManagerBodyBuilder(staffs);
        String tail = "</tr></table>";
        return head + body + tail;
    }

    private static String staffManagerBodyBuilder(Staffs[] staffs) {
        StringBuilder sb = new StringBuilder();
        String bodyTail = "</div><td><div style=\"width: 100px;text-align: center\"><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\" onclick=\"deleteIt(this)\">删除</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\" onclick=\"alter(this)\">修改</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;\" onclick=\"confirm(this)\">确认</a></div></td>";
        for (Staffs items : staffs) {
            String temp = "<tr id=\"Staff" + items.getUID() + "\"><div>" +
                    "<td>" + items.getUID() + "</td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getState() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getUserName() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getAvator() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getSex() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getAge() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getPhone() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getRegionCountry() + "," + items.getRegionProvince() + "," + items.getRegionCity() + "\" readonly /></td>" +
                    "<td>" + items.getJoinTime() + "</td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getRank() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getWorkGroup() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getWorkPlace() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getRoot() + "\" readonly /></td>" +
                    "<td>" + items.getWorkOn() + "</td>";
            sb.append(temp).append(bodyTail);
        }
        return sb.toString();
    }

    private static String cusManager(Customers[] customers) {
        String head = "<div><table class=\"pure-table\" id=\"cusTable\"><thead><tr><td>用户ID</td><td>状态</td><td>用户名</td><td>头像</td><td>性别</td><td>年龄</td><td>手机</td><td>地区</td><td>注册时间</td><td>property</td><td>credit</td><td>value</td><td>vip</td><td>methods</td></tr></thead>";
        String body = cusManagerBodyBuilder(customers);
        String tail = "</table></div>";
        return head + body + tail;
    }

    private static String cusManagerBodyBuilder(Customers[] customers) {
        StringBuilder sb = new StringBuilder();
        String bodyTail = "</div><td><div style=\"width: 100px;text-align: center\"><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\" onclick=\"deleteIt(this)\">删除</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\" onclick=\"alter(this)\">修改</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;\" onclick=\"confirm(this)\">确认</a></div></td></tr>";
        for (Customers items : customers) {
            String temp = "<tr id=\"Cus" + items.getUID() + "\"><div>" +
                    "<td>" + items.getUID() + "</td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getState() + "\" readonly /></td>" +
                    "<td>" + items.getUserName() + "</td>" +
                    "<td>" + items.getAvator() + "</td>" +
                    "<td>" + items.getSex() + "</td>" +
                    "<td>" + items.getAge() + "</td>" +
                    "<td>" + items.getPhone() + "</td>" +
                    "<td>" + items.getRegionCountry() + "," + items.getRegionProvince() + "," + items.getRegionCity() + "</td>" +
                    "<td>" + items.getJoinTime() + "</td>" +
                    "<td><input type=\"text\" placeholder=\"property\" readonly /></td>" +
                    "<td>credit</td>" +
                    "<td>value</td>" +
                    "<td>vip</td>";
            sb.append(temp).append(bodyTail);
        }
        return sb.toString();
    }

    private static String rankingManager(Items[] movie) {
        String head = "<div><table class=\"pure-table\" id=\"rankTable\"><thead><tr><td>排名</td><td>电影ID</td><td>电影名（英文名）</td><td style=\"width: 60px;\">methods</td></tr></thead>";
        String body = rankingManagerBodyBuilder(movie);
        String tail = "</table></div>";
        return head + body + tail;
    }

    private static String rankingManagerBodyBuilder(Items[] movie) {
        StringBuilder sb = new StringBuilder();
        String bodyTail = "<td><div style=\"width: 190px;text-align: center;display: inline-table;\"><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;\" onclick=\"deleteIt(this)\">删除</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;\" onclick=\"alter(this)\">修改</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;padding: 5px;\" onclick=\"confirm(this)\">确认</a>\n" +
                "<a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;padding: 5px;\" onclick=\"higher(this)\">上调</a>" +
                "<a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;\" onclick=\"lower(this)\">下移</a></div></td></tr>";
        int i = 1;
        for (Items items : movie) {
            String temp = "<tr id=\"Ranking_" + items.getID() + "\"><div>" +
                    "<td>" + (i++) + "</td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getID() + "\" readonly /></td>" +
                    "<td>" + items.getItemName() + "(" + items.getEngName() + ")</td></div>";
            sb.append(temp).append(bodyTail);
        }
        return sb.toString();
    }

    private static String cinemaManager(Theaters[] theaters) {
        String head = "<div><table class=\"pure-table\" id=\"cinemaTable\"><thead><tr><td>影城ID</td><td>状态</td><td>影城名</td><td>联系方式</td><td>介绍</td><td>地址</td><td>放映室</td><td>methods</td></tr></thead>";
        String body = cinemaManagerBodyBuilder(theaters);
        String tail = "</table></div>";
        return head + body + tail;
    }

    private static String cinemaManagerBodyBuilder(Theaters[] theaters) {
        StringBuilder sb = new StringBuilder();
        String bodyTail = "<td><div style=\"width: 100px;text-align: center\"><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\" onclick=\"deleteIt(this)\">删除</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\" onclick=\"alter(this)\">修改</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;\" onclick=\"confirm(this)\">确认</a></div></td></tr>";
        for (Theaters items : theaters) {
            String temp = "<tr id=\"Cinema_" + items.getID() + "\"><div>" +
                    "<td>" + items.getID() + "</td>\n" +
                    "<td><input style=\"width: 50px\" type=\"text\" placeholder=\"" + items.getState() + "\" readonly /></td>\n" +
                    "<td><input type=\"text\" placeholder=\"" + items.getTheaterName() + "\" readonly /></td>\n" +
                    "<td><input type=\"text\" placeholder=\"" + items.getPhone() + "\" readonly /></td>\n" +
                    "<td><input type=\"text\" placeholder=\"" + items.getIntroduction() + "\" readonly /></td>\n" +
                    "<td><input type=\"text\" placeholder=\"" + items.getState() + "\" readonly /></td>\n" +
                    "<td><input type=\"text\" placeholder=\"" + items.getShowrooms() + "\" readonly /></td></div>";
            sb.append(temp).append(bodyTail);
        }
        return sb.toString();
    }

    private static String moviesManager(Items[] movie) {
        String head = "<div><table class=\"pure-table\" id=\"movieTable\" style=\"display: inline-block;\"><thead><tr><td>电影ID</td><td>状态</td><td>电影名（英文名）</td><td>关键词</td><td>图片</td><td>类别</td><td>国家</td><td>放映时长</td><td>上映时间</td><td>评分</td><td>票房</td><td>介绍</td><td>声音技术</td><td>画面技术</td><td>观看渠道</td><td>获奖</td><td>methods</td></tr></thead>";
        String body = moviesManagerBodyBuilder(movie);
        String tail = "</table></div>";
        return head + body + tail;
    }

    private static String moviesManagerBodyBuilder(Items[] movie) {
        StringBuilder sb = new StringBuilder();
        String bodyTail = "<td><div style=\"width: 100px;text-align: center\"><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;\" onclick=\"deleteIt(this)\">删除</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;\" onclick=\"alter(this)\">修改</a><a href=\"#\" style=\"color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;\" onclick=\"confirm(this)\">确认</a></div></td></tr>";
        for (Items items : movie) {
            String temp = "<tr id=\"Movie_" + items.getID() + "\"><div>" +
                    "<td>" + items.getID() + "</td>" +
                    "<td><input style=\"width: 50px\" type=\"text\" placeholder=\"" + items.getState() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getItemName() + "(" + items.getEngName() + ")\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getKeywords() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getPics() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getGenreID() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getCountry() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getRuntime() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getReleaseDate() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getRate() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getBoxOffice() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getIntroduction() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getSoundMix() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getTech() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getWhereToWatch() + "\" readonly /></td>" +
                    "<td><input type=\"text\" placeholder=\"" + items.getAward() + "\" readonly /></td></div>";
            sb.append(temp).append(bodyTail);
        }
        return sb.toString();
    }

    private static String title() {
        return "<div style=\"float: left;width: 77%;padding-left: 10px; margin-right: 3%\"><dl style=\"width: 100%;font-size:24px;background: #222222;text-align: center;color: white;border-radius:5px \"><div class=\"div__title\" style=\"text-align: left;padding-bottom: 15px\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"\" class=\"titleBar\" id=\"movieO\">电影管理</b><b style=\"display: none;\" class=\"titleBar\" id=\"cinemaO\">影城管理</b><b style=\"display: none;\" class=\"titleBar\" id=\"NewsO\">NEWS管理</b><b style=\"display: none;\" class=\"titleBar\" id=\"rankO\">榜单管理</b><b style=\"display: none;\" class=\"titleBar\" id=\"usersO\">人员管理<nobr class=\"subTitle\" style=\"font-size: large;display: none;\" id=\"cusTitle\">客户管理</nobr><nobr class=\"subTitle\" style=\"font-size: large;display: none;\" id=\"staffTitle\">工作人员管理</nobr></b></div></dl></div>";
    }

    private static String leftBar(String table) {
        return "<dl style=\"width: 100%;font-size:24px;background: #222222;margin-left: 20px;text-align: center;color: white;border-radius:5px \"><div class=\"div__title\" style=\"text-align: left;padding-bottom: 15px\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">个人中心</b></div><form id=\"leftBarForm\" class=\"leftBarForm\"><div style=\"padding: 10px;cursor: pointer\" id=\"movieDiv\">" +
                "<input type=\"radio\" name=\"nav\" id=\"movieRadio\" value=\"movieRadio\" style=\"display: none;\" " + (table.equals("movieTable") || table.equals("") ? "checked" : "") + " /><dt>" +
                "<label for=\"movieRadio\" style=\"cursor: pointer\"><nobr style=\"font-weight: bold\">电影</nobr>管理</label></dt></div><div style=\"padding: 10px;cursor: pointer\" id=\"cinemaDiv\">" +
                "<input type=\"radio\" name=\"nav\" id=\"cinemaRadio\" value=\"cinemaRadio\" style=\"display:none;\" " + (table.equals("cinemaTable") ? "checked" : "") + " /><dt>" +
                "<label for=\"cinemaRadio\" style=\"cursor: pointer\"><nobr style=\"font-weight: bold\">影城</nobr>管理</label></dt></div><div style=\"padding: 10px;cursor: pointer\" id=\"NewsDiv\">" +
                "<input type=\"radio\" name=\"nav\" id=\"NewsRadio\" value=\"NewsRadio\" style=\"display: none;\" " + (table.equals("NewsTable") ? "checked" : "") + "/><dt>" +
                "<label for=\"NewsRadio\" style=\"cursor: pointer\"><nobr style=\"font-style: italic;font-weight: bold\">NEWS</nobr>管理</label></dt></div><div style=\"padding: 10px;cursor: pointer\" id=\"rankDiv\">" +
                "<input type=\"radio\" name=\"nav\" id=\"rankRadio\" value=\"rankRadio\" style=\"display: none;\" " + (table.equals("rankTable") ? "checked" : "") + "/><dt>" +
                "<label for=\"rankRadio\" style=\"cursor: pointer\"><nobr style=\"font-weight: bold\">榜单</nobr>管理</label></dt></div><div style=\"padding: 10px;\" class=\"pplOrg\" id=\"userDiv\">" +
                "<input type=\"radio\" name=\"nav\" id=\"userRadio\" value=\"userRadio\" style=\"display: none;\" " + (table.equals("cusTable") || table.equals("staffTable") ? "checked" : "") + "/><dt>人员管理" +
                "<label for=\"userRadio\" style=\"cursor: pointer\"><dl class=\"subTable\">" +
                "<dt onclick=\"sendInfo(this)\" class=\"subTableBar\" id=\"cusRadio\">客户管理</dt>" +
                "<dt onclick=\"sendInfo(this)\" class=\"subTableBar\" id=\"staffRadio\">工作人员管理</dt></dl></label></dt>";
    }


    //Ranking
    public static String rankingContentMaker(String title) {
        return rankingContentMaker(title, null);
    }

    public static String rankingContentMaker(String title, String login) {
        String head = contentHead(title, defaultCSS, defaultICO);
        String nav = contentNav(login);
        String rankStr = rankStrPageMaker(GetItem.rankInfo());
        return head + "<div class=\"app\">" + nav + "<div class=\"leftIframe\"style=\"width: 80%;height: auto\"><div class=\"rightIframe__div\"style=\"height: auto\"><div class=\"div__title\"><img height=\"12\"src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">今日排行</b></div><div style=\"padding: 15px 10px 10px 10px;cursor: pointer;\">"
                + rankStr + "</div>" + footer();
    }

    //main
    public static String mainContentMaker(String title, String login) {
        String nav = contentNav(login);

        String head = contentHead(title, defaultCSS, defaultICO);
        String banner = contentBanner(GetItem.RecInfo());

        String nowHit = contentShowMovie(postRowMaker(GetItem.moviePoster(1), 1), 1);
        String comming = contentShowMovie(postRowMaker(GetItem.moviePoster(2), 1), 2);
        String rightIframe = rankStrMaker(GetItem.rankInfo());
        return head + "<div class=\"app\">" +
                nav + "<div style=\"display: flex; width: 100%;overflow: hidden;\">" +
                banner + "<div style=\"width: 100%;\">" +
                nowHit + comming + "<div class=\"rightIframe\">" +
                rightIframe + footer();
    }

    public static String mainContentMaker(String title) {
        return mainContentMaker(title, null);
    }

    //MovieInfo
    public static String movieInfoContentMaker(String title, String id) {
        return movieInfoContentMaker(title, id, null);
    }

    public static String movieInfoContentMaker(String title, String id, String login) {
        String head = contentHead(title, defaultCSS, defaultICO);
        Items items = GetItem.moviePoster(id);
        String pic = items.getPics();
        String video = "<iframe width=\"100%\" height=\"300px\" src=\"https://www.youtube.com/embed/-ezfi6FQ8Ds\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen=\"\"></iframe>";
        String nav = contentNav(login);
        String leftIframe = rankStrMaker(GetItem.rankInfo());
        return head + "<div class=\"app2\">" + nav + "<div style=\"height: 1300px; padding-top: 100px\">" +
                "<div class=\"leftIframe\">" + leftIframe +
                "<!-- ShowAPic -->\n" +
                showAPic(pic, video) +
                "<!--基本信息-->\n" +
                basicInfo(items) +
                "<!--流媒体观看-->\n" +
//                streamWatch() +
                "<!--详细信息-->\n" +
                moreInfo(items) +
                "<!--获奖-->\n" +
                awardInfo(items.getAward(), id) +
                "</div>" +
                footer()
                ;
    }

    //TheaterInfo
    public static String theaterInfoContentMaker(String title, String id, String movieID) {
        return theaterInfoContentMaker(title, id, movieID, null);
    }

    public static String theaterInfoContentMaker(String title, String id, String movieID, String login) {
        String head = contentHead(title, defaultCSS, defaultICO);
        Theaters items = GetItem.theatersPoster(id);
        String pic = items.getPic();
        String nav = contentNav(login);
        return head + "<div class=\"app2\">" + nav +
                "<div style=\"height: 1300px;margin-left: 6.7%\"><div class=\"blocks\" style=\"padding: 100px 10px 10px 70px\"><div style=\"background-image: url('" + pic + "');border-radius: 5px;width: 100%;background-position: center center;background-size: cover;height: 300px;\"></div></div>" +

                "<!--基本信息-->\n" +
                "<div class=\"blocks\" style=\"padding: 100px 10px 25px 0\"><div style=\"float: left;background: #222222;border-radius: 5px;width: 79%;height: 300px;\"><div style=\"display: inline-block;color:white;\"><h4 style=\"font-size: 30px; margin: 15px 0 15px 15px;\">" +
                items.getTheaterName() + "</h4><h5 style=\"margin: 15px;\">" +
                items.getSite() + "</h5><h5 style=\"margin: 15px;\">电话: " +
                items.getPhone() + "</h5><h4 style=\"font-size: 22px; margin: 15px 0 0 15px;\">影院介绍</h4><h5 style=\"margin: 5px 15px 15px 15px;width: 90%; height: auto;\">" +
                items.getIntroduction() + "</h5></div></div></div>" +

                movieSelectorMaker(id, movieID, items.getOnShowMovie()) +

                "</dl></div></div></div></div></div>" +
                footer();
    }

    private static String arrangeSelector(Arrange[] arranges) {
        if (arranges != null) {
            StringBuilder sb = new StringBuilder();
            for (Arrange movieArrange : arranges) {
                String temp = arrangeSelector(movieArrange);
                sb.append(temp);
            }
            return sb.toString();
        } else return "";
    }

    private static String arrangeSelector(Arrange arrange) {
        if (arrange != null) {
            int i = arrange.getState();
            String strF = "<dt class=\"row__poster row__posterLarge\"" +
                    "id=\"arrange_" + arrange.getID() + "\"";
            String strS = "style=\"background: " +
                    (i == 1 ? "#1488CC" : "#b3b3b3") + ";width: 184px;height: 106px;\">" +
                    (i == 1 ? ("<a href=\"ticket?arrangeID=" + arrange.getID()) + "\">" : "") + "<div class=\"row__poster__info\"style=\"width: 184px;height: 20px;margin: 0 0 5px 5px;color: white\">" +
                    "<div style=\"float: right;padding: 10px 20px 0 10px;font-size: 28px\">" +
                    (i == 1 ? "¥" + arrange.getPrice() : "售罄");
            String strT = "</div><b style=\"font-size: 24px\">" +
                    "14:00</b><p style=\"color: #111111\">" +
                    "15:00结束</p><b style=\"font-size: 20px\">" +
                    "国语3DIMAX</b><p style=\"color: #111111\">" +
                    "1号3DIMAX厅</p></div>" +
                    (i == 1 ? "</a>" : "") + "</dt>";
            return strF + strS + strT;
        } else return "";
    }

    private static String movieSelectorMaker(String id, String movieID, Arrange[] movieArranges) {
        if (movieID == null) movieID = movieArranges[0].getMovieID();
        return "<!-- 电影栏-->\n" +
                "<div class=\"blocks\" style=\"width: 100%;padding: 0 10px 15px 0;border-radius: 5px;\"><div class=\"showMovies\" style=\"width: 83%;background: #222222;\">\n" +
                "<div class=\"div__title\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">在线购票</b></div>" +
                "<dl class=\"row__posters\" id=\"movies\">" +
                movieSelector(id, movieID, reduceMovie(movieArranges)) + //基于影院
                "</dl>" +
                "<!-- 电影排片-->\n" +
                "<div style=\"background: #222222;padding-bottom: 15px\">\n" +
                "<div style=\"margin-left: 15px;width: 97%;color: #1488CC;background: #333333;\"><h1 class=\"row__poster__name\" style=\"padding: 10px 0 10px 10px;font-size: 30px;\"" +
                ">" + GetItem.get(movieID).getItemName() + "</h1>\n" +
                //时间选择
//                "<table style=\"margin-left: 20px\"><tr><td style=\"width: 10%;text-align: center;\">观影时间</td><td><ul class=\"bottom-flex\" style=\"padding: 15px\">" +
//                "<li><a href=\"" + setHref(id, movieID, "0") + "\" id=\"time_0\" class=\"link-search\" style=\"padding-top: 10px;" + (time.equals("0") ? "color: #1488CC" : "") + "\">今天（11月29日）</a></li>" +
//                "<li><a href=\"" + setHref(id, movieID, "1") + "\" id=\"time_1\" class=\"link-search\" style=\"padding: 10px;" + (time.equals("1") ? "color: #1488CC" : "") + "\">明天（11月30日）</a></li>" +
//                "<li><a href=\"" + setHref(id, movieID, "2") + "\" id=\"time_2\" class=\"link-search\" style=\"padding: 10px;" + (time.equals("2") ? "color: #1488CC" : "") + "\">后天（12月1日）</a></ul></td></tr></table>" +
                //场次选择
                "<dl class=\"row__posters\">" +
                arrangeSelector(reduceArrange(movieArranges, movieID)) //基于影院和影片
                ;
    }

    private static String movieSelector(String id, String movieId, Arrange[] movieArranges) {
        if (movieArranges != null) {
            StringBuilder sb = new StringBuilder();
            for (Arrange movieArrange : movieArranges) {
                String temp = movieSelector(id, movieId, movieArrange);
                sb.append(temp);
            }
            return sb.toString();
        } else return "";
    }


    private static String movieSelector(String id, String movieId, Arrange movieArrange) {
        int t = movieArrange.getMovieID().equals(movieId) ? 1 : 2;
        Items movie = GetItem.get(movieArrange.getMovieID());
        if (movie != null) {
            String strF = "<dt class=\"row__poster row__posterLarge\"";
            String strS = "";
            String strT = "id=\"movie_" + movie.getID() + "\">" +
                    "<a href=\"" + setHref(id, movie.getID()) + "\"><img style=\"border-radius: 5px;\" width=\"162px\"" +
                    "src=\"" + movie.getPics() + "\"/></a></dt>";
            if (t == 1) //selected ;
                strS = "style=\"height: min-content;transform: scale(1.03);border: 3px solid #1488CC\"";
            if (t == 2) //unselected
                strS = "style=\"height: min-content;\"";
            return strF + strS + strT;
        } else return "";
    }

    private static String setHref(String id, String movieID) {
        return "TInfo?id=" + id + "&movieID=" + movieID;
    }

    //TicketBooking
    public static String ticketContent(String title, String login, Bills bills) {
        String head = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>" + title + "</title><link rel=\"stylesheet\"href=\"/movieTicketBooking/cinema.css\"><script type=\"text/javascript\"src=\"https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js\"></script><script type=\"text/javascript\"src=\"/movieTicketBooking/jquery.seat-charts.min.js\"></script><meta name=\"viewport\"content=\"width=device-width, initial-scale=1.0\"/></head><body>";
        HashMap<String, String> map = getSupInfo(bills.getArrange().getID());
        String content =
                "<div class=\"seating\"><div id=\"seats\"><div class=\"screen\" id=\"" + bills.getBid() + "\">荧幕</div></div><div id=\"seat-map\"><ul class=\"showcase\"><li>" +
                        "<div class=\"seatCharts-seat seatCharts-cell\">X</div><small>不可用</small></li><li><div class=\"seatCharts-seat seatCharts-cell selected\"></div><small>当前选择</small></li><li><div class=\"seatCharts-seat seatCharts-cell unavailable\"></div><small>已售</small></li></ul></div>" +
                        "<div class=\"booking-details\">\n" +
                        "<div style=\"width: 200px\">\n" +
                        "<p>电影名：<span>" + map.get("name") + "</span></p>\n" +
                        "<p>影城名：<span>" + map.get("theaterName") + "</span></p>\n" +
                        "<p>时间：<span>" + map.get("time") + "</span></p>\n" +
                        "</div><p>座位：</p><ul id=\"selected-seats\"></ul><p>订票数：<span id=\"counter\">0</span></p><p>总价：<b>¥<span id=\"total\">0</span></b></p></div></div>" +
                        "<button class=\"checkout-button\" onclick=\"submitTics()\">购票</button>\n" +
                        "<script type=\"text/javascript\" src=\"/movieTicketBooking/cinema.js\"></script>\n" +
                        "<script>" + cinemaScript(bills) +
                        "</script>" +
                        "</body>" +
                        "</html>";
        return head + content;
    }

    static String cinemaScript(Bills bills) {
        Arrange arrange = bills.getArrange();
        double price = arrange.getPrice();
        String map = arrange.getSetMap(arrange.getRoom());
        String sold = arrange.getSold(arrange.getRoom());
        String str =
                "var price = " + price + ";" +
                        "$(document).ready(function () {\n" +
                        " var $cart = $('#selected-seats'), $counter = $('#counter'), $total = $('#total');" +
                        " var sc = $('#seat-map').seatCharts({map: " + map + "," +
                        "click: function () {" +
                        "if (this.status() == 'available') {$('<li>' + (this.settings.row + 1) + '排' + this.settings.label + '座</li>').attr('id', 'cart-item-' + this.settings.id).data('seatId', this.settings.id).appendTo($cart);$counter.text(sc.find('selected').length + 1);$total.text(Totalamount(sc) + price);return 'selected';} " +
                        "else if (this.status() == 'selected') {$counter.text(sc.find('selected').length - 1);$total.text(Totalamount(sc) - price);$('#cart-item-' + this.settings.id).remove();return 'available';} " +
                        "else if (this.status() == 'unavailable') {return 'unavailable';} " +
                        "else {return this.style();}}}); " +

                        "sc.get(" + sold + ").status('unavailable');" + "});";
        return str;
    }

    //payTheBill
    public static String moneyMaker(String title, Bills bills) {
        String head = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>" + title + "</title><link rel=\"stylesheet\" href=\"/movieTicketBooking/cinema.css\"><script type=\"text/javascript\" src=\"https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js\"></script><script type=\"text/javascript\" src=\"/movieTicketBooking/jquery.seat-charts.min.js\"></script><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/></head><body>";
        String main = "<div class=\"seating\"><div class=\"seatCharts-container\"><div style=\"width: 250px; height: 250px; background: #1488CC\">\n" +
                "二维码" + bills.getBid() + "</div></div><div class=\"booking-details\"><div style=\"height: 100px\">\n" +
                "<p>电影名：<span>" + bills.getMovie().getItemName() + "</span></p></div>\n" +
                "<p>订票数：<span id=\"counter\">" + bills.getCounter() + "</span></p>\n" +
                "<p>总价：<b>¥<span id=\"total\">" + bills.getTotal() + "</span></b></p></div></div>\n" +
                "<button class=\"checkout-button\" " +
                "onclick=\"location.href='/cinema/handle?bid=" + bills.getBid() + "'\">已完成付款</button><script type=\"text/javascript\" src=\"/movieTicketBooking/cinema.js\"></script></body></html>";
        return head + main;
    }

    //

    public static String billInfoMaker(String title, Bills bills) {
        return billInfoMaker(title, bills, null);
    }

    public static String billInfoMaker(String title, Bills bills, String login) {
        String head = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>" + title + "</title><link href=\"/main_page/styles.css\" rel=\"stylesheet\"/><link href=\"/movieTicketBooking/cinema.css\" rel=\"stylesheet\"/></head><body style=\"background: #111111\">";
        String nav = contentNav(login);
        Theaters theaters = GetItem.theatersPoster(bills.getArrange().getTheaterID());
        String ticket = (bills.getState() == 0 ? ("<div class=\"seating\" style=\"padding-top: 30px;height: 300px;\"><div class=\"seatCharts-container\" style=\"border: none;display: contents;\"><div style=\"width: 250px; height: 250px; background: #1488CC\">" +
                "二维码</div></div></div><button class=\"checkout-button\" " +
                "onclick=\"location.href='/cinema/handle?bid=" + bills.getBid() + "'\">已取票</button>") : (bills.getState() == 1 ? "" :
                "<div class=\"seating\" style=\"padding-top: 30px;height: 300px;\"><div class=\"seatCharts-container\" style=\"border: none;display: contents;\"><div style=\"width: 250px; height: 250px; background: #1488CC\">" +
                        "付款二维码" + bills.getBid() + "</div></div></div><button class=\"checkout-button\" " +
                        "onclick=\"location.href='/cinema/handle?bid=" + bills.getBid() + "'\">已完成付款</button>"));
        String main =
                "<div style=\"width: 100%;display: flex;justify-content: center;padding-top: 100px;\"><img style=\"display: inline-block;border-radius: 5px;padding-right: 1%;\" height=\"180px\" " +
                        "src=\"" + bills.getMovie().getPics() + "\"><div style=\"display: inline-block;background: #222222;border-radius: 5px;width: 70%;height: 180px;\">\n" +
                        "<div style=\"display: inline-block;vertical-align: middle;color:white;\">" +
                        "<h4 style=\"font-size: 30px; margin: 30px 5px 0 10px;\">" +
                        bills.getMovie().getItemName() + "</h4><h5 style=\"margin: 5px 0 0 10px;\"><nobr style=\"color: #1488CC\">" +
                        bills.getArrange().getStartTime() + "</nobr></h5><h5 style=\"margin-left: 10px;width: 250px; max-height: 38px;\">" +
                        "<p>" + theaters.getTheaterName() + "</p>\n" +
                        "<p>" + bills.getArrange().getRoom() + "号影厅   " + bills.getTicketStr("bill") +
                        "</p></h5></div><div style=\"display: inline-block;vertical-align: middle\"><div style=\"height: 20px;margin: 10px 10px 5px 250px;float: right;color:white;\"><b class=\"row__poster__info\" style=\"font-size: 20px;color: white;margin: 0 5px 5px 0;\">" +
                        "¥" + bills.getTotal() + "</b></div></div><div style=\"display: inline-block;vertical-align: middle\"><div style=\"height: 20px;margin: 10px 10px 5px 250px;float: right;color:white;\"><b class=\"row__poster__info\" style=\"margin: 0 5px 5px 0;font-size: 18px;color: white;\">" +
                        (bills.getState() == 0 ? "待取票" : (bills.getState() == 1 ? "已完成" : "待付款")) + "</b></div></div></div></div>" + ticket +
                        "</body></html>";
        return head + nav + main;
    }

    //divMaker
    //我的订单
    //bill
    private static String contentBill(Bills[] bills) {
        String str = "";
        if (bills == null || bills.length <= 0)
            return "<b style=\"color: white;display: flex;justify-content: space-around;background: #222222;border-radius: 5px;height: 40px;flex-wrap: wrap;align-content: center;\">暂无订单！</b>";
        for (int i = 0; i < bills.length; i++) {
            Items movie = bills[i].getMovie();
            Arrange arrange = bills[i].getArrange();
            String temp = "<img style=\"margin-bottom: 10px;float: left;border-radius: 5px;padding-right: 1%\" height=\"180px\" src=\"" +
                    movie.getPics() + "\"><div style=\"margin-bottom: 10px;float: left;background: #222222;border-radius: 5px;width: 87.2%;height: 180px;\"><div style=\"display: inline-block;vertical-align: middle;color:white;\"><h4 style=\"font-size: 30px; margin: 30px 5px 0 10px;\">" +
                    movie.getItemName() + "</h4><h5 style=\"margin: 5px 0 0 10px;\"><nobr style=\"color: #1488CC\">" +
                    arrange.getStartTime() + "</nobr></h5><h5 style=\"margin-left: 10px;width: 250px; max-height: 38px;\"><p>" +
                    arrange.getTheaterID() + "</p><p>" +
                    bills[i].getTicketStr("bill") + "</p></h5></div><div style=\"display: inline-block;vertical-align: middle\"><div style=\"height: 20px;margin: 10px 10px 5px 250px;float: right;color:white;\"><b class=\"row__poster__info\" style=\"font-size: 20px;color: white;margin: 0 5px 5px 0;\"> ¥" +
                    bills[i].getTotal() + "</b></div></div><div style=\"display: inline-block;vertical-align: middle\"><div style=\"height: 20px;margin: 10px 10px 5px 250px;float: right;color:white;\"><b class=\"row__poster__info\" style=\"margin: 0 5px 5px 0;\">" +
                    "<a href=\"billInfo?bid=" + bills[i].getBid() + "\"style=\"text-decoration: none;font-size: 18px;color: white;\">" +
                    (bills[i].getState() == 0 ? "待取票" : (bills[i].getState() == 1 ? "已完成" : "待付款")) + "</a></b></div></div></div>";
            str = str.concat(temp);
        }
        return str;
    }

    //Theaters&Movies
    //Theaters-城市选择
    static String contentChoose() {
        return "<div class=\"showMovies\"style=\"width: 90%;margin: 100px 70px 15px 70px;height: auto;color: #1488CC\"><form><div class=\"div__title\"><img height=\"12\"src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">选择城市</b></div><div class=\"input-text\"style=\"display: flex;flex-direction: row;flex-wrap: nowrap;align-content: center;justify-content: center;\"><select id=\"prov\"name=\"regionProvince\"style=\"width: 20%;margin-right: 2%;\"></select><select id=\"city\"name=\"regionCity\"style=\"width: 20%;margin-right: 2%;\"></select><select id=\"site\"name=\"regionSite\"style=\"width: 20%\"></select><script src=\"/login_page/CustomerLoginPage/location.js\"></script></div><div style=\"width: 50%;margin: 10px 0 10px 25%\"><button class=\"con-button\"id=\"signup2\"type=\"submit\">查询</button></div></form></div>";
    }

    //Movies-类型选择
    static String contentChoose(String[] type, String[] rigion, String... choose) {
        return "<div class=\"showMovies\"style=\"width: 90%;margin: 100px 70px 15px 70px;height: auto;color: #1488CC\"><div class=\"div__title\"><img height=\"12\"src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">选择分类</b></div><div><table style=\"width: -webkit-fill-available;margin: 10px;\"><tr><td style=\"width: 5%;text-align: center\">" +
                "类型</td><td><ul class=\"bottom-flex\" style=\"margin: 0 0 10px 10px;\";>" +
                chooseStr(type, choose) + "</ul></td></tr><tr><td style=\"width: 5%;text-align: center\">" +
                "区域</td><td><ul class=\"bottom-flex\" style=\"margin: 0 0 10px 10px;\">" +
                chooseStr(rigion) + "</ul></td></tr></table></div></div>";
    }

    //Movies-类型s
    static String chooseStr(String[] choices, String... choose) {
        String str = "";
        for (int i = 0; i < choices.length; i++) {
            String temp = "<li><a href=\"?choose=" + i + "\"class=\"link-search\"style=\"padding: 10px" +
                    ((choose.length > 0 && !choose[0].equals("") && Integer.parseInt(choose[0]) == i) ? ";color: #1488CC " : "") + "\">" +
                    choices[i] + "</a></li>";
            str = str.concat(temp);
        }
        return str;
    }

    //Theaters/Movies--ShowMovie
    static String contentShowMovie(String str) {
        return "<div class=\"showMovies\"style=\"width: 90%;margin: 30px 70px 60px 70px;height: auto\"><dl class=\"row__posters\"style=\"\">" +
                str + "</dl></div>";
    }

    //avator
    public static String loginMaker(boolean loginSuc, String avator) {
        return !loginSuc ? "<a class=\"nav__bar__link\" href=\"/cinema\" style=\"position: absolute;top: 10px;right:0px;width: 55px\"id=\"login\"><b>[登录]</b></a>" :
                "<a href=\"" + navs[5] + "\"><img class=\"nav__avatar\" id=\"myAvator\" alt=\"avator\"" +
                        "src=\"" + avator + "\"></a>";
    }

    public static String loginMaker(boolean loginSuc, String avator, int t) {
        return !loginSuc ? "<a class=\"nav__bar__link\" href=\"/cinema\" style=\"position: absolute;top: 10px;right:0px;width: 55px\"id=\"login\"><b>[登录]</b></a>" :
                "<a href=\"#\"><img class=\"nav__avatar\" id=\"myAvator\" alt=\"avator\"" +
                        "src=\"" + avator + "\"></a>";
    }

    //Head
    static String contentHead(String title, String css, String ico) {
        return "<!DOCTYPE html><html><head><meta charset=\"utf-8\"/>" +
                "<title>" + title + "</title>" +
                "<link href=\"" + css + "\" rel=\"stylesheet\"/>" +
                "<link href=\"" + ico + "\" type=\"images/x-icon\" rel=\"icon\"></head><body style=\"background: #111111\">";
    }

    //footer
    static String footer() {
        return "<div class=\"bottom\"><div class=\"bottom-width\">有疑问请联系<a href=\"tel:1-844-542-4813\"class=\"tel-link\">123-1234-1234</a><div><ul class=\"bottom-flex\"><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">常见问题</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">客服中心</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">使用条款</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">隐私政策</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">关于我们</a></li></ul></div></div></div><script src=\"http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js\"></script>" +
                "<script>$(function(){var oTop1=$(document).scrollTop();$(window).scroll(function(){var oTop2=$(document).scrollTop();console.log(oTop2);if(oTop2>oTop1){$(\".nav\").addClass(\"hide\").removeClass(\"show\")}else{$(\".nav\").addClass(\"show\").removeClass(\"hide\")}oTop1=$(document).scrollTop()})});function UploadImg(obj){var file=obj.files[0];var reader=new FileReader();reader.readAsDataURL(file);reader.onload=function(e){var img=document.getElementById(\"myAvatorinput\");img.src=e.target.result}}$('input[type=radio][name=\"nav\"]').change(function(){var myvalue=$('input:radio[name=\"nav\"]:checked').val();var choose;var table;do{if(myvalue==\"billRadio\"){choose=document.getElementById(\"billO\");table=document.getElementById(\"billTable\")}if(myvalue==\"infoRadio\"){choose=document.getElementById(\"infoO\");table=document.getElementById(\"infoTable\")}}while(false);$('.table_content').css('display','none');table.style.display=\"inline-block\";$('.titleBar').css('display','none');choose.style.display=\"inline-block\"});</script></body></html>";
    }

    static String footer(String script) {
        return "<div class=\"bottom\"><div class=\"bottom-width\">有疑问请联系<a href=\"tel:1-844-542-4813\"class=\"tel-link\">123-1234-1234</a><div><ul class=\"bottom-flex\"><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">常见问题</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">客服中心</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">使用条款</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">隐私政策</a></li><li class=\"list-bottom\"><a href=\"#\"class=\"link-bottom\">关于我们</a></li></ul></div></div></div><script src=\"http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js\"></script>" +
                script;
    }

    static String script() {
        return "<script src=\"/CnmPersonalInfo/cinemaPerson.js\"></script>";
    }

    //navMaker
    public static String navMaker(String login, int t) {
        if (t == 1) return contentNav(login, navs);
        else return contentNav(login, navs, t);
    }

    static String contentNav(String login) {
        String str;
        if (login != null) {
            navStr = contentNav(login, navs);
            str = navStr;
        } else if (navStr != null) str = navStr;
        else str = contentNav(loginMaker(false, null), navs);
        return str;
    }

    static String contentNav(String login, String[] nav) {
        String fp = "<div class=\"nav\"><div class=\"nav__bar\">" +
                "<div class=\"nav__bar__tab\">" +
                "<a href=\"" + nav[1] + "\" class=\"nav__bar__link\"><b>电影</b></a></div>" +
                "<div class=\"nav__bar__tab\">" +
                "<a href=\"" + nav[2] + "\" class=\"nav__bar__link\"><b>影城</b></a></div>" +
                "<div class=\"nav__bar__tab\">" +
                "<a href=\"" + nav[3] + "\" class=\"nav__bar__link\"><b>榜单</b></a></div>" +
                "<div class=\"nav__bar__tab\">\n" +
                "<a href=\"" + nav[4] + "\" style=\"font-style: italic\" class=\"nav__bar__link\"><b>NEWS</b></a></div></div>";
        return fp + "<a href=\"" + nav[0] + "\"><img class=\"nav__logo\" " +
                "src=\"https://i.bmp.ovh/imgs/2021/11/12329def7ff8e6aa.png\" alt=\"mainPage\"/></a>" +
                "<div><div class=\"img-avator\">" +
                login + "</div></div></div>";
    }

    static String contentNav(String login, String[] nav, int t) {
        String fp = "<div class=\"nav\"><div class=\"nav__bar\">";
        if (t == 1) fp +=
                "<div class=\"nav__bar__tab\">" +
                        "<a href=\"" + nav[1] + "\" class=\"nav__bar__link\"><b>电影</b></a></div>" +
                        "<div class=\"nav__bar__tab\">" +
                        "<a href=\"" + nav[2] + "\" class=\"nav__bar__link\"><b>影城</b></a></div>" +
                        "<div class=\"nav__bar__tab\">" +
                        "<a href=\"" + nav[3] + "\" class=\"nav__bar__link\"><b>榜单</b></a></div>" +
                        "<div class=\"nav__bar__tab\">\n" +
                        "<a href=\"" + nav[4] + "\" style=\"font-style: italic\" class=\"nav__bar__link\"><b>NEWS</b></a></div>";
        return fp + "</div><a href=\"#\"><img class=\"nav__logo\" " +
                "src=\"https://i.bmp.ovh/imgs/2021/11/12329def7ff8e6aa.png\" alt=\"mainPage\"/></a>" +
                "<div><div class=\"img-avator\">" +
                login + "</div></div></div>";
    }

    //rankMaker
    public static String rankStrMaker(Items[] rankItems) {
        String viewAll = navs[3];
        String rankFirStr = "", rankStr = "";
        for (int i = 0; i < Math.min(rankItems.length, 10); i++) {
            HashMap<String, String> map = ItemInfoParse.parse(rankItems[i].toString());
            String temp = "";
            if (i == 0) rankFirStr =
                    "<div style=\"display: inline-flex\" onclick=\"location.href='/cinema/info?id=" +
                            map.get("ID") + "'\"><img style=\"display:inline-block;border-radius: 5px;padding: 60px 5px 0 0\"width=\"30px\" height=\"30px\"\n" +
                            "src=\"/imgs/icon.png\"><img style=\"display:inline-block; border-radius: 5px;\" height=\"150px\"\n" +
                            "src=\"" + map.get("pics") + "\"><div style=\"display: inline-block;color:white;\">\n" +
                            "<div class=\"row__poster__info\" style=\"height: 20px;margin: 0 0 5px 5px;\">\n" +
                            "<img class=\"row__poster__info\" width=\"25px\" height=\"25px\" src=\"/imgs/icon.png\">\n" +
                            "<b class=\"row__poster__info\" style=\"font-size: 20px;color: white;margin: 0 0 5px 5px;\">"
                            + (map.get("rate").equals("-1.0") ? "<nobr style=\"font-size: 16px\">暂无评分</nobr>" : map.get("rate")) + "</b></div><h4 style=\"font-size: 30px; margin: 5px 0 0 5px;\">"
                            + map.get("itemName") + "</h4><h5 style=\"margin: 5px 0 0 5px;\">"
                            + map.get("genreID") + "</h5><h5 style=\"margin: 5px 0 0 5px;width: 256.55px; height: 38px;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2;\">"
                            + map.get("introduction") + "</h5></div></div>";
            else
                temp = "<tr onclick=\"location.href='/cinema/info?id=" +
                        map.get("ID") + "'\" style=\"height:50px\"><td style=\"padding: 5px\"><img width=\"25px\"src=\"/imgs/icon.png\"></td><td style=\"width: 175px;padding: 5px\">" +
                        map.get("genreID") + "</td><td style=\"padding: 5px\">" +
                        map.get("itemName") + "</td></tr>";
            rankStr = rankStr.concat(temp);
        }
        return iframeMaker(rankFirStr, rankStr, viewAll);
    }

    //frame
    public static String iframeMaker(String rankFirStr, String rankStr, String viewAll) {
        return "<div class=\"rightIframe__div\"><div class=\"div__title\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: xx-large;padding-left: 15px\">今日排行</b></div>" +
                "<div style=\"padding: 15px 10px 10px 10px;cursor: pointer\"> " + rankFirStr +
                "<table>" + rankStr + "</table><div style=\"margin-top: 10px;text-align: center\"><a style=\"text-decoration: none;;color: #b3b3b3;\"" +
                "href=\"" + viewAll + "\">查看全部</a></div></div></div></div>";
        //<div class="rightIframe"> leftIframe
    }

    //Banner
    static String contentBanner(String[] recInfo) {
        return "<div class=\"banner\" " +
                "style=\"background-image: url('" + recInfo[3] + "');\" ><div class=\"banner__contents\">" +
                "<h1 class=\"banner__title\">" + recInfo[1] + "</h1><div class=\"banner__buttons\"><button class=\"banner__button\" " +
                "onclick=\"location.href='/cinema/chooseCinema?id=" + recInfo[0] + "'\">购票</button><button class=\"banner__button\" " +
                "onclick=\"location.href='/cinema/info?id=" + recInfo[0] + "'\">查看详情</button></div>" +
                "<h1 class=\"banner__description\">" + recInfo[2] + "</h1></div>" +
                "<div class=\"banner--fadeBottom\"></div></div>" +
                "<div class=\"secbanner\"" +
                "style=\"background-image: url('" + recInfo[6] + "');\"" +
                "onclick=\"location.href='/cinema/info?id=" + recInfo[4] + "'\"><div class=\"banner__contents\"></div><div class=\"banner--fadeBottom\"></div></div>" +
                "<div class=\"secbanner\"" +
                "style=\"background-image: url('" + recInfo[9] + "');\"" +
                "onclick=\"location.href='/cinema/info?id=" + recInfo[7] + "'\"><div class=\"banner__contents\"></div><div class=\"banner--fadeBottom\"></div></div></div>";
    }

    //ShowMovie(nowHitStr CommingStr)
    static String contentShowMovie(String str, int type) {
        return "<div class=\"showMovies\"><div class=\"div__title\"><img height=\"12\" src=\"/imgs/decorate.svg\"> <b style=\"font-size: xx-large;padding-left: 15px\">" +
                (type == 1 ? "正在热映" : "即将上映") + "</b></div><dl class=\"row__posters\">"
                + str + "</dl></div>";
    }

    //nowHitStr CommingStr
    public static String postRowMaker(Object[] newItems, int t) {
        String str = "";
        for (int i = 0; i < newItems.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            if (t == 1) map = ItemInfoParse.parse(((Items) newItems[i]).toString());
            if (t == 2) map = ItemInfoParse.parse(((Theaters) newItems[i]).toString());
            String temp =
                    "<dt class=\"row__poster row__posterLarge\" style=\"" +
                            (t == 2 ? "width: 186px;" : "") + "\">\n" +
                            (t == 2 ? "<a href=\"/cinema/TInfo?id=" + map.get("ID") : "<a href=\"/cinema/info?id=" + map.get("ID")) + "\">\n" +
                            "<img style=\"border-radius: 5px;\" " +
                            (t == 2 ? "width=\"186px\" height=\"105px\"" : "width=\"162px\" height=\"243px\"") +
                            "src=\"" + map.get("pics") + "\"\n" +
                            "alt=\"" + map.get("itemName") + "\"/>\n" +
                            "</a>\n" +
                            "<div class=\"row__poster__info\" style=\"height: 20px;margin: 0 0 5px 5px;\">\n" +
                            (t == 2 ? "" : ("<img class=\"row__poster__info\" width=\"25px\" height=\"25px\" src=\"/imgs/icon.png\">\n" +
                                    "<b class=\"row__poster__info\" style=\"font-size: 20px;color: white;margin: 0 0 5px 5px;\">" + (map.get("rate").equals("-1.0") ? "<nobr style=\"font-size: 16px\">暂无评分</nobr>" : map.get("rate")) + "</b>")) +
                            "</div>\n" +
                            "<h1 class=\"row__poster__name\"\n" +
                            ">" + map.get("itemName") + "</h1>\n" +
                            "</dt>\n";
            str = str.concat(temp);
        }
        return str;
    }

    //rankingPage
    public static String rankStrPageMaker(Items[] rankItems) {
        String rankStr = "";
        for (int i = 0; i < rankItems.length; i++) {
            HashMap<String, String> map = ItemInfoParse.parse(rankItems[i].toString());
            String temp = "<div style=\"display: inline-flex;padding: 25px;width: 100%\"><img style=\"display:inline-block;border-radius: 5px;padding: 60px 5px 0 0\"width=\"30px\" height=\"30px\"src=\"/imgs/icon.png\"><img style=\"float: left;border-radius: 5px;padding-right: 1%\" height=\"180px\"" +
                    "src=\"" + map.get("pics") + "\"><div style=\"float: left;background: #222222;border-radius: 5px;width: 79%;height: 180px;\"><div style=\"width: 100%;display: inline-block;color:white;\"><h4 style=\"font-size: 30px; margin: 5px 5px 0 5px;\">" +
                    map.get("itemName") + "<nobr style=\"margin:10px;font-size: 15px;\">" +
                    (map.get("engName") == null ? "" : map.get("engName")) + "</nobr>" +
                    "<div style=\"height: 20px;margin: 10px 10px 5px 5px;float: right;\"><img class=\"row__poster__info\" width=\"25px\" height=\"25px\" src=\"/imgs/icon.png\">" +
                    "<b class=\"row__poster__info\"\n" +
                    "   style=\"font-size: 28px;color: white;margin: 0 5px 5px 0;\">"
                    + (map.get("rate").equals("-1.0") ? "<nobr style=\"font-size: 16px\">暂无评分</nobr>" : map.get("rate")) + "</b>" +
                    "<nobr style=\"font-size: 18px;color: #1488CC\">票房</nobr>" +
                    "<b class=\"row__poster__info\"tyle=\"font-size: 28px;color: white;margin: 0 0 5px 0;\">NA</b><nobr style=\"font-size: 18px\">亿</nobr>" +
                    "</div></h4><h5 style=\"margin: 5px 0 0 5px;\">" +
                    map.get("genreID") +
                    "<nobr style=\"margin-left: 10px;\">" + map.get("releaseDate") + "上映</nobr></h5><h5 style=\"margin: 5px 0 0 5px;width: 90%; height: 38px;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2;\">" +
                    map.get("introduction") + "</h5></div><div class=\"banner__buttons\" style=\"padding: 10px 0 0 30px\">" +
                    "<button class=\"banner__button\" onclick=\"location.href='chooseCinema?id=" + map.get("ID") + "'\">在线购票</button>" +
                    "<button class=\"banner__button\" onclick=\"location.href='info?id=" + map.get("ID") + "'\">想看</button>" +
                    "<button class=\"banner__button\" onclick=\"location.href='info?id=" + map.get("ID") + "'\">评分</button></div></div></div>";
            rankStr = rankStr.concat(temp);
        }
        return rankStr;
    }

    //login
    public static String login(Cookie[] cookies) {
        String userInfo = "";
        for (Cookie cookie : cookies) {
            if ((cookie.getName()).equals("userInfo")) {
                userInfo = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                break;
            }
        }
        HashMap<String, String> infoMap = UserInfoParse.parse(userInfo);
        boolean loginSuc = infoMap.containsKey("UID") & infoMap.get("UID") != null;
        return loginMaker(loginSuc, infoMap.get("avator"));
    }

    //MovieInfo
    private static String awardInfo(String aid, String id) {
        StringBuilder sb = new StringBuilder();
        if (aid != null && !aid.equals("")) {
            String[] awards = aid.split(" ;|; ");
            sb.append("<div class=\"blocks\" style=\"border-radius: 5px;background: #222222\"><div style=\"height: auto;border-radius: 5px;padding-top: 10px\"><div class=\"div__title\" style=\"padding-top: 0\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: x-large;padding-left: 15px\">奖项</b></div><div>");

            sb.append("<table style=\"float: left;color:white;margin: 0 150px 10px 20px;text-align: left;\">");
            for (int j = 0; j < awards.length; j += 2) {
                sb.append("<tr style=\"height: 40px;\"><td style=\"text-align: left;color: #1488CC;font-weight: bolder\">").append("<img style=\"display:inline-block;vertical-align:middle;padding-right: 10px\"src=\"")
                        .append("/imgs/decorate.ico\">奖项名奖项名</td></tr><tr style=\"height: 40px;display: flex;\"><td>")
                        .append("获奖：获奖情况</td></tr>");
            }
            sb.append("</table>");

            sb.append("<table style=\"float: left;color:white;margin: 0 150px 10px 20px;text-align: left;\">");
            for (int j = 1; j < awards.length; j += 2) {
                sb.append("<tr style=\"height: 40px;\"><td style=\"text-align: left;color: #1488CC;font-weight: bolder\">").append("<img style=\"display:inline-block;vertical-align:middle;padding-right: 10px\"src=\"")
                        .append("/imgs/decorate.ico\">奖项名奖项名</td></tr><tr style=\"height: 40px;display: flex;\"><td>")
                        .append("获奖：获奖情况</td></tr>");
            }
            sb.append("</table>");

            sb.append("</div></div></div>");
        }
        return sb.toString();
    }

    private static String moreInfo(Items items) {
        Casts casts = items.getCast();
        return
                "<div class=\"blocks\" style=\"border-radius: 5px;background: #222222\"><div style=\"height: auto;border-radius: 5px;padding-top: 10px\"><div class=\"div__title\" style=\"padding-top: 0\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: x-large;padding-left: 15px\">详细信息</b></div><div style=\"text-align: center\"><table style=\"color:white;margin:0 auto;margin-bottom: 15px;\">" +
                        (items.getGenreID() == null ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">类型</div></td>" +
                                "<td>" + items.getGenreID() + "</td></tr>")) +

                        (items.getCountry() == 0 ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">语言</div></td>" +
                                "<td>" + items.getCountry() + "</td></tr>")) +
                        (casts == null ? "" :
                                (casts.getDirector() == null ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                        "<div style=\"padding: 3px 25px 0 0\">导演</div></td>" +
                                        "<td>" + casts.getDirector() + "</td></tr>")) +

                                        (casts.getProducer() == null ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                                "<div style=\"padding: 3px 25px 0 0\">制作人</div></td>" +
                                                "<td>" + casts.getProducer() + "</td></tr>")) +

                                        (casts.getWriter() == null ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                                "<div style=\"padding: 3px 25px 0 0\">剧作家</div></td>" +
                                                "<td>" + casts.getWriter() + "</td></tr>"))) +

                        (items.getReleaseDate() == null ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">上映时间</div></td>" +
                                "<td>" + items.getReleaseDate() + "</td></tr>")) +

                        (items.getBoxOffice() == 0 ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">票房</div></td>" +
                                "<td>" + items.getBoxOffice() + "</td></tr>")) +

                        (items.getRuntime() == null || items.getRuntime().equals("") ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">时长</div></td>" +
                                "<td>" + items.getRuntime() + "</td></tr>")) +

                        (items.getSoundMix() == null || items.getSoundMix().equals("") ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">声音技术</div></td>" +
                                "<td>" + items.getSoundMix() + "</td></tr><")) +

                        (items.getTech() == null || items.getTech().equals("") ? "" : ("<tr><td style=\"text-align: right;color: #1488CC;font-weight: bolder;\">" +
                                "<div style=\"padding: 3px 25px 0 0\">画面技术</div></td>" +
                                "<td>" + items.getTech() + "</td></tr>")) + "</table></div></div></div>";
    }

    private static String streamWatch() {
        return "<div class=\"blocks\"style=\"border-radius: 5px;background: #222222\"><div style=\"height: auto;border-radius: 5px;padding-top: 10px\"><div class=\"div__title\"style=\"padding-top: 0\"><img height=\"12\"src=\"/imgs/decorate.svg\"><b style=\"font-size: x-large;padding-left: 15px\">在线观看</b></div><dl class=\"row__posters\"style=\"padding: 20px 0 10px 0;\">" +
                "<dt class=\"row__poster row__posterLarge\"><a href=\"#\"><img style=\"border-radius: 5px;\"width=\"162px\"height=\"81px\"" +
                "src=\"https://image.tmdb.org/t/p/original//asDqvkE66EegtKJJXIRhBJPxscr.jpg\"/></a>" +
                "<h1 class=\"row__poster__name\"style=\"text-align: center;font-size: 15px;\">大会员免费看</h1></dt>" +
                "<dt class=\"row__poster row__posterLarge\"><a href=\"#\"><img style=\"border-radius: 5px;\"width=\"162px\"height=\"81px\"" +
                "src=\"https://image.tmdb.org/t/p/original//asDqvkE66EegtKJJXIRhBJPxscr.jpg\"/></a>" +
                "<h1 class=\"row__poster__name\"style=\"text-align: center;font-size: 15px;\">VIP免费看</h1></dt>" +
                "<dt class=\"row__poster row__posterLarge\"><a href=\"#\"><img style=\"border-radius: 5px;\"width=\"162px\"height=\"81px\"" +
                "src=\"https://image.tmdb.org/t/p/original//asDqvkE66EegtKJJXIRhBJPxscr.jpg\"/></a>" +
                "<h1 class=\"row__poster__name\"style=\"text-align: center;font-size: 15px;\">可租借/购买</h1></dt>" +
                "<dt class=\"row__poster row__posterLarge\"><a href=\"#\"><img style=\"border-radius: 5px;\"width=\"162px\"height=\"81px\"" +
                "src=\"https://image.tmdb.org/t/p/original//asDqvkE66EegtKJJXIRhBJPxscr.jpg\"/></a>" +
                "<h1 class=\"row__poster__name\"style=\"text-align: center;font-size: 15px;\">用户免费观看</h1></dt></dl></div></div>";
    }

    private static String basicInfo(Items items) {
        return "<div class=\"blocks\"><img style=\"float: left;border-radius: 5px;padding-right: 1%\";height=\"220px\"width=\"153.7px\"src=\"" +
                items.getPics() + "\"><div style=\"float: left;background: #222222;border-radius: 5px;width: 75.8%;height: 220px;\"><div style=\"display: inline-block;color:white;\"><h4 style=\"font-size: 30px; margin: 5px 5px 0 5px;\">\n" +
                items.getItemName() + "<nobr style=\"font-size: 15px;padding-left: 5px;\">" +
                items.getEngName() + "</nobr><div style=\"height: 20px;margin: 10px 10px 5px 5px;float: right;\"><img class=\"row__poster__info\"width=\"25px\"height=\"25px\"src=\"/imgs/icon.png\"><b class=\"row__poster__info\"style=\"font-size: 28px;color: white;margin: 0 5px 5px 0;\">" +
                items.getRate() + "</b><nobr style=\"font-size: 18px;color: #1488CC\">" +
                "票房</nobr><b class=\"row__poster__info\"style=\"font-size: 28px;color: white;margin: 0 0 5px 0;\">" +
                items.getBoxOffice() + "</b><nobr style=\"font-size: 18px\">亿</nobr></div></h4><h5 style=\"margin: 10px 0 0 5px;\">" +
                items.getGenreID() + "<nobr style=\"margin-left: 10px;\">" +
                items.getReleaseDate() + "上映</nobr></h5><h5 class=\"introduction\" style=\"margin: 10px 0 0 5px;-webkit-line-clamp: 3;height: 58px\">" +
                items.getIntroduction() + "</h5></div><div class=\"banner__buttons\"style=\"padding: 10px 0 0 30px\"><button class=\"banner__button\"onclick=\"location.href='/cinema/chooseCinema?id=" +
                items.getID() + "'\">在线购票</button><button class=\"banner__button\"onclick=\"location.href='#'\">想看</button><button class=\"banner__button\"onclick=\"location.href='#'\">评分</button></div></div></div>" +
                "<div class=\"blocks\" style=\"border-radius: 5px;background: #222222\"><div style=\"height: auto;border-radius: 5px;padding-top: 10px\"><div class=\"div__title\" style=\"padding-top: 0\"><img height=\"12\" src=\"/imgs/decorate.svg\"><b style=\"font-size: x-large;padding-left: 15px\">简介</b></div></div>" +
                "<h5 style=\"color: white;margin: 10px;\">" +
                items.getIntroduction() +
                "</h5></div>";
    }

    private static String showAPic(String pic, String video) {
        return "<div class=\"blocks\"><div style=\"background-image: url('"
                + pic + " ');\"class=\"showAPic\">"
                + video + "</div></div>";
    }
}
