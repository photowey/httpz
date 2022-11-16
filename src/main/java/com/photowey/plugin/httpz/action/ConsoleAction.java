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
package com.photowey.plugin.httpz.action;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.photowey.plugin.httpz.tool.ConsoleOutputToolWindow;
import org.jetbrains.annotations.NotNull;

/**
 * {@code ConsoleAction}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class ConsoleAction extends AnAction {

    private void testNotify(AnActionEvent e) {
        ConsoleOutputToolWindow.getConsoleView(e.getProject()).print("test output in console", ConsoleViewContentType.NORMAL_OUTPUT);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        testNotify(event);
    }
}
