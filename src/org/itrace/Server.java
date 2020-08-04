package org.itrace;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.StringJoiner;
import java.util.concurrent.Future;

import org.itrace.gaze.IGazeResponse;
import org.itrace.gaze.IStyledTextGazeResponse;

public class Server implements Runnable {
	int portNumber = 1111;
	private AsynchronousSocketChannel channel;
	private PrintWriter writer;
	
	public void start() throws IOException {
		AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
		server.bind(new InetSocketAddress("127.0.0.1", portNumber));
		
		server.accept( null, new CompletionHandler<AsynchronousSocketChannel,Void>() {

            @Override
            public void completed(AsynchronousSocketChannel ch, Void att)
            {
                // Accept the next connection
            	server.accept( null, this );
            	channel = ch;
            }

			@Override
			public void failed(Throwable exc, Void attachment) {
				// TODO Auto-generated method stub
				
			}
		});
    }
	
	public void process(IGazeResponse response) {
		
		StringJoiner str = new StringJoiner(";");
		str.add(String.valueOf(response.getGaze().getEventTime()));
		str.add(String.valueOf(System.currentTimeMillis()));
		str.add(String.valueOf(response.getGaze().getX()));
		str.add(String.valueOf(response.getGaze().getY()));
		str.add(response.getName());
		str.add(response.getGazeType());

		if (response instanceof IStyledTextGazeResponse) {
		    IStyledTextGazeResponse styledResponse = (IStyledTextGazeResponse) response;
			str.add(styledResponse.getPath());
			str.add(String.valueOf(styledResponse.getLine()));
			str.add(String.valueOf(styledResponse.getCol()));
		}
		
		channel.write(ByteBuffer.wrap( str.toString().getBytes() ) );
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
