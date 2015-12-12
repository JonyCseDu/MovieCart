package com.movieCart.server.BinaryControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.print.attribute.standard.PrinterMessageFromOperator;

import com.movieCart.Objects.UploadPacket;
import com.movieCart.Objects.welcomepacket;

public class BinaryReader extends Thread {
	Socket socket;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	
	public BinaryReader (Socket socket) {
		this.socket = socket;
	}
	
	public void run(){
		try {
			inputStream = new ObjectInputStream(socket.getInputStream());
			UploadPacket packet = (UploadPacket) inputStream.readObject();
			packet.write();
			System.out.println("WRITING OBJECT SUCCESSFUL");
			inputStream.close();
			socket.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
