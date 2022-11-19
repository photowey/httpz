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
package com.photowey.plugin.httpz.ast;

import com.fasterxml.jackson.annotation.JsonMerge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code FileMap}
 *
 * @author photowey
 * @date 2022/11/19
 * @since 1.0.0
 */
public class FileMap implements Serializable {

    private static final long serialVersionUID = 2688497152601020417L;

    @JsonMerge
    private Httpz httpz = new Httpz();

    public static class Httpz implements Serializable {

        @JsonMerge
        private Map<String, String> context = new HashMap<>(1 << 3);

        @JsonMerge
        private List<Request> requests = new ArrayList<>();

        public Map<String, String> getContext() {
            return context;
        }

        public void setContext(Map<String, String> context) {
            this.context = context;
        }

        public String get(String key) {
            return this.getContext().get(key);
        }

        public List<Request> getRequests() {
            return requests;
        }

        public void setRequests(List<Request> requests) {
            this.requests = requests;
        }
    }

    public static class Request implements Serializable {

        private static final long serialVersionUID = 865345716817732786L;

        private String profile;
        private String env;
        private String url;
        private String method;

        private Map<String, String> parameters;
        private Map<String, String> headers;
        private Map<String, Object> body;

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public Map<String, Object> getBody() {
            return body;
        }

        public void setBody(Map<String, Object> body) {
            this.body = body;
        }
    }

    public Httpz getHttpz() {
        return httpz;
    }

    public void setHttpz(Httpz httpz) {
        this.httpz = httpz;
    }
}
