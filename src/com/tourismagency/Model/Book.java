package com.tourismagency.Model;

import com.tourismagency.Helper.DBConnector;
import com.tourismagency.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Book {
    private int id;
    private int hotel_id;
    private  int lodgings_id;
    private int room_id;
    private  String name;
    private String id_no; // tc
    private String phone;
    private String mail;
    private String start_date;
    private String end_date;
    private double price;
    private String note;
    private Hotel hotel;
    private Room room;
    private Lodgings lodgings;

    public Book() {

    }

    public Book(int id, int hotel_id,int lodgings_id, int room_id,String name, String id_no, String phone, String mail, String start_date, String end_date, double price, String note) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.lodgings_id=lodgings_id;
        this.room_id=room_id;
        this.name = name;
        this.id_no = id_no;
        this.phone = phone;
        this.mail = mail;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.note = note;
        this.hotel=Hotel.getFetch(hotel_id);
        this.room=Room.getFetch(room_id);
        this.lodgings=Lodgings.getFetch(lodgings_id);
    }

    public static ArrayList<Book> getList(){
        ArrayList <Book> bookList=new ArrayList<>();
        Book obj;
        String query="SELECT * FROM book";
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet data=st.executeQuery(query);
            while (data.next()){
                obj = new Book(
                        data.getInt("id"),
                        data.getInt("hotel_id"),
                        data.getInt("lodgings_id"),
                        data.getInt("room_id"),
                        data.getString("book_name"),
                        data.getString("book_idno"),
                        data.getString("book_phone"),
                        data.getString("book_mail"),
                        data.getString("book_start_date"),
                        data.getString("book_end_date"),
                        data.getDouble("book_price"),
                        data.getString("book_note")
                );
                bookList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public static  boolean add(int hotel_id,int lodgings_id,int room_id, String name, String id_no, String phone, String mail, String start_date, String end_date, double price, String note){
        String query="INSERT INTO book (hotel_id,lodgings_id, room_id, book_name, book_idno, book_phone, book_mail, book_start_date, book_end_date, book_price, book_note) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,hotel_id);
            ps.setInt(2,lodgings_id);
            ps.setInt(3,room_id);
            ps.setString(4,name);
            ps.setString(5,id_no);
            ps.setString(6,phone);
            ps.setString(7,mail);
            ps.setString(8,start_date);
            ps.setString(9,end_date);
            ps.setDouble(10,price);
            ps.setString(11,note);

            int response = ps.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update( int id, String book_name, String book_idno, String book_phone, String book_mail, String book_start_date, String book_end_date, String book_note) {

        String query = "UPDATE book SET  book_name=?, book_idno=?, book_phone=?, book_mail=?, book_start_date=?, book_end_date=?, book_note=? WHERE id=? ";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,book_name);
            ps.setString(2, book_idno);
            ps.setString(3, book_phone);
            ps.setString(4, book_mail);
            ps.setString(5,book_start_date);
            ps.setString(6,book_end_date);
            ps.setString(7,book_note);
            ps.setInt(8,id);

            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean delete(int id){
        String query = "DELETE FROM book WHERE id=?";
        try {
            //book tablosunda book idden room idye ulaşıldı.
            int room_id=Book.getRoomId(id);
            int stock=Room.getStockNumber(room_id);

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, id);

            Room.updateStockPlus(room_id,stock);
            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getRoomId(int id) {
        String query = "SELECT room_id FROM book WHERE id=?";
        int room_id = 0;
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet data=ps.executeQuery();
            if(data.next()){
                 room_id=data.getInt("room_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  room_id;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getLodgings_id() {
        return lodgings_id;
    }

    public void setLodgings_id(int lodgings_id) {
        this.lodgings_id = lodgings_id;
    }

    public Lodgings getLodgings() {
        return lodgings;
    }

    public void setLodgings(Lodgings lodgings) {
        this.lodgings = lodgings;
    }
}
