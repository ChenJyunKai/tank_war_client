/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankwarclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class GetData {
    public int socketid,keycode,arr_tankx,arr_tanky,arr_tankx_2,arr_tanky_2;
    public Socket socket;
    public boolean sendswich;
    public int random_bulletlvup[]=new int[2];
    public int random_walllvup[]=new int[2];
    
    public void getdata(int keycode,int socketid){
        this.keycode=keycode;
        this.socketid=socketid;
    }
    public void getsocket(Socket socket){
        this.socket=socket;
    }
    public void turnsendswich(boolean turn){
        this.sendswich=turn;
    }
    public void saverandom(int []random_b,int []random_w){
        this.random_bulletlvup=random_b;
        this.random_walllvup=random_w;
    }
    public void getdata_f(int arr_tankx,int arr_tanky){
        this.arr_tankx=arr_tankx;
        this.arr_tanky=arr_tanky;
    }
    public void getdata_r(int arr_tankx,int arr_tanky){
        this.arr_tankx_2=arr_tankx;
        this.arr_tanky_2=arr_tanky;
    }
    
    public boolean sendswich(){
        return sendswich;
    } 
    public int Getkeycode(){
        return keycode;
    }
    public int Getid(){
        return socketid;
    }
    public Socket Getsocket(){
        return socket;
    }
    public int[] getrandom_bulletlvup(){
        return random_bulletlvup;
    }
    public int[] getrandom_walllvup(){
        return random_walllvup;
    }
    public int Getarr_tankx(){
        return arr_tankx;
    }
    public int Getarr_tanky(){
        return arr_tanky;
    }
    public int Getarr_tankx_2(){
        return arr_tankx_2;
    }
    public int Getarr_tanky_2(){
        return arr_tanky_2;
    }
}
