package it.unibo.osu.view;


import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.osu.controller.MusicControllerImpl;
import it.unibo.osu.controller.MusicControllerImplFactory;
import it.unibo.osu.controller.MusicEffectController;
import it.unibo.osu.controller.ScoreManagerImpl;
import it.unibo.osu.model.BeatMap;
import it.unibo.osu.model.GameModelImpl;
import it.unibo.osu.model.LifeBar;
import javafx.animation.Animation.Status;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class GameSceneController{


    @FXML
    private AnchorPane pane;

    @FXML
    private MediaView backgroundMedia;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private ProgressBar lifebar;

    @FXML
    private Label multiplier;

    @FXML
    private Label points;
    
    @FXML
    private Pane pausePane;
    
    private GameModelImpl game;
    
    private HitcircleViewFactory factory;
    private MusicEffectController effectController;
    private MusicControllerImpl pauseSound;
    private List<Transition> listTransitions;
    
    public void init(GameModelImpl game) {
    	this.game = game;
    	this.setBackground();
    	BeatMap beatmap = this.game.getBeatMap();
    	this.factory = new HitcircleViewFactory("/image/innerCircle.png", "/image/outerCircle.png", beatmap.getCircleSize(), beatmap.getOverallDifficulty(), beatmap.getApproachRate());
    	//Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    	//this.changeResolution(toolkit.getScreenSize().getWidth(), toolkit.getScreenSize().getHeight());
    	this.listTransitions = new ArrayList<>();
    	this.effectController = new MusicEffectController("/music/hitSound.wav","/music/missSound.wav");
    	this.pauseSound = MusicControllerImplFactory.getEffectImpl("/music/pauseSound.wav");
    	
    }
    
     public void  render() {
    	this.clearTransitionList();
    	this.lifebar.setProgress(this.game.getLifeBar().getHp()/LifeBar.MAXHP);
    	ScoreManagerImpl scoreManager = this.game.getScoreManager();
    	this.multiplier.setText("x" + Integer.toString(scoreManager.getMultiplier()));
    	this.points.setText(Integer.toString(scoreManager.getPoints()));
    	this.game.getCurrentHitbuttons().forEach(x -> {
    		HitcircleViewImpl hitcircleView = factory.getHitcircleView(x);
    		this.pane.getChildren().addAll(hitcircleView.getChildren());
//    		hitcircleView.addObserver(this.game.getLifeBar());
//    		hitcircleView.addObserver(this.game.getScoreManager());
    		hitcircleView.addObserver(this.effectController);
    		hitcircleView.addObserver(this.game);
    		hitcircleView.addObserver(this.game.getOsuClock());
    		this.listTransitions.add(hitcircleView.getParallelTransition());
    		this.listTransitions.add(hitcircleView.getScaleTransition());
    		hitcircleView.getParallelTransition().play();
    	});
    	this.game.getCurrentHitbuttons().clear();
    }
    private String getBackgroundType(String url) {
    	String ext = url.substring(url.indexOf(".") + 1);
        if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpeg")) {
              return  "photo";
        } else {
        	return "video";
        }
    }
    private void setBackground() {
    	String fileName = game.getBeatMap().getBackground();
    	String type = getBackgroundType(fileName);
    	if( type.equals("photo")) {
    		this.backgroundImage.setImage(new Image(this.getClass().getResource("/wallpaper/" + fileName).toString()));
    	} else {
    		this.backgroundMedia.setMediaPlayer(new MediaPlayer(new Media(this.getClass().getResource("/video/" + fileName).toString())));
    		this.backgroundMedia.getMediaPlayer().setCycleCount(Integer.MAX_VALUE);
    		this.backgroundMedia.getMediaPlayer().play();
    	}
    }
    //NON PIU NECESSARIO
//    public void changeResolution(double width,double height) {
//		// lo scale fa vedere l' immagine sotto lo stage, qualcosa non torna
//		Scale scale = new Scale(width/this.pane.getWidth(),height/this.pane.getHeight());
//		this.pane.getTransforms().add(scale);
//		this.pane.setPrefHeight(height);
//		this.pane.setPrefWidth(width);
//	}
    public AnchorPane getPane() {
    	return this.pane;
    }
    
    public void setPausePane() {
    	this.pauseSound.onNotify();
    	if(!this.isPausePaneVisible()) {
    		this.pausePane.toFront();
    		if(this.backgroundMedia.getMediaPlayer() != null) {
        		this.backgroundMedia.getMediaPlayer().pause();
        	}
    	} else {
    		this.pausePane.toBack();
    		if(this.backgroundMedia.getMediaPlayer() != null) {
        		this.backgroundMedia.getMediaPlayer().play();
        	}
    	};
    	this.pauseHitbuttons();

    	
    }
    
    public void pauseHitbuttons(){
    	this.listTransitions.forEach(transition -> {
    		if( transition.getStatus().equals(Status.PAUSED)) {
    			transition.play();
    		} else if(transition.getStatus().equals(Status.RUNNING)) {
    			transition.pause();
    		}
    	});
    }

    private void clearTransitionList() {
    	if (this.listTransitions.size() > 50) {
			this.listTransitions.clear();
		}
    }
   
    public Pane getPausePane() {
    	return this.pausePane;
    }
    public boolean isPausePaneVisible() {
    	return this.pane.getChildren().indexOf(this.pausePane) != 0;
    }
 
}
