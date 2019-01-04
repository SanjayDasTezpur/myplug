package com.swce.iind.plugin.github;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjayda on 8/20/2018 at 12:52 PM
 */
public class GitParser {
    private static final String GITHUB_LINK = "https://github.intel.com/SWISS/";

    public static String getCommitHash(String sGitLog)
    {
        String[] sCommitHash = sGitLog.split("Author:");
        String[] commits = sCommitHash[0].split("commit");
        return commits[1];
    }
    public static String getCommitLink(String sRepoName, String sCommithas)
    {
        String s = "Commit Link :- " + GITHUB_LINK  + sRepoName + "/commit/" + sCommithas.trim();
        return s;
    }

    public static String getCommittedFiles(String sGitDiff)
    {
        String sListOfCommittedFiles = "";
        List<String> lstLine = new ArrayList<>();
        String[] split = sGitDiff.split("\n");
        for(String line : split)
        {
            if(line.contains("diff --git "))
            {
                   lstLine.add(line);
            }
        }
        if(lstLine.isEmpty())
        {
            return "";
        }
        for(String line : lstLine)
        {
            String[] files = line.split("/");
            sListOfCommittedFiles = sListOfCommittedFiles + files[files.length-1] + "\n";
        }
        return "Committed Files :- " + "\n" + sListOfCommittedFiles;

    }
}
