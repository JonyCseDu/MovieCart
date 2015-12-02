package com.movieCart.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Reader extends Thread{
	Socket socket;
	BufferedReader streamReader;
	public Reader(Socket socket) {
		this.socket = socket;
		try {
			streamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		//READING FROM SOCKET
		String command;
		StreamingPlayer streaming=null;
		try {
			while((command = streamReader.readLine()) != null){
				System.out.println("recieved : " + command);
				if(command.equals("play")){
					streaming.play();
				}
				else if(command.equals("pause")){
					streaming.pause();
				}
				else if(command.equals("stop")){
					streaming.stop();
				}
				else if(command.charAt(0) >= '0' && command.charAt(0) <= '9'){
					//command.
					System.out.println("got : " + Integer.parseInt(command));
					streaming.seek(Integer.parseInt(command));
				}
				else{
					streaming = new StreamingPlayer(socket, command);
					System.out.println(socket);
					streaming.start(command);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished");
		//CLOSING STREAM READER
		try {
			streamReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}