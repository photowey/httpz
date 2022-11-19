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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code ConfigMap}
 *
 * @author photowey
 * @date 2022/11/19
 * @since 1.0.0
 */
public class ConfigMap implements Serializable {

    private static final long serialVersionUID = 2688497152601020417L;
    private Httpz httpz = new Httpz();

    public static class Httpz implements Serializable {

        private Map<String, String> context = new HashMap<>(1 << 3);

        public Map<String, String> getContext() {
            return context;
        }

        public void setContext(Map<String, String> context) {
            this.context = context;
        }

        public String get(String key) {
            return this.getContext().get(key);
        }
    }

    public Httpz getHttpz() {
        return httpz;
    }

    public void setHttpz(Httpz httpz) {
        this.httpz = httpz;
    }
}
