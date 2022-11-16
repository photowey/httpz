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
package com.photowey.plugin.httpz.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.photowey.plugin.httpz.icon.HttpzIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * {@code HttpzFileType}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class HttpzFileType extends LanguageFileType {

    public static final HttpzFileType INSTANCE = new HttpzFileType();

    public HttpzFileType() {
        super(HttpzLanguage.getInstance());
    }

    public static HttpzFileType getInstance() {
        return INSTANCE;
    }

    @Override
    public @NotNull
    String getName() {
        return "HTTPZ";
    }

    @Override
    public @NotNull
    String getDescription() {
        return "Http client HTTPZ language file";
    }

    @Override
    public @NotNull
    String getDefaultExtension() {
        return "httpz";
    }

    @Override
    public @Nullable
    Icon getIcon() {
        return HttpzIcons.ICON;
    }
}