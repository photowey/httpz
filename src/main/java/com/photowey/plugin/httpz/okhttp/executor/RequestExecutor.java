/**
 * Copyright © 2022 the original author or authors
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

import com.photowey.plugin.httpz.okhttp.context.RequestContext;
import com.photowey.plugin.httpz.okhttp.enums.HttpMethod;
import com.photowey.plugin.httpz.okhttp.model.RequestHeaders;
import com.photowey.plugin.httpz.okhttp.model.RequestParameters;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.IOException;

/**
 * {@code RequestExecutor}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public interface RequestExecutor {

    String HTTPS = "https";
    String HTTPS_TLS = "TLS";

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");

    // --------------------------------------------------------- get

    String doGet(String url);

    String doGet(String url, RequestParameters parameters);

    String doGet(String url, RequestHeaders requestHeaders);

    String doGet(String url, RequestParameters parameters, RequestHeaders headers);

    // --------------------------------------------------------- post-form

    String doPost(String url, RequestParameters parameters);

    String doPost(String url, RequestParameters parameters, RequestHeaders requestHeaders);

    // --------------------------------------------------------- post-json

    String doPost(String url, String body);

    String doPost(String url, String body, RequestHeaders requestHeaders);

    Response doPostr(String url, String body) throws IOException;

    Response doPostr(String url, String body, RequestHeaders requestHeaders) throws IOException;

    // --------------------------------------------------------- put-form

    String doPut(String url, RequestParameters parameters);

    String doPut(String url, RequestParameters parameters, RequestHeaders requestHeaders);

    // --------------------------------------------------------- put-json

    String doPut(String url, String body);

    String doPut(String url, String body, RequestHeaders requestHeaders);

    Response doPutr(String url, String body) throws IOException;

    Response doPutr(String url, String body, RequestHeaders requestHeaders) throws IOException;

    // --------------------------------------------------------- patch-form

    String doPatch(String url, RequestParameters parameters);

    String doPatch(String url, RequestParameters parameters, RequestHeaders requestHeaders);

    // --------------------------------------------------------- patch-json

    String doPatch(String url, String body);

    String doPatch(String url, String body, RequestHeaders requestHeaders);

    Response doPatchr(String url, String body) throws IOException;

    Response doPatchr(String url, String body, RequestHeaders requestHeaders) throws IOException;

    // --------------------------------------------------------- delete-form

    String doDelete(String url, RequestParameters parameters);

    String doDelete(String url, RequestParameters parameters, RequestHeaders requestHeaders);

    // --------------------------------------------------------- patch-json

    String doDelete(String url, String body);

    String doDelete(String url, String body, RequestHeaders requestHeaders);

    Response doDeleter(String url, String body) throws IOException;

    Response doDeleter(String url, String body, RequestHeaders requestHeaders) throws IOException;

    // --------------------------------------------------------- combine

    /**
     * 聚合请求
     * 支持 {@link HttpMethod} 多类请求
     *
     * @param context {@code RequestContext}
     * @return 响应字符串
     */
    String execute(RequestContext context);

    Response executeResponse(RequestContext context);

}