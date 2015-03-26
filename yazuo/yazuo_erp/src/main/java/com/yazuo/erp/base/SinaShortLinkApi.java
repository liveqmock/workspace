package com.yazuo.erp.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 */
public class SinaShortLinkApi {
    public static String getShortLink(String url) {
        return getJson(url).get("urls").get(0).get("url_short").getTextValue();
    }


    protected static JsonNode getJson( String longUrl) {
        HttpGet get = new HttpGet("https://api.weibo.com/2/short_url/shorten.json?url_long=" + longUrl+"&source=1751316174" );
        HttpContext context = HttpCoreContext.create();
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        JsonNode node = null;
        try {
            response = client.execute(get, context);
            ObjectMapper mapper = new ObjectMapper();
            node = mapper.readTree(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
}
