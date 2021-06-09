package org.itrace;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringJoiner;

import org.itrace.gaze.IGazeResponse;
import org.itrace.gaze.IStyledTextGazeResponse;

public class Client implements Runnable {
	int portNumber = 8089;
	String server = "127.0.0.1";
	private Socket socket;
	private PrintStream out;
	
	public void start() throws IOException {
		System.out.println("Start: client");
		socket = new Socket(server, portNumber);
		out = new PrintStream( socket.getOutputStream() );
		
    }
	
	public void process(IGazeResponse response) {
		
		StringJoiner str = new StringJoiner(";");
		//str.add(String.valueOf(response.getGaze().getEventTime()));
		//str.add(String.valueOf(System.currentTimeMillis()));
		//str.add(String.valueOf(response.getGaze().getX()));
		//str.add(String.valueOf(response.getGaze().getY()));
		//str.add(response.getName());
		//str.add(response.getGazeType());

		if (response instanceof IStyledTextGazeResponse) {
		    IStyledTextGazeResponse styledResponse = (IStyledTextGazeResponse) response;

			str.add("E");
			str.add("1");
			str.add("iTrace");
			str.add(""); // Source Definition
			str.add(""); // Instance
			str.add(""); // Elapsed Time
			str.add(""); // Media time
			str.add("TrackFilePosition"); // Sample Type
			str.add(styledResponse.getPath());
			str.add(String.valueOf(styledResponse.getLine()));
			str.add(String.valueOf(styledResponse.getCol()));
			
			System.out.println(str.toString());
			out.println(str.toString() + "\r\n");
		}
		
		
		
	}

	@Override
	public void run() {
		try {
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
