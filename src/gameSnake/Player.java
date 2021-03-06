/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameSnake;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;

/**
 *
 * @author educr
 */
public class Player extends Node{
    private  BetterCharacterControl physicsCharacter;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 0);
    public double theta = 0;
    private float x, z;
    private boolean antiLeft;
    private boolean antiRight;
    private boolean antiUp;
    private boolean antiDown;
    private float tamColisao;
    
    public Player(String player, AssetManager assetManager, BulletAppState bulletAppState, InputManager inputManager, Camera cam, Vector3f posicao, ColorRGBA color)
    {
        super(player);
        this.z = 0f;
        this.x = 0f;
        this.tamColisao = 0.4f;
        antiDown = true;
        antiLeft = true;
        antiRight = true;
        antiUp = true;
        
        Box boxMesh;
        Geometry boxGeo;
        Material boxMat;
        
        
        /*
        Sphere s = new Sphere(20,20,0.5f);
        Geometry geom = new Geometry(name, s);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap",assetManager.loadTexture("Textures/" + text));
        geom.setMaterial(mat);
        */
        Sphere s = new Sphere(20,20,0.5f);
        
        setName(player);
        //boxMesh = new Box(0.5f, 0.5f, 0.5f);
        boxGeo = new Geometry("Box", s);
        boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", color);
        boxGeo.setMaterial(boxMat);
        attachChild(boxGeo);
        
        setLocalTranslation(posicao);   
        
        physicsCharacter = new BetterCharacterControl(tamColisao, tamColisao, tamColisao);
        addControl(physicsCharacter);
        
        
        bulletAppState.getPhysicsSpace().add(physicsCharacter);
        

   }
    
    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(Vector3f walkDirection) {
        this.walkDirection = walkDirection;
    }

    public Vector3f getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(Vector3f viewDirection) {
        this.viewDirection = viewDirection;
    }
    
    
    void move(float tpf, boolean left, boolean right, boolean up, boolean down){
        Vector3f direcao;
        direcao = new Vector3f(this.x,0f,this.z);
        if((left && antiLeft)){
            //direcao = new Vector3f(-2f,0,0);
            antiLeft = true;
            antiRight = false;
            antiUp = true;
            antiDown = true;
            this.x = -2;
            this.z = 0;
        }else if((right && antiRight)){
            //direcao = new Vector3f(2f,0,0);
            antiLeft = false;
            antiRight = true;
            antiUp = true;
            antiDown = true;
            this.x = 2;
            this.z = 0;
        }else if((up && antiUp)){
            //direcao = new Vector3f(0,0,-2f);
            antiLeft = true;
            antiRight = true;
            antiUp = true;
            antiDown = false;
            this.z = -2;
            this.x = 0;
        }else if((down && antiDown)){
            //direcao = new Vector3f(0,0,2f);
            antiLeft = true;
            antiRight = true;
            antiUp = false;
            antiDown = true;
            this.z = 2;
            this.x = 0;
        }
        
        
        physicsCharacter.setWalkDirection(direcao);
        physicsCharacter.setViewDirection(direcao);
    }
    
    
    void crescer(BulletAppState bulletAppState){
        
        setLocalScale(getLocalScale().x+0.05f, getLocalScale().y+0.055f, getLocalScale().z+0.05f);
        
        bulletAppState.getPhysicsSpace().remove(physicsCharacter);
        
        tamColisao+=0.01f;
        physicsCharacter = new BetterCharacterControl(tamColisao,tamColisao,tamColisao);
        addControl(physicsCharacter);
        
        bulletAppState.getPhysicsSpace().add(physicsCharacter);
    }
    
}