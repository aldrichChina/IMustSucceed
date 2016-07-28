/******************************************************************
 *    Package:     com.jia.logintest
 *
 *    Filename:    LoginBean.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年7月27日 下午2:03:55
 *
 *    Revision:
 *
 *    2016年7月27日 下午2:03:55
 *        - second revision
 *
 *****************************************************************/
package net.micode.notes.entities;

/**
 * @ClassName LoginBean
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年7月27日 下午2:03:55
 * @version 1.0.0
 */
public class LoginBean {

    private String ret;
    private String pay_token;
    private String pf;
    private String query_authority_cost;
    private String authority_cost;
    private String openid;
    private String expires_in;
    private String pfkey;
    private String msg;
    private String access_token;
    private String login_cost;
    
    /**
     * @return the ret
     */
    public String getRet() {
        return ret;
    }
    
    /**
     * @param ret the ret to set
     */
    public void setRet(String ret) {
        this.ret = ret;
    }
    
    /**
     * @return the pay_token
     */
    public String getPay_token() {
        return pay_token;
    }
    
    /**
     * @param pay_token the pay_token to set
     */
    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }
    
    /**
     * @return the pf
     */
    public String getPf() {
        return pf;
    }
    
    /**
     * @param pf the pf to set
     */
    public void setPf(String pf) {
        this.pf = pf;
    }
    
    /**
     * @return the query_authority_cost
     */
    public String getQuery_authority_cost() {
        return query_authority_cost;
    }
    
    /**
     * @param query_authority_cost the query_authority_cost to set
     */
    public void setQuery_authority_cost(String query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }
    
    /**
     * @return the authority_cost
     */
    public String getAuthority_cost() {
        return authority_cost;
    }
    
    /**
     * @param authority_cost the authority_cost to set
     */
    public void setAuthority_cost(String authority_cost) {
        this.authority_cost = authority_cost;
    }
    
    /**
     * @return the openid
     */
    public String getOpenid() {
        return openid;
    }
    
    /**
     * @param openid the openid to set
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
    /**
     * @return the expires_in
     */
    public String getExpires_in() {
        return expires_in;
    }
    
    /**
     * @param expires_in the expires_in to set
     */
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
    
    /**
     * @return the pfkey
     */
    public String getPfkey() {
        return pfkey;
    }
    
    /**
     * @param pfkey the pfkey to set
     */
    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }
    
    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    /**
     * @return the access_token
     */
    public String getAccess_token() {
        return access_token;
    }
    
    /**
     * @param access_token the access_token to set
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    
    /**
     * @return the login_cost
     */
    public String getLogin_cost() {
        return login_cost;
    }
    
    /**
     * @param login_cost the login_cost to set
     */
    public void setLogin_cost(String login_cost) {
        this.login_cost = login_cost;
    }

    /* (非 Javadoc)
     * Description:
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LoginBean [ret=" + ret + ", pay_token=" + pay_token + ", pf=" + pf + ", query_authority_cost="
                + query_authority_cost + ", authority_cost=" + authority_cost + ", openid=" + openid + ", expires_in="
                + expires_in + ", pfkey=" + pfkey + ", msg=" + msg + ", access_token=" + access_token + ", login_cost="
                + login_cost + "]";
    }
    
}
