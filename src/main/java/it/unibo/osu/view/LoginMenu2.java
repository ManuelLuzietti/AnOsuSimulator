package it.unibo.osu.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import it.unibo.osu.controller.MusicControllerImpl;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoginMenu2 extends Stage {
	private StackPane pane;

	private AnchorPane ap;

	private Scene scene;

	private ImageView logo;


	public LoginMenu2() {
		this.ap = new AnchorPane();
		this.pane = new StackPane(ap);
		this.scene = new Scene(pane);
		this.setScene(scene);
		this.setFullScreen(true);

		drawLogo(this.logo);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		drawBackgroundImage(screen);
		//qui giusto:
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SongMenu.fxml"));
		try {
			this.pane.getChildren().add(0,loader.load());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// cambiare el nome a questa qua
		this.show();
	}


	private void drawBackgroundImage(Dimension screen) {
		Image image = new Image(this.getClass().getResource("/image/rainTokyo.png").toString());

		BackgroundSize bSize = new BackgroundSize(screen.getWidth(), screen.getHeight(), false, false, true, false);
		this.pane.setBackground(new Background(new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				bSize)));



		ImageView background = new ImageView(new Image(this.getClass().getResource("/image/rei.png").toString()));
		background.setFitHeight(screen.getHeight());
		background.setFitWidth(screen.getWidth());
		//this.pane.getChildren().add(background);
	}


	private void drawLogo(ImageView logoImage) {
		logoImage = new ImageView(new Image(this.getClass().getResource("/image/uso_icon2.png").toString()));
		logoImage.setFitWidth(500);
		logoImage.setFitHeight(500);

		ScaleTransition trans1 = new ScaleTransition();
		trans1.setNode(logoImage);
		trans1.setAutoReverse(true);
		trans1.setCycleCount(Animation.INDEFINITE);
		trans1.setDuration(Duration.seconds(1));
		trans1.setByX(0.1);
		trans1.setByY(0.1);
		trans1.play();
		

		this.pane.getChildren().add(logoImage);
		StackPane.setAlignment(logoImage, Pos.CENTER);
		
		MusicControllerImpl welcome = new MusicControllerImpl("/music/welcome_sound.wav");
		welcome.startMusic();

		logoImage.setOnMouseClicked(e -> {
			final TextField user = new TextField();
			user.setMaxWidth(300);
			user.setStyle("-fx-background-radius: 5em;");
			HBox hb = new HBox();
			hb.getChildren().addAll(new Label("Login"), user, new Label(" Desu!"));
			//trans1.stop();
			this.pane.getChildren().add(hb);
			hb.setAlignment(Pos.CENTER);
			

			user.setOnAction(es -> {
				if(!user.getText().toString().equals("")) {
					MenuView mV = new MenuView(user.getText().toString(), this);

					welcome.stopMusic();

					Parent root = mV.getPane();
					Scene scene = this.ap.getScene();
//					FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SongMenu.fxml"));
//					try {
//						this.pane.getChildren().add(0,loader.load());
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
					
				

					//trans1.stop();
					//root.translateYProperty().set(scene.getHeight()); /// l'altezzza qui non va bene.... 
					//root.translateYProperty().set(this.getHeight());/// l'altezzza qui non va bene.... 
					//Add second scene. Now both first and second scene is present
					//this.pane.getChildren().add(root);


					//Create new TimeLine animation
					//Timeline timeline = new Timeline();
					//Animate Y property
					//				KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
					//				KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
					//				timeline.getKeyFrames().add(kf);
					//After completing animation, remove first scene
					//				timeline.setOnFinished(event -> {
					FadeTransition fade = new FadeTransition();
					fade.setNode(this.pane);
					fade.setOnFinished(eve -> {
						this.pane.getChildren().remove(ap);
						//this.pane.getChildren().add(root);
//						try {
//							this.pane.getChildren().add(loader.load());
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}
						fade.setFromValue(0);
						fade.setToValue(1);
						fade.setDuration(Duration.seconds(1));
						fade.setOnFinished(null);
						fade.playFromStart();
						//				});
					});
					fade.setFromValue(1.0);
					fade.setToValue(0.0); 
					fade.setDuration(Duration.seconds(1));
					fade.play();
					
					//				timeline.play();
				}
			});
		});
	}
	
	 
	// va tenuta qua perche questo è lo stage che rimane attivo 
	public void changeResolution(double width,double height) {
		// lo scale fa vedere l' immagine sotto lo stage, qualcosa non torna
		Scale scale = new Scale(width/this.pane.getWidth(),height/this.pane.getHeight());
		this.pane.getTransforms().add(scale);
		this.pane.setPrefHeight(height);
		this.pane.setPrefWidth(width);
		this.sizeToScene();
	}


}