package pageConstructor;

import Items.GetItem;
import Users.Users;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static Users.UserInfoParse.parse;
import static pageConstructor.ContentMaker.*;

public class LoginContent {
    public static void loginContent(Users users, PrintWriter out, HttpServletResponse response, int t) {
        if (users == null) {
            out.write("<script language='javascript'>alert('密码错误或用户不存在');window.location.href='/cinema'</script>");
        } else {
            Cookie loginUserInfo = new Cookie("userInfo", URLEncoder.encode(users.toString(), StandardCharsets.UTF_8));
            loginUserInfo.setMaxAge(60 * 60 * 24 * 7);

            String rankStr = "";
            if (t == 1) {
                rankStr = rankStrMaker(GetItem.rankInfo());
                ContentMaker.setRankStr(rankStr);
            }

            HashMap<String, String> infoMap = parse(users.toString());
            boolean loginSuc = infoMap.containsKey("UID") & infoMap.get("UID") != null;
            String login;
            if (t == 1) login = loginMaker(loginSuc, infoMap.get("avator"));
            else login = loginMaker(loginSuc, infoMap.get("avator"), t);
            String navStr = navMaker(login, t);
            ContentMaker.setNavStr(navStr);

            response.addCookie(loginUserInfo);
            try {
                if (t == 1) response.sendRedirect("/cinema/main");
                else if (t == 2) response.sendRedirect("/cinema/admin/a1n0e0/me");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
