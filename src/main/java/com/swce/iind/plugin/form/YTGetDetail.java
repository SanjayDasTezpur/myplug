package com.swce.iind.plugin.form;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.swce.iind.plugin.github.GitCommandManager;
import com.swce.iind.plugin.github.GitParser;
import com.swce.iind.plugin.util.RepoParser;
import com.swce.iind.plugin.ytapi.RestServiceComponent;
import com.swce.iind.plugin.ytapi.YTParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * Created by sanjayda on 8/16/2018 at 10:31 PM
 */
public class YTGetDetail  extends JDialog
{
    private static final long serialVersionUID = -1L;
    private RestServiceComponent rest = new RestServiceComponent();
    private Project project;
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton button1;
    private JPanel mainPanel;
    private JButton checkButton;
    private AnActionEvent anAction;
    String USER;
    String PASSWORD;

    public YTGetDetail(AnActionEvent an)
    {
        super((Window)null);
        setTitle("iYT-SWISS");
        setModal(true);
        setContentPane(mainPanel);
        getRootPane().setDefaultButton(button1);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });
        mainPanel.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.anAction = an;
        this.project = an.getProject();
        checkButton.addActionListener(actionEvent -> {
            getDetails();
        });
        button1.addActionListener(actionEvent -> {
            fillYoutrack( "Using iYT Plugin:- \n" + getYTComment());
            dispose();
        });
        initUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        Map<String, String> ytCred = YTParser.getYTCred();
        USER = ytCred.get("username");
        PASSWORD = ytCred.get("password");
    }

    private void initUI()
    {
        this.textField1.setVisible(true);
        this.textArea1.setVisible(true);
        this.button1.setVisible(true);
        this.button1.setEnabled(false);
        this.mainPanel.setVisible(true);
    }

    private void getDetails()
    {
        if(textField1.getText().equalsIgnoreCase("") || textField1.getText().trim().equalsIgnoreCase(""))
        {
            Messages.showMessageDialog(project, "BugID Field cannot be empty", "Connecting", Messages.getInformationIcon());
        }else {
            String bugDetails = rest.getBugDetails(textField1.getText(),USER,PASSWORD);
            if(bugDetails.equalsIgnoreCase("") || bugDetails.isEmpty())
            {
                Messages.showMessageDialog(project, "BugID not Found in Youtrack", "Connecting", Messages.getInformationIcon());

            }else {
                textArea1.setText(bugDetails);
                textArea1.setEnabled(false);
                button1.setEnabled(true);
            }

        }
    }

    private String getDetOfChangedFile()
    {
        String sFileNames = "";
        String s = GitCommandManager.executeGitShowCmd(curDir());
        sFileNames = GitParser.getCommittedFiles(s);
        return sFileNames;
    }

    private void fillYoutrack(String comment)
    {
        rest.addYTComment(textField1.getText(),comment, USER,PASSWORD);
    }
    private String getYTComment()
    {
        String sDir = curDir();
        String repoName = RepoParser.getRepoName(sDir);
        String sCommitMessage = GitCommandManager.executeGitLogCmd(sDir);
        String commitHash = GitParser.getCommitHash(sCommitMessage);
        String commitLink = GitParser.getCommitLink(repoName, commitHash);
        String detOfChangedFile = getDetOfChangedFile();
        return  sCommitMessage + "\n" + detOfChangedFile + "\n" + commitLink;
    }

    private String curDir()
    {
        String property = project.getBasePath();
        return property;
    }
}
