import java.awt.*;
import java.awt.event.*;

class Fighter extends MovingObject{
    boolean lflag;
    boolean rflag;
    boolean uflag;
    boolean dflag;
    boolean sflag;
    int delaytime;
    Image img01 = getToolkit().getImage("img/ftr.jpg");

    Fighter(int apWidth,int apHeight){
        x=(int)(apWidth/2);
        y=(int)(apHeight*0.9);
        dx=5;
        dy=5;
        w=10;
        h=10;
        lflag = false;
        rflag = false;
        uflag = false;
        dflag = false;
        sflag = false;
        delaytime=5;
    }
    void revive(int apWidth,int apHeight){
    }
    void move(Graphics buf,int apWidth,int apHeight){
        buf.setColor(Color.white);
       // buf.fillRect(x-w,y-h,2*w,2*h);
       buf.drawImage(img01, x-w,y-h,2*w, 2*h,this);
    
        if(lflag && !rflag&&x>w)
        x=x-dx;
        if(rflag && !lflag&&x<apWidth-w)
        x=x+dx;
        if(uflag && !dflag&&y>h)
        y=y-dy;
        if(dflag && !uflag&&y<apHeight-h)
        y=y+dy;

    }

}