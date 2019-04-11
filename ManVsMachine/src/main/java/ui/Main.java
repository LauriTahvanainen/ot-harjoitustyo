package ui;

import dao.DatabaseUserDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import eventhandling.ActionEventHandler;
import statemanagement.StateManager;
import eventhandling.KeyEventHandler;
import statemanagement.State;

public class Main extends Application {

    public final int WIDTH = 1200;
    public final int HEIGHT = 720;
    private StateManager stateManager;
    private ActionEventHandler actionEventHandler;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Man Vs Machine");
        stage.setResizable(false);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(this.scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        this.scene = new Scene(new Pane(), WIDTH, HEIGHT);
        this.stateManager = new StateManager();
        this.stateManager.setScene(this.scene);
        DatabaseUserDao userDao = new DatabaseUserDao();
        KeyEventHandler KeyListener = new KeyEventHandler();
        this.scene.setOnKeyPressed(KeyListener);
        this.scene.setOnKeyReleased(KeyListener);
        State loginState = new LoginState(this.stateManager, userDao);
        State menuState = new MenuState(this.stateManager, userDao);
        State playingState = new PlayingState(this.stateManager, userDao);
        State settingsState = new SettingsState(this.stateManager, userDao);

        this.stateManager.addState(loginState);
        this.stateManager.addState(menuState);
        this.stateManager.addState(playingState);
        this.stateManager.addState(settingsState);
        this.scene.setRoot(this.stateManager.getCurrentState().getCurrent());
        this.actionEventHandler = new ActionEventHandler(stateManager);
        this.scene.addEventHandler(ActionEvent.ACTION, actionEventHandler);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
