package com.swce.iind.plugin.util;

import com.google.common.collect.Lists;
import com.intellij.openapi.ListSelection;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.actions.VcsContextFactory;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class VersionControlUtils
{
    @NotNull
    public static Collection<Change> getChanges(Project project, AnActionEvent anActionEvent)
    {
        Collection<Change> changes = Lists.newArrayList();
        VirtualFile[] selectedFiles = anActionEvent.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        ChangeListManager changesListManager  = ChangeListManager.getInstance(project);
        for(VirtualFile file : selectedFiles)
        {
            changes.addAll(changesListManager.getChangesIn(VcsContextFactory.SERVICE.getInstance().createFilePathOn(file)));
        }

        if(ApplicationInfo.getInstance().getBuild().getBaselineVersion() > 143)
        {
            ListSelection<Change> changesSelection = anActionEvent.getData(VcsDataKeys.CHANGES_SELECTION);
            if(null != changesSelection)
            {
                for (Change change : changesSelection.getList())
                {
                    if(!changes.contains(change))
                    {
                        changes.add(change);
                    }
                }
            }
        }

        Iterator<Change> iterator = changes.iterator();
        String sRemovedFiles = "";
        while(iterator.hasNext())
        {
            Change change = iterator.next();
            VirtualFile virtualFile = change.getVirtualFile();
            if(null == virtualFile)
            {
                continue;
            }
            String sFilename = virtualFile.getPresentableName();
            if(sFilename.endsWith(".eml") || sFilename.endsWith(".iml") || sFilename.endsWith(".classpath"))
            {
                sRemovedFiles += virtualFile.getPresentableName() + "\n";
                iterator.remove();
            }
        }
        if(!StringUtils.isEmpty(sRemovedFiles))
        {
            Messages.showWarningDialog("Following files have been removed from commit:\n" + sRemovedFiles, "IntelliJ/Eclipse Project files were selected");
        }

        return changes;
    }

    public static List<VirtualFile> getChangedFiles(Collection<Change> changes)
    {
        List<VirtualFile> lstFiles = Lists.newArrayList();
        for (Change change : changes)
        {
            lstFiles.add(change.getVirtualFile());
        }
        return lstFiles;
    }
}
