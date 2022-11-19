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
package com.photowey.plugin.httpz.ast.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code YamlParser}
 *
 * @author photowey
 * @date 2022/11/20
 * @since 1.0.0
 */
public class YamlParser {

    private static final ObjectMapper INSTANCE = YamlObjectMapperFactory.createYmlObjectMapper();

    public static <K, V> Map<K, V> walk(InputStream in) {
        try {
            return (Map<K, V>) INSTANCE.readValue(in, LinkedHashMap.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T walk(InputStream in, Class<T> clazz) {
        try {
            return INSTANCE.readValue(in, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getObjectMapperInstance() {
        return INSTANCE;
    }
}
