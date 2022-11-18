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
package com.photowey.plugin.httpz.lang;

import com.intellij.psi.tree.TokenSet;

/**
 * {@code HttpzTokenSets}
 *
 * @author photowey
 * @date 2022/11/18
 * @since 1.0.0
 */
public interface HttpzTokenSets {

    TokenSet COMMAND = TokenSet.create(HttpzTypes.COMMAND);

    TokenSet METHOD = TokenSet.create(HttpzTypes.METHOD);

    TokenSet KV = TokenSet.create(HttpzTypes.KV);

    TokenSet OPTION = TokenSet.create(HttpzTypes.OPTION);

    TokenSet OPTION_1 = TokenSet.create(HttpzTypes.OPTION_1);
    TokenSet OPTION_1_STATEMENT = TokenSet.create(HttpzTypes.OPTION_1_STATEMENT);
    TokenSet OPTION_2 = TokenSet.create(HttpzTypes.OPTION_2);
    TokenSet OPTION_2_STATEMENT = TokenSet.create(HttpzTypes.OPTION_2_STATEMENT);
    TokenSet OPTION_3 = TokenSet.create(HttpzTypes.OPTION_3);
    TokenSet OPTION_3_STATEMENT = TokenSet.create(HttpzTypes.OPTION_3_STATEMENT);
    TokenSet OPTION_4 = TokenSet.create(HttpzTypes.OPTION_4);
    TokenSet OPTION_4_STATEMENT = TokenSet.create(HttpzTypes.OPTION_4_STATEMENT);
    TokenSet OPTION_5 = TokenSet.create(HttpzTypes.OPTION_5);
    TokenSet OPTION_5_STATEMENT = TokenSet.create(HttpzTypes.OPTION_5_STATEMENT);
    TokenSet OPTION_6 = TokenSet.create(HttpzTypes.OPTION_6);
    TokenSet OPTION_6_STATEMENT = TokenSet.create(HttpzTypes.OPTION_6_STATEMENT);
    TokenSet OPTION_7 = TokenSet.create(HttpzTypes.OPTION_7);
    TokenSet OPTION_7_STATEMENT = TokenSet.create(HttpzTypes.OPTION_7_STATEMENT);
    TokenSet OPTION_8 = TokenSet.create(HttpzTypes.OPTION_8);
    TokenSet OPTION_8_STATEMENT = TokenSet.create(HttpzTypes.OPTION_8_STATEMENT);
    TokenSet OPTION_9 = TokenSet.create(HttpzTypes.OPTION_9);
    TokenSet OPTION_9_STATEMENT = TokenSet.create(HttpzTypes.OPTION_9_STATEMENT);

    TokenSet BASIC_STRING = TokenSet.create(HttpzTypes.BASIC_STRING);
    TokenSet COMMENT = TokenSet.create(HttpzTypes.COMMENT);
    TokenSet HTTPZ = TokenSet.create(HttpzTypes.HTTPZ);
    TokenSet QUOTED_STRING = TokenSet.create(HttpzTypes.QUOTED_STRING);

    TokenSet URL = TokenSet.create(HttpzTypes.URL);
}
