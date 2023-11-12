package com.tourismagency.Model;


import com.tourismagency.Helper.DBConnector;
import com.tourismagency.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String city;
    private String region;
    private String address;
    private String eposta;
    private String phone_number;
    private String star;
    private String feature;

    public Hotel(){

    }



    public Hotel(int id, String name, String city, String region, String address, String eposta, String phone_number, String star, String feature) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.address = address;
        this.eposta = eposta;
        this.phone_number = phone_number;
        this.star = star;
        this.feature = feature;
    }

    //Veritabanından Hotel tablosu çekildi
    public static ArrayList<Hotel> getList(){
        ArrayList<Hotel> hotelList=new ArrayList<>();
        Hotel obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet  data=st.executeQuery("SELECT * FROM hotel");
            while (data.next()){
                obj=new Hotel(
                        data.getInt("id"),
                        data.getString("name"),
                        data.getString("city"),
                        data.getString("region"),
                        data.getString("address"),
                        data.getString("eposta"),
                        data.getString("phone_number"),
                        data.getString("star"),
                        data.getString("feature"));
                hotelList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }


    public static ArrayList<Hotel> getByNameList(String name){
        ArrayList<Hotel> hotelList=new ArrayList<>();
        Hotel obj;
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement("SELECT * FROM hotel WHERE name=?");
            ps.setString(1,name);
            ResultSet  data=ps.executeQuery();
            while (data.next()){
                obj=new Hotel(
                        data.getInt("id"),
                        data.getString("name"),
                        data.getString("city"),
                        data.getString("region"),
                        data.getString("address"),
                        data.getString("eposta"),
                        data.getString("phone_number"),
                        data.getString("star"),
                        data.getString("feature"));
                hotelList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }
    // Veritabanına veri eklendi
    public static boolean add(String name, String city,String region, String address,String eposta,String phone_number, String star, String feature){
        Hotel  findMail=Hotel.getFetchByMail(eposta);
        if (findMail!=null){
            Helper.showMsg("Bu mail adresine ait otel sisteme daha önce kayıt edilmiştir.");
            return false;
        }


        String query="INSERT INTO hotel (name, city, region, address, eposta, phone_number, star, feature) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,city);
            ps.setString(3,region);
            ps.setString(4,address);
            ps.setString(5,eposta);
            ps.setString(6,phone_number);
            ps.setString(7,star);
            ps.setString(8,feature);
            int response= ps.executeUpdate();
            if (response==-1){
                Helper.showMsg("error");
            }
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static boolean update( int id,String name, String city,String region, String address, String eposta, String phone_number,String star,String feature) {

        String query = "UPDATE hotel SET  name=?, city=?, region=?, address=?, eposta=?, phone_number=?, star=?, feature=? WHERE id=? ";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2, city);
            ps.setString(3, region);
            ps.setString(4, address);
            ps.setString(5,eposta);
            ps.setString(6,phone_number);
            ps.setString(7,star);
            ps.setString(8,feature);
            ps.setInt(9,id);


            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    //Veritabanından ve tablodan veri sil
    public static boolean delete(int id){
        String query="DELETE FROM hotel WHERE id=?";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            int response=ps.executeUpdate();
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Hotel getFetch(int id){ // idye göre veritabanından nesne çeken metot Pansiyon tablosunda otelin ismi çekilirken kullanıldı
        String query="SELECT * FROM hotel WHERE id=?";
        Hotel obj = null;
        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setInt(1,id);
            ResultSet data=pt.executeQuery();
            if(data.next()){ // Veritabanında bu id varsa
                obj=new Hotel(data.getInt("id"),
                        data.getString("name"),
                        data.getString("city"),
                        data.getString("region"),
                        data.getString("address"),
                        data.getString("eposta"),
                        data.getString("phone_number"),
                        data.getString("star"),
                        data.getString("feature"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  obj;
    }
    public static Hotel getFetchByMail(String eposta){ // idye göre veritabanından nesne çeken metot Pansiyon tablosunda otelin ismi çekilirken kullanıldı
        String query="SELECT * FROM hotel WHERE eposta=?";
        Hotel obj = null;
        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setString(1,eposta);
            ResultSet data=pt.executeQuery();
            if(data.next()){ // Veritabanında bu id varsa
                obj=new Hotel(data.getInt("id"),
                        data.getString("name"),
                        data.getString("city"),
                        data.getString("region"),
                        data.getString("address"),
                        data.getString("eposta"),
                        data.getString("phone_number"),
                        data.getString("star"),
                        data.getString("feature"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  obj;
    }


    public static Hotel getFetch(String name){
        String query="SELECT * FROM hotel WHERE name=?";
        Hotel obj = null;
        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setString(1,name);
            ResultSet data=pt.executeQuery();
            if(data.next()){ // Veritabanında bu id varsa
                obj=new Hotel(data.getInt("id"),
                        data.getString("name"),
                        data.getString("city"),
                        data.getString("region"),
                        data.getString("address"),
                        data.getString("eposta"),
                        data.getString("phone_number"),
                        data.getString("star"),
                        data.getString("feature"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  obj;
    }














    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
