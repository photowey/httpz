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
package com.photowey.plugin.httpz.tool;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ConsoleOutputToolWindow}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class ConsoleOutputToolWindow implements ToolWindowFactory {

    private static final Map<Project, ConsoleView> CONSOLE_VIEWS = new HashMap<>();

    public static ToolWindow toolWindow;

    public static final String HTTPZ_CONSOLE_ID = "Httpz";
    public static final String HTTPZ_WINDOW = "👇Output👇";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (CONSOLE_VIEWS.get(project) == null) {
            createToolWindow(project, toolWindow);
        }

        ConsoleOutputToolWindow.toolWindow = toolWindow;
    }

    public static ConsoleView getConsoleView(Project project) {
        if (CONSOLE_VIEWS.get(project) == null) {
            ToolWindow toolWindow = getToolWindow(project);
            createToolWindow(project, toolWindow);
        }
        return CONSOLE_VIEWS.get(project);
    }

    private static void createToolWindow(Project project, ToolWindow toolWindow) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        CONSOLE_VIEWS.put(project, consoleView);
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), HTTPZ_WINDOW, false);
        //toolWindow.getContentManager().addContent(content);
        content.getComponent().setVisible(true);
        content.setCloseable(true);
        toolWindow.getContentManager().addContent(content);
    }

    public static ToolWindow getToolWindow(@NotNull Project project) {
        return ToolWindowManager.getInstance(project).getToolWindow(HTTPZ_CONSOLE_ID);
    }
}
