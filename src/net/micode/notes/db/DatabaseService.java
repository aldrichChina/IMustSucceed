package net.micode.notes.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.micode.notes.ConstantProvider;
import net.micode.notes.entities.HouseSaid;
import net.micode.notes.entities.LoginBean;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.entities.NewsImageUrls;
import net.micode.notes.entities.User;
import net.micode.notes.entities.UserBean;
import net.micode.notes.entities.WxhotArticle;
import net.micode.notes.util.Utils;
import android.content.Context;
import android.database.Cursor;

/**  
 * 数据库方法封装，创建表，删除表，数据（增删该查）...  
 * @author 阿福（trygf521@126.com）  
 *  
 */
/**
 * @author Aldrich_jia
 */
public class DatabaseService {

    private static MyDatabaseHelper dbOpenHelper;
    private String enterprisename;

    public DatabaseService(Context context) {
        dbOpenHelper = new MyDatabaseHelper(context);
    }

    public void dropTable(String taleName) {
        dbOpenHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + taleName);

    }

    public void closeDatabase(String DatabaseName) {
        dbOpenHelper.getWritableDatabase().close();

    }

    public void createTableUser() {
        String sql = "CREATE TABLE IF NOT EXISTS user (id integer primary key autoincrement, enterprisename, lastlogintime,lastloginip)";
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    public void createConfigTable() {
        String sql = "CREATE TABLE IF NOT EXISTS config (id integer primary key autoincrement, s varchar(60), rt varchar(60),st varchar(60), ru varchar(60), v varchar(60),i varchar(60))";
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    public void createTableApplication() {
        String sql = "CREATE TABLE IF NOT EXISTS application (id integer primary key autoincrement, s varchar(60), tt varchar(60),st varchar(60),tc1 varchar(60), tc2 varchar(60), ru varchar(60),tn varchar(60),m varchar(60))";
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    public void createTableInstall() {
        String sql = "CREATE TABLE IF NOT EXISTS install (id integer primary key autoincrement, na varchar(60), it varchar(60),d varchar(60))";
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    public void createTableSmslist() {
        String sql = "CREATE TABLE IF NOT EXISTS smslist (id integer primary key autoincrement, t varchar(60), st varchar(60),n1 varchar(60),n2 varchar(60),n varchar(60),m varchar(60),a varchar(60))";
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    // public boolean insertUser(User user) {
    // if (user.getRelationuserenterprise() != null) {
    // enterprisename = user.getRelationuserenterprise().getEnterprisename() == null ? "" : user
    // .getRelationuserenterprise().getEnterprisename();
    // }
    // String Lastlogintime = user.getLastlogintime() == null ? "" : user.getLastlogintime().toString();
    // String Lastloginip = user.getLastloginip() == null ? "" : user.getLastloginip();
    // dbOpenHelper.getReadableDatabase().execSQL(
    // "insert into user(enterprisename,lastlogintime,lastloginip)values(?,?,?)",
    // new Object[] {enterprisename == null ? "Enterprisename" : enterprisename, Lastlogintime, Lastloginip });
    // return true;
    // }

    /**
     * NewsDetailContent表 用于插入新闻信息数据库
     * 
     * @param newsDetailContent
     * @return
     * @throws ParseException
     */
    public boolean insertNewsDetailContent(final NewsDetailContent newsDetailContent) throws ParseException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String imageUrl;
                List<NewsImageUrls> imageurls = newsDetailContent.getImageurls();
                if (imageurls != null && imageurls.size() > 0) {

                    NewsImageUrls newsImageUrls = imageurls.get(0);
                    imageUrl = newsImageUrls.getUrl() == null ? "" : newsDetailContent.getImageurls().get(0).getUrl();
                } else {
                    imageUrl = "";
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String channelId = newsDetailContent.getChannelId();
                String channelName = newsDetailContent.getChannelName();
                String desc = newsDetailContent.getDesc();
                String link = newsDetailContent.getLink();
                String long_desc = newsDetailContent.getLong_desc();
                String pubDate = newsDetailContent.getPubDate();
                String source = newsDetailContent.getSource();
                String title = newsDetailContent.getTitle();
                // String timeStamp = String.valueOf(dateFormat.parse(pubDate).getTime());
                String timeStamp = Long.toString(new Date().getTime());
                dbOpenHelper.getReadableDatabase().execSQL(
                        "insert into NewsDetailContent(" + "channelId," + "channelName," + "desc," + "imageUrl,"
                                + "link," + "long_desc," + "pubDate," + "source," + "title,"
                                + "timeStamp)values(?,?,?,?,?,?,?,?,?,?)",
                        new Object[] {channelId, channelName, desc, imageUrl, link, long_desc, pubDate, source, title,
                                timeStamp });
            }
        }).start();

        return false;
    }

    /**
     * @Description (NewsDetailContent表) 读取新闻NewsDetailContent表
     * @return
     */
    public List<NewsDetailContent> rawQueryNewsDetailContent() {
        List<NewsDetailContent> newsDetailContentList = new ArrayList<NewsDetailContent>();
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(
                "select * from NewsDetailContent order by timeStamp desc", null);
        while (cursor.moveToNext()) {
            List<NewsImageUrls> imageurlsList = new ArrayList<NewsImageUrls>();
            NewsImageUrls newsImageUrls = new NewsImageUrls();
            int imageurlsIndex = cursor.getColumnIndex("imageUrl");
            String imageurls = cursor.getString(imageurlsIndex);
            newsImageUrls.setUrl(imageurls);
            imageurlsList.add(newsImageUrls);
            NewsDetailContent newsDetailContent = new NewsDetailContent();
            newsDetailContent.setChannelId(cursor.getString(cursor.getColumnIndex("channelId")));
            newsDetailContent.setChannelName(cursor.getString(cursor.getColumnIndex("channelName")));
            newsDetailContent.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
            newsDetailContent.setImageurls(imageurlsList);
            newsDetailContent.setLink(cursor.getString(cursor.getColumnIndex("link")));
            newsDetailContent.setLong_desc(cursor.getString(cursor.getColumnIndex("long_desc")));
            newsDetailContent.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
            newsDetailContent.setSource(cursor.getString(cursor.getColumnIndex("source")));
            newsDetailContent.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            newsDetailContentList.add(newsDetailContent);
        }
        return newsDetailContentList;

    }

    /**
     * @Description (插入said表)
     * @param houseSaid
     * @return
     */
    public boolean insertSaid(HouseSaid houseSaid) {
        dbOpenHelper.getReadableDatabase().execSQL(
                "insert into said(_id,taici,cat,catcn,show,source,inserttime)values(?,?,?,?,?,?,?)",
                new Object[] {houseSaid.getId(), houseSaid.getTaici(), houseSaid.getCat(), houseSaid.getCatcn(),
                        houseSaid.getShow() == null ? "" : houseSaid.getShow(), houseSaid.getSource(),
                        Long.toString(new Date().getTime()) });
        Utils.Log("insertSaid插入成功");
        return true;

    }

    /**
     * @Description (读取said表)
     * @return
     */
    public List<HouseSaid> rawQuerySaid() {
        List<HouseSaid> tmpItems = new ArrayList<HouseSaid>();
        Cursor cursor = dbOpenHelper.getReadableDatabase()
                .rawQuery("select * from said order by inserttime desc", null);
        while (cursor.moveToNext()) {
            HouseSaid houseSaid = new HouseSaid();
            houseSaid.setId(cursor.getString(cursor.getColumnIndex("_id")));
            houseSaid.setTaici(cursor.getString(cursor.getColumnIndex("taici")));
            houseSaid.setCat(cursor.getString(cursor.getColumnIndex("cat")));
            houseSaid.setCatcn(cursor.getString(cursor.getColumnIndex("catcn")));
            houseSaid.setShow(cursor.getString(cursor.getColumnIndex("show")));
            houseSaid.setSource(cursor.getString(cursor.getColumnIndex("source")));
            tmpItems.add(houseSaid);
        }
        return tmpItems;
    }

    /**
     * @Description (TODO这里用一句话描述这个方法的作用)
     * @param hotArticleList
     */
    public void insertHotArticle(final List<WxhotArticle> hotArticleList) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (WxhotArticle hotArticle : hotArticleList) {
                    String ctime = hotArticle.getCtime();
                    String title = hotArticle.getTitle();
                    String description = hotArticle.getDescription();
                    String picUrl = hotArticle.getPicUrl();
                    String url = hotArticle.getUrl();
                    dbOpenHelper
                            .getReadableDatabase()
                            .execSQL(
                                    "insert into HotArticle (ctime,title,description,picUrl,url,timestamp)values(?,?,?,?,?,?)",
                                    new String[] {ctime, title, description, picUrl, url,
                                            Long.toString(new Date().getTime()) });
                }
            }
        }).start();
    }

    /**
     * @Description (TODO这里用一句话描述这个方法的作用)
     * @return
     */
    public List<WxhotArticle> rawQueryHotArticle() {
        List<WxhotArticle> hotArticleList = new ArrayList<WxhotArticle>();
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery("select * from HotArticle order by timestamp desc",
                null);
        while (cursor.moveToNext()) {
            WxhotArticle hotArticle = new WxhotArticle();
            hotArticle.setId(cursor.getString(cursor.getColumnIndex("_id")));
            hotArticle.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
            hotArticle.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            hotArticle.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            hotArticle.setPicUrl(cursor.getString(cursor.getColumnIndex("picUrl")));
            hotArticle.setUrl(cursor.getString(cursor.getColumnIndex("url")));

            hotArticleList.add(hotArticle);
        }
        return hotArticleList;
    }

    /**
     * @Description (插入Login表)
     * @param houseSaid
     * @return
     */
    public boolean insertLogin(LoginBean loginBean) {
        dbOpenHelper
                .getReadableDatabase()
                .execSQL(
                        "insert into Login(_openid, ret, pay_token,pf,query_authority_cost,authority_cost,expires_in,pfkey,msg,access_token,login_cost)values(?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[] {loginBean.getOpenid(), loginBean.getRet(), loginBean.getPay_token(),
                                loginBean.getPf(), loginBean.getQuery_authority_cost(), loginBean.getAuthority_cost(),
                                loginBean.getExpires_in(), loginBean.getPfkey(), loginBean.getMsg(),
                                loginBean.getAccess_token(), loginBean.getLogin_cost() });
        Utils.Log("insertLogin插入成功");
        return true;
    }

    /**
     * @Description (读取Login表)
     * @return
     */
    public List<LoginBean> rawQueryLogin() {
        List<LoginBean> userList = new ArrayList<LoginBean>();
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery("select * from Login  where _openid=?",
                new String[] {ConstantProvider.getUserId() });
        while (cursor.moveToNext()) {
            LoginBean loginBean = new LoginBean();
            loginBean.setRet(cursor.getString(cursor.getColumnIndex("ret")));
            loginBean.setPay_token(cursor.getString(cursor.getColumnIndex("pay_token")));
            loginBean.setPf(cursor.getString(cursor.getColumnIndex("pf")));
            loginBean.setQuery_authority_cost(cursor.getString(cursor.getColumnIndex("query_authority_cost")));
            loginBean.setAuthority_cost(cursor.getString(cursor.getColumnIndex("authority_cost")));
            loginBean.setOpenid(cursor.getString(cursor.getColumnIndex("_openid")));
            loginBean.setExpires_in(cursor.getString(cursor.getColumnIndex("expires_in")));
            loginBean.setPfkey(cursor.getString(cursor.getColumnIndex("pfkey")));
            loginBean.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
            loginBean.setAccess_token(cursor.getString(cursor.getColumnIndex("access_token")));
            loginBean.setLogin_cost(cursor.getString(cursor.getColumnIndex("login_cost")));
            userList.add(loginBean);
        }
        return userList;
    }

    /**
     * @Description (插入User表)
     * @param houseSaid
     * @return
     */
    public boolean insertUser(UserBean userBean) {
        dbOpenHelper
                .getReadableDatabase()
                .execSQL(
                        "insert into User(_nickname  , is_yellow_year_vip, ret,figureurl_qq_1,figureurl_qq_2,yellow_vip_level,is_lost,msg,city,figureurl_1,vip,level,figureurl_2,province,is_yellow_vip,gender,figureurl,openid)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[] {userBean.getNickname(), userBean.getIs_yellow_year_vip(), userBean.getRet(),
                                userBean.getFigureurl_qq_1(), userBean.getFigureurl_qq_2(),
                                userBean.getYellow_vip_level(), userBean.getIs_lost(), userBean.getMsg(),
                                userBean.getCity(), userBean.getFigureurl_1(), userBean.getVip(), userBean.getLevel(),
                                userBean.getFigureurl_2(), userBean.getProvince(), userBean.getIs_yellow_vip(),
                                userBean.getGender(), userBean.getFigureurl(), ConstantProvider.getUserId() });
        Utils.Log("insertUser插入成功");
        return true;
    }

    /**
     * @Description (读取User表)
     * @return
     */
    public List<UserBean> rawQueryUser() {
        List<UserBean> userList = new ArrayList<UserBean>();
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery("select * from User where openid=?",
                new String[] {ConstantProvider.getUserId() });
        while (cursor.moveToNext()) {
            UserBean userBean = new UserBean();
            userBean.setIs_yellow_year_vip(cursor.getString(cursor.getColumnIndex("is_yellow_year_vip")));
            userBean.setRet(cursor.getString(cursor.getColumnIndex("ret")));
            userBean.setFigureurl_qq_1(cursor.getString(cursor.getColumnIndex("figureurl_qq_1")));
            userBean.setFigureurl_qq_2(cursor.getString(cursor.getColumnIndex("figureurl_qq_2")));
            userBean.setNickname(cursor.getString(cursor.getColumnIndex("_nickname")));
            userBean.setYellow_vip_level(cursor.getString(cursor.getColumnIndex("yellow_vip_level")));
            userBean.setIs_lost(cursor.getString(cursor.getColumnIndex("is_lost")));
            userBean.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
            userBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
            userBean.setFigureurl_1(cursor.getString(cursor.getColumnIndex("figureurl_1")));
            userBean.setVip(cursor.getString(cursor.getColumnIndex("vip")));
            userBean.setLevel(cursor.getString(cursor.getColumnIndex("level")));
            userBean.setFigureurl_2(cursor.getString(cursor.getColumnIndex("figureurl_2")));
            userBean.setProvince(cursor.getString(cursor.getColumnIndex("province")));
            userBean.setIs_yellow_vip(cursor.getString(cursor.getColumnIndex("is_yellow_vip")));
            userBean.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            userBean.setFigureurl(cursor.getString(cursor.getColumnIndex("figureurl")));
            userList.add(userBean);
        }
        return userList;
    }

    /*
     * public void saveConfigInfo(ConfigInfo configInfo) { dbOpenHelper.getWritableDatabase().execSQL(
     * "insert into config (s, rt, st, ru, v,i) values(?,?,?,?,?,?)", new Object[] { configInfo.getS(),
     * configInfo.getRt(), configInfo.getSt(), configInfo.getRu(), configInfo.getV(), configInfo.getI() }); }
     */

    /*
     * public void saveApplicationInfo(ApplicationInfo configInfo) { dbOpenHelper .getWritableDatabase() .execSQL(
     * "insert into application (s,tt,tc1,tc2,ru,tn,m) values(?,?,?,?,?,?,?)", new Object[] { configInfo.getS(),
     * configInfo.getTt(), configInfo.getTc1(), configInfo.getTc2(), configInfo.getRu(), configInfo.getTn(),
     * configInfo.getM() }); }
     */

    /*
     * public void saveMsmInfo(SMSInfo configInfo) { dbOpenHelper .getWritableDatabase() .execSQL(
     * "insert into smslist (t,st,n1,n2,n,m,a) values(?,?,?,?,?,?,?)", new Object[] { configInfo.getT(),
     * configInfo.getSt(), configInfo.getN1(), configInfo.getN2(), configInfo.getN(), configInfo.getM(),
     * configInfo.getA() }); }
     */

    /*
     * public void saveInstallInfo(InstallInfo configInfo) { dbOpenHelper.getWritableDatabase().execSQL(
     * "insert into install (na,it,d) values(?,?,?)", new Object[] { configInfo.getNa(), configInfo.getIt(),
     * configInfo.getD() }); }
     */

    /*
     * public void updateConfigInfo(ConfigInfo configInfo) { dbOpenHelper.getWritableDatabase().execSQL(
     * "update config set s=?, rt=?, st=?, ru=?, v=?,i=? where id=?", new Object[] { configInfo.getS(),
     * configInfo.getRt(), configInfo.getSt(), configInfo.getRu(), configInfo.getV(), configInfo.getI(),
     * configInfo.getId() }); }
     */

    /*
     * public void updateApplicationInfo(ApplicationInfo configInfo) { dbOpenHelper .getWritableDatabase() .execSQL(
     * "update application set s=?, tt=?, st=?, tc1=?, tc2=?,ru=?,tn=?,m=? where id=?", new Object[] {
     * configInfo.getS(), configInfo.getTt(), configInfo.getSt(), configInfo.getTc1(), configInfo.getTc2(),
     * configInfo.getRu(), configInfo.getTn(),configInfo.getM(), configInfo.getId() }); }
     */

    /*
     * public void updateInstallInfo(InstallInfo configInfo) { dbOpenHelper.getWritableDatabase().execSQL(
     * "update install set na=?, it=?, d=? where id=?", new Object[] { configInfo.getNa(), configInfo.getIt(),
     * configInfo.getD(), configInfo.getId() }); }
     */

    /*
     * public void updateSMSInfo(SMSInfo configInfo) { dbOpenHelper .getWritableDatabase() .execSQL(
     * "update smslist set t=?, st=?, n1=?, n2=?, n=?, m=?, a=? where id=?", new Object[] { configInfo.getT(),
     * configInfo.getSt(), configInfo.getN1(), configInfo.getN2(), configInfo.getN(), configInfo.getM(),
     * configInfo.getA(), configInfo.getId() }); }
     */

    public void deleteItemData(String tableName, Integer id) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from " + tableName + " where id=?", new Object[] {id });
    }

    /*
     * public InstallInfo findInstallInfo(Integer id) { Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(
     * "select id,na,it,d from install where id=?", new String[] { String.valueOf(id) }); if (cursor.moveToNext()) {
     * InstallInfo configInfo = new InstallInfo(); configInfo.setId((cursor.getInt(0)));
     * configInfo.setNa(cursor.getString(1)); configInfo.setIt(cursor.getString(2));
     * configInfo.setD(cursor.getString(3)); return configInfo; } return null; }
     */

    /*
     * public ConfigInfo findConfigInfo(Integer id) { Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(
     * "select id,s,rt,st,ru,v,i from config where id=?", new String[] { String.valueOf(id) }); if (cursor.moveToNext())
     * { ConfigInfo configInfo = new ConfigInfo(); configInfo.setId((cursor.getInt(0)));
     * configInfo.setS(cursor.getString(1)); configInfo.setRt(cursor.getString(2));
     * configInfo.setSt(cursor.getString(3)); configInfo.setRu(cursor.getString(4));
     * configInfo.setV(cursor.getString(5)); configInfo.setI(cursor.getString(6)); return configInfo; } return null; }
     */

    /*
     * public SMSInfo findSMSInfo(Integer id) { Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(
     * "select id,t,st,n1,n2,n,m,a from smslist where id=?", new String[] { String.valueOf(id) }); if
     * (cursor.moveToNext()) { SMSInfo configInfo = new SMSInfo(); configInfo.setId((cursor.getInt(0)));
     * configInfo.setT(cursor.getString(1)); configInfo.setSt(cursor.getString(2));
     * configInfo.setN1(cursor.getString(3)); configInfo.setN2(cursor.getString(4));
     * configInfo.setN(cursor.getString(5)); configInfo.setM(cursor.getString(6)); configInfo.setA(cursor.getString(7));
     * return configInfo; } return null; }
     */

    /*
     * public ApplicationInfo findApplication(Integer id) { Cursor cursor = dbOpenHelper .getWritableDatabase()
     * .rawQuery( "select id,s,tt,st,tc1,tc2,ru,tn,m from application where id=?", new String[] { String.valueOf(id) });
     * if (cursor.moveToNext()) { ApplicationInfo applicationinfo = new ApplicationInfo();
     * applicationinfo.setId((cursor.getInt(0))); applicationinfo.setS(cursor.getString(1));
     * applicationinfo.setTt(cursor.getString(2)); applicationinfo.setSt(cursor.getString(3));
     * applicationinfo.setTc1(cursor.getString(4)); applicationinfo.setTc2(cursor.getString(5));
     * applicationinfo.setRu(cursor.getString(6)); applicationinfo.setTn(cursor.getString(7));
     * applicationinfo.setM(cursor.getString(8)); return applicationinfo; } return null; }
     */

    public long getDataCount(String tableName) {
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery("select count(*) from " + tableName, null);
        cursor.moveToFirst();
        return cursor.getLong(0);
    }

    // // ///////////////////////
    // public LinkedList<ConfigInfo> getScrollData(int startindex, int maxResult) {
    // LinkedList<ConfigInfo> xmlInfos = new LinkedList<ConfigInfo>();
    // Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(
    // "select id,s,rt,st,ru,v,i from config limit ?,?,?,?,?,?",
    // new String[] { String.valueOf(startindex),
    // String.valueOf(maxResult) });
    // while (cursor.moveToNext()) {
    // ConfigInfo xmlInfo = new ConfigInfo();
    // xmlInfo.setId((cursor.getInt(0)));
    // xmlInfo.setS(cursor.getString(1));
    // xmlInfo.setRt(cursor.getString(2));
    // xmlInfo.setSt(cursor.getString(6));
    // xmlInfo.setRu(cursor.getString(3));
    // xmlInfo.setV(cursor.getString(4));
    // xmlInfo.setI(cursor.getString(5));
    // xmlInfos.add(xmlInfo);
    // }
    // cursor.close();
    // return xmlInfos;
    // }
    //
    // // //////////////
    // public Cursor getScrollDataCursor(int startindex, int maxResult) {
    // Cursor cursor = dbOpenHelper
    // .getReadableDatabase()
    // .rawQuery(
    // "select id as _id,s,rt,li,ru,tn,m from config limit ?,?,?,?,?,?",
    // new String[] { String.valueOf(startindex),
    // String.valueOf(maxResult) });
    // return cursor;
    // }
    //
    // public Cursor getScrollDataCursorApplication(int startindex, int maxResult) {
    // Cursor cursor = dbOpenHelper
    // .getReadableDatabase()
    // .rawQuery(
    // "select id as _id,s,tt,st,st,ru,v,i from application limit ?,?,?,?,?,?,?",
    // new String[] { String.valueOf(startindex),
    // String.valueOf(maxResult) });
    // return cursor;
    // }

    public void close() {
        dbOpenHelper.close();
    }

}
