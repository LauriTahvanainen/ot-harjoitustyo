
package algorithm;

import sprite.Sprite;
import java.util.ArrayDeque;


public abstract class Algorithm {
    
    public abstract void calculateRoute();
    public abstract void takeStep();
    public abstract ArrayDeque<Vertex> getRoute();
    public abstract void buildRoute();
    public abstract void scanTile();
}
