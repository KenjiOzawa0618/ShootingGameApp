import java.awt.*;
class EnemyB extends MovingObject{
    Image img03 = getToolkit().getImage("img/EnemyB.jpg");
    EnemyB(int apWidth,int apHeight){
        super(apWidth,apHeight);
        w = 50;
        h = 50;
        hp = 0;
       
    }
    void move(Graphics buf, int apWidth, int apHeight){
        buf.setColor(Color.blue);
        if(hp>0){
           // buf.drawRect(x, y, w, h);
            buf.drawImage(img03,x, y, w, h,this);
            x = x+dx;
            y = y+dy;
            if(y>apHeight+h)
            hp=0;
        }
    }
    void revive(int apWidth,int apHeight){
        x= (int)(Math.random()*(apWidth-2*w)+w);
        y= -h;
        dy=1;
        if(x<apWidth/2)
            dx = -(int)(Math.random()*2);
            hp = 10;
    }
}