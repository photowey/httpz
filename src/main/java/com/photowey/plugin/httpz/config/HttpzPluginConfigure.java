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
package com.photowey.plugin.httpz.config;

import com.photowey.plugin.httpz.okhttp.executor.OkhttpRequestExecutorImpl;
import com.photowey.plugin.httpz.okhttp.executor.RequestExecutor;
import com.photowey.plugin.httpz.okhttp.trust.TrustAnyHostnameVerifier;
import com.photowey.plugin.httpz.okhttp.trust.TrustAnyTrustManager;
import com.photowey.plugin.httpz.property.OkHttpProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * {@code HttpzPluginConfigure}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class HttpzPluginConfigure {

    private final TrustAnyTrustManager trustAnyTrustManager;
    private final TrustAnyHostnameVerifier trustAnyHostnameVerifier;
    private final OkHttpProperties okHttpProperties;
    private final RequestExecutor requestExecutor;

    public HttpzPluginConfigure() {
        this.trustAnyTrustManager = new TrustAnyTrustManager();
        this.trustAnyHostnameVerifier = new TrustAnyHostnameVerifier();
        this.okHttpProperties = new OkHttpProperties();
        this.requestExecutor = new OkhttpRequestExecutorImpl(this.buildOkHttpClient());
    }

    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.sslSocketFactory(this.sslSocketFactory(), this.trustAnyTrustManager)
                .retryOnConnectionFailure(true)
                .connectionPool(this.okHttpConnectionPool())
                .connectTimeout(this.okHttpProperties.getOkHttp().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(this.okHttpProperties.getOkHttp().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(this.okHttpProperties.getOkHttp().getWriteTimeout(), TimeUnit.SECONDS)
                .hostnameVerifier(this.trustAnyHostnameVerifier)
                .build();
    }

    private SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{this.trustAnyTrustManager}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("init the SSLSocketFactory instance exception", e);
        }
    }

    private ConnectionPool okHttpConnectionPool() {
        return new ConnectionPool(this.okHttpProperties.getOkHttp().getMaxIdleConnections(),
                this.okHttpProperties.getOkHttp().getKeepAliveDuration(), TimeUnit.SECONDS);
    }

    public TrustAnyTrustManager getTrustAnyTrustManager() {
        return trustAnyTrustManager;
    }

    public TrustAnyHostnameVerifier getTrustAnyHostnameVerifier() {
        return trustAnyHostnameVerifier;
    }

    public OkHttpProperties getOkHttpProperties() {
        return okHttpProperties;
    }

    public RequestExecutor getRequestExecutor() {
        return requestExecutor;
    }
}
