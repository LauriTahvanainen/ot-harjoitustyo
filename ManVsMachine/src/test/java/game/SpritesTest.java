
package game;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import sprite.Sprite;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SpritesTest {
    private Sprite testSprite;
    private Pane pane;
    
    public SpritesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testSprite = new Sprite(Color.BLACK, 10, 10);
        this.pane = new Pane();
        this.pane.getChildren().add(this.testSprite.getForm());
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMoveUp() {
        this.testSprite.moveUp();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0 && this.testSprite.getForm().getTranslateY() == -0.5);
    }
    @Test
    public void testMoveDown() {
        this.testSprite.moveDown();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0 && this.testSprite.getForm().getTranslateY() == 0.5);
    }
    @Test
    public void testMoveLeft() {
        this.testSprite.moveLeft();
        assertTrue(this.testSprite.getForm().getTranslateX() == -0.5 && this.testSprite.getForm().getTranslateY() == 0);
    }
    @Test
    public void testMoveRight() {
        this.testSprite.moveRight();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0.5 && this.testSprite.getForm().getTranslateY() == 0);
    }
    
    @Test
    public void checkCollisionDoesntCollideWithItself() {
        assertFalse(this.testSprite.checkCollision(this.testSprite.getForm()));
    }
    
    @Test
    public void checkCollisionWorksWhenColliding() {
        Rectangle rec = new Rectangle(10,10);
        this.pane.getChildren().add(rec);
        assertTrue(this.testSprite.checkCollision(rec));
    }
    
    @Test
    public void checkCollisionWorksWhenNotColliding() {
        Rectangle rec = new Rectangle(10,10);
        this.pane.getChildren().add(rec);
        for (int i = 0; i < 100;i++) {
            this.testSprite.moveRight();
            rec.setTranslateY(rec.getTranslateY() + 0.5);
        }
        assertFalse(this.testSprite.checkCollision(rec));
    }
    
    @Test
    public void getFormTest() {
        Sprite sprite = new Sprite(Color.AQUA,10,15);
        ObservableList<Double> points = sprite.getForm().getPoints();
        double[] targetPoints = {0, 0, 0, 10, 15, 10, 15, 0}; 
        boolean pointsOk = true;
        for (int i = 0; i < points.size();i++) {
            if (points.get(i) != targetPoints[i]) {
                pointsOk = false;
            }
        }
        if (pointsOk) {
            return;
        }
        fail();
    }
    
    @Test
    public void clearTranslateTest() {
        this.testSprite.moveDown();
        this.testSprite.moveDown();
        this.testSprite.moveDown();
        this.testSprite.moveRight();
        this.testSprite.moveUp();
        this.testSprite.moveDown();
        this.testSprite.clearTranslate();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0 && this.testSprite.getForm().getTranslateY() == 0);
    }
    
    @Test 
    public void moveAlongTest() {
        boolean rightCoordinates = true;
        this.testSprite.moveAlong(new Point2D(1,0));
        if (this.testSprite.getForm().getTranslateX() != 0.5 || this.testSprite.getForm().getTranslateY() != 0) {
            rightCoordinates = false;
        }
        this.testSprite.moveAlong(new Point2D(0.6,-0.8));
        if (this.testSprite.getForm().getTranslateX() != 0.8 || this.testSprite.getForm().getTranslateY() != -0.4) {
            rightCoordinates = false;
        }
        this.testSprite.getForm().setTranslateX(0);
        this.testSprite.getForm().setTranslateY(0);
        this.testSprite.moveAlong(new Point2D(-8,6));
        if (this.testSprite.getForm().getTranslateX() != -0.4 || this.testSprite.getForm().getTranslateY() != 0.3) {
            rightCoordinates = false;
        }
        assertTrue(rightCoordinates);
    }
 
}
