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

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.photowey.plugin.httpz.constant.HttpzConstants;
import com.photowey.plugin.httpz.icon.HttpzIcons;
import com.photowey.plugin.httpz.lang.HttpzFile;
import com.photowey.plugin.httpz.lang.HttpzFileType;
import com.photowey.plugin.httpz.util.APIUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@code HttpzRelatedItemLineMarkerProvider}
 *
 * @author photowey
 * @date 2022/11/18
 * @since 1.0.0
 */
public class HttpzRelatedItemLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        if (!(element instanceof PsiMethod)) {
            return;
        }
        PsiMethod psiMethod = (PsiMethod) element;
        String path = APIUtils.determinePath(psiMethod);
        if (path.equals(HttpzConstants.EMPTY_STRING)) {
            return;
        }
        Project project = element.getProject();
        List<HttpzCommand> commands = findCommand(project, path);
        if (commands.size() > 0) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(HttpzIcons.ICON)
                            .setTargets(commands)
                            .setTooltipText("Navigate to HTTPZ");

            result.add(builder.createLineMarkerInfo(element));
        }
    }

    public static List<HttpzCommand> findCommand(Project project, String key) {
        List<HttpzCommand> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(HttpzFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            HttpzFile xcurlFile = (HttpzFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (xcurlFile == null) {
                continue;
            }
            HttpzCommand[] commands = PsiTreeUtil.getChildrenOfType(xcurlFile, HttpzCommand.class);
            if (commands == null) {
                continue;
            }
            for (HttpzCommand command : commands) {
                try {
                    String url = command.getUrl().getText();
                    String path = new URL(url).getPath();
                    if (key.equals(path)) {
                        result.add(command);
                    }
                } catch (Exception ignored) {
                }
            }
        }

        return result;
    }

}