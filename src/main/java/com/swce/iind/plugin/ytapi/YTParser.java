package com.swce.iind.plugin.ytapi;

import com.swce.iind.plugin.cred.Cred;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sanjayda on 8/16/2018 at 10:10 PM
 */
public class YTParser {

    public static String  getSummery(String xml)
    {
        String summery = "";
        try {
            JSONObject jsonObject = XML.toJSONObject(xml);
            JSONObject issue = (JSONObject) jsonObject.get("issue");
            JSONArray field = (JSONArray) issue.get("field");
            for (Object attr : field)
            {
                String name = (String) ((JSONObject) attr).get("name");
                if(name.equalsIgnoreCase("summary"))
                {
                    summery = (String) ((JSONObject) attr).get("value");
                }

            }
        } catch (JSONException e){
        } catch (Exception e){
        }
        return summery;
    }

    public static String  getYTIssueFieldValue(String xml, String sField)
    {
        String summery = "";
        try {
            JSONObject jsonObject = XML.toJSONObject(xml);
            JSONObject issue = (JSONObject) jsonObject.get("issue");
            JSONArray field = (JSONArray) issue.get("field");
            for (Object attr : field)
            {
                String name = (String) ((JSONObject) attr).get("name");
                if(name.equalsIgnoreCase(sField))
                {
                    summery = (String) ((JSONObject) attr).get("value");
                }

            }
        } catch (JSONException e){
        } catch (Exception e){
        }
        return summery;
    }

    public static Map<String,String > getYTCred()
    {
        String currentUsersHomeDir = System.getProperty("user.home");
        String ytFile = currentUsersHomeDir + "/.yt";

        Map<String,String > lstYTCred = new HashMap<>();

        FileInputStream fi = null;
        ObjectInputStream oi = null;
        Cred cred = null;
        try {
            fi = new FileInputStream(new File(ytFile));
            oi = new ObjectInputStream(fi);
            cred = (Cred) oi.readObject();
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        lstYTCred.put("username",cred.getUSERNAME());
        lstYTCred.put("password",cred.getPASSWORD());
        lstYTCred.put("host",cred.getHOST());
        lstYTCred.put("port",""+cred.getPORT());
        return lstYTCred;
    }

}

