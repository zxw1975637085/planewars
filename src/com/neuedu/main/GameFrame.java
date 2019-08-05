package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.*;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameFrame extends Frame {

    //创建背景对象
    private Background background = new Background();

    //创建飞机对象
    private Plane plane = new Plane();

    //创建子弹对象
    public final List<Bullet> bulletList = new CopyOnWriteArrayList<>();

    //创建敌方飞机集合
    public final List<EnemyPlane> enemyPlaneList = new CopyOnWriteArrayList<>();

    //敌方子弹集合
    public final List<EnemyBullet> enemyBulletList = new CopyOnWriteArrayList<>();

    //加血
    public final List<Blood> bloodList = new CopyOnWriteArrayList<>();

    public boolean gameOver = false;

    //得分
    public int score = 0;

    //生命值
    public int life = 20;

    private Random random = new Random();

    @Override
    public void paint(Graphics g) {
        if (!gameOver && !Plane.status) {
            background.draw(g);
            plane.draw(g);
            for (Bullet bullet : bulletList) {
                bullet.draw(g);
            }

            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.draw(g);
            }

            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.draw(g);
            }

            for (Blood blood : bloodList) {
                blood.draw(g);
            }

            for (Bullet bullet : bulletList) {
                bullet.collisionTesting(enemyPlaneList);
            }

            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.collisionTesting(plane);
            }

            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.collisionTesting(plane);
            }

            for (Blood blood : bloodList) {
                blood.collisionTesting(plane);
            }

            g.setFont(new Font("楷体", 4, 20));
            g.setColor(Color.CYAN);
            g.drawString("生命值:" + life, 70, 70);

            g.setFont(new  Font("楷体", Font.BOLD, 20));
            g.setColor(Color.RED);
            g.drawString("无尽模式", FrameConstant.FRAME_WIDTH / 2 - 30, 50);

        }
        if (Plane.status) {
            g.setFont(new Font("宋体", Font.BOLD, 30));
            g.setColor(Color.BLUE);
            g.drawString("暂停一下!", FrameConstant.FRAME_WIDTH / 2 - 80,
                    FrameConstant.FRAME_HEIGHT / 2 + 10);
        }
        if (gameOver) {
            g.setFont(new Font("宋体", Font.BOLD, 30));
            g.setColor(Color.red);
            g.drawImage(ImageMap.get("720"), FrameConstant.FRAME_WIDTH / 2 - 200, FrameConstant.FRAME_HEIGHT / 2 - 70, null);
            g.drawString("您的得分为：" + score, FrameConstant.FRAME_WIDTH / 2 - 120,
                    FrameConstant.FRAME_HEIGHT / 2 + 30);
        }
    }

    /**
     * 使用这个方法初始化窗口
     */
    public void init() {
        //设置好尺寸
        setSize(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);
        //设置居中
        setLocationRelativeTo(null);

        enableInputMethods(false);

        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //添加键盘监听
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GameFrame gameFrame = DataStore.get("gameFrame");
                    if (random.nextInt(1000) > 980) {
                        gameFrame.enemyPlaneList.add(new EnemyPlane(random.nextInt(700), random.nextInt(100), ImageMap.get("ep01")));
                    }
                    GameFrame gameFrame1 = DataStore.get("gameFrame");
                    if (random.nextInt(1000) > 995) {
                        gameFrame1.bloodList.add(new Blood(random.nextInt(600), random.nextInt(50), ImageMap.get("blood1")));
                    }
                }
            }
        }.start();

        setVisible(true);
    }

    //闪屏问题
    private Image offScreenImage = null;//创建缓冲区

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}
