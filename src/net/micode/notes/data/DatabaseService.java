package net.micode.notes.data;   
  
  
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.micode.notes.entities.HouseSaid;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.entities.NewsImageUrls;
import net.micode.notes.entities.User;
import net.micode.notes.tool.Utils;
import android.content.Context;
import android.database.Cursor;
/**  
 * 数据库方法封装，创建表，删除表，数据（增删该查）...  
 * @author 阿福（trygf521@126.com）  
 *  
 */  
/**
 * @author Aldrich_jia
 *
 */
public class DatabaseService {   
    private static MyDatabaseHelper dbOpenHelper;
	private String enterprisename;   
  
    public DatabaseService(Context context) {   
        dbOpenHelper = new MyDatabaseHelper(context);   
    }   
  
    public void dropTable(String taleName) {   
        dbOpenHelper.getWritableDatabase().execSQL(   
                "DROP TABLE IF EXISTS " + taleName);   
  
    }   
       
       
    public void closeDatabase(String DatabaseName) {   
        dbOpenHelper.getWritableDatabase().close();   
                   
    }   
    public void createTableUser(){
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
  public  boolean insertUser(User user){
	  if(user.getRelationuserenterprise()!=null){enterprisename = user.getRelationuserenterprise().getEnterprisename()==null?"":user.getRelationuserenterprise().getEnterprisename();}
	  String Lastlogintime=user.getLastlogintime()==null?"":user.getLastlogintime().toString();
	  String Lastloginip= user.getLastloginip()==null?"":user.getLastloginip();
		dbOpenHelper.getReadableDatabase().execSQL(
						"insert into user(enterprisename,lastlogintime,lastloginip)values(?,?,?)",
						new Object[] { enterprisename==null?"Enterprisename":enterprisename, Lastlogintime,Lastloginip });
	  return true;
  }
 /**
 * @param newsDetailContent
 * @return
 */
public boolean insertNewsDetailContent(NewsDetailContent newsDetailContent){
	String channelId=newsDetailContent.getChannelId();
	String channelName=newsDetailContent.getChannelName();
	String desc=newsDetailContent.getDesc();
//	String imageUrl=newsDetailContent.getImageurls().get(0).getUrl()==null?"没有图片路径":newsDetailContent.getImageurls().get(0).getUrl();
	String link=newsDetailContent.getLink();
	String long_desc=newsDetailContent.getLong_desc();
	String pubDate=newsDetailContent.getPubDate();
	String source=newsDetailContent.getSource();
	String title=newsDetailContent.getTitle();
	  dbOpenHelper.getReadableDatabase().execSQL("insert into NewsDetailContent("
	  		+ "_id,"
	  		+ "channelName,"
	  		+ "desc,"
	  		+ "link,"
	  		+ "long_desc,"
	  		+ "pubDate,"
	  		+ "source,"
	  		+ "title)values(?,?,?,?,?,?,?,?)",new Object[]{channelId,channelName,desc,link,long_desc,pubDate,source,title});
	return false;
  }
	public  List<NewsDetailContent> rawQueryNewsDetailContent(){
		List<NewsDetailContent> newsDetailContentList=new ArrayList<NewsDetailContent>();
		Cursor cursor=dbOpenHelper.getReadableDatabase().rawQuery("select * from NewsDetailContent order by pubDate desc", null);
		while(cursor.moveToNext()){
			NewsDetailContent newsDetailContent=new NewsDetailContent();
			newsDetailContent.setChannelId(cursor.getString(cursor.getColumnIndex("channelId")));
			newsDetailContent.setChannelName(cursor.getString(cursor.getColumnIndex("channelName")));
			newsDetailContent.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
//			newsDetailContent.setImageurls(cursor.getFloat((cursor.getColumnIndex("imageurls")));
			newsDetailContent.setLink(cursor.getString(cursor.getColumnIndex("link")));
			newsDetailContent.setLong_desc(cursor.getString(cursor.getColumnIndex("long_desc")));
			newsDetailContent.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
			newsDetailContent.setSource(cursor.getString(cursor.getColumnIndex("source")));
			newsDetailContent.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			newsDetailContentList.add(newsDetailContent);
		}
		return newsDetailContentList;
		
	}
  public boolean insertSaid(HouseSaid houseSaid){
	  dbOpenHelper.getReadableDatabase().execSQL("insert into said(_id,taici,cat,catcn,show,source,inserttime)values(?,?,?,?,?,?,?)",
			  new Object[]{
			  houseSaid.getId(),
			  houseSaid.getTaici(),
			  houseSaid.getCat(),
			  houseSaid.getCatcn(),
			  houseSaid.getShow()==null?"":houseSaid.getShow() ,
			  houseSaid.getSource(),
			  Long.toString(new Date().getTime())});
	  Utils.Log("insertSaid插入成功");
	return true;
	  
  }
  public List<HouseSaid> rawQuerySaid(){
	  List<HouseSaid> tmpItems = new ArrayList<HouseSaid>();
	  Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(
				"select * from said order by inserttime desc", null);
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
  /*  public void saveConfigInfo(ConfigInfo configInfo) {   
        dbOpenHelper.getWritableDatabase().execSQL(   
                "insert into config (s, rt, st, ru, v,i) values(?,?,?,?,?,?)",   
                new Object[] { configInfo.getS(), configInfo.getRt(),   
                        configInfo.getSt(), configInfo.getRu(),   
                        configInfo.getV(), configInfo.getI() });   
    }   */
  
  /*  public void saveApplicationInfo(ApplicationInfo configInfo) {   
        dbOpenHelper   
                .getWritableDatabase()   
                .execSQL(   
                        "insert into application (s,tt,tc1,tc2,ru,tn,m) values(?,?,?,?,?,?,?)",   
                        new Object[] { configInfo.getS(), configInfo.getTt(),   
                                configInfo.getTc1(), configInfo.getTc2(),   
                                configInfo.getRu(), configInfo.getTn(),   
                                configInfo.getM() });   
    }   */
  
/*    public void saveMsmInfo(SMSInfo configInfo) {   
        dbOpenHelper   
                .getWritableDatabase()   
                .execSQL(   
                        "insert into smslist (t,st,n1,n2,n,m,a) values(?,?,?,?,?,?,?)",   
                        new Object[] { configInfo.getT(), configInfo.getSt(),   
                                configInfo.getN1(), configInfo.getN2(),   
                                configInfo.getN(), configInfo.getM(),   
                                configInfo.getA() });   
    }   */
  
/*    public void saveInstallInfo(InstallInfo configInfo) {   
        dbOpenHelper.getWritableDatabase().execSQL(   
                "insert into install (na,it,d) values(?,?,?)",   
                new Object[] { configInfo.getNa(), configInfo.getIt(),   
                        configInfo.getD() });   
    }   */
  
/*    public void updateConfigInfo(ConfigInfo configInfo) {   
        dbOpenHelper.getWritableDatabase().execSQL(   
                "update config set s=?, rt=?, st=?, ru=?, v=?,i=? where id=?",   
                new Object[] { configInfo.getS(), configInfo.getRt(),   
                        configInfo.getSt(), configInfo.getRu(),   
                        configInfo.getV(), configInfo.getI(),   
                        configInfo.getId() });   
    }   */
  
/*    public void updateApplicationInfo(ApplicationInfo configInfo) {   
        dbOpenHelper   
                .getWritableDatabase()   
                .execSQL(   
                        "update application set s=?, tt=?, st=?, tc1=?, tc2=?,ru=?,tn=?,m=? where id=?",   
                        new Object[] { configInfo.getS(), configInfo.getTt(),   
                                configInfo.getSt(), configInfo.getTc1(),   
                                configInfo.getTc2(), configInfo.getRu(),   
                                configInfo.getTn(),configInfo.getM(), configInfo.getId() });   
    }   */
  
  /*  public void updateInstallInfo(InstallInfo configInfo) {   
        dbOpenHelper.getWritableDatabase().execSQL(   
                "update install set na=?, it=?, d=? where id=?",   
                new Object[] { configInfo.getNa(), configInfo.getIt(),   
                        configInfo.getD(), configInfo.getId() });   
    }   */
  
/*    public void updateSMSInfo(SMSInfo configInfo) {   
        dbOpenHelper   
                .getWritableDatabase()   
                .execSQL(   
                        "update smslist set t=?, st=?, n1=?, n2=?, n=?, m=?, a=? where id=?",   
                        new Object[] { configInfo.getT(), configInfo.getSt(),   
                                configInfo.getN1(), configInfo.getN2(),   
                                configInfo.getN(), configInfo.getM(),   
                                configInfo.getA(), configInfo.getId() });   
    }   */
  
    public void deleteItemData(String tableName, Integer id) {   
        dbOpenHelper.getWritableDatabase()   
                .execSQL("delete from " + tableName + " where id=?",   
                        new Object[] { id });   
    }   
  
  
/*    public InstallInfo findInstallInfo(Integer id) {   
        Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(   
                "select id,na,it,d from install where id=?",   
                new String[] { String.valueOf(id) });   
        if (cursor.moveToNext()) {   
            InstallInfo configInfo = new InstallInfo();   
            configInfo.setId((cursor.getInt(0)));   
            configInfo.setNa(cursor.getString(1));   
            configInfo.setIt(cursor.getString(2));   
            configInfo.setD(cursor.getString(3));   
  
            return configInfo;   
        }   
        return null;   
    }   */
  
/*    public ConfigInfo findConfigInfo(Integer id) {   
        Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(   
                "select id,s,rt,st,ru,v,i from config where id=?",   
                new String[] { String.valueOf(id) });   
        if (cursor.moveToNext()) {   
            ConfigInfo configInfo = new ConfigInfo();   
            configInfo.setId((cursor.getInt(0)));   
            configInfo.setS(cursor.getString(1));   
            configInfo.setRt(cursor.getString(2));   
            configInfo.setSt(cursor.getString(3));   
            configInfo.setRu(cursor.getString(4));   
            configInfo.setV(cursor.getString(5));   
            configInfo.setI(cursor.getString(6));   
  
            return configInfo;   
        }   
        return null;   
    }   */
  
/*    public SMSInfo findSMSInfo(Integer id) {   
        Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(   
                "select id,t,st,n1,n2,n,m,a from smslist where id=?",   
                new String[] { String.valueOf(id) });   
        if (cursor.moveToNext()) {   
            SMSInfo configInfo = new SMSInfo();   
            configInfo.setId((cursor.getInt(0)));   
            configInfo.setT(cursor.getString(1));   
            configInfo.setSt(cursor.getString(2));   
            configInfo.setN1(cursor.getString(3));   
            configInfo.setN2(cursor.getString(4));   
            configInfo.setN(cursor.getString(5));   
            configInfo.setM(cursor.getString(6));   
            configInfo.setA(cursor.getString(7));   
  
            return configInfo;   
        }   
        return null;   
    }   */
  
/*    public ApplicationInfo findApplication(Integer id) {   
        Cursor cursor = dbOpenHelper   
                .getWritableDatabase()   
                .rawQuery(   
                        "select id,s,tt,st,tc1,tc2,ru,tn,m from application where id=?",   
                        new String[] { String.valueOf(id) });   
        if (cursor.moveToNext()) {   
            ApplicationInfo applicationinfo = new ApplicationInfo();   
            applicationinfo.setId((cursor.getInt(0)));   
            applicationinfo.setS(cursor.getString(1));   
            applicationinfo.setTt(cursor.getString(2));   
            applicationinfo.setSt(cursor.getString(3));   
            applicationinfo.setTc1(cursor.getString(4));   
            applicationinfo.setTc2(cursor.getString(5));   
            applicationinfo.setRu(cursor.getString(6));   
            applicationinfo.setTn(cursor.getString(7));   
            applicationinfo.setM(cursor.getString(8));   
  
            return applicationinfo;   
        }   
        return null;   
    }   */
  
    public long getDataCount(String tableName) {   
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(   
                "select count(*) from " + tableName, null);   
        cursor.moveToFirst();   
        return cursor.getLong(0);   
    }   
  
//  // ///////////////////////   
//  public LinkedList<ConfigInfo> getScrollData(int startindex, int maxResult) {   
//      LinkedList<ConfigInfo> xmlInfos = new LinkedList<ConfigInfo>();   
//      Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(   
//              "select id,s,rt,st,ru,v,i from config limit ?,?,?,?,?,?",   
//              new String[] { String.valueOf(startindex),   
//                      String.valueOf(maxResult) });   
//      while (cursor.moveToNext()) {   
//          ConfigInfo xmlInfo = new ConfigInfo();   
//          xmlInfo.setId((cursor.getInt(0)));   
//          xmlInfo.setS(cursor.getString(1));   
//          xmlInfo.setRt(cursor.getString(2));   
//          xmlInfo.setSt(cursor.getString(6));   
//          xmlInfo.setRu(cursor.getString(3));   
//          xmlInfo.setV(cursor.getString(4));   
//          xmlInfo.setI(cursor.getString(5));   
//          xmlInfos.add(xmlInfo);   
//      }   
//      cursor.close();   
//      return xmlInfos;   
//  }   
//   
//  // //////////////   
//  public Cursor getScrollDataCursor(int startindex, int maxResult) {   
//      Cursor cursor = dbOpenHelper   
//              .getReadableDatabase()   
//              .rawQuery(   
//                      "select id as _id,s,rt,li,ru,tn,m from config limit ?,?,?,?,?,?",   
//                      new String[] { String.valueOf(startindex),   
//                              String.valueOf(maxResult) });   
//      return cursor;   
//  }   
//   
//  public Cursor getScrollDataCursorApplication(int startindex, int maxResult) {   
//      Cursor cursor = dbOpenHelper   
//              .getReadableDatabase()   
//              .rawQuery(   
//                      "select id as _id,s,tt,st,st,ru,v,i from application limit ?,?,?,?,?,?,?",   
//                      new String[] { String.valueOf(startindex),   
//                              String.valueOf(maxResult) });   
//      return cursor;   
//  }   
  
    public void close() {   
        dbOpenHelper.close();   
    }   
  
}  