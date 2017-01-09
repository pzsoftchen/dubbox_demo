package com.daydao.dubbox.rest.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * HttpUtils
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.service
 * @date 2017/1/9/14:54
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static String postJsonStr(String url, String jsonStr) throws Exception {

        String res = null;
        try {

            logger.info("postJsonStr url:{}, param:{}", url, jsonStr);
            URI uri = new URI(url);
            HttpPost httpPost = new HttpPost(uri);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);

            HttpResponse response = httpclient.execute(httpPost);
            res = EntityUtils.toString(response.getEntity());
            logger.info("请求返回结果集:{}", res);

        }catch (Exception e){
            logger.error("postJsonStr exception error,{}", e);
        }
        return res;
    }

    public static String get(String url) throws Exception {

        String res = null;
        try {

            logger.info("postJsonStr url:{}", url);
            URI uri = new URI(url);
            HttpGet httpget = new HttpGet(uri);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(httpget);
            res = EntityUtils.toString(response.getEntity());
            logger.info("请求返回结果集:{}", res);

        }catch (Exception e){
            logger.error("postJsonStr exception error,{}", e);
        }
        return res;
    }
}
