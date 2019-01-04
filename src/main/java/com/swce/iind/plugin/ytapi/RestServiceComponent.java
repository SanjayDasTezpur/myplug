package com.swce.iind.plugin.ytapi;

import com.squareup.okhttp.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

public class RestServiceComponent {
    private static  String SERVER ;
    private static  String PORT ;
    private static  String YTENDPOINT;
    private String FETCHBUGURL;
    OkHttpClient client;
    static {

    }

    public RestServiceComponent() {
        client = new OkHttpClient();
        Map<String, String> ytCred = YTParser.getYTCred();
        SERVER = ytCred.get("host");
        PORT = ytCred.get("port");
        YTENDPOINT = SERVER + ":" + PORT;
        FETCHBUGURL = YTENDPOINT + "/rest/issue/";
    }


    public Request.Builder buildRequest(String url, String usr, String pass) {
        url = null != url ? url : FETCHBUGURL;
        RequestBody body = RequestBody.create(null, new byte[0]);
        Request.Builder request = new Request.Builder()
                .addHeader("Authorization", Credentials.basic(usr,pass))
                .url(url); // "http://localhost:8081/clients"
        return request;
    }
    public Response runPost(String url, String body, String usr, String pass)
    {
        MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        Request.Builder builder = buildRequest(url,usr,pass);
        builder.post(RequestBody.create(FORM, body));
        Request request = builder.build();
        return executeRequest(request);
    }

    @Nullable
    private Response executeRequest(Request request) {
        try {
            Response response = client.newCall(request).execute();
            if(response.code() != 200)
            {
                System.err.println(response.message());
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response runGet(String url, String usr, String pass)
    {
        Request.Builder builder = buildRequest(url,usr,pass);
        Request request = builder.build();
        return executeRequest(request);
    }
    public String getBugDetails(String sBugID,String usr, String pass)
    {
        Response response = runGet(FETCHBUGURL + sBugID,usr,pass);
        String xml = null;
        try {
            xml = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return YTParser.getSummery(xml);
    }

    public String addYTComment(String sBugID, String sComment, String usr, String pass)
    {
        Response response = runPost(FETCHBUGURL + sBugID + "/execute","comment="+sComment, usr, pass);
        String xml = null;
        try {
            xml = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return YTParser.getSummery(xml);
    }
}
