package com.tourismagency.Model;

import com.tourismagency.Helper.DBConnector;
import com.tourismagency.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Lodgings { // Pansiyon Sınıfı
    private int id;
    private int hotel_id;
    private String type;
    private Hotel hotel;


    public Lodgings(){

    }
    public Lodgings(int id, int hotel_id, String type) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.type = type;
        this.hotel= Hotel.getFetch(hotel_id);
    }



   // Veritabanına veri ekleme
   public static boolean add(int hotel_id,String type){

       // Otele aynı pansiyon tipinin girilmesi engellenmiştir.
        Lodgings findType=Lodgings.getFetch(hotel_id,type);
        if(findType!= null){
            Helper.showMsg("Seçilen pansiyon tipi bu otelde zaten olduğu için tekrar eklenemez!");
            return false;
        }
       String query= "INSERT INTO lodgings (hotel_id, type) VALUES (?, ?)";
       try {
           PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
           ps.setInt(1,hotel_id);
           ps.setString(2,type);
           int response= ps.executeUpdate();
           if (response==-1){
               Helper.showMsg("error");
           }
           return response!=-1;
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return true;
   }


  /*  public static boolean update( int id,String type) {

        String query = "UPDATE hotel SET type=?, WHERE id=? ";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,type);
            ps.setInt(2,id);

            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/


    //idye göre veritabanından silme işlemi
   public static boolean delete(int id){
        String query=" DELETE FROM lodgings WHERE id=? ";

       try {
           PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
           ps.setInt(1,id);
           int respose= ps.executeUpdate();
           return respose!=-1;
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

   }
    // hotel_id ye göre Veritabanından elemanları çekme
    public static ArrayList<Lodgings> getList(int hotel_id) {
        ArrayList<Lodgings> lodgingsList = new ArrayList<>();
        Lodgings obj;
        String query = "SELECT * FROM lodgings WHERE hotel_id="+hotel_id;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            //ps.setInt(1,hotel_id);
            ResultSet data=st.executeQuery(query);
            while (data.next()){
                int id=data.getInt("id");
                int h_id=data.getInt("hotel_id");
                String type=data.getString("type");
                obj=new Lodgings(id,h_id,type);
                lodgingsList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lodgingsList;
    }
    public static ArrayList<Lodgings> getList() {
        ArrayList<Lodgings> lodgingsList = new ArrayList<>();
        Lodgings obj;
        String query = "SELECT * FROM lodgings ";
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet data=st.executeQuery(query);
            while (data.next()){
                int id=data.getInt("id");
                int h_id=data.getInt("hotel_id");
                String type=data.getString("type");
                obj=new Lodgings(id,h_id,type);
                lodgingsList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lodgingsList;
    }

   // parametre olarak verilen pansiyon tipi veritabanında varsa  nesne dönecek (o tipe ait verileri tutan)
   // Yoksa null dönecek.
   public static Lodgings getFetch(int hotel_id,String type){
       Lodgings obj=null;
       String query;
       if (type!=null){
           query="SELECT * FROM lodgings WHERE hotel_id=? AND type=?";
       }else{
           query="SELECT * FROM lodgings WHERE hotel_id=?";
       }


       try {
           PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
           ps.setInt(1,hotel_id);
           if (type!=null){
               ps.setString(2,type);
           }
           ResultSet data=ps.executeQuery();
           if(data.next()){
               obj=new Lodgings();
               obj.setId(data.getInt("id"));
               obj.setHotel_id(data.getInt("hotel_id"));
               obj.setType(data.getString("type"));
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return obj;
   }
    public static Lodgings getFetch(int id){
        Lodgings obj=null;
        String query="SELECT * FROM lodgings WHERE id=?";

        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet data=ps.executeQuery();
            if(data.next()){
                obj=new Lodgings();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setType(data.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
