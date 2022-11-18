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
package com.photowey.plugin.httpz.lang.psi;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@code HttpzCommandDocumentProvider}
 *
 * @author photowey
 * @date 2022/11/18
 * @since 1.0.0
 */
public class HttpzCommandDocumentProvider implements DocumentationProvider {

    @Override
    public @Nullable
    String generateHoverDoc(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
        switch (element.getText().toLowerCase()) {
            case "-h":
            case "--header":
                return this.headerDoc();
            case "-d":
            case "--data":
                return this.dataDoc();
            case "-q":
            case "--query":
                return this.queryDoc();
            case "-a":
            case "--user-agent":
                return this.uaDoc();
            case "-e":
            case "--env":
                return this.envDoc();
            case "-p":
            case "--profile":
                return this.profileDoc();
            case "-c":
            case "--config":
                return this.configDoc();
            case "-f":
            case "--file":
                return this.fileDoc();
            case "-o":
            case "--output":
                return this.outputDoc();
            default:
                return this.syntax();
        }
    }

    @Override
    public @Nullable
    PsiElement getCustomDocumentationElement(@NotNull Editor editor, @NotNull PsiFile file, @Nullable PsiElement contextElement, int targetOffset) {
        return contextElement;
    }

    private String syntax() {
        return "";
    }

    private String headerDoc() {
        return "";
    }

    private String dataDoc() {
        return "";
    }

    private String queryDoc() {
        return "";
    }

    private String uaDoc() {
        return "";
    }

    private String envDoc() {
        return "";
    }

    private String profileDoc() {
        return "";
    }

    private String configDoc() {
        return "";
    }

    private String fileDoc() {
        return "";
    }

    private String outputDoc() {
        return "";
    }
}