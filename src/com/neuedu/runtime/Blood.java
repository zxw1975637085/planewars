package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

import java.awt.*;

public class Blood extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int speed = FrameConstant.GAME_SPEED * 2;

    public Blood() {
    }

    public Blood(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        g.drawImage(image, getX(), getY(), image.getWidth(null),
                image.getHeight(null), null);
    }

    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting();
    }

    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bloodList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }

    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.bloodList.remove(this);
            gameFrame.life++;
        }
    }
}
