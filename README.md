# iYT
iYT Plugin:-
Youtrack configuration: - http://<youtrack host>:<port>
 
2.	Your Intellij IDEA version  should be 2018 
GOTO File -> Settings -> Plugins -> Install from Disk And give the path 
/nfs/iind/disks/nbval5/intellij_plugin/iYT-2.0-all.jar 
3.	Restart Intellij
4.	Click iYT -> Configure Youtrack 
 
5.	Provide Username and Password
6.	Plugin is ready to use.

Commit Procedure:- 
1.	Select files you want to commit and Push (Git -> Commit Files)
2.	After Successful commit and Push 
3.	Click iYT -> iYTAddComment
4.	Enter Issue ID of Youtrack ( e.g. YOUTRACK-13527 ) and click Check button it will check ID is correct and Fetch the Issue ID title. Once you verify that ID is correct in wich  you want add comment. Click Comment the latest Commit
 

Plugin will comment your latest git commit link and git hash and Files which you have pushed into master. 
	e.g. 
	 
