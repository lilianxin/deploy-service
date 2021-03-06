package com.boco.deploy.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellExecutor extends Thread{
	private static final Logger logger = LoggerFactory.getLogger(ShellExecutor.class);
	
	String cmd;
	File logFile;
	
	public ShellExecutor(String cmd,File logFile) {
		this.cmd=cmd;
		this.logFile=logFile;
	}
	
	@Override
	public void run() {
		executeShell();
	}
	
	private void executeShell(){
		OutputStream output=null;
		InputStream input=null;
		try {
			output=new FileOutputStream(logFile);	
			logger.info("going to executeShell:"+cmd+",save log at:"+logFile.getName());
			Process proc = Runtime.getRuntime().exec(cmd);
			proc.waitFor();
			input = proc.getInputStream();
			IOUtils.copy(input, output);
		} catch (Exception e) {
			logger.error("executeShell error:",e);
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
	
}
