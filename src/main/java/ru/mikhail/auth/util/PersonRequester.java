package ru.mikhail.auth.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersonRequester {
    private final CloseableHttpClient httpClient;
    private String address = "";
    public PersonRequester() {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("application.properties");
        try {
            prop.load(in);
            address = (String) prop.get("request.address");
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient = HttpClients.createDefault();
    }

    public String executeGetRequest(){
        final HttpUriRequest httpGet = new HttpGet(address +"get_all");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        HttpEntity entity = response.getEntity();
        String strResponse = null;
        try {
            strResponse = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }
    public void executePostRequest(String name, String email, String password){
        final HttpPost httpPost = new HttpPost(address + "add");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            CloseableHttpResponse response2 = httpClient.execute(httpPost);
            final HttpEntity entity2 = response2.getEntity();
            System.out.println(EntityUtils.toString(entity2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection(){
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
