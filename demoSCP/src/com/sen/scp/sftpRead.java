package com.sen.scp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.ws.rs.client.Client;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class sftpRead {

	
	
	
public static void main(String[] paras) throws IOException, JSchException, SftpException {
Boolean secure = true;
	
 String user = "16196107T";
 String host = "sftp8.sapsf.com";
 String password = "JwTbUr603Xs";
 String rfile = "/FEED";



 
JSch jsch=new JSch();



//String s="hostname,sftp8.sapsf.com, ssh-rsa b7:78:5b:fe:e7:62:89:d5:ca:a2:af:e5:5d:of";
//jsch.setKnownHosts(s);

Channel channel = null;
ChannelSftp channelSftp = null;


java.util.Properties config = new java.util.Properties(); 
config.put("StrictHostKeyChecking", "no");


Session session=jsch.getSession(user, host, 22);
session.setPassword(password);
session.setConfig(config);
session.connect();


ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
String SFTPWORKINGDIR = "/FEED";
sftp.connect();
sftp.cd(SFTPWORKINGDIR);
Vector filelist = sftp.ls(SFTPWORKINGDIR);
for (int i = 0; i < filelist.size(); i++) {
    System.out.println(filelist.get(i).toString());
}





InputStream stream = sftp.get("/EC/EEKEYMAP/In/w4.txt");
try {
    BufferedReader br = new BufferedReader(new InputStreamReader(stream));
    // read from br
    
    String line;
    while ((line = br.readLine()) != null) {
     System.out.println(line);
     
}
    
    
} finally {
    stream.close();
}




}


}