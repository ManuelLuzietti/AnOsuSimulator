package it.unibo.osu.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyValue;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable {

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private AnchorPane pane;

    @FXML
    private MediaView mediaView;
    
    @FXML
    private ImageView mainLogo;
    
    @FXML
    private ImageView banner;
    
    private MediaPlayer menuPlayer;
    
    @FXML 
    private ImageView welcomeString;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//this.mediaView.setMediaPlayer(new MediaPlayer(new Media(this.getClass().getResource("/video/ReiBackgroundVideo.mp4").toString())));
		//this.mediaView.getMediaPlayer().play();
		this.scene.setOnMouseClicked(e->{
			changeResolution(680, 420);
		});
		ScaleTransition trans1 = new ScaleTransition();
		trans1.setNode(this.mainLogo);
		trans1.setAutoReverse(true);
		trans1.setCycleCount(Animation.INDEFINITE);
		trans1.setDuration(Duration.seconds(1));
		trans1.setByX(0.1); 
		trans1.setByY(0.1); 
		trans1.play();
		this.setInputHandlers();

		//this.menuPlayer = new MediaPlayer(new Media(this.getClass().getResource("/music/menuOst.mp3").toString()));
		//this.menuPlayer.play();
		
		this.welcomeString.setLayoutX(-this.welcomeString.getFitWidth());
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10), new KeyValue(this.welcomeString.layoutXProperty(), 1920)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		//new KeyFrame(Duration.seconds(3), new KeyValue(this.welcomeString.translateXProperty(), 0)
		//new KeyValue(this.welcomeString.translateXProperty(), this.pane.getWidth())

		// qua va sistemato da file fxml 
		VBox vB = new VBox(200);
		vB.setTranslateX(300);
		vB.setTranslateY(300);
		Button playBtn= new MenuSkinButton("/buttonSkin/uso_icon_play.png").getSkinnedButton();
		Button optBtn = new MenuSkinButton("/buttonSkin/uso_icon_options.png").getSkinnedButton();
		Button extBtn = new MenuSkinButton("/buttonSkin/uso_icon_exit.png").getSkinnedButton();
		vB.getChildren().addAll(playBtn, optBtn, extBtn);
	playBtn.setOnMouseClicked(e -> {
		System.out.println("plaii");
	});
		getPane().getChildren().add(vB);

	}
    
	public void changeResolution(double width,double height) {
		Scale scale = new Scale(width/this.pane.getWidth(),height/this.pane.getHeight());
		this.pane.getTransforms().add(scale);
		this.pane.setPrefHeight(height);
		this.pane.setPrefWidth(width);
		this.stage.sizeToScene();
	}
	
	private void setInputHandlers() {
		this.stage.setOnShown(e -> {
			this.setInitialRes();
		});
		
	}
	
	public AnchorPane getPane() {
		return this.pane;
	}

	public void setInitialRes() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    this.changeResolution(screen.getWidth(), screen.getHeight());
	    this.stage.setX(0);
	    this.stage.setY(0);		
	}
	
}