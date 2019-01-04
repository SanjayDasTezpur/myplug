package com.swce.iind.plugin.form;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.swce.iind.plugin.cred.Cred;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by sanjayda on 8/20/2018 at 3:32 PM
 */
public class ConfigurePlugin extends JDialog
{
    private static final long serialVersionUID = -2L;
    private JPanel YouTrackConfiguration;
    private JTextField textField1;
    private JPasswordField textField2;
    private JButton button1;
    private JTextField textField3;
    private JTextField textField4;
    private AnActionEvent evnt;

    public ConfigurePlugin(AnActionEvent an)
    {

        super((Window)null);
        evnt = an;
        setTitle("iYT-SWISS");
        setModal(true);
        setContentPane(YouTrackConfiguration);
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
        YouTrackConfiguration.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        button1.addActionListener(actionEvent -> {
            onClick();
            dispose();
        });
        initUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI()
    {
        this.textField1.setVisible(true);
        this.textField2.setVisible(true);
        this.textField2.setEchoChar(' ');
        this.button1.setVisible(true);
        this.YouTrackConfiguration.setVisible(true);
    }
    private void onClick()
    {
        String file = createFile();
        Cred cred = createCredObject();
        writeToFile(cred,file);
        Messages.showMessageDialog(evnt.getProject(), "Youtrack Configured", "Connecting", Messages.getInformationIcon());
    }
    private String createFile()
    {
        String property = System.getProperty("user.home");
        String ytFile = property + "/.yt";
        File file = new File(ytFile);
        if(file.exists()){
            file.delete();
        }
        try {
            boolean newFile = file.createNewFile();
            if (! newFile){
                System.err.println("Credential File not created");
                return null;
            }else {
                return ytFile;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Cred createCredObject()
    {
        Cred cred = null;
        boolean b1 = textField1.getText().equalsIgnoreCase("") || textField1.getText().trim().equalsIgnoreCase("");
        boolean b2 = textField2.getText().equalsIgnoreCase("") || textField2.getText().trim().equalsIgnoreCase("");
        boolean b3 = textField3.getText().equalsIgnoreCase("") || textField3.getText().trim().equalsIgnoreCase("");
        boolean b4 = textField4.getText().equalsIgnoreCase("") || textField4.getText().trim().equalsIgnoreCase("");

        if(b1 || b2){
            Messages.showMessageDialog(evnt.getProject(), "Enter Youtrack User Name and Password", "Connecting", Messages.getInformationIcon());
            return cred;
        } else {
            String username = textField1.getText();
            String password = textField2.getText();
            String host = textField3.getText();
            int port = Integer.valueOf(textField4.getText());
            cred = new Cred(username, password, host, port);
            return cred;
        }
    }

    private void writeToFile(Cred cred, String sOpathFile)
    {
        try {
            FileOutputStream fos = new FileOutputStream(sOpathFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cred);
            System.out.println("Done");
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
