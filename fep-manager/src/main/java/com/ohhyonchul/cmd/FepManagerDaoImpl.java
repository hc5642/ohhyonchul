package com.ohhyonchul.cmd;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class FepManagerDaoImpl {
	
	public void exec() {
		String [] command = {"echo", "test"};
		DefaultExecutor executor = new DefaultExecutor();
	    CommandLine cmdLine = CommandLine.parse(command[0]);
	    for (int i=1 ; i<command.length ; i++ ) {
	        cmdLine.addArgument(command[i]);
	    }
	    try {
			executor.execute(cmdLine);
		} catch (ExecuteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		FepManagerDaoImpl dao = new FepManagerDaoImpl();
		dao.exec();
	}

}
