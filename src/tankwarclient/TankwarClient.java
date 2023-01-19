package tankwarclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TankwarClient extends JFrame {

    JPanel contentPanel = null, imagePanel = null;
    JLabel gdLabel[] = new JLabel[100];
    JLabel bgLabel, carLabel, car2Label, btLabel, bt2Label, homeLabel, fakecar2Label;
    ImageIcon background, groud, bwall, tank, tank2, bullet, home;
    boolean checkgetdata = false;
    int SocketID;
    public Socket socket;
    public GetData getdata;
    public MyKeyListener mykeylistener;
    public int random_bulletlvup[] = new int[2];
    public int random_walllvup[] = new int[2];

    public static void main(String[] args) throws IOException {
        //建立連線指定Ip和埠的socket
        Socket socket = new Socket("127.0.0.1", 5200);
        GetData getdata = new GetData();
        getdata.getsocket(socket);
        //獲取系統標準輸入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String socketId = in.readLine();
        int SocketID = Integer.parseInt(socketId);
        MyKeyListener mykeylistener = new MyKeyListener();
        new TankwarClient(SocketID, getdata, mykeylistener);
        //建立一個執行緒用於讀取伺服器的資訊
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        //System.out.println(in.readLine());
                        String tmp = in.readLine();
                        System.out.println(tmp);
                        char check = tmp.charAt(0);
                        if (check == 'P') {
                            String cut[] = tmp.split(":");
                            String cutPlayer[] = cut[0].split("");
                            int player = Integer.parseInt(cutPlayer[1]);
                            int code = Integer.parseInt(cut[1]);
                            if (player != SocketID) {
                                if (code == 39 || code == 38 || code == 37 || code == 40 || code == 32) {
                                    mykeylistener.repeatkeycode(player, code);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            String line = null;

            @Override
            public void run() {
                while (true) {
                    if (getdata.sendswich == true) {
                        out.println(getdata.Getkeycode());
                        out.flush();
                        boolean turnoff = false;
                        getdata.turnsendswich(turnoff);
                    }
                    sleep(50);
                }
            }

            private void sleep(int delay) {
                long start = (new Date()).getTime();
                while ((new Date()).getTime() - start < delay) {
                    continue;
                }
            }
        }).start();

        //nouse
        String line = reader.readLine();
        while (!"end".equalsIgnoreCase(line)) {
            out.println(line);
            out.flush();
            line = reader.readLine();
        }
        out.close();
        in.close();
        socket.close();
    }

    public void setbg() {
        ((JPanel) this.getContentPane()).setOpaque(false);
        background = new ImageIcon(getClass().getResource("backgroud.png"));
        bgLabel = new JLabel(background);
        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        bgLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
    }

    public TankwarClient(int SocketID, GetData getdata, MyKeyListener mykeylistener) {
        // 1.設置frame title及Layout之類型
        this.setTitle("Tank War");
        this.setLayout(new GridLayout(15, 15));
        this.SocketID = SocketID;
        this.getdata = getdata;
        this.mykeylistener = mykeylistener;
        int random_bulletlvup[] = {9, 19};
        int random_walllvup[] = {8, 11};

        // 2.地圖初始化
        int map[][] = new int[15][15];
        int wtmp = 0, wall_bulletlvup_count = 0, wall_walllvup_count = 0, bup_tmp = 0, wup_tmp = 0;
        String wallxy[] = new String[62];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if ((i == 0 && j == 0)) {     //子彈P1
                    bullet = new ImageIcon(getClass().getResource("gray.png"));
                    btLabel = new JLabel(bullet);
                    this.add(btLabel);
                    map[j][i] = 7;
                } else if ((i == 0 && j == 14)) {     //子彈P2
                    bullet = new ImageIcon(getClass().getResource("gray.png"));
                    bt2Label = new JLabel(bullet);
                    this.add(bt2Label);
                    map[j][i] = 7;
                } else if (i == 0 && j == 7) {        //P2
                    tank2 = new ImageIcon(getClass().getResource("gray.png"));
                    car2Label = new JLabel(tank2);
                    this.add(car2Label);
                    map[j][i] = 0;
                } else if (i == 0 || i == 14 || j == 0 || j == 14) {       //外圍
                    bwall = new ImageIcon(getClass().getResource("gray.png"));
                    bgLabel = new JLabel(bwall);
                    this.add(bgLabel);
                    map[j][i] = 9;
                } else if ((i != 1 && i != 5 && i != 6 && i != 7 && i != 8 && i != 9 && i != 13 && j == 2) || (i != 1 && i != 7 && i != 13 && j == 4)
                        || (i != 1 && i != 7 && i != 13 && j == 10) || (i != 1 && i != 5 && i != 6 && i != 7 && i != 8 && i != 9 && i != 13 && j == 12)) //直行,埋子彈升級道具
                {      //土牆
                    bwall = new ImageIcon(getClass().getResource("lv1_wall.png"));
                    gdLabel[wtmp] = new JLabel(bwall);
                    this.add(gdLabel[wtmp]);
                    wallxy[wtmp] = j + "," + i;
                    wtmp += 1;
                    if (wall_bulletlvup_count == random_bulletlvup[bup_tmp]) {
                        map[j][i] = 3;
                        if (bup_tmp < 1) {
                            bup_tmp += 1;
                        }
                    } else {
                        map[j][i] = 1;
                    }
                    wall_bulletlvup_count += 1;
                } else if ((i == 1 && j == 6) || (i == 1 && j == 8) || (i == 2 && j > 5 && j < 9) //p1主堡
                        || (i == 13 && j == 6) || (i == 13 && j == 8) || (i == 12 && j > 5 && j < 9)) //p2主堡
                {      //土牆
                    bwall = new ImageIcon(getClass().getResource("lv1_wall.png"));
                    gdLabel[wtmp] = new JLabel(bwall);
                    this.add(gdLabel[wtmp]);
                    wallxy[wtmp] = j + "," + i;
                    wtmp += 1;
                    map[j][i] = 1;
                } else if ((i == 4 && j > 5 && j < 9) || (i == 6 && j > 5 && j < 9)
                        || (i == 8 && j > 5 && j < 9) || (i == 10 && j > 5 && j < 9)
                        || (i == 6 && j == 1) || (i == 6 && j == 13) || (i == 8 && j == 1) || (i == 8 && j == 13)
                        || (i == 7 && j == 1) || (i == 7 && j == 2) || (i == 7 && j == 12) || (i == 7 && j == 13)) {      //鋼牆
                    bwall = new ImageIcon(getClass().getResource("lv2_wall.png"));
                    gdLabel[wtmp] = new JLabel(bwall);
                    this.add(gdLabel[wtmp]);
                    wallxy[wtmp] = j + "," + i;
                    wtmp += 1;
                    if (wall_walllvup_count == random_walllvup[wup_tmp]) {
                        map[j][i] = 4;
                        if (wup_tmp < 1) {
                            wup_tmp += 1;
                        }
                    } else {
                        map[j][i] = 2;
                    }
                    wall_walllvup_count += 1;
                } else if (i == 1 && j == 1) {        //P1
                    tank = new ImageIcon(getClass().getResource("tank_down.png"));
                    carLabel = new JLabel(tank);
                    this.add(carLabel);
                    map[j][i] = 0;
                } else if (i == 13 && j == 13) {        //偽P2
                    bwall = new ImageIcon(getClass().getResource("tank2_up.png"));
                    fakecar2Label = new JLabel(bwall);
                    this.add(fakecar2Label);
                    map[j][i] = 0;
                } else if (i == 1 && j == 7) {        //P1主堡
                    home = new ImageIcon(getClass().getResource("corn.png"));
                    homeLabel = new JLabel(home);
                    this.add(homeLabel);
                    map[j][i] = 5;
                } else if (i == 13 && j == 7) {        //P2主堡
                    home = new ImageIcon(getClass().getResource("buger.png"));
                    homeLabel = new JLabel(home);
                    this.add(homeLabel);
                    map[j][i] = 6;
                } else {       //空地
                    bwall = new ImageIcon(getClass().getResource("sblack.png"));
                    bgLabel = new JLabel(bwall);
                    this.add(bgLabel);
                    map[j][i] = 0;
                }
                //System.out.print(map[j][i]);
            }
            //System.out.println();
        }

        // 3.加入動作
        mykeylistener.getlistendata(carLabel, car2Label, tank, tank2, map, btLabel, bt2Label, gdLabel, wallxy, SocketID, getdata, fakecar2Label);
        this.addKeyListener(mykeylistener);

        // 4.設置frame之基本設定
        setbg();
        this.setSize(1000, 1020);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension jframesize = this.getSize();
        this.setLocation((screensize.width - jframesize.width) / 2, (screensize.height - jframesize.height) / 2 - 20);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
