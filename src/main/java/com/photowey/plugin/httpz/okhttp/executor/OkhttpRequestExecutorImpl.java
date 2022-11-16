/**
 * Copyright © 2022 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.plugin.httpz.okhttp.executor;

import com.photowey.plugin.httpz.App;
import com.photowey.plugin.httpz.constant.HttpzConstants;
import com.photowey.plugin.httpz.okhttp.context.RequestContext;
import com.photowey.plugin.httpz.okhttp.enums.BodyType;
import com.photowey.plugin.httpz.okhttp.enums.HttpMethod;
import com.photowey.plugin.httpz.okhttp.model.RequestHeaders;
import com.photowey.plugin.httpz.okhttp.model.RequestParameters;
import com.photowey.plugin.httpz.okhttp.trust.TrustAnyHostnameVerifier;
import com.photowey.plugin.httpz.okhttp.trust.TrustAnyTrustManager;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * {@code OkhttpRequestExecutorImpl}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class OkhttpRequestExecutorImpl implements OkhttpRequestExecutor {

    private final OkHttpClient okHttpClient;

    public OkhttpRequestExecutorImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public String doGet(String url) {
        return this.doGet(url, new RequestParameters(), new RequestHeaders());
    }

    @Override
    public String doGet(String url, RequestParameters parameters) {
        return this.doGet(url, parameters, new RequestHeaders());
    }

    @Override
    public String doGet(String url, RequestHeaders requestHeaders) {
        return this.doGet(url, new RequestParameters(), requestHeaders);
    }

    @Override
    public String doGet(String url, RequestParameters parameters, RequestHeaders headers) {
        StringBuilder queryStrings = new StringBuilder(url);
        this.populateParams(parameters, queryStrings);
        Request.Builder builder = new Request.Builder();
        this.populateHeaders(headers, builder);
        Request request = builder.url(queryStrings.toString()).build();

        return this.syncExecute(request);
    }

    // ----------------------------------------------------------

    /**
     * 表单形式的 {@code POST}
     */
    @Override
    public String doPost(String url, RequestParameters parameters) {
        return this.doPost(url, parameters, new RequestHeaders());
    }

    @Override
    public String doPost(String url, RequestParameters parameters, RequestHeaders requestHeaders) {
        FormBody.Builder builder = new FormBody.Builder();
        parameters.get().forEach((k, v) -> builder.add(k, v.toString()));

        Request.Builder postBuilder = new Request.Builder().url(url).post(builder.build());
        this.populateHeaders(requestHeaders, postBuilder);

        Request request = postBuilder.build();

        return this.syncExecute(request);
    }

    // ----------------------------------------------------------

    @Override
    public String doPost(String url, String body) {
        return this.executePost(url, body, JSON);
    }

    @Override
    public String doPost(String url, String body, RequestHeaders requestHeaders) {
        return this.executePost(url, body, JSON, requestHeaders);
    }

    @Override
    public Response doPostr(String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        return this.executeRequest(this.okHttpClient, request);
    }

    @Override
    public Response doPostr(String url, String body, RequestHeaders requestHeaders) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        this.populateHeaders(requestHeaders, builder);

        Request request = builder.build();

        return this.executeRequest(this.okHttpClient, request);
    }

    // ----------------------------------------------------------

    @Override
    public String doPut(String url, RequestParameters parameters) {
        return this.doPut(url, parameters, new RequestHeaders());
    }

    @Override
    public String doPut(String url, RequestParameters parameters, RequestHeaders requestHeaders) {
        FormBody.Builder builder = new FormBody.Builder();
        parameters.get().forEach((k, v) -> builder.add(k, v.toString()));

        Request.Builder requestBuilder = new Request.Builder().url(url).put(builder.build());
        this.populateHeaders(requestHeaders, requestBuilder);

        Request request = requestBuilder.build();

        return this.syncExecute(request);
    }

    @Override
    public String doPut(String url, String body) {
        return this.executePut(url, body, JSON);
    }

    @Override
    public String doPut(String url, String body, RequestHeaders requestHeaders) {
        return this.executePut(url, body, JSON, requestHeaders);
    }

    @Override
    public Response doPutr(String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder().url(url).put(requestBody).build();

        return this.executeRequest(this.okHttpClient, request);
    }

    @Override
    public Response doPutr(String url, String body, RequestHeaders requestHeaders) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request.Builder requestBuilder = new Request.Builder().url(url).put(requestBody);
        this.populateHeaders(requestHeaders, requestBuilder);

        Request request = requestBuilder.build();

        return this.executeRequest(this.okHttpClient, request);
    }

    // ----------------------------------------------------------

    @Override
    public String doPatch(String url, RequestParameters parameters) {
        return this.doPatch(url, parameters, new RequestHeaders());
    }

    @Override
    public String doPatch(String url, RequestParameters parameters, RequestHeaders requestHeaders) {
        FormBody.Builder builder = new FormBody.Builder();
        parameters.get().forEach((k, v) -> builder.add(k, v.toString()));

        Request.Builder requestBuilder = new Request.Builder().url(url).patch(builder.build());
        this.populateHeaders(requestHeaders, requestBuilder);

        Request request = requestBuilder.build();

        return this.syncExecute(request);
    }

    @Override
    public String doPatch(String url, String body) {
        return this.executePatch(url, body, JSON);
    }

    @Override
    public String doPatch(String url, String body, RequestHeaders requestHeaders) {
        return this.executePatch(url, body, JSON, requestHeaders);
    }

    @Override
    public Response doPatchr(String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder().url(url).patch(requestBody).build();

        return this.executeRequest(this.okHttpClient, request);
    }

    @Override
    public Response doPatchr(String url, String body, RequestHeaders requestHeaders) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request.Builder requestBuilder = new Request.Builder().url(url).patch(requestBody);
        this.populateHeaders(requestHeaders, requestBuilder);

        Request request = requestBuilder.build();

        return this.executeRequest(this.okHttpClient, request);
    }

    // ----------------------------------------------------------

    @Override
    public String doDelete(String url, RequestParameters parameters) {
        return this.doDelete(url, parameters, new RequestHeaders());
    }

    @Override
    public String doDelete(String url, RequestParameters parameters, RequestHeaders requestHeaders) {
        FormBody.Builder builder = new FormBody.Builder();
        parameters.get().forEach((k, v) -> builder.add(k, v.toString()));

        Request.Builder requestBuilder = new Request.Builder().url(url).delete(builder.build());
        this.populateHeaders(requestHeaders, requestBuilder);

        Request request = requestBuilder.build();

        return this.syncExecute(request);
    }

    @Override
    public String doDelete(String url, String body) {
        return this.executeDelete(url, body, JSON);
    }

    @Override
    public String doDelete(String url, String body, RequestHeaders requestHeaders) {
        return this.executeDelete(url, body, JSON, requestHeaders);
    }

    @Override
    public Response doDeleter(String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder().url(url).delete(requestBody).build();

        return this.executeRequest(this.okHttpClient, request);
    }

    @Override
    public Response doDeleter(String url, String body, RequestHeaders requestHeaders) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request.Builder postBuilder = new Request.Builder().url(url).delete(requestBody);
        this.populateHeaders(requestHeaders, postBuilder);

        Request request = postBuilder.build();

        return this.executeRequest(this.okHttpClient, request);
    }

    // ----------------------------------------------------------

    @Override
    public String execute(RequestContext context) {
        Response response = this.executeResponse(context);

        return this.handleResponse(response);
    }

    @Override
    public Response executeResponse(RequestContext context) {
        HttpMethod httpMethod = context.getHttpMethod();
        Request.Builder builder = this.populateRequest(httpMethod, context);

        OkHttpClient okHttpClient = this.okHttpClient;
        if (context.getNewClient()) {
            OkHttpClient.Builder newBuilder = this.determineClient(context.getUrl());
            this.preBuildClient(newBuilder);
            okHttpClient = this.buildOkHttpClient(newBuilder);
        }

        this.preRequest(okHttpClient, builder);

        this.preHandle(context, builder);
        Request request = builder.build();

        try {
            return this.executeRequest(okHttpClient, request);
        } catch (IOException e) {
            throw new RuntimeException(String.format("execute httpz request:[%s] exception", this.parseUrl(request)), e);
        }
    }

    @Override
    public Request.Builder populateRequest(HttpMethod httpMethod, RequestContext context) {
        BodyType bodyType = context.getBodyType();
        MediaType mediaType = BodyType.JSON.equals(bodyType) ? JSON : FORM;
        String url = context.getUrl();

        RequestBody body = this.determineRequestBody(context, mediaType);

        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("illegal parameter:url");
        }

        Request.Builder builder = new Request.Builder().url(url);
        switch (httpMethod) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case PATCH:
                builder.patch(body);
                break;
            case DELETE:
                builder.delete(body);
                break;
            default:
                break;
        }

        RequestHeaders headers = context.getHeaders();
        this.populateHeaders(headers, builder);

        return builder;
    }

    // ---------------------------------------------------------- EXEC

    @Override
    public Response executeRequest(OkHttpClient okHttpClient, Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    @Override
    public void executeRequest(OkHttpClient okHttpClient, Request request, Consumer<Response> callback) throws IOException {
        try (Response response = this.executeRequest(okHttpClient, request)) {
            callback.accept(response);
        }
    }

    // ----------------------------------------------------------

    private void preHandle(RequestContext context, Request.Builder builder) {
        // do pre
    }

    private String handleResponse(final Request request, OkHttpClient okHttpClient, RequestContext context) {
        try (Response response = this.executeRequest(okHttpClient, request)) {
            this.postHandle(context, response);

            return this.populateResponseString(response);
        } catch (Exception e) {
            throw new RuntimeException(String.format("execute httpz request:[%s] exception", this.parseUrl(request)), e);
        }
    }

    private String handleResponse(Response response) {
        try {
            return this.populateResponseString(response);
        } catch (Exception e) {
            throw new RuntimeException("execute httpz request exception", e);
        }
    }

    private void postHandle(RequestContext context, Response response) {
        // do post
    }

    // ----------------------------------------------------------

    protected void preRequest(OkHttpClient okHttpClient, Request.Builder builder) {
        // do some for subclass if necessary
    }

    protected OkHttpClient buildOkHttpClient(OkHttpClient.Builder builder) {
        return builder.build();
    }

    protected void preBuildClient(OkHttpClient.Builder builder) {
        // do some for subclass if necessary
    }

    // ---------------------------------------------------------- REQUEST

    protected Request.Builder populateGetRequest(String url) {
        return this.populateGetRequest(new RequestContext(url));
    }

    protected Request.Builder populateGetRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.GET, context);
    }

    protected Request.Builder populatePostRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.POST, context);
    }

    protected Request.Builder populatePuttRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.PUT, context);
    }

    protected Request.Builder populatePatchRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.PATCH, context);
    }

    protected Request.Builder populateDeleteRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.DELETE, context);
    }

    // ---------------------------------------------------------- CLIENT

    public OkHttpClient.Builder determineClient(String url) throws RuntimeException {
        OkHttpClient.Builder builder = this.populateOkHttpClient(url);

        return builder;
    }

    public OkHttpClient.Builder populateOkHttpClient(final String url) {
        // @formatter:off
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .hostnameVerifier(this.trustAnyHostnameVerifier())
                // default
                .connectTimeout(3_000, TimeUnit.SECONDS)
                .readTimeout(30_000, TimeUnit.SECONDS)
                .writeTimeout(30_000, TimeUnit.SECONDS);
        // @formatter:on

        if (url.contains(HTTPS)) {
            SSLSocketFactory sslSocketFactory = this.createIgnoreVerifySSL();
            builder.sslSocketFactory(sslSocketFactory, this.trustAnyTrustManager());
        }

        return builder;
    }

    private RequestBody determineRequestBody(RequestContext context, MediaType mediaType) {

        if (HttpMethod.GET.equals(context.getHttpMethod())) {
            return null;
        }

        RequestBody body = RequestBody.create(mediaType, context.getRequestBody());
        if (BodyType.FORM.equals(context.getBodyType())) {
            FormBody.Builder builder = new FormBody.Builder();
            if (context.getParameters().get().keySet().size() > 0) {
                for (String key : context.getParameters().get().keySet()) {
                    builder.add(key, context.getParameters().get(key));
                }
            }

            body = builder.build();
        }

        return body;
    }

    // ---------------------------------------------------------- SSL

    private SSLSocketFactory createIgnoreVerifySSL() {
        try {
            TrustManager[] tm = {this.trustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance(HTTPS_TLS);
            sslContext.init(null, tm, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("handle the https SSLSocketFactory exception", e);
        }
    }

    // ----------------------------------------------------------

    private TrustAnyTrustManager trustAnyTrustManager() {
        App app = App.getInstance();

        return app.getHttpzPluginConfigure().getTrustAnyTrustManager();
    }

    private TrustAnyHostnameVerifier trustAnyHostnameVerifier() {
        App app = App.getInstance();

        return app.getHttpzPluginConfigure().getTrustAnyHostnameVerifier();
    }

    // ----------------------------------------------------------

    private String executePost(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, (builder -> builder.post(requestBody)));
    }

    private String executePost(String url, String data, MediaType contentType, RequestHeaders requestHeaders) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, requestHeaders, (builder -> builder.post(requestBody)));
    }

    // ----------------------------------------------------------

    private String executePut(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, (builder -> builder.put(requestBody)));
    }

    private String executePut(String url, String data, MediaType contentType, RequestHeaders requestHeaders) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, requestHeaders, (builder -> builder.put(requestBody)));
    }

    // ----------------------------------------------------------

    private String executePatch(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, (builder -> builder.patch(requestBody)));
    }

    private String executePatch(String url, String data, MediaType contentType, RequestHeaders requestHeaders) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, requestHeaders, (builder -> builder.patch(requestBody)));
    }

    // ----------------------------------------------------------

    private String executeDelete(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, (builder -> builder.delete(requestBody)));
    }

    private String executeDelete(String url, String data, MediaType contentType, RequestHeaders requestHeaders) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        return this.executeMethod(url, requestHeaders, (builder -> builder.delete(requestBody)));
    }

    // ----------------------------------------------------------

    private String executeMethod(String url, Consumer<Request.Builder> fx) {
        Request.Builder builder = new Request.Builder().url(url);
        fx.accept(builder);

        Request request = builder.build();

        return this.syncExecute(request);
    }

    private String executeMethod(String url, RequestHeaders requestHeaders, Consumer<Request.Builder> fx) {
        Request.Builder builder = new Request.Builder().url(url);
        fx.accept(builder);

        this.populateHeaders(requestHeaders, builder);

        Request request = builder.build();

        return this.syncExecute(request);
    }

    // ----------------------------------------------------------

    private String syncExecute(Request request) {
        return this.syncExecute(this.okHttpClient, request);
    }

    private String syncExecute(OkHttpClient okHttpClient, Request request) {
        try (Response response = this.executeRequest(okHttpClient, request)) {
            return this.populateResponseString(response);
        } catch (Exception e) {
            throw new RuntimeException("Request fail", e);
        }
    }

    private String parseUrl(Request request) {
        return request.url().toString();
    }

    private String populateResponseString(Response response) throws IOException {
        String result = null != response.body() ? response.body().string() : HttpzConstants.EMPTY_STRING;
        if (response.isSuccessful()) {
            return result;
        }

        throw new RuntimeException("Request fail");
    }

    // ---------------------------------------------------------- HEADERS

    private void populateHeaders(RequestHeaders requestHeaders, Request.Builder builder) {
        if (requestHeaders != null && requestHeaders.get().keySet().size() > 0) {
            Set<String> keySet = requestHeaders.get().keySet();
            for (String key : keySet) {
                Object hv = requestHeaders.get(key);
                builder.addHeader(key, hv.toString());
            }
        }
    }

    private void populateParams(RequestParameters requestParameters, StringBuilder queryStrings) {
        if (requestParameters != null && requestParameters.get().keySet().size() > 0) {
            boolean firstAt = true;
            for (String key : requestParameters.get().keySet()) {
                Object value = requestParameters.get(key);
                if (firstAt) {
                    queryStrings.append("?").append(key).append("=").append(value.toString());
                    firstAt = false;
                } else {
                    queryStrings.append("&").append(key).append("=").append(value.toString());
                }
            }
        }
    }
}
