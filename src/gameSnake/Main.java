/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameSnake;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.ArrayList;

/**
 *
 * @author educr
 */
public class Main extends SimpleApplication implements ActionListener, PhysicsCollisionListener{

    private BulletAppState bulletAppState;
    private Node map;
    private Player player;
    private boolean upP1 = false, downP1 = false, leftP1 = false, rightP1 = false;
    private boolean upP2 = false, downP2 = false, leftP2 = false, rightP2 = false;
    
    private Fruit fruit;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    //Inicializa game
    public static void main(String[] args){
        Main app = new Main();
        app.showSettings = false;
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        //Permitindo o uso de fisica
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        //pausando a camera
        flyCam.setEnabled(paused);
        cam.setRotation(new Quaternion(5.688213E-4f, 0.7832054f, -0.6217624f, 7.167674E-4f));
        cam.setLocation(new Vector3f(11.054492f, 30.43117f, 19.83869f));
        
        InitKeys();
        CreateMap();
        CreatePlayer();
        CreateFruit();
        
        
        
        //bulletAppState.setDebugEnabled(true);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    public void onAction(String binding, boolean value, float tpf) {
        switch (binding) {
            case "onePlayerLeft":
                if (value) {
                    leftP1 = true;
                } else {
                    leftP1 = false;
                }
                break;
            case "onePlayerRight":
                if (value) {
                    rightP1 = true;
                } else {
                    rightP1 = false;
                }
                break;
        }
        switch (binding) {
            case "onePlayerUp":
                if (value) {
                    upP1 = true;
                } else {
                    upP1 = false;
                }
                break;
            case "onePlayerDown":
                if (value) {
                    downP1 = true;
                } else {
                    downP1 = false;
                }
                break;
        }
        switch (binding) {
            case "twoPlayerLeft":
                if (value) {
                    leftP2 = true;
                } else {
                    leftP2 = false;
                }
                break;
            case "twoPlayerRight":
                if (value) {
                    rightP2 = true;
                } else {
                    rightP2 = false;
                }
                break;
        }
        switch (binding) {
            case "twoPlayerUp":
                if (value) {
                    upP2 = true;
                } else {
                    upP2 = false;
                }
                break;
            case "twoPlayerDown":
                if (value) {
                    downP2 = true;
                } else {
                    downP2 = false;
                }
                break;
        }
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        
        System.out.println("Evento A: " + event.getNodeA().getName());
        System.out.println("Evento B: " + event.getNodeB().getName());
        
        if (event.getNodeA().getName().equals("player1") || event.getNodeB().getName().equals("player1")) {
            if(event.getNodeA().getName().equals("fruit")) {
                rootNode.detachChild(event.getNodeA());
                bulletAppState.getPhysicsSpace().remove(event.getNodeA().getControl(RigidBodyControl.class));
                fruits.remove(event.getNodeA());
            }
            else if(event.getNodeB().getName().equals("fruit")) {
                rootNode.detachChild(event.getNodeB());
                bulletAppState.getPhysicsSpace().remove(event.getNodeB().getControl(RigidBodyControl.class));
                fruits.remove(event.getNodeB());
            }
        }
    }
    
    
    @Override
    public void simpleUpdate(float tpf) {

         player.move(tpf,leftP1,rightP1,upP1,downP1);
         
         //Ver posicao Tela
        // System.out.println("Posicao Player: " + player.getLocalTranslation());
         //System.out.println("Posicao Player Rotacao: " + player.getLocalRotation());
         //System.out.println("Nome player:" + player.getName());
         
     }
    
    
    /****** Métodos Utilizados *******/
    private void InitKeys() {
        //Player 1
        inputManager.addMapping("onePlayerLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("onePlayerRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("onePlayerUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("onePlayerDown", new KeyTrigger(KeyInput.KEY_S));
        //Player 2
        inputManager.addMapping("twoPlayerLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("twoPlayerRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("twoPlayerUp", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("twoPlayerDown", new KeyTrigger(KeyInput.KEY_DOWN));
        
        inputManager.addListener(this, "onePlayerLeft", "onePlayerRight");
        inputManager.addListener(this, "onePlayerUp", "onePlayerDown");
        inputManager.addListener(this, "twoPlayerLeft", "twoPlayerRight");
        inputManager.addListener(this, "twoPlayerUp", "twoPlayerDown");
    }

    private void CreateMap() {
        map = new Node("map");
        
        
        //criando mapa 
        int tamanho = 25;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                
                if (i == tamanho - 1 || i == 0 || j == 0 || j == tamanho - 1) {
                   map.attachChild(CreateBoxMap("parede",i, 1, j, "pedra.jpg"));
                }else{
                    map.attachChild(CreateBoxMap("chao",i, 0, j, "grama.jpg"));
                }
            }
        }
        
        RigidBodyControl boxPhysicsNode = new RigidBodyControl(0);
        map.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);
        rootNode.attachChild(map);
        
    }

    public Geometry CreateBoxMap(String nameObject,float x, float y, float z, String text) {
        
        Box boxMesh = new Box(0.5f, 0.5f, 0.5f);
        Geometry boxGeo = new Geometry(nameObject, boxMesh);
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture monkeyTex = assetManager.loadTexture("Textures/" + text);
        
        boxGeo.setName(nameObject);
        boxMat.setTexture("ColorMap", monkeyTex);
        boxGeo.setMaterial(boxMat);
        boxGeo.setLocalTranslation(x, y, z);
        return boxGeo;
    }
    

    private void CreatePlayer() {
        player = new Player("player1", assetManager, bulletAppState, inputManager, cam);
        rootNode.attachChild(player);
    }

    private void CreateFruit() {
       fruit = new Fruit(bulletAppState, assetManager);
       
       fruits.add(fruit);
       rootNode.attachChild(fruit);
       
    }
}
