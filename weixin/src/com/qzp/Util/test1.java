package com.qzp.Util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class test1 {

	public static void main(String[] args) {
        String strJson = "{\"sqls\":[{\"sql\":\"INSERT INTO T_BASE_PERSON (PERSON_ID,PERSON_NAME) VALUES (?,?)\",\"values\":\"0dc11abb-967d-11e3-afc0-000c29c3253b,张三\"},{\"sql\":\"UPDATE T_BASE_CLASS SET CLASS_NAME=? WHERE CLASS_ID=?\",\"values\":\"一年一班,538b7ee7-967d-11e3-afc0-000c29c3253b\"}]}";
        
        JSONObject myObj = JSONObject.parseObject(strJson);
        JSONArray myArray = myObj.getJSONArray("sqls");
        for(int i=0;i<myArray.size();i++)
        {
            JSONObject o = myArray.getJSONObject(i);
            System.out.println(o.get("sql"));
            System.out.println(o.get("values"));
        }
	}
}
