package com.tourismagency.View;

import com.tourismagency.Helper.Config;
import com.tourismagency.Helper.Helper;
import com.tourismagency.Model.Admin;
import com.tourismagency.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_title;
    private JLabel lbl_welcome;
    private JButton btn_exit;
    private JTable tbl_user_list;
    private JScrollPane scrl_user_list;
    private JPanel pnl_user_list;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_user_title;


    //User Tablosu için
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    //##
    private final Admin admin;
    public AdminGUI(Admin admin){
        this.admin=admin;
        setContentPane(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x", getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText(admin.getName()+", Yönetim Paneline Hoş Geldiniz! ");

        //ModelUserList

        mdl_user_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. kolonun bilgileri değiştirilmesin.
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list={"ID","Ad Soyad","Kullanıcı Adı","Şifre","Kullanıcı Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list=new Object[col_user_list.length]; // satırdaki eleman sayısı sütun sayısına eşit.
        loadUserModel();
        tbl_user_list.setModel(mdl_user_list); // Tablonun modeline mdl user list modeli aktarıldı.
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        //Tabloda seçilen satırın idsini kullnaıcı sil kısmındaki id alanına ekler
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {

            try {
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(select_user_id);
            } catch (Exception ex){
                // Hata yakalayınca gösterme
            }
        });
        // Tablo Güncelleme İşlemi
        tbl_user_list.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                if (e.getType()==TableModelEvent.UPDATE){

                    int user_id=Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                    String user_name=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                    String user_uname= tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                    String user_pass=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                    String user_type=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                    if (User.update(user_id,user_name,user_uname,user_pass,user_type)){
                        Helper.showMsg("done");

                    }

                    loadUserModel();

                }

            }
        });

        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI againLogin=new LoginGUI();

            }
        });
        btn_user_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_name)|| Helper.isFieldEmpty(fld_user_uname)||Helper.isFieldEmpty(fld_user_pass)){
                    Helper.showMsg("fill");
                }else{
                    String name= fld_user_name.getText();
                    String userName=fld_user_uname.getText();
                    String password=fld_user_pass.getText();
                    String userType=cmb_user_type.getSelectedItem().toString(); // comboboxtaki seçilen veriyi değişkene atadık.
                    if (User.add(name,userName,password,userType)){
                        Helper.showMsg("done");
                        loadUserModel();

                        fld_user_name.setText(null);
                        fld_user_uname.setText(null);
                        fld_user_pass.setText(null);
                    }
                }
            }
        });



        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_id)){ // fld_user_id boşsa  tüm alanları doldurun şeklinde uyarı verecek.
                    Helper.showMsg("fill");
                }
                else {
                    if (Helper.confirm("sure")) {
                        int userID = Integer.parseInt(fld_user_id.getText());
                        if (User.delete(userID)) {
                            Helper.showMsg("done");
                            loadUserModel(); // Silme işlemi yapılınca tablo yenilendi.
                            fld_user_id.setText(null);

                        } else {
                            Helper.showMsg("error");
                        }
                    }
                }
            }
        });
        btn_user_sh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name= fld_sh_user_name.getText();
                String uname=fld_sh_user_uname.getText();
                String utype=cmb_sh_user_type.getSelectedItem().toString();
                String query=User.searchQuery(name,uname,utype);
                loadUserModel(User.searchUserList(query));
            }
        });
    }

    public  void loadUserModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (User obj: User.getList()) {
            i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPass();
            row_user_list[i++]=obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    //Filreleme İşlemi
    public  void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (User obj: list) {
            int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPass();
            row_user_list[i++]=obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }
}
