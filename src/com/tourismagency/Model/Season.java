package com.tourismagency.Model;

import com.tourismagency.Helper.DBConnector;
import com.tourismagency.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Season {
    private  int id;
    private  int hotel_id;
    private String name;
    private  String start_date;
    private String end_date;
    private Hotel hotel;

    public Season() {
    }

    public Season(int id, int hotel_id, String name, String start_date, String end_date) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.hotel=Hotel.getFetch(hotel_id);
    }

    public static ArrayList<Season> getList(int hotel_id) {
        ArrayList<Season> seasonList = new ArrayList<>();
        Season obj;
        String query = "SELECT * FROM season WHERE hotel_id="+hotel_id;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            //ps.setInt(1,hotel_id);
            ResultSet data=st.executeQuery(query);
            while (data.next()){
                int id=data.getInt("id");
                int h_id=data.getInt("hotel_id");
                String name=data.getString("season_name");
                String start_date=data.getString("start_date");
                String end_date=data.getString("end_date");

                obj=new Season(id,h_id,name,start_date,end_date);
                seasonList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasonList;
    }
    public static ArrayList<Season> getListByHotelId(int hotel_id) {
        ArrayList<Season> seasonList = new ArrayList<>();
        Season obj;
        String query = "SELECT * FROM season WHERE hotel_id="+hotel_id;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            //ps.setInt(1,hotel_id);
            ResultSet data=st.executeQuery(query);
            while (data.next()){
                int id=data.getInt("id");
                int h_id=data.getInt("hotel_id");
                String name=data.getString("season_name");
                String start_date=data.getString("start_date");
                String end_date=data.getString("end_date");

                obj=new Season(id,h_id,name,start_date,end_date);
                seasonList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasonList;
    }

    public static Season getFetch(int id){
        Season obj=null;
        String query="SELECT * FROM season WHERE id=?";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet data=ps.executeQuery();
            if (data.next()){
                obj=new Season();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setName(data.getString("season_name"));
                obj.setStart_date(data.getString("start_date"));
                obj.setEnd_date(data.getString("end_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Season getFetchH(String name,int hotel_id){
        Season obj=null;
        String query="SELECT * FROM season WHERE season_name=? AND hotel_id=?";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,hotel_id);
            ResultSet data=ps.executeQuery();
            if (data.next()){
                obj=new Season();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setName(data.getString("season_name"));
                obj.setStart_date(data.getString("start_date"));
                obj.setEnd_date(data.getString("end_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Season getFetch(String name,int room_id){
        Season obj=null;
        String query="SELECT * FROM season WHERE start_date=?";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ResultSet data=ps.executeQuery();
            if (data.next()){
                obj=new Season();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setName(data.getString("season_name"));
                obj.setStart_date(data.getString("start_date"));
                obj.setEnd_date(data.getString("end_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static boolean add(int hotel_id,String name, String start_date, String end_date){

        // Otele aynı dönem adının eklenmesi engellendi böylece aynı tarihlerin girilmesi engellenmiştir.
        Season findDate=Season.getFetch(hotel_id, name);
        if(findDate!= null){
            Helper.showMsg("Seçilen tarihler bu otelde önceden kaydedildiği için tekrar eklenemez!");
            return false;
        }
        String query= "INSERT INTO season (hotel_id, season_name, start_date, end_date) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,hotel_id);
            ps.setString(2,name);
            ps.setString(3,start_date);
            ps.setString(4,end_date);
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


    public static boolean update( int id,String start_date,String end_date) {

        String query = "UPDATE season SET start_date=?, end_date=? WHERE id=? ";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, start_date);
            ps.setString(2, end_date);
            ps.setInt(3, id);

            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public static boolean delete(int id){
        String query=" DELETE FROM season WHERE id=? ";

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            int respose= ps.executeUpdate();
            return respose!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Season getFetch(int hotel_id,String name){
        Season obj=null;
        String query="SELECT * FROM season WHERE hotel_id=? AND season_name=? ";

        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,hotel_id);
            ps.setString(2,name);
            ResultSet data=ps.executeQuery();
            if(data.next()){
                obj=new Season();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setName(data.getString("season_name"));
                obj.setStart_date(data.getString("start_date"));
                obj.setEnd_date(data.getString("end_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static ArrayList<Season> getFetchByID(int hotel_id){
        ArrayList<Season> seasonList= new ArrayList<>();
        Season obj=null;
        String query="SELECT * FROM season WHERE hotel_id=? ";

        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,hotel_id);

            ResultSet data=ps.executeQuery();
            while(data.next()){

                obj=new Season();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setName(data.getString("season_name"));
                obj.setStart_date(data.getString("start_date"));
                obj.setEnd_date(data.getString("end_date"));
                seasonList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasonList;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
