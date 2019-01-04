package com.swce.iind.plugin.util;

/**
 * Created by sanjayda on 8/20/2018 at 1:01 PM
 */
public class RepoParser {
    public static String getRepoName(String sRepoDir)
    {
        if(sRepoDir.contains("/fullproject")){
            sRepoDir = sRepoDir.replace("/fullproject","");
        }
        String[] split;
        if (OSValidator.isWindows()){
            split = sRepoDir.split("/");
        } else {
            split = sRepoDir.split("/");
        }
        String s = split[split.length - 1];
        return s;
    }
}
