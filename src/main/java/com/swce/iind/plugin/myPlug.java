package com.swce.iind.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.swce.iind.plugin.form.YTGetDetail;

/**
 * Created by sanjayda on 6/25/2018 at 4:46 PM
 */
public class myPlug extends AnAction {
    public myPlug() {
        super("Hello Plugin Ready for loggy");
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        try {
            YTGetDetail yt = new YTGetDetail(anActionEvent);
        } catch (Exception e) {
            Messages.showMessageDialog(project, "Done", "Connecting", Messages.getInformationIcon());
        }
    }
}
