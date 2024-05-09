import java.awt.*;
class Boss extends MovingObject{
    Image img04 = getToolkit().getImage("img/Boss.png");

    Boss(int apWidth,int apHeight){
        super(apWidth,apHeight);
        w = 150;
        h = 100;
        hp = 0;
       
    
    }
    void move(Graphics buf, int apWidth, int apHeight){
        buf.setColor(Color.black);
        if(hp>0){
            //buf.drawRect(x, y, w, h);
            buf.drawImage(img04,x, y, w, h,this);
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
            hp = 30;
    }
}
