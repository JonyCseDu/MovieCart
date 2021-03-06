package com.movieCart.server.readers;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.movieCart.Objects.GridPacket;
import com.movieCart.Objects.RequestObject;
import com.movieCart.Objects.UploadPacket;

public class GridReader extends Thread {
	Socket socket;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	
	public GridReader (Socket socket) {
		this.socket = socket;
	}
	
	void catchRequest(){
		try{
			inputStream = new ObjectInputStream(socket.getInputStream());
			RequestObject requestObject = (RequestObject) inputStream.readObject();
			System.out.println("grid request accepted");
			//inputStream.close();
			
			if(requestObject.getCommand().equals("search")){
				serveRequest(requestObject.getSearchKey());
			}
			
			outputStream.writeObject(null);
			requestObject = (RequestObject) inputStream.readObject();
			if(requestObject == null){
				System.out.println("NULL FOUND\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// close
		try {
			outputStream.flush();		
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void serveRequest(String key){
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// search
		File folder = new File("./PsudoServer/infos");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				// serving ... 
				if(key.length() == 0 || listOfFiles[i].getName().toLowerCase().startsWith(key.toLowerCase())){
					System.out.println("Found " + listOfFiles[i].getName());
					GridPacket object = new GridPacket(listOfFiles[i].getName());
					try {
						outputStream.writeObject(object);
						outputStream.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
		    } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		    }
		}
		System.out.println("grid request served");
	}
	
	public void run(){
		catchRequest();
	}
}

