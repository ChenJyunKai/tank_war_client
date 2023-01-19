/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankwarclient;


import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class Bullet extends Thread{
    private JLabel bullet,bullet2,tank,tank2;
    private int map [][]=new int[15][15];
    private int btx,bty,arr_btx,arr_bty,win_check=0;
    private int btdir,tmpa,tmpb,wallcheck,SocketID;
    private ImageIcon bwall,gdwall;
    private JLabel gdLabel[]=new JLabel[100];
    private String wallxy[]=new String[62];
    private boolean bt_lvup=false;
    private boolean bt2_lvup=false;
    
    public Bullet(int tankx,int tanky,int arr_tankx,int arr_tanky,JLabel bullet,JLabel bullet2,int map[][],JLabel gdLabel[],int count,int tankdir,String wallxy[],int SocketID,JLabel tank,JLabel tank2){
        this.btx=tankx;
        this.bty=tanky;
        this.arr_btx=arr_tankx;
        this.arr_bty=arr_tanky;
        this.bullet=bullet;
        this.bullet2=bullet2;
        if(count==0){
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    this.map[i][j]=map[i][j];
                }
            }
        }
        for(int i=0;i<wallxy.length;i++){
            this.wallxy[i]=wallxy[i];
        }
        for(int i=0;i<100;i++){
            this.gdLabel[i]=gdLabel[i];
        }
        this.btdir=tankdir;
        this.SocketID=SocketID;
        this.tank=tank;
        this.tank2=tank2;
    }
    
    public int[][]Getmap(int map[][]){
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                this.map[i][j]=map[i][j];
            }
        }
        return null;
    }
    public boolean Getbtlv(boolean bt_lvup){
        this.bt_lvup=bt_lvup;
        return true;
    }
    public boolean Getbt2lv(boolean bt_lvup){
        this.bt2_lvup=bt_lvup;
        return true;
    }
    
    
    @Override
    public void run(){
        if(SocketID==1){
            switch(btdir){
                case 1:     //上
                    try {
                        int bulletcase =0;
                        if(map[arr_btx][arr_bty-1]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx][arr_bty-1]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx][arr_bty-1]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx][arr_bty-1]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx][arr_bty-1]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx][arr_bty-1]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx][arr_bty-1]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx][arr_bty-1]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx][arr_bty-1]==13){
                            bulletcase =9;
                        }
                        

                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty-1]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx][arr_bty-1]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx, bty-65, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx, bty-65, 65, 65);
                                    }
                                    bty-=65;
                                    arr_bty-=1;
                                    sleep(150);
                                    if(map[arr_btx][arr_bty-1]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        //System.out.println(tmpc);
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty-1]=0;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty-1]=10;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==4){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty-1]=11;
                                        }
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==2){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty-1]=0;
                                        }
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==13){
                                        tank2.setBounds(849, 848, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:     //遇到升級子彈道具
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty-1]=10;
                                break;
                            case 4:     //遇到升級牆壁道具
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty-1]=11;
                                }
                                break;
                            case 5:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty-1]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                break;
                            case 9:
                                tank2.setBounds(849, 848, 65, 65);
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }  
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2:     //下
                    try {
                        int bulletcase =0;
                        if(map[arr_btx][arr_bty+1]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx][arr_bty+1]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx][arr_bty+1]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx][arr_bty+1]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx][arr_bty+1]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx][arr_bty+1]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx][arr_bty+1]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx][arr_bty+1]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx][arr_bty+1]==13){
                            bulletcase =9;
                        }

                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty+1]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx][arr_bty+1]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx, bty+65, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx, bty+65, 65, 65);
                                    }
                                    bty+=65;
                                    arr_bty+=1;
                                    sleep(150);
                                    if(map[arr_btx][arr_bty+1]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty+1]=0;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty+1]=10;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==4){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty+1]=11;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx][arr_bty+1]==2){
                                    if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty+1]=0;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx][arr_bty+1]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==13){
                                        tank2.setBounds(849, 848, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty+1]=10;
                                break;
                            case 4:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty+1]=11;
                                }
                                break;
                            case 5:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty+1]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                break;
                            case 9:
                                tank2.setBounds(849, 848, 65, 65);
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }  
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 3:     //左
                    try {
                        int bulletcase =0;
                        if(map[arr_btx-1][arr_bty]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx-1][arr_bty]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx-1][arr_bty]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx-1][arr_bty]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx-1][arr_bty]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx-1][arr_bty]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx-1][arr_bty]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx-1][arr_bty]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx-1][arr_bty]==13){
                            bulletcase =9;
                        }


                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx-1][arr_bty]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx-1][arr_bty]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx-65, bty, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx-65, bty, 65, 65);
                                    }
                                    btx-=65;
                                    arr_btx-=1;
                                    sleep(150);
                                    if(map[arr_btx-1][arr_bty]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx-1][arr_bty]=0;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx-1][arr_bty]=10;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==4){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx-1][arr_bty]=11;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx-1][arr_bty]==2){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx-1][arr_bty]=0;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx-1][arr_bty]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==13){
                                        tank2.setBounds(849, 848, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx-1][arr_bty]=10;
                                break;
                            case 4:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx-1][arr_bty]=11;
                                }
                                break;
                            case 5:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx-1][arr_bty]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                break;
                            case 9:
                                tank2.setBounds(849, 848, 65, 65);
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }  
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 4:     //右
                    try {
                        int bulletcase =0;
                        if(map[arr_btx+1][arr_bty]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx+1][arr_bty]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx+1][arr_bty]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx+1][arr_bty]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx+1][arr_bty]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx+1][arr_bty]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx+1][arr_bty]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx+1][arr_bty]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx+1][arr_bty]==13){
                            bulletcase =9;
                        }

                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx+1][arr_bty]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx+1][arr_bty]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx+65, bty, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx+65, bty, 65, 65);
                                    }
                                    btx+=65;
                                    arr_btx+=1;
                                    sleep(150);
                                    if(map[arr_btx+1][arr_bty]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx+1][arr_bty]=0;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx+1][arr_bty]=10;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==4){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx+1][arr_bty]=11;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx+1][arr_bty]==2){
                                        if(bt_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx+1][arr_bty]=0;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx+1][arr_bty]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==13){
                                        tank2.setBounds(849, 848, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx+1][arr_bty]=10;
                                break;
                            case 4:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx+1][arr_bty]=11;
                                }
                                break;
                            case 5:
                                if(bt_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx+1][arr_bty]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                break;
                            case 9:
                                tank2.setBounds(849, 848, 65, 65);
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }                    
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
        }
        //  P2
        if(SocketID==2){
            switch(btdir){
                case 1:     //上
                    try {
                        int bulletcase =0;
                        if(map[arr_btx][arr_bty-1]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx][arr_bty-1]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx][arr_bty-1]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx][arr_bty-1]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx][arr_bty-1]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx][arr_bty-1]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx][arr_bty-1]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx][arr_bty-1]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx][arr_bty-1]==13){
                            bulletcase =9;
                        }

                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty-1]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx][arr_bty-1]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx, bty-65, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx, bty-65, 65, 65);
                                    }
                                    bty-=65;
                                    arr_bty-=1;
                                    sleep(150);
                                    if(map[arr_btx][arr_bty-1]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        //System.out.println(tmpc);
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty-1]=0;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty-1]=10;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==4){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty-1]=11;
                                        }
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==2){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty-1]=0;
                                        }
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty-1]==12){
                                        tank.setBounds(69, 69, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:     //遇到升級子彈道具
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty-1]=10;
                                break;
                            case 4:     //遇到升級牆壁道具
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty-1]=11;
                                }
                                break;
                            case 5:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty-1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty-1]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                tank.setBounds(69, 69, 65, 65);
                                break;
                            case 9:
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }  
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2:     //下
                    try {
                        int bulletcase =0;
                        if(map[arr_btx][arr_bty+1]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx][arr_bty+1]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx][arr_bty+1]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx][arr_bty+1]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx][arr_bty+1]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx][arr_bty+1]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx][arr_bty+1]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx][arr_bty+1]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx][arr_bty+1]==13){
                            bulletcase =9;
                        }

                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty+1]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx][arr_bty+1]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx, bty+65, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx, bty+65, 65, 65);
                                    }
                                    bty+=65;
                                    arr_bty+=1;
                                    sleep(150);
                                    if(map[arr_btx][arr_bty+1]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty+1]=0;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx][arr_bty+1]=10;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==4){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty+1]=11;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx][arr_bty+1]==2){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx][arr_bty+1]=0;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx][arr_bty+1]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx][arr_bty+1]==12){
                                        tank.setBounds(69, 69, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx][arr_bty+1]=10;
                                break;
                            case 4:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty+1]=11;
                                }
                                break;
                            case 5:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx&&tmpb==arr_bty+1){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx][arr_bty+1]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                tank.setBounds(69, 69, 65, 65);
                                break;
                            case 9:
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }  
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 3:     //左
                    try {
                        int bulletcase =0;
                        if(map[arr_btx-1][arr_bty]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx-1][arr_bty]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx-1][arr_bty]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx-1][arr_bty]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx-1][arr_bty]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx-1][arr_bty]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx-1][arr_bty]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx-1][arr_bty]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx-1][arr_bty]==13){
                            bulletcase =9;
                        }


                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx-1][arr_bty]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx-1][arr_bty]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx-65, bty, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx-65, bty, 65, 65);
                                    }
                                    btx-=65;
                                    arr_btx-=1;
                                    sleep(150);
                                    if(map[arr_btx-1][arr_bty]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx-1][arr_bty]=0;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx-1][arr_bty]=10;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==4){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx-1][arr_bty]=11;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx-1][arr_bty]==2){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx-1][arr_bty]=0;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx-1][arr_bty]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx-1][arr_bty]==12){
                                        tank.setBounds(69, 69, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx-1][arr_bty]=10;
                                break;
                            case 4:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx-1][arr_bty]=11;
                                }
                                break;
                            case 5:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx-1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx-1][arr_bty]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                tank.setBounds(69, 69, 65, 65);
                                break;
                            case 9:
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }  
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 4:     //右
                    try {
                        int bulletcase =0;
                        if(map[arr_btx+1][arr_bty]==1){
                            bulletcase =1;
                        }
                        else if(map[arr_btx+1][arr_bty]==0){
                            bulletcase =2;
                        }
                        else if(map[arr_btx+1][arr_bty]==3){
                            bulletcase =3;
                        }
                        else if(map[arr_btx+1][arr_bty]==4){
                            bulletcase =4;
                        }
                        else if(map[arr_btx+1][arr_bty]==2){
                            bulletcase =5;
                        }
                        else if(map[arr_btx+1][arr_bty]==5){
                            bulletcase =6;
                        }
                        else if(map[arr_btx+1][arr_bty]==6){
                            bulletcase =7;
                        }
                        else if(map[arr_btx+1][arr_bty]==12){
                            bulletcase =8;
                        }
                        else if(map[arr_btx+1][arr_bty]==13){
                            bulletcase =9;
                        }

                        switch(bulletcase){
                            case 1:     //第一步遇到牆壁
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx+1][arr_bty]=0;
                                break;
                            case 2:     //第一步不是牆壁，直到遇到牆壁
                                while(map[arr_btx+1][arr_bty]==0){
                                    if(SocketID==1){
                                        bullet.setBounds(btx+65, bty, 65, 65);
                                    }
                                    if(SocketID==2){
                                        bullet2.setBounds(btx+65, bty, 65, 65);
                                    }
                                    btx+=65;
                                    arr_btx+=1;
                                    sleep(150);
                                    if(map[arr_btx+1][arr_bty]==1){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx+1][arr_bty]=0;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==3){
                                        for(int i=0;i<wallxy.length;i++){
                                            String tmpwallString[]=wallxy[i].split(",");
                                            tmpa=Integer.parseInt(tmpwallString[0]);
                                            tmpb=Integer.parseInt(tmpwallString[1]);
                                            if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                wallcheck=i;
                                                break;
                                            }
                                        }
                                        gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                        gdLabel[wallcheck].setIcon(gdwall);
                                        map[arr_btx+1][arr_bty]=10;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==4){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx+1][arr_bty]=11;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx+1][arr_bty]==2){
                                        if(bt2_lvup==true){
                                            for(int i=0;i<wallxy.length;i++){
                                                String tmpwallString[]=wallxy[i].split(",");
                                                tmpa=Integer.parseInt(tmpwallString[0]);
                                                tmpb=Integer.parseInt(tmpwallString[1]);
                                                if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                                    wallcheck=i;
                                                    break;
                                                }
                                            }
                                            gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                            gdLabel[wallcheck].setIcon(gdwall);
                                            map[arr_btx+1][arr_bty]=0;
                                            break;
                                        }
                                    }
                                    else if(map[arr_btx+1][arr_bty]==5){
                                        win_check=2;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==6){
                                        win_check=1;
                                        break;
                                    }
                                    else if(map[arr_btx+1][arr_bty]==12){
                                        tank.setBounds(69, 69, 65, 65);
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for(int i=0;i<wallxy.length;i++){
                                    String tmpwallString[]=wallxy[i].split(",");
                                    tmpa=Integer.parseInt(tmpwallString[0]);
                                    tmpb=Integer.parseInt(tmpwallString[1]);
                                    if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                        wallcheck=i;
                                        break;
                                    }
                                }
                                gdwall =new ImageIcon(getClass().getResource("bullet_lvup.png"));
                                gdLabel[wallcheck].setIcon(gdwall);
                                map[arr_btx+1][arr_bty]=10;
                                break;
                            case 4:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("wall_lvup.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx+1][arr_bty]=11;
                                }
                                break;
                            case 5:
                                if(bt2_lvup==true){
                                    for(int i=0;i<wallxy.length;i++){
                                        String tmpwallString[]=wallxy[i].split(",");
                                        tmpa=Integer.parseInt(tmpwallString[0]);
                                        tmpb=Integer.parseInt(tmpwallString[1]);
                                        if(tmpa==arr_btx+1&&tmpb==arr_bty){
                                            wallcheck=i;
                                            break;
                                        }
                                    }
                                    gdwall =new ImageIcon(getClass().getResource("sblack.png"));
                                    gdLabel[wallcheck].setIcon(gdwall);
                                    map[arr_btx+1][arr_bty]=0;
                                }
                                break;
                            case 6:
                                win_check=2;
                                break;
                            case 7:
                                win_check=1;
                                break;
                            case 8:
                                tank.setBounds(69, 69, 65, 65);
                                break;
                            case 9:
                                break;
                        }
                        if(SocketID==1){
                            bullet.setBounds(4, 4, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet.setIcon(bwall);
                        }
                        if(SocketID==2){
                            bullet2.setBounds(914, 914, 65, 65);
                            bwall =new ImageIcon(getClass().getResource("gray.png"));
                            bullet2.setIcon(bwall);
                        }                    
                        if(win_check==1){
                            JOptionPane.showMessageDialog(null,"P1 WIN!");
                        }
                        if(win_check==2){
                            JOptionPane.showMessageDialog(null,"P2 WIN!");
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
        }
    }

    public int[][] getmap(){
        return map;
    }
}

