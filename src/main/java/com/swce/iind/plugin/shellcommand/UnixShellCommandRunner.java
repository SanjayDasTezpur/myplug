package com.swce.iind.plugin.shellcommand;

/**
 * Created by sanjayda on 8/17/2018 at 1:59 PM
 */
public class UnixShellCommandRunner extends AbstarctCommandRunner
{
    @Override
    public String excuteGitLog(String[] cmd) {
        return super.executeShell(cmd);
    }
}
