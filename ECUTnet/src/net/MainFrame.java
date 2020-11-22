package net;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpRequest;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.awt.*;
import javax.swing.*;



class MyItemListener implements ItemListener {
    private final ImageIcon checked = new ImageIcon(MainFrame.class.getResource("/image/checked.png"));
    private final ImageIcon unchecked = new ImageIcon(MainFrame.class.getResource("/image/unchecked.png"));

    public void itemStateChanged(ItemEvent e) {
        JCheckBox jcb = (JCheckBox) e.getItem();
        {
            if (jcb.isSelected()) {
                jcb.setIcon(checked);
            } else jcb.setIcon(unchecked);
        }
    }
}

public class MainFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -1450498460078768368L;

    MainFrame() {
        try {
            init();

        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    private final ImageIcon iconLogin = new ImageIcon(MainFrame.class.getResource("/image/login.png"));
    private final ImageIcon iconLogout = new ImageIcon(MainFrame.class.getResource("/image/logout.png"));
    private final ImageIcon iconReset = new ImageIcon(MainFrame.class.getResource("/image/reset.png"));
    private final ImageIcon checked = new ImageIcon(MainFrame.class.getResource("/image/checked.png"));
    private final ImageIcon unchecked = new ImageIcon(MainFrame.class.getResource("/image/unchecked.png"));
    JLabel UserName = new JLabel("账号:");
    JLabel UserPassword = new JLabel("密码:");
    JLabel type = new JLabel("网络类型:");
    JTextField userField = new JTextField(10);
    JPasswordField passwordField = new JPasswordField(10);
    final Choice choice = new Choice();

    JButton B_submit = new JButton("登录");
    JButton B_reset = new JButton("重置");
    JButton B_logout = new JButton("注销");
    JCheckBox RB = new JCheckBox("记住密码");
    JCheckBox Auto = new JCheckBox("自动登录");
    JCheckBox AutoClose = new JCheckBox("自动关闭");
    boolean flag = false;
    boolean flagAuto = false;
    boolean flagAutoClose = false;

    public void init() throws IOException {

        SetFont.InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 23));
        setLayout(null);
        LinkedHashSet<String> ipSet = NetUtil.localIpv4s();
        String IPaddr = "";
        for (String ip : ipSet) {
            if (ip.indexOf("172") == 0) {
                IPaddr = ip;
            }
        }

        if (IPaddr.equals(""))
            IPaddr = "获取ip地址失败将无法登录";
        setTitle("By:Olvi73  版本:v2.2  当前IP:" + IPaddr);

        setLayout(new FlowLayout());
        setResizable(true);
        this.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - 280, ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - 150, 593, 180);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        B_submit.setIcon(iconLogin);
        B_logout.setIcon(iconLogout);
        B_reset.setIcon(iconReset);

        choice.add("校园网");
        choice.add("中国移动");
        choice.add("中国联通");
        choice.add("中国电信");
        choice.setFont(new Font("微软雅黑", Font.PLAIN, 22));

        passwordField.setEchoChar('*');

        UserName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        UserPassword.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        type.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        userField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        B_submit.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        B_reset.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        B_logout.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        RB.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        Auto.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        AutoClose.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        RB.setIcon(unchecked);
        Auto.setIcon(unchecked);
        AutoClose.setIcon(unchecked);

        Container ct = getContentPane();
        ct.add(UserName);
        ct.add(userField);
        ct.add(UserPassword);
        ct.add(passwordField);
        ct.add(type);
        ct.add(choice);
        ct.add(RB);
        ct.add(Auto);
        ct.add(AutoClose);

        RB.addItemListener(new MyItemListener());
        Auto.addItemListener(new MyItemListener());
        AutoClose.addItemListener(new MyItemListener());
        B_submit.setPreferredSize(new Dimension(130, 45));
        B_logout.setPreferredSize(new Dimension(130, 45));
        ct.add(B_logout);
        ct.add(B_submit);

        JButton Bt_gap = new JButton(" ");
        Bt_gap.setContentAreaFilled(false);
        Bt_gap.setBorderPainted(false);
        Bt_gap.setEnabled(false);

        ct.add(Bt_gap);
        ct.add(B_reset);

        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/image/icon.png"));
        this.setIconImage(icon.getImage());

        addPassword();
        if (Auto.isSelected()) {
            Login();
        }
        this.validate();    //重画窗口

        //对重置按钮添加监听事件
        B_reset.addActionListener(arg0 -> {
            userField.setText("");//对用户名文本框进行重置
            passwordField.setText("");//对密码文本框进行重置
            choice.select(0);
            RB.setSelected(false);
            Auto.setSelected(false);
            AutoClose.setSelected(false);
            try {
                cancelPwd();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        });

        //对确定按钮添加监听事件
        B_submit.addActionListener(arg0 -> {
            String pw = new String(passwordField.getPassword());
            if (userField.getText().equals("") || pw.equals(""))
                JOptionPane.showMessageDialog(null, "请输入所有信息！", "错误", JOptionPane.WARNING_MESSAGE);
            else
                Login();
        });
        //对确定按钮添加监听事件
        B_logout.addActionListener(arg0 -> {

            String c = "Portal";
            String a2 = "logout";
            String wlan_user_ip = "";

            String login_method = "1";
            String user_account = "drcom";
            String user_password = "123";

            LinkedHashSet<String> ipSet1 = NetUtil.localIpv4s();
            for (String ip : ipSet1) {
                if (ip.indexOf("172") == 0) {
                    wlan_user_ip = ip;
                    System.out.println("本机ip为：" + ip);
                }
            }


            System.out.println(user_account);
            System.out.println(user_password);

            String body = HttpRequest.get("http://172.21.255.105:801/eportal/")
                    .form("c", c)
                    .form("a", a2)
                    .form("wlan_user_ip", wlan_user_ip)
                    .form("login_method", login_method)
                    .form("user_account", user_account)
                    .form("user_password", user_password)
                    .form("ac_logout", 0)
                    .execute()
                    .body();
            System.out.println(body);

            String result = "({\"result\":\"1\",\"msg\":\"\\u6ce8\\u9500\\u6210\\u529f\"})";

            if (body.equals(result))

                JOptionPane.showMessageDialog(null, "注销成功", "成功", JOptionPane.INFORMATION_MESSAGE);

            else {
                body = body.substring(2, body.length() - 2).replace("\"", "");
                body = UnicodeUtil.toString(body);
                JOptionPane.showMessageDialog(null, "注销失败\n" + "错误信息:" + body, "失败", JOptionPane.ERROR_MESSAGE);
                try {
                    FileWriter fw = new FileWriter("error.txt", true);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    fw.write(df.format(new Date()) + "      注销失败  错误信息:  " + body);
                    fw.write("\r\n");
                    fw.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        });
        //对确定按钮添加监听事件
        RB.addActionListener(arg0 -> {
            if (RB.isSelected()) {
                Auto.setEnabled(true);
                AutoClose.setEnabled(true);
            } else {
                Auto.setEnabled(false);
                Auto.setSelected(false);
                AutoClose.setEnabled(false);
                AutoClose.setSelected(false);
            }
        });
    }

    private void cancelPwd() throws IOException {

        FileWriter fw = new FileWriter("users.txt");
        fw.close();
    }

    private void remPwd(String Name, String Password, String Type, String auto, String AutoClose) throws IOException {

        FileWriter fw = new FileWriter("users.txt");
        fw.write(Name);
        fw.write(" ");
        fw.write(Type);
        fw.write(" ");
        fw.write(auto);
        fw.write(" ");
        fw.write(AutoClose);
        fw.write(" ");
        String EnCode = DesUtil.getInstance("Default").getEnCodeString(Password);
        fw.write(EnCode);
        fw.close();
    }

    private void addPassword() throws IOException {
        File f = new File("users.txt");
        if (!f.exists()) {
            boolean isNewFile = false;
            try {
                isNewFile = f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println(isNewFile);
            }
        }
        BufferedReader buf = new BufferedReader(new FileReader("users.txt"));
        String line = buf.readLine();
        if (line != null) {
            flag = true;
            String[] users = line.split(" ");
            userField.setText(users[0]);
            String Decode = DesUtil.getInstance("Default").getDecodeString(users[4]);
            passwordField.setText(Decode);
            String Ck = users[2];
            String Close = users[3];
            if (Ck.equals("True")) {
                flagAuto = true;
            }
            if (Close.equals("True")) {
                flagAutoClose = true;
            }

            switch (users[1]) {
                case "@campus":
                    choice.select("校园网");
                    break;
                case "@cmcc":
                    choice.select("中国移动");
                    break;
                case "@unicom":
                    choice.select("中国联通");
                    break;
                case "@telecom":
                    choice.select("中国电信");
                    break;
            }
        }
        buf.close();
        if (flag) {
            RB.setSelected(true);
            RB.setIcon(checked);
        }
        if (RB.isSelected()) {

            if (flagAuto) {
                Auto.setSelected(true);
                Auto.setIcon(checked);
            }
            if (flagAutoClose) {
                AutoClose.setSelected(true);
                AutoClose.setIcon(checked);
            }
        } else {
            Auto.setEnabled(false);
            Auto.setIcon(unchecked);
            AutoClose.setEnabled(false);
            AutoClose.setIcon(unchecked);
        }

    }

    private void Login() {
        String Ntype = null;
        switch (choice.getSelectedItem()) {
            case "校园网":
                Ntype = "@campus";
                break;
            case "中国移动":
                Ntype = "@cmcc";
                break;
            case "中国联通":
                Ntype = "@unicom";
                break;
            case "中国电信":
                Ntype = "@telecom";
                break;
        }

        String AutoCk = "False";
        String AutoCls = "False";

        if (Auto.isSelected()) {
            AutoCk = "True";
        }
        if (AutoClose.isSelected()) {
            AutoCls = "True";
        }


        String c = "Portal";
        String a = "login";
        String login_method = "1";
//        String callback= "dr1004";
        String user_account = userField.getText().trim() + Ntype;
        String user_password = new String(passwordField.getPassword());
        String wlan_user_ip = "";

        LinkedHashSet<String> ipSet = NetUtil.localIpv4s();
        for (String ip : ipSet) {
            if (ip.indexOf("172") == 0) {
                wlan_user_ip = ip;
                System.out.println("本机ip为：" + ip);

            }
        }

        //      System.out.println(user_account);
        //      System.out.println(user_password);

        String body = HttpRequest.get("http://172.21.255.105:801/eportal/")
                .form("c", c)
                .form("a", a)
//                .form("callback",callback)
                .form("login_method", login_method)
                .form("user_account", user_account)
                .form("user_password", user_password)
                .form("wlan_user_ip", wlan_user_ip)
                .execute()
                .body();
        System.out.println(body);

        String result = "({\"result\":\"1\",\"msg\":\"\\u8ba4\\u8bc1\\u6210\\u529f\"})";

        if (body.equals(result)) {
            if (RB.isSelected()) {
                String pwd = new String(passwordField.getPassword());
                try {
                    remPwd(userField.getText().trim(), pwd, Ntype, AutoCk, AutoCls);
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            } else {
                try {
                    cancelPwd();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "登录成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            if (AutoClose.isSelected())
                System.exit(0);
        } else {
            body = body.substring(2, body.length() - 2).replace("\"", "");
            body = UnicodeUtil.toString(body);
            if (body.equals("result:0,msg:,ret_code:2")) {
                JOptionPane.showMessageDialog(null, "常用错误类型:\n当前网络已登录账号", "警告", JOptionPane.WARNING_MESSAGE);
            } else if (body.equals("result:0,msg:dXNlcmlkIGVycm9yMQ==,ret_code:1")) {
                JOptionPane.showMessageDialog(null, "认证失败:\n账号或密码错误", "警告", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "登录失败\n" + "错误信息:" + body, "失败", JOptionPane.ERROR_MESSAGE);
                try {
                    FileWriter fw = new FileWriter("error.txt", true);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    fw.write(df.format(new Date()) + "      登录失败  错误信息:  " + body);
                    fw.write("\r\n");
                    fw.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

