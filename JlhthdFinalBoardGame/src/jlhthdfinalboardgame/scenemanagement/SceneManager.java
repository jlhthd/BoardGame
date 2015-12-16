/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.scenemanagement;

import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author jlhth_000
 */
public class SceneManager {
    private static Scene scene;
    private static HashMap<String, LoadableScene> controllers = new HashMap<>();
    
    private static class SceneManagerHolder {
        private static SceneManager INSTANCE = new SceneManager();
    }
    
    private SceneManager() {
        
    }
    
    public static SceneManager getInstance() {
        return SceneManagerHolder.INSTANCE;
    }
    
    public static SceneManager getInstance(Scene scene) {
        SceneManagerHolder.INSTANCE.setScene(scene);
        return SceneManagerHolder.INSTANCE;
    }
    
    public void setScene(Scene scene) {
        SceneManager.scene = scene;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public LoadableScene add(String name) {
        LoadableScene controller;
        
        controller = controllers.get(name);
        
        if (controller == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(name + ".fxml"));
                Parent root = (Parent)loader.load();
                controller = (LoadableScene)loader.getController();
                controller.setRoot(root);
                controller.setSceneManager(SceneManagerHolder.INSTANCE);
                controllers.put(name, controller);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                controller = null;
            }
        }
        
        return controller;
    }
    
    public void switchTo(String name) {
        LoadableScene controller = controllers.get(name);
        
        //if scene doesn't exist yet, add it
        if (controller == null) {
            controller = add(name);
        }
        
        //if scene exists, return it
        if (controller != null) {
            if (scene != null) {
                scene.setRoot(controller.getRoot());
            } else {
                System.err.println("Scene failed to load");
            }
        }
    }
    
    public LoadableScene getControllerAt (String name) {
        LoadableScene controller = controllers.get(name);
        
        //if scene doesn't exist yet, add it
        if (controller == null) {
            controller = add(name);
        }
        
        return controller;
    }
}
