package com.gamesmart.main.handler;

import java.util.List;

import dev.onvoid.webrtc.PeerConnectionFactory;
import dev.onvoid.webrtc.PeerConnectionObserver;
import dev.onvoid.webrtc.RTCConfiguration;
import dev.onvoid.webrtc.RTCIceCandidate;
import dev.onvoid.webrtc.RTCIceServer;
import dev.onvoid.webrtc.RTCPeerConnection;
import dev.onvoid.webrtc.media.MediaType;
import dev.onvoid.webrtc.media.audio.AudioOptions;
import dev.onvoid.webrtc.media.audio.AudioTrack;
import dev.onvoid.webrtc.media.video.VideoDeviceSource;
import dev.onvoid.webrtc.media.video.VideoTrack;

public class RequestHandler {
	/**
	 * create PeerConnection
	 * 
	 */
	public void createConnection() {
		//create peerConnectionFactory
		PeerConnectionFactory factory = new PeerConnectionFactory();
		RTCConfiguration rtcConfig = new RTCConfiguration();
		
		List<RTCIceServer> iceServers = rtcConfig.iceServers;
		RTCIceServer rtcIceServer = new RTCIceServer();
		rtcIceServer.urls.add("192.168.23.3");
		rtcIceServer.username = "test";
		rtcIceServer.password = "test";
		iceServers.add(rtcIceServer);
		//create peerConnection
		RTCPeerConnection peerConnection = factory.createPeerConnection(rtcConfig, new PeerConnectionObserver() {
			@Override
			public void onIceCandidate(RTCIceCandidate candidate) {
				System.out.print(candidate);
			}});
		//create audio/video track
		AudioTrack audioTrack = factory.createAudioTrack("audio_track", factory.createAudioSource(new AudioOptions()));
		VideoTrack videoTrack = factory.createVideoTrack("video_track", new VideoDeviceSource());
		//create localMediaStream and add audio/video track to stream
		
		peerConnection.createDataChannel("data_channel", );
		
	}
}
