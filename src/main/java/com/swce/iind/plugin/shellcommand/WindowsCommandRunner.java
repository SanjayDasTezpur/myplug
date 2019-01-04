package com.swce.iind.plugin.shellcommand;

/**
 * Created by sanjayda on 8/17/2018 at 3:09 PM
 */
public class WindowsCommandRunner extends AbstarctCommandRunner
{
    @Override
    public String excuteGitLog(String[] cmd) {
        return super.executeShell(cmd);
    }
}
