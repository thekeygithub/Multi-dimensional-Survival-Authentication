package com.xcuni.guizhouyl.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;


public class RestClientConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestClientConfig.class);

    private class CustomErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            LOGGER.error("HTTP unknown error!");
            throw new IOException("Rest Client exception!");
        }
    }


    private ClientHttpRequestFactory httpRequestFactory() {
        //return new HttpComponentsClientHttpRequestFactory(httpClient());
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient());
//        factory.setReadTimeout(2000);
//        factory.setConnectTimeout(2000);
        factory.setReadTimeout(0);
        factory.setConnectTimeout(0);
        return factory;
    }


    public RestTemplate restTemplate() {
        ClientHttpRequestFactory clientReqFac = this.httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(clientReqFac);
        restTemplate.setErrorHandler(new CustomErrorHandler());
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        restTemplate.getMessageConverters().add(m);//   additionalMessageConverters(m).build();
        //return new RestTemplate(httpRequestFactory());
        return restTemplate;
    }


    private HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//        connectionManager.setMaxTotal(5);
//        connectionManager.setDefaultMaxPerRoute(5);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(200);

        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(8000)
//                .setConnectTimeout(8000)
//                .setConnectionRequestTimeout(8000)
                .setSocketTimeout(0)
                .setConnectTimeout(0)
                .setConnectionRequestTimeout(0)
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

}