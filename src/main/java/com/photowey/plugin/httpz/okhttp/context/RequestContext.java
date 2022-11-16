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
package com.photowey.plugin.httpz.okhttp.context;

import com.photowey.plugin.httpz.okhttp.enums.BodyType;
import com.photowey.plugin.httpz.okhttp.enums.HttpMethod;
import com.photowey.plugin.httpz.okhttp.model.RequestHeaders;
import com.photowey.plugin.httpz.okhttp.model.RequestParameters;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.net.URL;

/**
 * {@code RequestContext}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class RequestContext implements Serializable {

    private String url;
    private String requestBody;
    private RequestHeaders headers = new RequestHeaders();
    private RequestParameters parameters = new RequestParameters();
    private HttpMethod httpMethod = HttpMethod.GET;
    private BodyType bodyType = BodyType.JSON;

    private Boolean newClient = Boolean.FALSE;

    public RequestContext() {
    }

    public RequestContext(String url) {
        this.url = url;
    }

    public RequestContext(String url, HttpMethod httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public RequestContext(String url, String requestBody, HttpMethod httpMethod) {
        this.url = url;
        this.requestBody = requestBody;
        this.httpMethod = httpMethod;
    }

    public BodyType getBodyType() {
        return null == bodyType ? BodyType.JSON : bodyType;
    }

    public RequestHeaders getHeaders() {
        return null == headers ? new RequestHeaders() : headers;
    }

    public RequestParameters getParameters() {
        return null == parameters ? new RequestParameters() : parameters;
    }

    public HttpMethod getHttpMethod() {
        return null == httpMethod ? HttpMethod.GET : httpMethod;
    }

    public String schema() {
        if (StringUtils.isEmpty(this.getUrl())) {
            return "";
        }
        try {
            URL url = new URL(this.getUrl());
            return url.getProtocol();
        } catch (Exception ignored) {
        }

        throw new RuntimeException(String.format("parse the url:[%s] for protocol exception", this.getUrl()));
    }

    public String host() {
        if (StringUtils.isEmpty(this.getUrl())) {
            return "";
        }
        try {
            URL url = new URL(this.getUrl());
            return url.getHost();
        } catch (Exception ignored) {
        }

        throw new RuntimeException(String.format("parse the url:[%s] for host exception", this.getUrl()));
    }

    public static RequestContext builder() {
        return new RequestContext();
    }

    public RequestContext url(String url) {
        this.url = url;
        return this;
    }

    public RequestContext requestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public RequestContext headers(RequestHeaders headers) {
        this.headers = headers;
        return this;
    }

    public RequestContext parameters(RequestParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public RequestContext httpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RequestContext bodyType(BodyType bodyType) {
        this.bodyType = bodyType;
        return this;
    }

    public RequestContext newClient(Boolean newClient) {
        this.newClient = newClient;
        return this;
    }

    public RequestContext build() {
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void setHeaders(RequestHeaders headers) {
        this.headers = headers;
    }

    public void setParameters(RequestParameters parameters) {
        this.parameters = parameters;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Boolean getNewClient() {
        return newClient;
    }

    public void setNewClient(Boolean newClient) {
        this.newClient = newClient;
    }
}
