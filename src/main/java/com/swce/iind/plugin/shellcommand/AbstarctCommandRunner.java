package com.swce.iind.plugin.shellcommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjayda on 8/17/2018 at 1:58 PM
 */
public abstract class AbstarctCommandRunner implements ICommandRunner
{
    static Process process;
    static ProcessBuilder processB;

    public String executeShell(String[] sCommand)
    {
        List<String> lstCommand = new ArrayList<>();
        for(String cmd : sCommand)
        {
            lstCommand.add(cmd);
        }
        try {
            processB = new ProcessBuilder();
            processB.command(lstCommand);
            process = processB.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            String fullLine = "";
            while ((line = reader.readLine())!= null) {
                fullLine = fullLine + line + "\n";
            }
            return fullLine;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            process.destroy();
        }
    }
    public abstract String excuteGitLog(String[] cmd);
}
