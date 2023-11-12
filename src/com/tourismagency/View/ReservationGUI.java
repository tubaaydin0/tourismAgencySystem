package com.tourismagency.View;



import com.tourismagency.Helper.Config;
import com.tourismagency.Helper.Helper;
import com.tourismagency.Model.Book;
import com.tourismagency.Model.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ReservationGUI extends JFrame {

    private JPanel wrapper;
    private JTextField fld_book_name;
    private JTextField fld_book_tc;
    private JTextField fld_book_phone;
    private JTextField fld_book_mail;
    private JTextField fld_book_startDate;
    private JTextField fld_book_endDate;
    private JTextField fld_book_price;
    private JTextArea txt_book_note;
    private JButton btn_book_save;

    private Room room;


    public ReservationGUI(Room selectedHotel, String checkIn, String checkOut){
        this.room=selectedHotel;

        add(wrapper);
        setSize(300,600);
        setLocationRelativeTo(null);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        this.fld_book_startDate.setText(checkIn);
        this.fld_book_endDate.setText(checkOut);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /*test için
        this.fld_book_name.setText("Tuğba İşlyn");
        this.fld_book_tc.setText("1111111111");
        this.fld_book_phone.setText("888888888");
        this.fld_book_mail.setText("tuba@gmail.com");
        this.fld_book_price.setText("500");
        this.txt_book_note.setText("nokta");
*/
        btn_book_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Helper.isFieldEmpty(fld_book_mail) ||Helper.isFieldEmpty(fld_book_name) || Helper.isFieldEmpty(fld_book_tc) || Helper.isFieldEmpty(fld_book_phone) || Helper.isFieldEmpty(fld_book_price)|| Helper.isFieldEmpty(fld_book_startDate)|| Helper.isFieldEmpty(fld_book_endDate)){
                    Helper.showMsg("fill");
                }else{
                  //  lbl_reservation_inf.setText("Otel Adı:"+room.getHotel().getName()+"\nPansiyon tipi: "+room.getLodgings().getType()+"\nOda Tipi: "+room.getRoom_type());

                    int hotel_id=room.getHotel_id();
                    int lodgings_id=room.getLodgings_id();
                    int room_id=room.getId();
                    String start_date=fld_book_startDate.getText();
                    String end_date=fld_book_endDate.getText();
                    String name=fld_book_name.getText();
                    String id_no=fld_book_tc.getText();
                    String phone=fld_book_phone.getText();
                    String mail=fld_book_mail.getText();
                    Double price=Double.parseDouble(fld_book_price.getText());
                    String note= txt_book_note.getText();
                    int stock=room.getStock();


                    if (Book.add(hotel_id,lodgings_id,room_id,name,id_no,phone,mail,start_date,end_date,price,note)){
                       Helper.showMsg("done");

                       dispose();

                        ArrayList<Integer> roomId=new ArrayList<>();
                        for (Book book: Book.getList()){
                            roomId.add(book.getRoom_id());

                        }
                        //Değerlendirme Formu 17
                        if(roomId.contains(room_id)){
                            Room.updateStockReduce(stock,room_id);
                        }



                    }else{
                        Helper.showMsg("error");
                    }
                //   }


                }
            }
        });
    }


    //EmployeeGUI deki fld_total_price değeri fld_book_price ye kopyalandı.
    public void incomingData(String data){
        fld_book_price.setText(data);
    }
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

