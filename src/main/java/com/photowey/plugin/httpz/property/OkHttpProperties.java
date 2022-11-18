/**
 * Copyright © 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.plugin.httpz.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code OkHttpProperties}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class OkHttpProperties implements Serializable {

    private static final long serialVersionUID = -1150847959044432671L;

    private HttpClient httpClient = new HttpClient();
    private OkHttp okHttp = new OkHttp();

    /**
     * {@code ApacheHttpClient}
     */
    public static class HttpClient implements Serializable {

        private static final long serialVersionUID = -3028473773734246325L;

        private boolean enabled = false;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getWriteTimeout() {
            return writeTimeout;
        }

        public void setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
        }

        public int getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(int maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
        }

        public long getKeepAliveDuration() {
            return keepAliveDuration;
        }

        public void setKeepAliveDuration(long keepAliveDuration) {
            this.keepAliveDuration = keepAliveDuration;
        }
    }

    public static class OkHttp implements Serializable {

        private static final long serialVersionUID = 4674953379945484770L;

        private boolean enabled = true;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;
        private List<String> ignoreApis = new ArrayList<>();

        public OkHttp() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getWriteTimeout() {
            return writeTimeout;
        }

        public void setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
        }

        public int getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(int maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
        }

        public long getKeepAliveDuration() {
            return keepAliveDuration;
        }

        public void setKeepAliveDuration(long keepAliveDuration) {
            this.keepAliveDuration = keepAliveDuration;
        }

        public List<String> getIgnoreApis() {
            return ignoreApis;
        }

        public void setIgnoreApis(List<String> ignoreApis) {
            this.ignoreApis = ignoreApis;
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public OkHttp getOkHttp() {
        return okHttp;
    }
}
