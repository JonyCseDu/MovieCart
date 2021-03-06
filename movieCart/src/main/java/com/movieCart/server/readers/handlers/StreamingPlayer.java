package com.movieCart.server.readers.handlers;

import java.net.Socket;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

public class StreamingPlayer{
	Socket socket;
	HeadlessMediaPlayer mediaPlayer;
	
	public StreamingPlayer(Socket socket, String media) {
		this.socket = socket;
		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(media);
        mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        String options[] = {
        		":no-sout-rtp-sap", 
				":no-sout-standard-sap",
				":sout-all",
    			":sout-keep"
        };
        mediaPlayer.setStandardMediaOptions(options);
        System.out.println("streaming set");
		
	}
	
	public void start(String media){
        String ip = socket.getInetAddress().toString().substring(1);
		int port = socket.getPort();
		
		System.out.println(ip);
		System.out.println(port);

        mediaPlayer.startMedia(media, formatRtpStream(ip, port));
        //mediaPlayer.setRepeat(true);
        System.out.println("started successfully");

	}
	public void play() throws InterruptedException{
		Thread.sleep(100);
		mediaPlayer.play();
		System.out.println("server playing");
	}
	public void pause() throws InterruptedException{
		Thread.sleep(100);
		mediaPlayer.pause();
		System.out.println("server paused");
	}
	public void seek(int time) throws InterruptedException{
		Thread.sleep(100);
		mediaPlayer.setTime(time);
		System.out.println("isSeekable : " + mediaPlayer.isSeekable());
		//mediaPlayer.setPosition(time);
		//mediaPlayer.play();
		
		System.out.println("server seeking : " + time);
	}
	public void stop(){
		mediaPlayer.stop();
	}
	
	private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}");
        return sb.toString();
    }
}
