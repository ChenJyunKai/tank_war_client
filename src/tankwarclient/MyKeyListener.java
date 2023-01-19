/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankwarclient;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class MyKeyListener implements KeyListener{
    private JLabel tank,tank2,bullet,bullet2,fakecar2Label;
    private ImageIcon tankd,tank2d,bulletd,propimg,wall;
    private int map [][]=new int[15][15];
    private int tankdir = 2,count = 0,tmpa,tmpb,wallcheck,SocketID;
    private JLabel gdLabel[]=new JLabel[100];
    private String wallxy[]=new String[62];
    private boolean bt_lvup=false,bt2_lvup=false,p2first=true;
    private GetData getdata;
    private boolean notfirsttank = false,notfirsttank2 = false;
    private boolean r_notfirsttank = false,r_notfirsttank2 = false;
    
    public MyKeyListener() {
        super();
    }

    public void getlistendata(JLabel tank,JLabel tank2,ImageIcon tankd,ImageIcon tank2d,int map[][],JLabel bullet,JLabel bullet2,JLabel gdLabel[],String wallxy[],int SocketID,GetData getdata,JLabel fakecar2Label){
        this.tank = tank;
        this.tank2 = tank2;
        this.tankd = tankd;
        this.tank2d = tank2d;
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                this.map[i][j]=map[i][j];
            }
        }
        for(int i=0;i<100;i++){
            this.gdLabel[i]=gdLabel[i];
        }        
        this.bullet = bullet;
        this.bullet2 = bullet2;
        for(int i=0;i<wallxy.length;i++){
            this.wallxy[i] = wallxy[i];
        }    
        this.SocketID=SocketID;
        this.getdata=getdata;
        this.fakecar2Label=fakecar2Label;
        System.out.println("test listener");
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    //每按下一個任意鍵盤，此方法都會自動呼叫一次
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

        /*
         * 監聽鍵盤上下左右鍵，改變tnak的座標
         */
        //TankwarClient
        int keyCode = e.getKeyCode();//獲取所按鍵盤的鍵盤編碼
        boolean turnon = true;
        getdata.getdata(keyCode,SocketID);
        getdata.turnsendswich(turnon);

        if(SocketID==1){
            int tankx = tank.getX();//獲取tank當前的橫座標值
            int tanky = tank.getY();//獲取tank當前的縱座標值
            int width = tank.getWidth();//獲取tnak當前的寬
            int height = tank.getHeight();//獲取tank當前的高

            //test
            int arr_tankx=(tankx-4)/65;        //左右
            int arr_tanky=(tanky-3)/65;        //上下
            //System.out.println(arr_tankx+","+arr_tanky);        //作標回傳

            if (keyCode == KeyEvent.VK_UP) {    //上鍵
                Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_u.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_up.png"));
                tank.setIcon(tankd);
                tankdir=1;
                if(map[arr_tankx][arr_tanky-1]==0){
                   tank.setBounds(tankx, tanky-65, width, height);
                }
                else if(map[arr_tankx][arr_tanky-1]==10||map[arr_tankx][arr_tanky-1]==11){      //吃到道具
                    tank.setBounds(tankx, tanky-65, width, height);
                    if(map[arr_tankx][arr_tanky-1]==10){
                        this.bt_lvup=true;
                        bt_u.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx][arr_tanky-1]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);                    
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky-1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky-1]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_DOWN) {  //下鍵
                Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_d.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_down.png"));
                tank.setIcon(tankd);
                tankdir=2;
                if(map[arr_tankx][arr_tanky+1]==0){
                    tank.setBounds(tankx, tanky+65, width, height);
                }
                else if(map[arr_tankx][arr_tanky+1]==10||map[arr_tankx][arr_tanky+1]==11){
                    tank.setBounds(tankx, tanky+65, width, height);
                    if(map[arr_tankx][arr_tanky+1]==10){
                        this.bt_lvup=true;
                        bt_d.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx][arr_tanky+1]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky+1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky+1]=0;
                }
            }
            else if (keyCode == KeyEvent.VK_LEFT) {  //左鍵
                Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_l.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_left.png"));
                tank.setIcon(tankd);
                tankdir=3;
                if(map[arr_tankx-1][arr_tanky]==0){
                    tank.setBounds(tankx-65, tanky, width, height);
                }
                else if(map[arr_tankx-1][arr_tanky]==10||map[arr_tankx-1][arr_tanky]==11){
                    tank.setBounds(tankx-65, tanky, width, height);
                    if(map[arr_tankx-1][arr_tanky]==10){
                        this.bt_lvup=true;
                        bt_l.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx-1][arr_tanky]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx-1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx-1][arr_tanky]=0;      
                }
            }
            else if (keyCode == KeyEvent.VK_RIGHT) {  //右鍵
                Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_r.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_right.png"));
                tank.setIcon(tankd);
                tankdir=4;
                if(map[arr_tankx+1][arr_tanky]==0){
                    tank.setBounds(tankx+65, tanky, width, height);
                }
                else if(map[arr_tankx+1][arr_tanky]==10||map[arr_tankx+1][arr_tanky]==11){
                    tank.setBounds(tankx+65, tanky, width, height);
                    if(map[arr_tankx+1][arr_tanky]==10){
                        this.bt_lvup=true;
                        bt_r.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx+1][arr_tanky]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx+1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx+1][arr_tanky]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_SPACE) {   //空格          
                switch(tankdir){
                    case 1:     //上
                        bulletd = new ImageIcon(getClass().getResource("bullet_up.png"));
                        bullet.setIcon(bulletd);
                        Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_u.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_u.Getmap(map);
                        }
                        count+=1;
                        bt_u.start();
                        this.map=bt_u.getmap();
                        break;

                    case 2:     //下
                        bulletd = new ImageIcon(getClass().getResource("bullet_down.png"));
                        bullet.setIcon(bulletd);                     
                        Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_d.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_d.Getmap(map);
                        }
                        count+=1;
                        bt_d.start();
                        this.map=bt_d.getmap();
                        break;

                    case 3:     //左
                        bulletd = new ImageIcon(getClass().getResource("bullet_left.png"));
                        bullet.setIcon(bulletd);
                        Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_l.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_l.Getmap(map);
                        }
                        count+=1;
                        bt_l.start();
                        this.map=bt_l.getmap();
                        break;
                    case 4:     //右
                        bulletd = new ImageIcon(getClass().getResource("bullet_right.png"));
                        bullet.setIcon(bulletd);                                     
                        Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_r.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_r.Getmap(map);
                        }
                        count+=1;
                        bt_r.start();
                        this.map=bt_r.getmap();
                        break;
                }
            }
        }
        if(SocketID==1){
            int tankx = tank.getX();
            int tanky = tank.getY();
            int arr_tankx=(tankx-4)/65;        
            int arr_tanky=(tanky-3)/65;
            if(notfirsttank==true){
                map[getdata.Getarr_tankx()][getdata.Getarr_tanky()]=0;
            }
            map[arr_tankx][arr_tanky]=12;
            
            getdata.getdata_f(arr_tankx,arr_tanky);
            notfirsttank=true;
        }
        
        //P2
        if(SocketID==2){
            
            if(p2first==true){
                tank2.setBounds(849, 848, 65, 65);
                tank2d = new ImageIcon(getClass().getResource("tank2_up.png"));
                tank2.setIcon(tank2d);
                propimg =new ImageIcon(getClass().getResource("sblack.png"));
                fakecar2Label.setIcon(propimg);
                p2first=false;
            }
            
            int tankx = tank2.getX();//獲取tank當前的橫座標值
            int tanky = tank2.getY();//獲取tank當前的縱座標值
            int width = tank2.getWidth();//獲取tnak當前的寬
            int height = tank2.getHeight();//獲取tank當前的高
            System.out.println(tankx);
            System.out.println(tanky);
            

            //test
            int arr_tankx=(tankx-4)/65;        //左右
            int arr_tanky=(tanky-3)/65;        //上下
            //System.out.println(arr_tankx+","+arr_tanky);        //作標回傳
           

            if (keyCode == KeyEvent.VK_UP) {    //上鍵
                Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_u.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_up.png"));
                tank2.setIcon(tank2d);
                tankdir=1;
                if(map[arr_tankx][arr_tanky-1]==0){
                   tank2.setBounds(tankx, tanky-65, width, height);
                }
                else if(map[arr_tankx][arr_tanky-1]==10||map[arr_tankx][arr_tanky-1]==11){      //吃到道具
                    tank2.setBounds(tankx, tanky-65, width, height);
                    if(map[arr_tankx][arr_tanky-1]==10){
                        this.bt2_lvup=true;
                        bt_u.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx][arr_tanky-1]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);                    
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky-1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky-1]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_DOWN) {  //下鍵
                Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_d.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_down.png"));
                tank2.setIcon(tank2d);
                tankdir=2;
                if(map[arr_tankx][arr_tanky+1]==0){
                    tank2.setBounds(tankx, tanky+65, width, height);
                }
                else if(map[arr_tankx][arr_tanky+1]==10||map[arr_tankx][arr_tanky+1]==11){
                    tank2.setBounds(tankx, tanky+65, width, height);
                    if(map[arr_tankx][arr_tanky+1]==10){
                        this.bt2_lvup=true;
                        bt_d.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx][arr_tanky+1]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky+1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky+1]=0;
                }
            }
            else if (keyCode == KeyEvent.VK_LEFT) {  //左鍵
                Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_l.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_left.png"));
                tank2.setIcon(tank2d);
                tankdir=3;
                if(map[arr_tankx-1][arr_tanky]==0){
                    tank2.setBounds(tankx-65, tanky, width, height);
                }
                else if(map[arr_tankx-1][arr_tanky]==10||map[arr_tankx-1][arr_tanky]==11){
                    tank2.setBounds(tankx-65, tanky, width, height);
                    if(map[arr_tankx-1][arr_tanky]==10){
                        this.bt2_lvup=true;
                        bt_l.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx-1][arr_tanky]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx-1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx-1][arr_tanky]=0;      
                }
            }
            else if (keyCode == KeyEvent.VK_RIGHT) {  //右鍵
                Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_r.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_right.png"));
                tank2.setIcon(tank2d);
                tankdir=4;
                if(map[arr_tankx+1][arr_tanky]==0){
                    tank2.setBounds(tankx+65, tanky, width, height);
                }
                else if(map[arr_tankx+1][arr_tanky]==10||map[arr_tankx+1][arr_tanky]==11){
                    tank2.setBounds(tankx+65, tanky, width, height);
                    if(map[arr_tankx+1][arr_tanky]==10){
                        this.bt2_lvup=true;
                        bt_r.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx+1][arr_tanky]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx+1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx+1][arr_tanky]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_SPACE) {   //空格          
                switch(tankdir){
                    case 1:     //上
                        bulletd = new ImageIcon(getClass().getResource("bullet_up.png"));
                        bullet2.setIcon(bulletd);                                      
                        Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_u.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_u.Getmap(map);
                        }
                        count+=1;
                        bt_u.start();
                        this.map=bt_u.getmap();
                        break;

                    case 2:     //下
                        bulletd = new ImageIcon(getClass().getResource("bullet_down.png"));
                        bullet2.setIcon(bulletd);           
                        Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_d.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_d.Getmap(map);
                        }
                        count+=1;
                        bt_d.start();
                        this.map=bt_d.getmap();
                        break;

                    case 3:     //左
                        bulletd = new ImageIcon(getClass().getResource("bullet_left.png"));
                        bullet2.setIcon(bulletd);             
                        Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_l.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_l.Getmap(map);
                        }
                        count+=1;
                        bt_l.start();
                        this.map=bt_l.getmap();
                        break;
                    case 4:     //右
                        bulletd = new ImageIcon(getClass().getResource("bullet_right.png"));
                        bullet2.setIcon(bulletd);              
                        Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_r.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_r.Getmap(map);
                        }
                        count+=1;
                        bt_r.start();
                        this.map=bt_r.getmap();
                        break;
                }
            }
        }
        if(SocketID==2){
            int tankx = tank2.getX();
            int tanky = tank2.getY();
            int arr_tankx=(tankx-4)/65;        
            int arr_tanky=(tanky-3)/65;  
            if(notfirsttank2==true){
                map[getdata.Getarr_tankx()][getdata.Getarr_tanky()]=0;
            }
            map[arr_tankx][arr_tanky]=13;
            getdata.getdata_f(arr_tankx,arr_tanky);
            notfirsttank2=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
    public void repeatkeycode(int SocketID,int keyCode){
    
        if(SocketID==1){
            int tankx = tank.getX();//獲取tank當前的橫座標值
            int tanky = tank.getY();//獲取tank當前的縱座標值
            int width = tank.getWidth();//獲取tnak當前的寬
            int height = tank.getHeight();//獲取tank當前的高

            //test
            int arr_tankx=(tankx-4)/65;        //左右
            int arr_tanky=(tanky-3)/65;        //上下
            //System.out.println(arr_tankx+","+arr_tanky);        //作標回傳

            if (keyCode == KeyEvent.VK_UP) {    //上鍵
                Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_u.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_up.png"));
                tank.setIcon(tankd);
                tankdir=1;
                if(map[arr_tankx][arr_tanky-1]==0){
                   tank.setBounds(tankx, tanky-65, width, height);
                }
                else if(map[arr_tankx][arr_tanky-1]==10||map[arr_tankx][arr_tanky-1]==11){      //吃到道具
                    tank.setBounds(tankx, tanky-65, width, height);
                    if(map[arr_tankx][arr_tanky-1]==10){
                        this.bt_lvup=true;
                        bt_u.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx][arr_tanky-1]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);                    
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky-1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky-1]=0;                
                }
            }
             else if (keyCode == KeyEvent.VK_DOWN) {  //下鍵
                Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_d.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_down.png"));
                tank.setIcon(tankd);
                tankdir=2;
                if(map[arr_tankx][arr_tanky+1]==0){
                    tank.setBounds(tankx, tanky+65, width, height);
                }
                else if(map[arr_tankx][arr_tanky+1]==10||map[arr_tankx][arr_tanky+1]==11){
                    tank.setBounds(tankx, tanky+65, width, height);
                    if(map[arr_tankx][arr_tanky+1]==10){
                        this.bt_lvup=true;
                        bt_d.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx][arr_tanky+1]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky+1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky+1]=0;
                }
            }
            else if (keyCode == KeyEvent.VK_LEFT) {  //左鍵
                Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_l.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_left.png"));
                tank.setIcon(tankd);
                tankdir=3;
                if(map[arr_tankx-1][arr_tanky]==0){
                    tank.setBounds(tankx-65, tanky, width, height);
                }
                else if(map[arr_tankx-1][arr_tanky]==10||map[arr_tankx-1][arr_tanky]==11){
                    tank.setBounds(tankx-65, tanky, width, height);
                    if(map[arr_tankx-1][arr_tanky]==10){
                        this.bt_lvup=true;
                        bt_l.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx-1][arr_tanky]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx-1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx-1][arr_tanky]=0;      
                }
            }
            else if (keyCode == KeyEvent.VK_RIGHT) {  //右鍵
                Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_r.Getmap(map);
                }
                tankd = new ImageIcon(getClass().getResource("tank_right.png"));
                tank.setIcon(tankd);
                tankdir=4;
                if(map[arr_tankx+1][arr_tanky]==0){
                    tank.setBounds(tankx+65, tanky, width, height);
                }
                else if(map[arr_tankx+1][arr_tanky]==10||map[arr_tankx+1][arr_tanky]==11){
                    tank.setBounds(tankx+65, tanky, width, height);
                    if(map[arr_tankx+1][arr_tanky]==10){
                        this.bt_lvup=true;
                        bt_r.Getbtlv(bt_lvup);
                    }
                    if(map[arr_tankx+1][arr_tanky]==11){
                        map[6][1]=2;
                        map[8][1]=2;
                        map[6][2]=2;
                        map[7][2]=2;
                        map[8][2]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[0].setIcon(wall);
                        gdLabel[1].setIcon(wall);
                        gdLabel[4].setIcon(wall);
                        gdLabel[5].setIcon(wall);
                        gdLabel[6].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx+1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx+1][arr_tanky]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_SPACE) {   //空格          
                switch(tankdir){
                    case 1:     //上
                        bulletd = new ImageIcon(getClass().getResource("bullet_up.png"));
                        bullet.setIcon(bulletd);     
                        Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_u.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_u.Getmap(map);
                        }
                        count+=1;
                        bt_u.start();
                        this.map=bt_u.getmap();
                        break;

                    case 2:     //下
                        bulletd = new ImageIcon(getClass().getResource("bullet_down.png"));
                        bullet.setIcon(bulletd);                  
                        Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_d.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_d.Getmap(map);
                        }
                        count+=1;
                        bt_d.start();
                        this.map=bt_d.getmap();
                        break;

                    case 3:     //左
                        bulletd = new ImageIcon(getClass().getResource("bullet_left.png"));
                        bullet.setIcon(bulletd);                   
                        Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_l.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_l.Getmap(map);
                        }
                        count+=1;
                        bt_l.start();
                        this.map=bt_l.getmap();
                        break;
                    case 4:     //右
                        bulletd = new ImageIcon(getClass().getResource("bullet_right.png"));
                        bullet.setIcon(bulletd);             
                        Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_r.Getbtlv(bt_lvup);
                        if(count!=0){
                            bt_r.Getmap(map);
                        }
                        count+=1;
                        bt_r.start();
                        this.map=bt_r.getmap();
                        break;
                }
            }
        }
        if(SocketID==1){
            int tankx = tank.getX();
            int tanky = tank.getY();
            int arr_tankx=(tankx-4)/65;        
            int arr_tanky=(tanky-3)/65;
            if(r_notfirsttank==true){
                map[getdata.Getarr_tankx_2()][getdata.Getarr_tanky_2()]=0;
            }
            map[arr_tankx][arr_tanky]=12;
            
            getdata.getdata_r(arr_tankx,arr_tanky);
            r_notfirsttank=true;
        }
        
        if(SocketID==2){
            
            if(p2first==true){
                tank2.setBounds(849, 848, 65, 65);
                tank2d = new ImageIcon(getClass().getResource("tank2_up.png"));
                tank2.setIcon(tank2d);
                propimg =new ImageIcon(getClass().getResource("sblack.png"));
                fakecar2Label.setIcon(propimg);
                p2first=false;
            }
            
            int tankx = tank2.getX();//獲取tank當前的橫座標值
            int tanky = tank2.getY();//獲取tank當前的縱座標值
            int width = tank2.getWidth();//獲取tnak當前的寬
            int height = tank2.getHeight();//獲取tank當前的高
            System.out.println(tankx);
            System.out.println(tanky);
            

            //test
            int arr_tankx=(tankx-4)/65;        //左右
            int arr_tanky=(tanky-3)/65;        //上下
            System.out.println(arr_tankx+","+arr_tanky);        //作標回傳
           

            if (keyCode == KeyEvent.VK_UP) {    //上鍵
                Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_u.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_up.png"));
                tank2.setIcon(tank2d);
                tankdir=1;
                if(map[arr_tankx][arr_tanky-1]==0){
                   tank2.setBounds(tankx, tanky-65, width, height);
                }
                else if(map[arr_tankx][arr_tanky-1]==10||map[arr_tankx][arr_tanky-1]==11){      //吃到道具
                    tank2.setBounds(tankx, tanky-65, width, height);
                    if(map[arr_tankx][arr_tanky-1]==10){
                        this.bt2_lvup=true;
                        bt_u.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx][arr_tanky-1]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);             
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky-1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky-1]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_DOWN) {  //下鍵
                Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_d.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_down.png"));
                tank2.setIcon(tank2d);
                tankdir=2;
                if(map[arr_tankx][arr_tanky+1]==0){
                    tank2.setBounds(tankx, tanky+65, width, height);
                }
                else if(map[arr_tankx][arr_tanky+1]==10||map[arr_tankx][arr_tanky+1]==11){
                    tank2.setBounds(tankx, tanky+65, width, height);
                    if(map[arr_tankx][arr_tanky+1]==10){
                        this.bt2_lvup=true;
                        bt_d.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx][arr_tanky+1]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx&&tmpb==arr_tanky+1){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx][arr_tanky+1]=0;
                }
            }
            else if (keyCode == KeyEvent.VK_LEFT) {  //左鍵
                Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_l.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_left.png"));
                tank2.setIcon(tank2d);
                tankdir=3;
                if(map[arr_tankx-1][arr_tanky]==0){
                    tank2.setBounds(tankx-65, tanky, width, height);
                }
                else if(map[arr_tankx-1][arr_tanky]==10||map[arr_tankx-1][arr_tanky]==11){
                    tank2.setBounds(tankx-65, tanky, width, height);
                    if(map[arr_tankx-1][arr_tanky]==10){
                        this.bt2_lvup=true;
                        bt_l.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx-1][arr_tanky]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx-1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx-1][arr_tanky]=0;      
                }
            }
            else if (keyCode == KeyEvent.VK_RIGHT) {  //右鍵
                Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                if(count!=0){
                    bt_r.Getmap(map);
                }
                tank2d = new ImageIcon(getClass().getResource("tank2_right.png"));
                tank2.setIcon(tank2d);
                tankdir=4;
                if(map[arr_tankx+1][arr_tanky]==0){
                    tank2.setBounds(tankx+65, tanky, width, height);
                }
                else if(map[arr_tankx+1][arr_tanky]==10||map[arr_tankx+1][arr_tanky]==11){
                    tank2.setBounds(tankx+65, tanky, width, height);
                    if(map[arr_tankx+1][arr_tanky]==10){
                        this.bt2_lvup=true;
                        bt_r.Getbt2lv(bt2_lvup);
                    }
                    if(map[arr_tankx+1][arr_tanky]==11){
                        map[6][13]=2;
                        map[8][13]=2;
                        map[6][12]=2;
                        map[7][12]=2;
                        map[8][12]=2;
                        wall =new ImageIcon(getClass().getResource("lv2_wall.png"));
                        gdLabel[55].setIcon(wall);
                        gdLabel[56].setIcon(wall);
                        gdLabel[57].setIcon(wall);
                        gdLabel[60].setIcon(wall);
                        gdLabel[61].setIcon(wall);
                    }
                    for(int i=0;i<wallxy.length;i++){
                        String tmpwallString[]=wallxy[i].split(",");
                        tmpa=Integer.parseInt(tmpwallString[0]);
                        tmpb=Integer.parseInt(tmpwallString[1]);
                        if(tmpa==arr_tankx+1&&tmpb==arr_tanky){
                            wallcheck=i;
                            break;
                        }
                    }
                    propimg =new ImageIcon(getClass().getResource("sblack.png"));
                    gdLabel[wallcheck].setIcon(propimg);
                    map[arr_tankx+1][arr_tanky]=0;                
                }
            }
            else if (keyCode == KeyEvent.VK_SPACE) {   //空格          
                switch(tankdir){
                    case 1:     //上
                        bulletd = new ImageIcon(getClass().getResource("bullet_up.png"));
                        bullet2.setIcon(bulletd);          
                        Bullet bt_u=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_u.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_u.Getmap(map);
                        }
                        count+=1;
                        bt_u.start();
                        this.map=bt_u.getmap();
                        break;

                    case 2:     //下
                        bulletd = new ImageIcon(getClass().getResource("bullet_down.png"));
                        bullet2.setIcon(bulletd);                
                        Bullet bt_d=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_d.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_d.Getmap(map);
                        }
                        count+=1;
                        bt_d.start();
                        this.map=bt_d.getmap();
                        break;

                    case 3:     //左
                        bulletd = new ImageIcon(getClass().getResource("bullet_left.png"));
                        bullet2.setIcon(bulletd);              
                        Bullet bt_l=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_l.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_l.Getmap(map);
                        }
                        count+=1;
                        bt_l.start();
                        this.map=bt_l.getmap();
                        break;
                    case 4:     //右
                        bulletd = new ImageIcon(getClass().getResource("bullet_right.png"));
                        bullet2.setIcon(bulletd);
                        Bullet bt_r=new Bullet(tankx,tanky,arr_tankx,arr_tanky,bullet,bullet2,map,gdLabel,count,tankdir,wallxy,SocketID,tank,tank2);
                        bt_r.Getbt2lv(bt2_lvup);
                        if(count!=0){
                            bt_r.Getmap(map);
                        }
                        count+=1;
                        bt_r.start();
                        this.map=bt_r.getmap();
                        break;
                }
            }
        }
        if(SocketID==2){
            int tankx = tank2.getX();
            int tanky = tank2.getY();
            int arr_tankx=(tankx-4)/65;        
            int arr_tanky=(tanky-3)/65;  
            if(r_notfirsttank2==true){
                map[getdata.Getarr_tankx_2()][getdata.Getarr_tanky_2()]=0;
            }
            map[arr_tankx][arr_tanky]=13;

            getdata.getdata_r(arr_tankx,arr_tanky);
            r_notfirsttank2=true;
        }
    }
}
