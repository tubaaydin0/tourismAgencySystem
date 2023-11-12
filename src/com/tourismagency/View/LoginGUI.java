package com.tourismagency.View;

import com.tourismagency.Helper.Config;
import com.tourismagency.Helper.Helper;
import com.tourismagency.Model.Admin;
import com.tourismagency.Model.Employee;
import com.tourismagency.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_buttom;
    private JTextField fld_login_uname;
    private JTextField fld_login_upass;
    private JButton btn_login;

    public LoginGUI(){
        setContentPane(wrapper);
        setSize(400,500);
        setLocation(Helper.screenCenterPoint("x", getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_login_uname)|| Helper.isFieldEmpty(fld_login_upass)){
                    Helper.showMsg("fill");
                }
                else {
                    //Yeni u nesnesi oluşturup User ın getfetch() metodundan dönen nesneye eşitledik.
                    User u= User.getCredentials(fld_login_uname.getText(),fld_login_upass.getText());
                    //Değerlendirme Formu 8
                    if (u==null){
                        Helper.showMsg("Böyle bir kullanıcı bulunamadı.Kullanıcı adı veya parola hatalı!");
                    }else{
                        //Değerlendirme Formu 7
                        switch (u.getType()){
                            case "admin":
                                AdminGUI aGuI=new AdminGUI((Admin)u);
                                break;
                            case "employee":
                                EmployeeGUI eGuI=new EmployeeGUI((Employee) u);
                                break;
                        }
                        dispose();
                    }




                }
            }
        });
    }
public static void main(String[] args){
        Helper.setLayout();
        LoginGUI l=new LoginGUI();
}

}
