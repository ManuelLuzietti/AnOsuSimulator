package it.unibo.osu.controller;

import java.net.URISyntaxException;

import it.unibo.osu.model.GameModelImpl;
import it.unibo.osu.model.User;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public abstract class MusicControllerImpl extends AbstractSubject implements MusicController, Observer {

	private MediaPlayer audioMedia;
	
	/**
	 * Instantiates a new music controller implementation.
	 *
	 * @param name the song name
	 */
	public MusicControllerImpl(final String name) {

		try {
			this.audioMedia = new MediaPlayer(new Media(this.getClass().getResource(name).toURI().toString()));
		} catch (URISyntaxException e) {
			System.out.println("error in resource loading");
			e.printStackTrace();
		}

	}
	
	/**
	 * Gets the {@link MediaPlayer}.
	 *
	 * @return the media player
	 */
	public  MediaPlayer getMediaPlayer() {
		return this.audioMedia;
	}

	@Override
	public void startMusic() {
		this.audioMedia.play();
	}

	@Override
	public void stopMusic() {
		this.audioMedia.stop();
	}
	
	@Override
	public Duration getDuration() {
		if( this.audioMedia == null) {
			throw new NullPointerException();
		}
		return this.audioMedia.getTotalDuration();
	}
	
	/**
	 * Gets the status of the {@link MediaPlayer}.
	 *
	 * @return the status
	 */
	public MediaPlayer.Status getStatus(){
		return this.audioMedia.getStatus();
	}
	
	@Override
	public void pauseMusic() {
		this.audioMedia.pause();
	}
	
	@Override
	public void onNotify() {
	}
	
	@Override
	public void notifyObs() {	
	}
	
	@Override
	public void setOnFinished(Runnable runnable) {		
	}
	
	/**
	 * Update volume.
	 */
	public void updateVolume() {
		this.getMediaPlayer().setVolume(User.getMusicVolume());
	}
	
}
