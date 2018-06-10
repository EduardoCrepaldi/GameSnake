/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameSnake;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.Random;

/**
 *
 * @author educr
 */
public class Fruit extends Node{
    
    public Fruit(BulletAppState state, AssetManager manager){
        setName("fruit");
        Box boxMesh = new Box(0.25f,0.25f,0.25f);
        Geometry boxGeo = new Geometry("fruit",boxMesh);
        Material boxMat = new Material(manager,"Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", ColorRGBA.Pink);
        boxGeo.setMaterial(boxMat);
        attachChild(boxGeo);
        
        setLocalTranslation(PosicaoRandom(1, 22), 1.40f, PosicaoRandom(1, 22));
     
        RigidBodyControl boxPhysicsNode = new RigidBodyControl(0);
        addControl(boxPhysicsNode);
        state.getPhysicsSpace().add(boxPhysicsNode);
    }
    
    
    private float PosicaoRandom(int minimo, int maximo){
        Random random = new Random();
        return (float)random.nextInt((maximo-minimo)+1)+minimo;
    }
}
