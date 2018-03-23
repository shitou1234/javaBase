/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package cn.itcast.solrj.cloudTest;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常量
 * 
 * @author lsk
 * @version 1.0
 * @since 1.0
 */
public class BaseConstant implements java.io.Serializable{

    public static final int DEFAULT_LIMIT = 15;

    public static final int DEL_FLAG_DEL=0;//删除
    public static final int DEL_FLAG_ENABLE=1;//启用
    public static final int DEL_FLAG_DISABLE=2;//禁用

    public static String SOLR_URL_RAYLI_MEMBER = "http://192.168.3.43:8081/solr/";
    public static String SOLR_CLOUD_ZKHOST="192.168.3.43:4180,192.168.3.43:4181,192.168.3.43:4182";
    public static String SOLR_CLOUD_DEF_COLLECTION="collection1";

    public static String SERVER_IPADDRESS_1;
    public static String SERVER_IPADDRESS_2;
    public static String SERVER_IPADDRESS_3;
    public static String SERVER_IPADDRESS_4;
    public static String SERVER_IPADDRESS_5;

    public static final String defaultFile = "sId,sType,uid,username,regdate,lastlogintime,email,lastloginip";

    public void setSOLR_URL_RAYLI_MEMBER(String SOLR_URL_RAYLI_MEMBER) {
        BaseConstant.SOLR_URL_RAYLI_MEMBER = SOLR_URL_RAYLI_MEMBER;
    }

    public void setSOLR_CLOUD_DEF_COLLECTION(String solrCloudDefCollection) {
        BaseConstant.SOLR_CLOUD_DEF_COLLECTION = solrCloudDefCollection;
    }

    public  void setSERVER_IPADDRESS_1(String SERVER_IPADDRESS_1) {
        BaseConstant.SERVER_IPADDRESS_1 = SERVER_IPADDRESS_1;
    }

    public  void setSERVER_IPADDRESS_2(String SERVER_IPADDRESS_2) {
        BaseConstant.SERVER_IPADDRESS_2 = SERVER_IPADDRESS_2;
    }

    public  void setSERVER_IPADDRESS_3(String SERVER_IPADDRESS_3) {
        BaseConstant.SERVER_IPADDRESS_3 = SERVER_IPADDRESS_3;
    }

    public  void setSERVER_IPADDRESS_4(String SERVER_IPADDRESS_4) {
        BaseConstant.SERVER_IPADDRESS_4 = SERVER_IPADDRESS_4;
    }

    public  void setSERVER_IPADDRESS_5(String SERVER_IPADDRESS_5) {
        BaseConstant.SERVER_IPADDRESS_5 = SERVER_IPADDRESS_5;
    }

    public void setSOLR_CLOUD_ZKHOST(String solrCloudZkhost) {
        BaseConstant.SOLR_CLOUD_ZKHOST = solrCloudZkhost;
    }

    /**
     * 删除启用禁用状态，
     */
    public static enum DelFlag {
        del("删除",DEL_FLAG_DEL),
        enable("启用",DEL_FLAG_ENABLE),
        disable("禁用",DEL_FLAG_DISABLE);

        private String name;
        private int code;

        DelFlag(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public static String getName(Integer code) {
            if(null!=code){
                DelFlag[] status = DelFlag.values();
                for( DelFlag as : status ) {
                    if( code.intValue() == as.code ) {
                        return as.name;
                    }
                }
            }
            return "";
        }

        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
    }

    /**
     * 只带有是否关系的状态选择
     */
    public static enum Status {
        TRUE("是",1),
        FALSE("否",0);
        private String name;
        private int code;

        Status(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public static String getName(Integer code) {
            if(null!=code){
                Status[] ss = Status.values();
                for( Status as : ss ) {
                    if( code.intValue() == as.code ) {
                        return as.name;
                    }
                }
            }
            return "";
        }

        public String getName() {
            return this.name;
        }
        public int getCode() {
            return this.code;
        }
    }


    public static final String[][] type = {
        {"单选","RADIO"},
        {"多选","CHECKBOX"},
        {"下拉框","SELECT"},
        {"时间","TIME"},
        {"文本","TEXT"},
        {"数字","NUMBER"},
    };

    /**
     * 元素类型
     */
    public static enum ElementType {
        RADIO(type[0][0],type[0][1]),
        CHECKBOX(type[1][0],type[1][1]),
        SELECT(type[2][0],type[2][1]);

        private String name;
        private String code;

        ElementType(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public static String getName(String code) {
            if(null!=code){
                ElementType[] ss = ElementType.values();
                for( ElementType as : ss ) {
                    if( as.code.equals(code) ) {
                        return as.name;
                    }
                }
            }
            return "";
        }
        public String getName() {
            return this.name;
        }
        public String getCode() {
            return this.code;
        }
    }

    /**
     * 元素类型
     */
    public static enum AllElementType {
        RADIO(type[0][0],type[0][1]),
        CHECKBOX(type[1][0],type[1][1]),
        SELECT(type[2][0],type[2][1]),
        TIME(type[3][0],type[3][1]),
        TEXT(type[4][0],type[4][1]),
        NUMBER(type[5][0],type[5][1]);

        private String name;
        private String code;

        AllElementType(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public static String getName(String code) {
            if(null!=code){
                AllElementType[] ss = AllElementType.values();
                for( AllElementType as : ss ) {
                    if( as.code.equals(code) ) {
                        return as.name;
                    }
                }
            }
            return "";
        }
        public String getName() {
            return this.name;
        }
        public String getCode() {
            return this.code;
        }
    }

    public static class MemberBaseClmn {
        public static Map<String, String> CLMN_KEY_VALUE = new LinkedHashMap<String, String>(){{
            put("uid", "用户主键");
            put("headImg", "头像");
            put("realName", "姓名");
            put("gender", "性别");
            put("phone", "电话");
            put("zipCode", "邮编");
            put("mobile", "手机");
            put("birthday", "出生年");
            put("bloodType", "血型");
            put("education", "教育程度");
            put("contactAddress", "联系地址");
            put("mailingAddress", "邮寄地址");
            put("billingAddress", "帐单地址");
            put("income", "个人月收入");
            put("homeIncome", "家庭月收入");
            put("revenue", "年收入");
            put("occupation", "职业");
            put("province", "省");
            put("city", "市");
            put("marriage", "婚姻状况");
            put("msn", "MSN");
            put("qq", "QQ");
            put("company", "工作单位");
            put("idType", "证件类型");
            put("idNumber", "证件号码");
            put("interests", "兴趣爱好");
        }};
    }

    public static class Intfc{
        public static class Action{
            public static String CREATE = "add";
            public static String UPDATE = "update";
        }

        public static class Log{
            public static String CREATE = "用户名为[{0}]创建了帐号";
        }
    }

    public static class MemcachePrefix{
        public static final String COM_MEMBER_BASE = "uid-%d";
        public static final String BANK_MEMBER_BASE = "BANK_{0}_{1}_{2}_{3}_{4}";
    }

    public static class ElementGroup{
        /** 性别 **/
        public static final String SEX = "SEX";
        /** 婚姻状况 **/
        public static final String MARRIAGE = "MARRIAGE";
        /** 证件类型 **/
        public static final String ID_TYPE = "ID_TYPE";
        /** 职业 **/
        public static final String OCCUPATION = "OCCUPATION";
        /** 学历 **/
        public static final String EDUCATION = "EDUCATION";
        /** 兴趣爱好 **/
        public static final String HOBBY = "HOBBY";
        /** 血型 **/
        public static final String BLOOD = "BLOOD";
    }

    public static class Level {
        public static String HIGH = "2";
        public static String MEDIUM = "1";
        public static String LOW = "0";
        public static Map<String, String> LEVEL_KEY_VALUE = new LinkedHashMap<String, String>(){{
            put(HIGH, "高");
            put(MEDIUM, "中");
            put(LOW, "低");
        }};
    }

    public static class Monitoring{
        public static class Type{
            public static int INTFC = 0;
            public static int SERV = 1;
        }
        public static String DO_DEAL = "doDeal";
        public static String DO_DEAL_HST = "doDealHst";

        public static class Content{
            public static String CPU_TMPL = "{0}{1}CPU使用率达到{2}%";
            public static String MEM_TMPL = "{0}{1}内存使用率达到{2}M";
            public static String DISK_TMPL = "{0}{1}硬盘空间剩余{2}M";
            public static String DISCONN = "{0}{1}无法连通";

            public static String INTFC_MSG = "{0}{1}分钟内调用{2}{3}次";
        }
    }
}
