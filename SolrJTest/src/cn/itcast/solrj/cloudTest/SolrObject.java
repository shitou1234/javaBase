package cn.itcast.solrj.cloudTest;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.up72.rayli.dto.req.ComMemberBaseSearchReq;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2015/12/14.
 */
public class SolrObject {
    public static Object toBean(SolrDocument record , Class clazz){
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Field[] fields =   clazz.getDeclaredFields();
        for(Field field:fields){
            Object value = record.get(field.getName());
            try {
                BeanUtils.setProperty(o, field.getName(), value);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return o;
    }

    public static List toBeanList(SolrDocumentList records, Class  clazz){
        List list = new ArrayList();
        for(SolrDocument record : records){
            list.add(toBean(record,clazz));
        }
        return list;
    }

    public static String jsonToSolrQuery(String json){
        Pattern pattern = Pattern.compile("(?<!\\\\)\\[.*?(?<!\\\\)\\]");
        Matcher matcher = pattern.matcher(json);
        while(matcher.find()) {
            String temp = matcher.group();
            json = json.replace(temp,temp.replaceAll("(?<!\\\\),"," OR "));
        }

        return json.replaceAll("(?<!\\\\)(\"|Array|\\{|})", "").replaceAll("(?<!\\\\),", " AND ")
                .replaceAll("(?<!\\\\)\\[", "(").replaceAll("(?<!\\\\)]", ")")
                .replaceAll("(\\w+)Start:(\\d+|\\*) AND (\\w+)End:(\\d+|\\*)", "$1:[$2 TO $4]");
    }

    public static String escapeQueryChars(String s) {
        if(null == s) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/' || c == ','
                    || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        ComMemberBaseSearchReq b = new ComMemberBaseSearchReq();
//        b.setsId("12313");
//        b.setsType("324243");
//        Long[] l = new Long[2];
//        l[0] = 3L;
//        l[1] = 3L;
////        b.setCartTypeArray(l);
//        try {
//            String outJson = JSON.toJSONString(b, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCheckSpecialChar);
//            System.out.println(outJson);
//            System.out.println("发布量_lStart".matches("(Start|End)$"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
