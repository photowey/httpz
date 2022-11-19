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
package com.photowey.plugin.httpz.ast.merge;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code MergeMap}
 *
 * @author photowey
 * @date 2022/11/20
 * @since 1.0.0
 */
public final class MergeMap {

    private MergeMap() {
        // utility class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static <K, V> void walk(Map<K, V> m1, Map<K, V> m2) {
        if (m1 == null || m2 == null) {
            return;
        }

        m1.putAll(m2);
    }

    public static <V> V cascadeGet(Map<String, V> m1, String key) {
        if (m1 == null || StringUtils.isBlank(key)) {
            return null;
        }

        int index = 0;
        List<String> keyArr = Stream.of(key.split("\\.")).collect(Collectors.toList());
        V next = m1.get(keyArr.get(index));
        while (index < keyArr.size() - 1) {
            if (null != next && next instanceof Map) {
                next = ((Map<String, V>) next).get(keyArr.get(index + 1));
                index++;
            } else {
                break;
            }
        }

        if (index < (keyArr.size() - 1)) {
            return null;
        }

        return next;
    }

}
