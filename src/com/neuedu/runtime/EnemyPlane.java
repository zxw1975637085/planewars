package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.Random;

public class EnemyPlane extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int speed = FrameConstant.GAME_SPEED;

    private Random random = new Random();

    public EnemyPlane() {
    }

    public EnemyPlane(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        fire();
        g.drawImage(image, getX(), getY(), image.getWidth(null),
                image.getHeight(null), null);

    }

    public void fire() {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 985) {
            gameFrame.enemyBulletList.add(new EnemyBullet(
                    getX() + (image.getWidth(null) / 2) - ((ImageMap.get("epb01").getWidth(null) / 2)),
                    getY() + image.getHeight(null), ImageMap.get("epb01")));
        }
    }

    private boolean right = true;

    @Override
    public void move() {
            if (right) {
                setX(getX() + speed);
                setY(getY() + speed);
            } else {
                setX(getX() - speed);
            }
        borderTesting();
    }

    public void borderTesting() {
        if (getX() + image.getWidth(null) >= FrameConstant.FRAME_WIDTH) {
            right = false;
        } else if (getX() < 0) {
            right = true;
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }

    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.enemyPlaneList.remove(this);
            gameFrame.life--;
        }
    }

}
