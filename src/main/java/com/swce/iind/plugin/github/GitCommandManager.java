package com.swce.iind.plugin.github;

import com.swce.iind.plugin.shellcommand.UnixShellCommandRunner;
import com.swce.iind.plugin.shellcommand.WindowsCommandRunner;
import com.swce.iind.plugin.util.OSValidator;

/**
 * Created by sanjayda on 8/17/2018 at 2:07 PM
 */
public class GitCommandManager {
    public static String executeGitLogCmd(String sDir)
    {
        if(sDir.contains("/fullproject")){
            sDir = sDir.replace("/fullproject","");
        }
        if( !OSValidator.isWindows() ) {
            String command[] = new String[]{"/usr/intel/bin/git","--git-dir", sDir+"/.git", "log", "--max-count=1"};
            return new UnixShellCommandRunner().excuteGitLog(command);
        }
        if (OSValidator.isWindows()) {
            String command[] = new String[]{"git","--git-dir", sDir+"/.git", "log", "--max-count=1"};
            return new WindowsCommandRunner().excuteGitLog(command);
        }
        return null;
    }

    public static String executeGitShowCmd(String sDir)
    {
        if(sDir.contains("/fullproject")){
            sDir = sDir.replace("/fullproject","");
        }
        if( !OSValidator.isWindows() ) {
            String command[] = new String[]{"/usr/intel/bin/git","--git-dir", sDir+"/.git", "show"};
            return new UnixShellCommandRunner().excuteGitLog(command);
        }
        if (OSValidator.isWindows()) {
            String command[] = new String[]{"git","--git-dir", sDir+"/.git", "show"};
            return new WindowsCommandRunner().excuteGitLog(command);
        }
        return null;
    }
}
