package com.tourismagency.View;

import com.tourismagency.Helper.Config;
import com.tourismagency.Helper.Helper;
import com.tourismagency.Helper.Item;
import com.tourismagency.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeGUI extends JFrame {
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JButton btn_login;
    private JTabbedPane tab_employee;
    private JPanel pnl_hotel_list;
    private JTable tbl_hotel_list;
    private JScrollPane scrl_hotel_list;
    private JPanel pnl_hotel_addanddel;
    private JTextField fld_hotel_name;
    private JTextField fld_hotel_city;
    private JTextField fld_hotel_region;
    private JTextField fld_hotel_address;
    private JTextField fld_hotel_eposta;
    private JTextField fld_hotel_phone;
    private JTextField fld_hotel_star;
    private JTextField fld_hotel_feature;
    private JButton btn_hotel_add;
    private JTextField fld_hotel_id;
    private JButton btn_hotel_delete;
    private JPanel pnl_lodgings_list;
    private JScrollPane scrol_lodgings_list;
    private JTable tbl_lodgings_list;
    private JTextField fld_lodgings_h_id;
    private JComboBox cmb_lodgings_type;
    private JButton btn_lodgings_add;
    private JTextField fld_lodgings_id;
    private JButton btn_lodgings_delete;
    private JTable tbl_room_list;
    private JScrollPane scrl_room_list;
    private JComboBox cmb_hotel_name;
    private JComboBox cmb_lodgings_name;
    private JComboBox cmb_season_name;
    private JComboBox cmb_room_type;
    private JTextField fld_stock_number;
    private JTextField fld_bed_number;
    private JTextField fld_sqr_meter;
    private JTextField fld_other_features;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JButton btn_room_add;
    private JButton btn_room_delete;
    private JTextField fld_room_id;
    private JTable tbl_season_list;
    private JScrollPane scrl_season_list;
    private JPanel pnl_lodgings_addDel;
    private JPanel pnl_season_addDel;

    private JComboBox cmb_season_type;
    private JTextField fld_season_start;
    private JTextField fld_season_end;
    private JButton btn_season_add;
    private JTextField fld_season_id;
    private JButton btn_season_delete;
    private JTextField fld_season_h_id;
    private JTextField fld_start_date_search;
    private JTextField fld_end_date_search;
    private JTextField fld_name_search;
    private JTextField fld_adult_number;
    private JTextField fld_child_number;
    private JButton btn_room_search;
    private JTable tbl_roomSearch_list;
    private JScrollPane scrl_roomSearch_list;
    private JButton btn_go_reservation;
    private JTextField fld_region_search;
    private JButton btn_search_delete;
    private JTable tbl_reservation_list;
    private JScrollPane scrl_reservation_list;
    private JTextField fld_reservation_id;
    private JButton btn_reservation_delete;
    private JTextField fld_total_person;
    private JTextField fld_total_day;
    private JTextField fld_unic_child_price;
    private JTextField fld_total_price;
    private JTextField fld_unic_adult_price;
    private JTextField fld_select_room_id;
    //Hotel Management
    //HotelList tablosu
    private DefaultTableModel mdl_hotel_list;
    private  Object[] row_hotel_list;

    //##hotellist tablosu
    //lodgingslist tablosu
    private DefaultTableModel mdl_lodgings_list;
    private  Object[] row_lodgings_list;
    //##lodgings

    //season tablosu
    private  DefaultTableModel mdl_season_list;
    private  Object[] row_season_list;
    //##season tablosu
    //##HotelManagement

    //RoomManagement
    //roomlisttablosu
    private  DefaultTableModel mdl_room_list;
    private Object[] row_room_list;

    //##roomlisttablosu
    //##RoomManagement

    //RoomSearch tablosu
    private DefaultTableModel mdl_roomSearch_list;
    private  Object[] row_roomSearch_list;
    //##Room Search

    //reservationlist Tablosu
    private  DefaultTableModel mdl_reservation_list;
    private  Object[] row_reservation_list;
    //##reservationlist Tablosu
    private final Employee employee;
    private ArrayList<Room> roomList;
    private ArrayList<Book> reservationList;


    public EmployeeGUI(Employee employee) {
        this.employee = employee;
        setContentPane(wrapper);
        setSize(2400, 1100);
        setLocationRelativeTo(null);
        // setLocation(Helper.screenCenterPoint("x", getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText(employee.getName() + ", Yönetim Paneline Hoş Geldiniz!");

        //mdl_hotel_list

        mdl_hotel_list = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) { // 0. kolonun bilgileri değiştirilmesin.
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_hotel_list = {"ID", "Otel Adı", "Şehir", "Bölge", "Tam Adres", "E-posta", "Telefon", "Yıldız", "Tesis Özellikleri"};
        mdl_hotel_list.setColumnIdentifiers(col_hotel_list);
        row_hotel_list = new Object[col_hotel_list.length];

        loadHotelModel();


        //Tablodaki tıklanan otel id bilgisi otel id bilgisin tutan textfieldlere eklendi.
        tbl_hotel_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedData = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString();
                        fld_hotel_id.setText(selectedData);
                        fld_lodgings_h_id.setText(selectedData);
                        fld_season_h_id.setText(selectedData);

                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_hotel_list");
                    }
                });

        tbl_hotel_list.setModel(mdl_hotel_list);


        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        tbl_hotel_list.getColumnModel().getColumn(0).setMaxWidth(30);
        tbl_hotel_list.getColumnModel().getColumn(7).setMaxWidth(50);


        tbl_hotel_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int id = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
                String name = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 1).toString();
                String city = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 2).toString();
                String region= tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 3).toString();
                String address= tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 4).toString();
                String eposta = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 5).toString();
                String phone = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 6).toString();
                String star = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 7).toString();
                String feature = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 8).toString();


                if (Hotel.update(id, name, city, region, address, eposta, phone, star, feature)) { // Güncelleme başarılıysa
                    Helper.showMsg("done");
                    loadLodgingsModel(id);
                    loadSeasonModel(id);
                    loadHotelCombo();
                    loadRoomModel();
                    loadReservationList();
                    loadRoomSearchModel(null);

                } else {
                    Helper.showMsg("error");
                }

            }
        });


        //##mdl_hotel_list

        //mdl_lodgings_list
        mdl_lodgings_list = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) { // tüm kolonların bilgileri değiştirilmesin.
                if (column==0 || column==1 || column==2 || column==3){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_lodgings_list = {"ID","Otel ID", "Otel Adı", "Pansiyon Türü"};
        mdl_lodgings_list.setColumnIdentifiers(col_lodgings_list);
        row_lodgings_list = new Object[col_lodgings_list.length];

        //Hotel tablosunda satıra tıklandığında o satırın Otel idsini alıp pansiyon tablosunda o otele ait bilgileri listeler.
        tbl_hotel_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedData = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString();
                        loadLodgingsModel(Integer.parseInt(selectedData));
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_hotel_list");
                    }
                });

        // Pansiyon tablosuna tıklayınca id yi formdaki pansiyon idye yazdırır.
        tbl_lodgings_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedData = tbl_lodgings_list.getValueAt(tbl_lodgings_list.getSelectedRow(), 0).toString();
                        fld_lodgings_id.setText(selectedData);
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_lodgings_list");
                    }
                });

        tbl_lodgings_list.setModel(mdl_lodgings_list);
        tbl_lodgings_list.getTableHeader().setReorderingAllowed(false);
        tbl_lodgings_list.getColumnModel().getColumn(0).setMaxWidth(30);



        //##mdl_lodgings_list
        mdl_season_list = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) { // 0,1,2. kolonun bilgileri değiştirilmesin.
                if (column==0 || column==1 || column==2){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_season_list = {"ID","Otel ID", "Otel Adı", "Dönem Adı", "Dönem Başlangıcı", "Dönem Bitişi"};
        mdl_season_list.setColumnIdentifiers(col_season_list);
        row_season_list = new Object[col_season_list.length];

        //Hotel tablosunda satıra tıklandığında o satırın Otel idsini alıp dönem tablosunda o otele ait dönem bilgilerini listeler.
        tbl_hotel_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedData = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString();
                        loadSeasonModel(Integer.parseInt(selectedData));
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_hotel_list");
                    }
                });


        // Season tablosuna tıklayınca id yi formdaki season idye yazdırır.
        tbl_season_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedData = tbl_season_list.getValueAt(tbl_season_list.getSelectedRow(), 0).toString();
                        fld_season_id.setText(selectedData);
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_season_list");
                    }
                });

        tbl_season_list.setModel(mdl_season_list);
        tbl_season_list.getTableHeader().setReorderingAllowed(false);
        tbl_season_list.getColumnModel().getColumn(0).setMaxWidth(30);

        //Season tablosu güncelleme işlemi
        //Değerlendirme Formu 10
        tbl_season_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int id = Integer.parseInt(tbl_season_list.getValueAt(tbl_season_list.getSelectedRow(), 0).toString());
                int hotel_id = Integer.parseInt(tbl_season_list.getValueAt(tbl_season_list.getSelectedRow(), 1).toString());
                String start_date =tbl_season_list.getValueAt(tbl_season_list.getSelectedRow(), 4).toString();
                String end_date= tbl_season_list.getValueAt(tbl_season_list.getSelectedRow(), 5).toString();


                if (Season.update(id ,start_date,end_date)) { // Güncelleme başarılıysa
                    Helper.showMsg("done");

                    loadSeasonModel(hotel_id);
                    loadHotelCombo();
                    loadRoomModel();
                    loadReservationList();
                    //loadRoomSearchModel(null);

                } else {
                    Helper.showMsg("error");
                }

            }
        });



        //mdl_season_list

        //##mdl_season_list

        //mdl_room_list
        mdl_room_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0,1,2,3. kolonun bilgileri değiştirilmesin.
                if (column==0 || column==1 || column==2 || column==3){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_room_list = {"ID", "Otel ID","Otel Adı", "Pansiyon Tipi", "Dönem Adı","Dönem Başlangıç","Dönem Bitiş", "Oda Tipi", "Stok Adeti", "Yatak Adeti", "Metrekare", "Diğer Özellikler", "Yetişkin Fiyat", "Çocuk Fiyat"};
        mdl_room_list.setColumnIdentifiers(col_room_list);
        row_room_list = new Object[col_room_list.length];
        loadRoomModel();

        //Tablodaki tıklanan room id bilgisi room id bilgisini tutan textfielde eklendi.
        tbl_room_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedData = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 0).toString();
                        fld_room_id.setText(selectedData);

                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_room_list");
                    }
                });


        tbl_room_list.setModel(mdl_room_list);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);
        tbl_room_list.getColumnModel().getColumn(0).setMaxWidth(30);
        loadHotelCombo();
        //loadLodgingsCombo();

        // Room tablosunda güncelleme işlemi

        tbl_room_list.getModel().addTableModelListener(e -> {
            try{
            if (e.getType() == TableModelEvent.UPDATE) {
                int id = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 0).toString());
                String room_type = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 4).toString();
                int stock = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 5).toString());
                int bed_number = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 6).toString());
                int sqr_meter = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 7).toString());
                String other_features = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 8).toString();
                double adult_price = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 9).toString());
                double child_price = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 10).toString());


                if (Room.update(id ,room_type, stock, bed_number, sqr_meter, other_features, adult_price, child_price)) { // Güncelleme başarılıysa
                    Helper.showMsg("done");
                    loadRoomModel();
                    loadReservationList();
                    loadRoomSearchModel(null);

                } else {
                    Helper.showMsg("error");
                }

            }}catch(Exception exception){

            }
        });

        //##mdl_room_list


        //mdlRoomSearch tablosu
        mdl_roomSearch_list = new DefaultTableModel();
        Object[] col_roomSearch_list = {"Oda ID","Otel ID","Baş. Tarihi","Bit. Tarihi", "Otel Adı", "Bölge", "Şehir", "Tam Adres", "Telefon", "E-posta", "Yıldız", "Tesis Özellikleri", "Oda Tipi", "Stok Adet", "Yatak Adet", "Oda Özellikleri"};
        mdl_roomSearch_list.setColumnIdentifiers(col_roomSearch_list);
        row_roomSearch_list = new Object[col_roomSearch_list.length];
        //loadRoomSearchModel(null);


            tbl_roomSearch_list.getSelectionModel().addListSelectionListener(

                    e -> {
                        try {
                        String selectedroomID = (tbl_roomSearch_list.getValueAt(tbl_roomSearch_list.getSelectedRow(), 0).toString());
                        fld_select_room_id.setText(selectedroomID);
                    }catch (ArrayIndexOutOfBoundsException exception){
                System.out.println(exception.getMessage());
            }
                    });

            tbl_roomSearch_list.setModel(mdl_roomSearch_list);
            tbl_roomSearch_list.getTableHeader().setReorderingAllowed(false);
            tbl_roomSearch_list.getColumnModel().getColumn(0).setMaxWidth(60);


        //## mdl_roomSearch_list


        //mdl_reservation_list
        mdl_reservation_list=new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) { //Seçili kolonlar değiştirilmesin.
                    if (column<=5 ||column==12){
                        return false;
                    }
                    return super.isCellEditable(row, column);
                }

        };
        Object[] col_reservation_list={"ID","Bölge","Şehir","Otel Adı","Pansiyon Tipi","Oda Tipi","Müşteri Adı","TC No","Telefon","Mail","Başlangıç Tarihi","Bitiş Tarihi","Fiyat","Ek Not"};
        mdl_reservation_list.setColumnIdentifiers(col_reservation_list);
        row_reservation_list=new Object[col_reservation_list.length];
        loadReservationList();

        tbl_reservation_list.getSelectionModel().addListSelectionListener(
                e -> {
                    try {
                        String selectedId = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 0).toString();
                        fld_reservation_id.setText(selectedId);

                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_reservation_list");
                    }
                });

        tbl_reservation_list.setModel(mdl_reservation_list);
        tbl_reservation_list.getTableHeader().setReorderingAllowed(false);
        tbl_reservation_list.getColumnModel().getColumn(0).setMaxWidth(30);

        //Rezervasyon listesi güncelleme
        tbl_reservation_list.getModel().addTableModelListener(e -> {
            try{
                if (e.getType() == TableModelEvent.UPDATE) {
                    int id = Integer.parseInt(tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 0).toString());
                    String book_name = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 6).toString();
                    String book_idno = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 7).toString();
                    String book_phone = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 8).toString();
                    String book_mail = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 9).toString();
                    String book_start_date = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 10).toString();
                    String book_end_date= tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 11).toString();
                    String book_note = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(), 13).toString();


                    if (Book.update(id, book_name, book_idno, book_phone, book_mail, book_start_date, book_end_date, book_note)) { // Güncelleme başarılıysa
                        Helper.showMsg("done");
                        loadReservationList();


                    } else {
                        Helper.showMsg("error");
                    }

                }}catch(Exception exception){

            }
        });

        //## mdl_reservation_list

        //Değerlendirme Formu 9
        btn_hotel_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_name) || Helper.isFieldEmpty(fld_hotel_city) || Helper.isFieldEmpty(fld_hotel_region) || Helper.isFieldEmpty(fld_hotel_address) || Helper.isFieldEmpty(fld_hotel_phone) || Helper.isFieldEmpty(fld_hotel_eposta) || Helper.isFieldEmpty(fld_hotel_feature) || Helper.isFieldEmpty(fld_hotel_star)) {
                Helper.showMsg("fill");
            } else {
                //String name, String city,String region, String address,String eposta,String phone_number, int star, String feature
                if (Hotel.add(fld_hotel_name.getText(), fld_hotel_city.getText(), fld_hotel_region.getText(), fld_hotel_address.getText(), fld_hotel_eposta.getText(), fld_hotel_phone.getText(), fld_hotel_star.getText(), fld_hotel_feature.getText())) {
                    Helper.showMsg("done");

                    fld_hotel_name.setText(null);
                    fld_hotel_city.setText(null);
                    fld_hotel_region.setText(null);
                    fld_hotel_address.setText(null);
                    fld_hotel_eposta.setText(null);
                    fld_hotel_phone.setText(null);
                    fld_hotel_star.setText(null);
                    fld_hotel_feature.setText(null);
                    loadHotelCombo();
                    // loadLodgingsCombo();
                    loadHotelModel();


                } else {
                    Helper.showMsg("error");
                }


            }

        });
        btn_hotel_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int id = Integer.parseInt(fld_hotel_id.getText());
                    if (Hotel.delete(id)) {
                        Helper.showMsg("done");
                        loadHotelCombo();
                        // loadLodgingsCombo();
                        loadHotelModel();

                        fld_hotel_id.setText(null);
                    }

                }
            }
        });
        btn_login.addActionListener(e -> {
            dispose();
            new LoginGUI();
        });

        //Değerlendirme Formu 9
        btn_lodgings_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_lodgings_h_id)) {
                Helper.showMsg("fill");
            } else {
                int id = Integer.parseInt(fld_lodgings_h_id.getText());
                String type = cmb_lodgings_type.getSelectedItem().toString();
                if (Lodgings.add(id, type)) {
                    Helper.showMsg("done");
                    int h_id = Integer.parseInt(fld_lodgings_h_id.getText());
                    loadHotelModel();
                    loadLodgingsModel(h_id);
                    loadRoomModel();
                    loadSeasonModel(h_id);
                    loadHotelCombo();
                    loadReservationList();
                    loadRoomSearchModel(null);
                    cmb_lodgings_type.setSelectedItem(null);

                }
            }


        });
        btn_lodgings_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_lodgings_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int londgings_id = Integer.parseInt(fld_lodgings_id.getText());
                    if (Lodgings.delete(londgings_id)) {
                        Helper.showMsg("done");
                        int hotel_id = Integer.parseInt(fld_hotel_id.getText());
                        loadLodgingsModel(hotel_id);
                        loadHotelModel();
                        loadRoomModel();
                        loadSeasonModel(hotel_id);
                        loadHotelCombo();
                        loadReservationList();
                        loadRoomSearchModel(null);
                        fld_lodgings_id.setText(null);
                    }
                }
            }
        });


        cmb_hotel_name.addActionListener(e -> {
            cmb_lodgings_name.removeAllItems();
            cmb_season_name.removeAllItems();
            try {
                //Hotel_idye göre o otelin pansiyonları cmb_lodgingste listelendi.
                Item hotelItem = (Item) cmb_hotel_name.getSelectedItem();

                for (Lodgings obj : Lodgings.getList(hotelItem.getKey())) {
                    cmb_lodgings_name.addItem(new Item(obj.getId(), obj.getType()));
                }
                cmb_lodgings_name.setSelectedItem(null);

                //Hotel idye göre o otelin dönemleri cmb_seson_name e gelecek.
                for (Season obj : Season.getList(hotelItem.getKey())) {
                    cmb_season_name.addItem(new Item(obj.getId(), obj.getName()));
                }
                cmb_season_name.setSelectedItem(null);


            } catch (Exception ex) {

            }
        });

        //Değerlendirme Formu 11 ,12
        btn_room_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_stock_number) || Helper.isFieldEmpty(fld_bed_number) || Helper.isFieldEmpty(fld_sqr_meter) || Helper.isFieldEmpty(fld_other_features) || Helper.isFieldEmpty(fld_adult_price) || Helper.isFieldEmpty(fld_child_price)) {
                    Helper.showMsg("fill");

                } else {
                    Item hotelItem = (Item) cmb_hotel_name.getSelectedItem();
                    int hotel_id=hotelItem.getKey();
                   // Item lodgingsItem = (Item) cmb_hotel_name.getSelectedItem();
                    int lodgingItem=Lodgings.getFetch(Hotel.getFetch(cmb_hotel_name.getSelectedItem().toString()).getId(),null).getId();
                    //int seasonItem = Season.getFetch(cmb_season_name.getSelectedItem().toString()).getId();
                    int seasonItem=Season.getFetchH(cmb_season_name.getSelectedItem().toString(),hotel_id).getId();
                    String room_type = cmb_room_type.getSelectedItem().toString();
                    int stock = Integer.parseInt(fld_stock_number.getText());
                    int bed_number = Integer.parseInt(fld_bed_number.getText());
                    int sqr_meter = Integer.parseInt(fld_sqr_meter.getText());
                    String o_features = fld_other_features.getText();
                    double a_price = Double.parseDouble(fld_adult_price.getText());
                    double c_price = Double.parseDouble(fld_child_price.getText());

                   // for (Season season: Season.getListByHotelId())

                    if (Room.add(hotelItem.getKey(), lodgingItem, seasonItem, room_type, stock, bed_number, sqr_meter, o_features, a_price, c_price)) {
                        Helper.showMsg("done");
                        loadRoomModel();
                        cmb_hotel_name.setSelectedItem(null);
                        cmb_room_type.setSelectedItem(null);
                        fld_stock_number.setText(null);
                        fld_bed_number.setText(null);
                        fld_sqr_meter.setText(null);
                        fld_other_features.setText(null);
                        fld_adult_price.setText(null);
                        fld_child_price.setText(null);


                    }

                }
            }
        });
        btn_room_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_room_id)) {
                Helper.showMsg("fill");
            } else {
                int id = Integer.parseInt(fld_room_id.getText());
                if (Room.delete(id)) {
                    Helper.showMsg("done");
                    loadRoomModel();
                } else {
                    Helper.showMsg("error");
                }
            }
        });


        //Değerlendirme Formu 10
        btn_season_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_season_h_id) || Helper.isFieldEmpty(fld_season_start) || Helper.isFieldEmpty(fld_season_end)) {
                Helper.showMsg("fill");
            } else {
                int hotel_id = Integer.parseInt(fld_season_h_id.getText());
                String name = cmb_season_type.getSelectedItem().toString();
                String start_date = fld_season_start.getText();
                String end_date = fld_season_end.getText();
                if (Season.add(hotel_id, name, start_date, end_date)) {
                    Helper.showMsg("done");
                    fld_season_start.setText(null);
                    fld_season_end.setText(null);
                    cmb_season_name.setSelectedItem(null);
                    loadSeasonModel(hotel_id);
                    loadLodgingsModel(hotel_id);
                    loadHotelModel();
                    loadRoomModel();
                    loadHotelCombo();
                    loadReservationList();
                    loadRoomSearchModel(null);
                    // fld_season_h_id.setText(null);
                }
            }
        });

        //Değerlendirme Formu 10
        btn_season_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_season_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int season_id = Integer.parseInt(fld_season_id.getText());
                    if (Season.delete(season_id)) {
                        Helper.showMsg("done");
                        int hotel_id = Integer.parseInt(fld_hotel_id.getText());
                        loadSeasonModel(hotel_id);
                        loadHotelModel();
                        loadLodgingsModel(hotel_id);
                        loadRoomModel();
                        loadHotelCombo();
                        loadReservationList();
                        loadRoomSearchModel(null);
                        fld_lodgings_id.setText(null);
                    }
                }
            }
        });


        //Değerlendirme Formu 13,14
        btn_room_search.addActionListener(e -> {
            try{
            String name = fld_name_search.getText();
            String region = fld_region_search.getText();
            String check_in = fld_start_date_search.getText();
            String check_out = fld_end_date_search.getText();


            if (Helper.isFieldEmpty(fld_adult_number) || fld_adult_number.getText().equals("")){
                fld_adult_number.setText("0");

            }
            if(Helper.isFieldEmpty(fld_child_number) || fld_child_number.getText().equals("")){
                fld_child_number.setText("0");
            }


            if ((Helper.isFieldEmpty(fld_region_search) && Helper.isFieldEmpty(fld_start_date_search) && Helper.isFieldEmpty(fld_end_date_search) && Helper.isFieldEmpty(fld_name_search))){
                loadRoomSearchModel(Room.getListHotelRoomSeason());// Tüm otelleri listeler
            }
           else if (Helper.isFieldEmpty(fld_region_search) && Helper.isFieldEmpty(fld_start_date_search) && Helper.isFieldEmpty(fld_end_date_search)) {
                    loadRoomSearchModel(Room.searchRoomByOnlyName(name)); // Otel adına göre listeler
            }
            else if (Helper.isFieldEmpty(fld_start_date_search) && Helper.isFieldEmpty(fld_end_date_search) && Helper.isFieldEmpty(fld_name_search)){
                    loadRoomSearchModel(Room.searchRoomByOnlyRegion(region)); // Otel bölgesine göre listeler

            }
            else if (Helper.isFieldEmpty(fld_region_search) && Helper.isFieldEmpty(fld_name_search)){
                    loadRoomSearchModel(Room.searchRoomByOnlyDate(check_in,check_out)); //Tarihe göre listeler
                    //Arama ve Rezervasyon panelindeki verilerin toplam değerlerini tutan metot.
                    calculateTotalNumbers();

            }
            else if(Helper.isFieldEmpty(fld_start_date_search) && Helper.isFieldEmpty(fld_end_date_search)){
                    loadRoomSearchModel(Room.searchRoomByNameRegion( name, region)); // İsim ve bölgeye göre listeler
            }else {
                    loadRoomSearchModel(Room.searchRoomByAll(check_in,check_out,name,region));
                    calculateTotalNumbers();

            }
            }catch(Exception ex){

            }



        });



        btn_search_delete.addActionListener(e -> {
            //loadRoomSearchModel(null);
            fld_name_search.setText(null);
            fld_region_search.setText(null);
            fld_start_date_search.setText(null);
            fld_end_date_search.setText(null);
        });


        //Değerlendirme Formu 16,17,18
        btn_go_reservation.addActionListener(e -> {

            try { // oda henüz seçilmezse hata vermesin.
                int selectRoom = Integer.parseInt(fld_select_room_id.getText());

            if (Helper.isFieldEmpty(fld_start_date_search) || Helper.isFieldEmpty(fld_end_date_search) || (Helper.isFieldEmpty(fld_adult_number)&& Helper.isFieldEmpty(fld_child_number) )) {
                Helper.showMsg("Tarih aralığı veya kişi sayısı eksik girilemez. Lütfen tekrar kontrol ediniz.");
            }else {
                if (fld_child_number.getText().equals("")) {
                    fld_child_number.setText("0");
                }else if (fld_adult_number.getText().equals("")) {
                    fld_adult_number.setText("0");
                }
                int person_number = Integer.parseInt(fld_adult_number.getText()) + Integer.parseInt(fld_child_number.getText());

                if (person_number > 0 && Integer.parseInt(fld_total_day.getText())>0) {
                    ReservationGUI resGUI = new ReservationGUI(Room.getFetch(selectRoom), fld_start_date_search.getText(), fld_end_date_search.getText());

                    if(resGUI.isVisible()) {//Form zaten açıksa
                        resGUI.incomingData(fld_total_price.getText()); //EmployeeGUI deki fld_total_price'ında ki veri resGUI deki incomingData metoduna gönderildi.
                    }else{ // Form açık değilse
                        resGUI.setVisible(true);
                        resGUI.incomingData(fld_total_price.getText());
                    }
                    resGUI.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {

                            //Değerlendirme Formu 18
                            loadReservationList();
                            loadRoomModel();
                            loadRoomSearchModel(null);
                        }
                    });


                }else{
                    Helper.showMsg("Kişi sayısı / gün sayısı belli değil. Lütfen kontrol ediniz.");
                }
            }
            }catch(NumberFormatException ne){
                Helper.showMsg("Seçilecek alanlar eksik. Lütfen kontrol ediniz.");
            }

        });

        //Değerlendirme Formu 18
        btn_reservation_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_reservation_id)) {
                Helper.showMsg("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int reservation_id = Integer.parseInt(fld_reservation_id.getText());
                    if (Book.delete(reservation_id)) {
                        Helper.showMsg("done");
                        loadReservationList();
                        loadRoomModel();
                        loadRoomSearchModel(null);
                        fld_reservation_id.setText(null);





                    }
                }
            }
        });
    }//## Kurucu metot



    //Değerlendirme Formu 15
    private void calculateTotalNumbers(){
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate;
        Date lastDate;

        try {
            firstDate= formatter.parse(fld_start_date_search.getText());
            lastDate=formatter.parse(fld_end_date_search.getText());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        Long total_day=(lastDate.getTime()-firstDate.getTime())/(1000*60*60*24);
        fld_total_day.setText(String.valueOf(total_day));

        //Oda arama tablosunda tıklanan satırın fiyat bilgisine ulaşıldı.
        tbl_roomSearch_list.getSelectionModel().addListSelectionListener(
                ex -> {
                    try {
                        int selectedData = Integer.parseInt(tbl_roomSearch_list.getValueAt(tbl_roomSearch_list.getSelectedRow(), 0).toString());
                        double child_price=(Room.getFetchForChildPrice(selectedData));
                        double adult_price=(Room.getFetchForAdultPrice(selectedData));

                        int adult_number=Integer.parseInt(fld_adult_number.getText());
                        int child_number=Integer.parseInt(fld_child_number.getText());
                        int person_number= adult_number+child_number;
                        fld_total_person.setText(String.valueOf(person_number));

                        fld_unic_child_price.setText(String.valueOf(child_price));
                        fld_unic_adult_price.setText(String.valueOf(adult_price));

                        double total_price=(total_day*adult_price*adult_number)+(total_day*child_number*child_price);
                        fld_total_price.setText(String.valueOf(total_price));


                    } catch (Exception exception) {
                        System.out.println(exception.getMessage() + "tbl_roomSearch_list");
                    }
                });
       /* int selectRoomId=Integer.parseInt(tbl_roomSearch_list.getValueAt(tbl_roomSearch_list.getSelectedRow(),0).toString());

        if (Integer.parseInt(fld_total_person.getText()) > Room.getFetch(selectRoomId).getBed_number()){
            Helper.showMsg("Odadaki yatak sayısı kişi sayısından az, lütfen başka bir odaya bakınız.");
        }*/

    }


        private void loadHotelModel () {
            DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
            clearModel.setRowCount(0);
            //{"ID","Otel Adı","Şehir","Bölge", "Tam Adres", "E-posta", "Telefon", "Yıldız","Tesis Özellikleri"}
            int i = 0;
            for (Hotel obj : Hotel.getList()) {
                i = 0;
                row_hotel_list[i++] = obj.getId();
                row_hotel_list[i++] = obj.getName();
                row_hotel_list[i++] = obj.getCity();
                row_hotel_list[i++] = obj.getRegion();
                row_hotel_list[i++] = obj.getAddress();
                row_hotel_list[i++] = obj.getEposta();
                row_hotel_list[i++] = obj.getPhone_number();
                row_hotel_list[i++] = obj.getStar();
                row_hotel_list[i++] = obj.getFeature();
                mdl_hotel_list.addRow(row_hotel_list);

            }

        }



    private void loadLodgingsModel(int hotel_id) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_lodgings_list.getModel();
        clearModel.setRowCount(0); // course tablosu silindi
        // ArrayList<Lodgings> lodginsList = Lodgings.getFetch(hotel_id);
        int i = 0;

        for (Lodgings obj : Lodgings.getList(hotel_id)) {
            i = 0;
            row_lodgings_list[i++] = obj.getId();
            row_lodgings_list[i++] = obj.getHotel_id();
            row_lodgings_list[i++] = obj.getHotel().getName();
            row_lodgings_list[i++] = obj.getType();
            mdl_lodgings_list.addRow(row_lodgings_list);

        }


    }

    private void loadSeasonModel(int hotel_id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_season_list.getModel();
        clearModel.setRowCount(0); // course tablosu silindi
        // ArrayList<Lodgings> lodginsList = Lodgings.getFetch(hotel_id);
        int i = 0;

        for (Season obj : Season.getList(hotel_id)) {
            i = 0;
            row_season_list[i++] = obj.getId();
            row_season_list[i++] = obj.getHotel_id();
            row_season_list[i++] = obj.getHotel().getName();
            row_season_list[i++] = obj.getName();
            row_season_list[i++] = obj.getStart_date();
            row_season_list[i++] = obj.getEnd_date();
            mdl_season_list.addRow(row_season_list);

        }


    }
    private void loadRoomModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);

        roomList=Room.getList();

        //{"ID","Otel ID","Otel Adı","Pansiyon Tipi","Dönem Adı", "baş tr ,bit tar","Pansiyon Tipi", "Stok Adeti", "Yatak Adeti", "Metrekare","Diğer Özellikler","Yetişkin Fiyat","Çocuk Fiyat"};
        int i = 0;
        for (Room obj : roomList) {
            i = 0;
            row_room_list[i++] = obj.getId();
            row_room_list[i++] = obj.getHotel_id();
            row_room_list[i++] = Hotel.getFetch(obj.getHotel_id()).getName();
            row_room_list[i++] = Lodgings.getFetch(obj.getLodgings_id()).getType();
            row_room_list[i++] = Season.getFetch(obj.getSeason_id()).getName();
            row_room_list[i++] = Season.getFetch(obj.getSeason_id()).getStart_date();
            row_room_list[i++] = Season.getFetch(obj.getSeason_id()).getEnd_date();
            row_room_list[i++] = obj.getRoom_type();
            row_room_list[i++] = obj.getStock();
            row_room_list[i++] = obj.getBed_number();
            row_room_list[i++] = obj.getSqr_meter();
            row_room_list[i++] = obj.getOther_features();
            row_room_list[i++] = obj.getAdult_price();
            row_room_list[i++] = obj.getChild_price();
            mdl_room_list.addRow(row_room_list);

        }

    }


    //Room panelindeki cmb_hotel_name in içini dolduran metot
    public void loadHotelCombo() {
        cmb_hotel_name.removeAllItems();

        for (Hotel obj : Hotel.getList()) {
            cmb_hotel_name.addItem(new Item(obj.getId(), obj.getName()));
        }
        cmb_hotel_name.setSelectedItem(null);


    }

    public void loadRoomSearchModel(ArrayList<Room> list){
            DefaultTableModel clearModel=(DefaultTableModel) tbl_roomSearch_list.getModel();
            clearModel.setRowCount(0);

            try{
            int i=0;

            //liste boş ve null olmadığında ekrana yazdırsın.
            if((list != null) && (!list.isEmpty()) ){

                for(Room obj: list) {
                i = 0;
                row_roomSearch_list[i++] = obj.getId();
                row_roomSearch_list[i++] = obj.getHotel_id();
                row_roomSearch_list[i++] = obj.getSeason().getStart_date();
                row_roomSearch_list[i++] = obj.getSeason().getEnd_date();
                row_roomSearch_list[i++] = obj.getHotel().getName();
                row_roomSearch_list[i++] = obj.getHotel().getRegion();
                row_roomSearch_list[i++] = obj.getHotel().getCity();
                row_roomSearch_list[i++] = obj.getHotel().getAddress();
                row_roomSearch_list[i++] = obj.getHotel().getPhone_number();
                row_roomSearch_list[i++] = obj.getHotel().getEposta();
                row_roomSearch_list[i++] = obj.getHotel().getStar();
                row_roomSearch_list[i++] = obj.getHotel().getFeature();
                row_roomSearch_list[i++] = obj.getRoom_type();
                row_roomSearch_list[i++] = obj.getStock();
                row_roomSearch_list[i++] = obj.getBed_number();
                row_roomSearch_list[i++] = obj.getOther_features();
                mdl_roomSearch_list.addRow(row_roomSearch_list);
                }
            }
            //Değer hiçbirine girilmeyip yinele butonuna tıklandığında null gönderiyoruz.
            else if (list.equals(null)){



            }else{
                Helper.showMsg("Sistemde bu verinin kaydı bulunmamaktadır!");
            }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    public void loadReservationList(){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_reservation_list.getModel();
        clearModel.setRowCount(0);
       // {"ID","Bölge","Şehir","Otel Adı","Pansiyon Tipi","Oda Tipi","Müşteri Adı","TC No","Telefon","Mail","Başlangıç Tarihi","Bitiş Tarihi","Fiyat","Ek Not"};
        int i=0;
        reservationList=Book.getList();
        try {
            for (Book obj : reservationList) {
                i = 0;
                row_reservation_list[i++] = obj.getId();
                row_reservation_list[i++] = obj.getHotel().getRegion();
                row_reservation_list[i++] = obj.getHotel().getCity();
                row_reservation_list[i++] = obj.getHotel().getName();
                row_reservation_list[i++] = obj.getLodgings().getType();
                row_reservation_list[i++] = obj.getRoom().getRoom_type();
                row_reservation_list[i++] = obj.getName();
                row_reservation_list[i++] = obj.getId_no();
                row_reservation_list[i++] = obj.getPhone();
                row_reservation_list[i++] = obj.getMail();
                row_reservation_list[i++] = obj.getStart_date();
                row_reservation_list[i++] = obj.getEnd_date();
                row_reservation_list[i++] = obj.getPrice();
                row_reservation_list[i++] = obj.getNote();
                mdl_reservation_list.addRow(row_reservation_list);
            }
        }catch (Exception e){}
    }


    }



