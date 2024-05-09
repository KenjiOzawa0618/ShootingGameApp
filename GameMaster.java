import java.awt.*;
import java.awt.event.*;

public class GameMaster extends Canvas implements KeyListener {
    Image buf;
    Graphics buf_gc;
    Dimension d;
    private int imgW, imgH;
    

    private int enmyAnum = 25;
    private int enmyBnum = 15;
    private int bo = 5;
    private int ftrBltNum = 10;
    private int mode = -1;
    private int i, j, a, b, c, sum, score;

    Image img = getToolkit().getImage("img/utyu.jpg");
    Image imgS = getToolkit().getImage("img/start.jpg");
    Image imgE = getToolkit().getImage("img/end.png");

    Fighter ftr;
    FighterBullet ftrBlt[] = new FighterBullet[ftrBltNum];
    EnemyA enmyA[] = new EnemyA[enmyAnum];
    EnemyB enmyB[] = new EnemyB[enmyBnum];
    Boss boss[] = new Boss[bo];

    GameMaster(int imgW, int imgH) {
        this.imgW = imgW;
        this.imgH = imgH;
        setSize(imgW, imgH);

        addKeyListener(this);

        ftr = new Fighter(imgW, imgH);
        for (i = 0; i < ftrBltNum; i++)
            ftrBlt[i] = new FighterBullet();
        for (i = 0; i < enmyAnum; i++)
            enmyA[i] = new EnemyA(imgW, imgH);
        for (i = 0; i < enmyBnum; i++)
            enmyB[i] = new EnemyB(imgW, imgH);
        for (i = 0; i < bo; i++)
            boss[i] = new Boss(imgW, imgH);
    }

    public void addNotify() {
        super.addNotify();
        buf = createImage(imgW, imgH);
        buf_gc = buf.getGraphics();
    }

    public void paint(Graphics g) {
       // buf_gc.setColor(Color.white);
        
        switch (mode) {
        case -2:
            buf_gc.setColor(Color.white);
            buf_gc.drawImage(imgE, 0,0,imgW, imgH,this);
            buf_gc.drawString("== Game over==", imgW / 2 - 80, imgH / 2 - 20);
            buf_gc.drawString("Hit SPACE bar to start game", imgW / 2 - 80, imgH / 2 + 20);
            buf_gc.drawString("Score:" + String.valueOf(score), imgW / 2 - 80, imgH / 2 + 60);
            break;
        case -1:
            buf_gc.setColor(Color.white);
            buf_gc.drawImage(imgS, 0,0,imgW, imgH,this);
            buf_gc.drawString("== Shooting Game Title ==", imgW / 2 - 80, imgH / 2 - 20);
            buf_gc.drawString("Hit SPACE bar to start game", imgW / 2 - 80, imgH / 2 + 20);
            buf_gc.drawString("FighterHP=15",imgW / 2-40, imgH / 2+60);
            String line1 = "Jellyfish HP=5 (10 points), Frog HP=10 (30 points), Alien HP=30";
            String line2 = "   100 points, appears when the score is over 200 points.";
            int x = imgW / 2 - 200;
            int y = imgH / 2 + 100;
            buf_gc.drawString(line1, x, y);  
            buf_gc.drawString(line2, x, y + 20);  

            buf_gc.drawString("score:" + String.valueOf(score), imgW / 2 - 20, imgH / 2 + 140);
            sum = 0;
            score=0;
            a = 0;
            b = 0;
            c = 0;
            break;
        default:
           buf_gc.drawImage(img, 0,0,imgW, imgH,this);
            makeEnmy: if (Math.random() < 0.1) {
                for (i = 0; i < enmyAnum; i++) {
                    if (enmyA[i].hp == 0) {
                        enmyA[i].revive(imgW, imgH);
                        break makeEnmy;
                    }
                }
                for (i = 0; i < enmyBnum; i++) {
                    if (enmyB[i].hp == 0) {
                        enmyB[i].revive(imgW, imgH);
                        break makeEnmy;
                    }
                }
            }
            if(sum>200){
            makeEnmy: if (Math.random() < 0.1){
                
                for (i = 0; i < bo; i++) {
                    if (boss[i].hp == 0) {
                        boss[i].revive(imgW, imgH);
                        break makeEnmy;
                        }
                     }
                }
            }

            if (ftr.sflag == true && ftr.delaytime == 0) {
                for (i = 0; i < ftrBltNum; i++) {
                    if (ftrBlt[i].hp == 0) {
                        ftrBlt[i].revive(ftr.x, ftr.y);
                        ftr.delaytime = 5;
                        break;
                    }
                }
            } 
            else if (ftr.delaytime > 0)
                ftr.delaytime--;

            for (i = 0; i < enmyAnum; i++)
                if (enmyA[i].hp > 0) {
                    ftr.collisionCheck(enmyA[i]);
            for (j = 0; j < ftrBltNum; j++)
                if (ftrBlt[j].hp > 0)
                    ftrBlt[j].collisionCheck(enmyA[i]);
                if ( enmyA[i].hp == 0 ) {
                     a++;
                             }
                }
            for (i = 0; i < enmyBnum; i++)
                if (enmyB[i].hp > 0) {
                    ftr.collisionCheck(enmyB[i]);
            for (j = 0; j < ftrBltNum; j++)
                if (ftrBlt[j].hp > 0)
                    ftrBlt[j].collisionCheck(enmyB[i]);
                if ( enmyB[i].hp == 0) {
                     b++;
                             }

                }
                
             for (i = 0; i < bo; i++)
                if (boss[i].hp > 0) {
                    ftr.collisionCheck(boss[i]);
             for (j = 0; j < ftrBltNum; j++)
                if (ftrBlt[j].hp > 0)
                    ftrBlt[j].collisionCheck(boss[i]);
                if ( boss[i].hp == 0 ) {
                     c++;
                             }

                }
            sum = (10*a) + (30*b) ; 
            score= (10*a) + (30*b) + (100*c);

            if (ftr.hp < 1)

                mode = -2;

            for (i = 0; i < enmyAnum; i++)
                enmyA[i].move(buf_gc, imgW, imgH);
            for (i = 0; i < enmyBnum; i++)
                enmyB[i].move(buf_gc, imgW, imgH);
            for (i = 0; i < bo; i++)
                boss[i].move(buf_gc, imgW, imgH);
            for (i = 0; i < ftrBltNum; i++)
                ftrBlt[i].move(buf_gc, imgW, imgH);

            ftr.move(buf_gc, imgW, imgH);

            for (i = 0; i < enmyAnum; i++) {
                System.out.print(enmyA[i].hp + "");
            }
            for (i = 0; i < enmyBnum; i++) {
                 System.out.print(enmyB[i].hp + "");
            }
            for (i = 0; i < bo; i++) {
                System.out.print(boss[i].hp + "");
            }
            System.out.println("");
        }
        g.drawImage(buf, 0, 0, this);
    }

    public void update(Graphics gc) {
        paint(gc);
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
        case KeyEvent.VK_LEFT:
            ftr.lflag = true;
            break;
        case KeyEvent.VK_RIGHT:
            ftr.rflag = true;
            break;
        case KeyEvent.VK_UP:
            ftr.uflag = true;
            break;
        case KeyEvent.VK_DOWN:
            ftr.dflag = true;
            break;
        case KeyEvent.VK_SPACE:
            ftr.sflag = true;
            if (this.mode != 1) {
                this.mode++;
            }
            ftr.hp = 5;
            break;
        }
    }

    public void keyReleased(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
        case KeyEvent.VK_LEFT:
            ftr.lflag = false;
            break;
        case KeyEvent.VK_RIGHT:
            ftr.rflag = false;
            break;
        case KeyEvent.VK_UP:
            ftr.uflag = false;
            break;
        case KeyEvent.VK_DOWN:
            ftr.dflag = false;
            break;
        case KeyEvent.VK_SPACE:
            ftr.sflag = false;
            break;
        }
    }

}
