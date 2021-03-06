// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.intellij.sdk.editor;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * If conditions support it, makes a menu visible to display information
 * about the caret.
 *
 * @see com.intellij.openapi.actionSystem.AnAction
 */
public class EditorAreaIllustration extends AnAction {
  
  /**
   * Displays a message with information about the current caret.
   * @param e  Event related to this action
   */
  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    // Get access to the editor and caret model. update() validated editor's existence.
    final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
    final CaretModel caretModel = editor.getCaretModel();
    // Getting the primary caret ensures we get the correct one of a possible many.
    final Caret primaryCaret = caretModel.getPrimaryCaret();
    // Get the caret information
    LogicalPosition logicalPos = primaryCaret.getLogicalPosition();
    VisualPosition visualPos = primaryCaret.getVisualPosition();
    int caretOffset = primaryCaret.getOffset();
    // Build and display the caret report.
    StringBuilder report = new StringBuilder(logicalPos.toString() + "\n");
    report.append(visualPos.toString() + "\n");
    report.append("Offset: " + caretOffset);
    Messages.showInfoMessage(report.toString(), "Caret Parameters Inside The Editor");
  }
  
  /**
   * Sets visibility and enables this action menu item if:
   *   A project is open,
   *   An editor is active,
   * @param e  Event related to this action
   */
  @Override
  public void update(@NotNull final AnActionEvent e) {
    // Get required data keys
    final Project project = e.getProject();
    final Editor editor = e.getData(CommonDataKeys.EDITOR);
    //Set visibility only in case of existing project and editor
    e.getPresentation().setEnabledAndVisible(project != null && editor != null);
  }
}
