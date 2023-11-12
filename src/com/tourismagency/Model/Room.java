package com.tourismagency.Model;


import com.tourismagency.Helper.DBConnector;
import com.tourismagency.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Room {
    private int id;
    private int hotel_id;
    private int lodgings_id;
    private int season_id;
    private String room_type;
    private int stock;
    private int bed_number;
    private int sqr_meter;
    private String other_features;
    private double adult_price;
    private double child_price;
    private Hotel hotel;
    private Lodgings lodgings;
    private Season season;

    public Room() {
    }

    public Room(int id, int hotel_id, int lodgings_id, int season_id, String room_type, int stock, int bed_number, int sqr_meter, String other_features, double adult_price, double child_price) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.lodgings_id = lodgings_id;
        this.season_id = season_id;
        this.room_type = room_type;
        this.stock = stock;
        this.bed_number = bed_number;
        this.sqr_meter = sqr_meter;
        this.other_features = other_features;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.hotel = Hotel.getFetch(hotel_id); // otelin idsine göre nesne döner.
        this.lodgings = Lodgings.getFetch(lodgings_id);
        this.season = Season.getFetch(season_id);

    }

    public static ArrayList<Room> getList() {
        String query = "SELECT * FROM room";
        ArrayList<Room> roomList = new ArrayList<>();
        Room obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet data = st.executeQuery(query);
            while (data.next()) {
                obj = new Room(
                        data.getInt("id"),
                        data.getInt("hotel_id"),
                        data.getInt("lodgings_id"),
                        data.getInt("season_id"),
                        data.getString("room_type"),
                        data.getInt("stock"),
                        data.getInt("bed_number"),
                        data.getInt("sqr_meter"),
                        data.getString("other_features"),
                        data.getDouble("adult_price"),
                        data.getDouble("child_price")
                );
                roomList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }




    public static boolean add(int hotel_id, int lodgings_id, int season_id, String room_type, int stock, int bed_number, int sqr_meter, String other_features, double adult_price, double child_price) {
        String query = "INSERT INTO room (hotel_id, lodgings_id, season_id, room_type, stock, bed_number, sqr_meter, other_features, adult_price, child_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, hotel_id);
            ps.setInt(2, lodgings_id);
            ps.setInt(3, season_id);
            ps.setString(4, room_type);
            ps.setInt(5, stock);
            ps.setInt(6, bed_number);
            ps.setInt(7, sqr_meter);
            ps.setString(8, other_features);
            ps.setDouble(9, adult_price);
            ps.setDouble(10, child_price);
            int response = ps.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM room WHERE id=?";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, id);
            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Room getFetch(int id){
        Room obj=null;
        String query="SELECT * FROM room WHERE id=? ";

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet data= ps.executeQuery();
            if (data.next()){//Eğer veritabanında bu hotelid lodgingsid ve rooptype varsa
                obj=new Room(); // yeni nesne oluşturduk  veritabanındaki bilgileri bu nesneye aktarıyoruz.
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setLodgings_id(data.getInt("lodgings_id"));
                obj.setSeason_id(data.getInt("season_id"));
                obj.setRoom_type(data.getString("room_type"));
                obj.setStock(data.getInt("stock"));
                obj.setBed_number(data.getInt("bed_number"));
                obj.setSqr_meter(data.getInt("sqr_meter"));
                obj.setOther_features(data.getString("other_features"));
                obj.setAdult_price(data.getDouble("adult_price"));
                obj.setChild_price(data.getDouble("child_price"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
    public static double getFetchForChildPrice(int id){
        String query="SELECT child_price FROM room WHERE id=?";
        Double c_price=0.0;

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);

            ResultSet data= ps.executeQuery();
            if (data.next()){
                c_price=data.getDouble("child_price");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c_price;
    }
    public static double getFetchForAdultPrice(int id){
        String query="SELECT adult_price FROM room WHERE id=?";
        Double a_price=0.0;

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);

            ResultSet data= ps.executeQuery();
            if (data.next()){
                a_price=data.getDouble("adult_price");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a_price;
    }
    public static Room getFetchByRoomId(int id){
        Room obj=null;
        String query="SELECT * FROM room WHERE id=?";

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            // ps.setInt(1,hotel_id);
            //ps.setInt(2,lodgings_id);
            ps.setInt(1,id);
            ResultSet data= ps.executeQuery();
            if (data.next()){//Eğer veritabanında bu hotelid lodgingsid ve rooptype varsa
                obj=new Room(); // yeni nesne oluşturduk  veritabanındaki bilgileri bu nesneye aktarıyoruz.
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setLodgings_id(data.getInt("lodgings_id"));
                obj.setSeason_id(data.getInt("season_id"));
                obj.setRoom_type(data.getString("room_type"));
                obj.setStock(data.getInt("stock"));
                obj.setBed_number(data.getInt("bed_number"));
                obj.setSqr_meter(data.getInt("sqr_meter"));
                obj.setOther_features(data.getString("other_features"));
                obj.setAdult_price(data.getDouble("adult_price"));
                obj.setChild_price(data.getDouble("child_price"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
    public static boolean update( int id,String room_type, int stock, int bed_number, int sqr_meter, String other_features, double adult_price, double child_price) {

        String query = "UPDATE room SET  room_type=?, stock=?, bed_number=?, sqr_meter=?, other_features=?, adult_price=?, child_price=? WHERE id=? ";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,room_type);
            ps.setInt(2, stock);
            ps.setInt(3, bed_number);
            ps.setInt(4, sqr_meter);
            ps.setString(5,other_features);
            ps.setDouble(6,adult_price);
            ps.setDouble(7,child_price);
            ps.setInt(8,id);


            int response = ps.executeUpdate();
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean updateStockReduce(int stock,int id){
        String query="UPDATE room SET stock=?  WHERE id=? AND stock>0";
        //String query="UPDATE room SET stock=? FROM room as r INNER JOIN book as b ON r.id=b.room_id";
        //query+=" WHERE r.id=? ";
        stock-=1;

        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setInt(1,stock);
            pt.setInt(2,id);
            int response=pt.executeUpdate();
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean updateStockPlus(int id,int stock){
        String query="UPDATE room SET stock=?  WHERE id=?";
        //String query="UPDATE room SET stock=? FROM room as r INNER JOIN book as b ON r.id=b.room_id";
        //query+=" WHERE r.id=? ";
        stock+=1;

        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setInt(1,stock);
            pt.setInt(2,id);
            int response=pt.executeUpdate();
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean update(int id){
        String query="UPDATE room SET stock=?  WHERE id=?";
        //String query="UPDATE room SET stock=? FROM room as r INNER JOIN book as b ON r.id=b.room_id";
        //query+=" WHERE r.id=? ";


        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setInt(2,id);
            int response=pt.executeUpdate();
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static ArrayList <Room>  searchRoomByOnlyRegion( String region){
        String query="SELECT * FROM room  as r LEFT JOIN hotel as h ON r.hotel_id=h.id";
        query+=" WHERE h.region LIKE '%{{h.region}}%' AND r.stock>0";
        query=query.replace("{{h.region}}",region);

        ArrayList < Room > hotelRoomList=new ArrayList<>();
        Room obj;
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ResultSet data=ps.executeQuery();
            while (data.next()) {
                obj = new Room(
                        data.getInt("id"),
                        data.getInt("hotel_id"),
                        data.getInt("lodgings_id"),
                        data.getInt("season_id"),
                        data.getString("room_type"),
                        data.getInt("stock"),
                        data.getInt("bed_number"),
                        data.getInt("sqr_meter"),
                        data.getString("other_features"),
                        data.getDouble("adult_price"),
                        data.getDouble("child_price")
                );
                hotelRoomList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("İsme göre listele");
        return hotelRoomList;
    }

    public static ArrayList <Room>  searchRoomByOnlyName( String name){
        String query="SELECT * FROM room  as r LEFT JOIN hotel as h ON r.hotel_id=h.id";
        query+=" WHERE name LIKE '%{{name}}%' AND r.stock>0";
        query=query.replace("{{name}}",name);

        ArrayList < Room > hotelRoomList=new ArrayList<>();
        Room obj;
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ResultSet data=ps.executeQuery();
            while (data.next()) {
                obj = new Room(
                        data.getInt("id"),
                        data.getInt("hotel_id"),
                        data.getInt("lodgings_id"),
                        data.getInt("season_id"),
                        data.getString("room_type"),
                        data.getInt("stock"),
                        data.getInt("bed_number"),
                        data.getInt("sqr_meter"),
                        data.getString("other_features"),
                        data.getDouble("adult_price"),
                        data.getDouble("child_price")
                );
                hotelRoomList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelRoomList;
    }
    public static ArrayList <Room>  searchRoomByNameRegion( String name, String region){
        String query="SELECT * FROM room  as r INNER JOIN hotel as h ON r.hotel_id=h.id";
        query+=" WHERE name LIKE '%{{name}}%' AND region LIKE '%{{region}}%' AND r.stock>0";
        query=query.replace("{{name}}",name);
        query=query.replace("{{region}}",region);
        ArrayList < Room > roomSearchList=new ArrayList<>();
        Room obj;

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ResultSet data=ps.executeQuery();
            while (data.next()) {
                obj = new Room(
                        data.getInt("id"),
                        data.getInt("hotel_id"),
                        data.getInt("lodgings_id"),
                        data.getInt("season_id"),
                        data.getString("room_type"),
                        data.getInt("stock"),
                        data.getInt("bed_number"),
                        data.getInt("sqr_meter"),
                        data.getString("other_features"),
                        data.getDouble("adult_price"),
                        data.getDouble("child_price")
                );
                roomSearchList.add(obj);
             }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
        return roomSearchList;

    }


    public static ArrayList <Room> getListHotelRoomSeason(){
        String query="SELECT * FROM room  as r INNER JOIN hotel as h ON r.hotel_id=h.id INNER JOIN season as s ON r.season_id=s.id";
        query+=" WHERE r.stock>0";
        ArrayList < Room > roomSearchList=new ArrayList<>();
        Room obj;

        try {
           Statement st=DBConnector.getInstance().createStatement();

            ResultSet data=st.executeQuery(query);
            while (data.next()) {
                obj = new Room(
                        data.getInt("id"),
                        data.getInt("hotel_id"),
                        data.getInt("lodgings_id"),
                        data.getInt("season_id"),
                        data.getString("room_type"),
                        data.getInt("stock"),
                        data.getInt("bed_number"),
                        data.getInt("sqr_meter"),
                        data.getString("other_features"),
                        data.getDouble("adult_price"),
                        data.getDouble("child_price")
                );
                roomSearchList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomSearchList;

    }

    public static ArrayList <Room>  searchRoomByOnlyDate( String check_in, String check_out) {

        //Girilen tarihler date formatına çevrildi.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fldStartDate;
        Date fldEndDate;

        try {
            fldStartDate = formatter.parse(check_in);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            fldEndDate = formatter.parse(check_out);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //Hotel,room ve season tabloları birleştirildi.
        String query="SELECT * FROM room  as r INNER JOIN hotel as h ON r.hotel_id=h.id INNER JOIN season as s ON r.season_id=s.id";
        query+=" WHERE r.stock>0";

        ArrayList <Room> roomByDateList=new ArrayList<>();
        Room obj;

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ResultSet data=ps.executeQuery();
            while (data.next()) {


                Date dBaseStartDate;
                Date dBaseEndDate;
                try {
                    dBaseStartDate = formatter.parse(data.getString("start_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dBaseEndDate = formatter.parse(data.getString("end_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (fldStartDate.after(dBaseStartDate) && fldEndDate.before(dBaseEndDate)) {
                    obj = new Room(
                            data.getInt("id"),
                            data.getInt("hotel_id"),
                            data.getInt("lodgings_id"),
                            data.getInt("season_id"),
                            data.getString("room_type"),
                            data.getInt("stock"),
                            data.getInt("bed_number"),
                            data.getInt("sqr_meter"),
                            data.getString("other_features"),
                            data.getDouble("adult_price"),
                            data.getDouble("child_price")
                    );
                    roomByDateList.add(obj);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  roomByDateList;

    }





    public static ArrayList <Room>  searchRoomByAll( String check_in, String check_out,String name,String region) {

        //Girilen tarihler date formatına çevrildi.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fldStartDate;
        Date fldEndDate;

        try {
            fldStartDate = formatter.parse(check_in);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            fldEndDate = formatter.parse(check_out);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        ArrayList<Room> roomByDateList=new ArrayList<>();
        Room obj;
        String query="SELECT * FROM room  as r INNER JOIN hotel as h ON r.hotel_id=h.id INNER JOIN season as s ON r.season_id=s.id";
       try{
        if(name.equals("")) {
            query += " WHERE region LIKE '%{{region}}%'";
            query+=" AND r.stock>0";
            query=query.replace("{{region}}",region);

            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ResultSet data = ps.executeQuery();
            while (data.next()) {


                Date dBaseStartDate;
                Date dBaseEndDate;
                try {
                    dBaseStartDate = formatter.parse(data.getString("start_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dBaseEndDate = formatter.parse(data.getString("end_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (fldStartDate.after(dBaseStartDate) && fldEndDate.before(dBaseEndDate)) {
                    obj = new Room(
                            data.getInt("id"),
                            data.getInt("hotel_id"),
                            data.getInt("lodgings_id"),
                            data.getInt("season_id"),
                            data.getString("room_type"),
                            data.getInt("stock"),
                            data.getInt("bed_number"),
                            data.getInt("sqr_meter"),
                            data.getString("other_features"),
                            data.getDouble("adult_price"),
                            data.getDouble("child_price")
                    );
                    roomByDateList.add(obj);
                }

            }
        } else if(region.equals("")){
            query += " WHERE name LIKE '%{{name}}%'";
            query+=" AND r.stock>0";
            query=query.replace("{{name}}",name);


            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ResultSet data = ps.executeQuery();
            while (data.next()) {


                Date dBaseStartDate;
                Date dBaseEndDate;
                try {
                    dBaseStartDate = formatter.parse(data.getString("start_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dBaseEndDate = formatter.parse(data.getString("end_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (fldStartDate.after(dBaseStartDate) && fldEndDate.before(dBaseEndDate)) {
                    obj = new Room(
                            data.getInt("id"),
                            data.getInt("hotel_id"),
                            data.getInt("lodgings_id"),
                            data.getInt("season_id"),
                            data.getString("room_type"),
                            data.getInt("stock"),
                            data.getInt("bed_number"),
                            data.getInt("sqr_meter"),
                            data.getString("other_features"),
                            data.getDouble("adult_price"),
                            data.getDouble("child_price")
                    );
                    roomByDateList.add(obj);
                }

            }
        }else{
            query += " WHERE region LIKE '%{{region}}%' AND  name LIKE '%{{name}}%'";
            query+=" AND r.stock>0";
            query=query.replace("{{region}}",region);
            query=query.replace("{{name}}",name);


            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ResultSet data = ps.executeQuery();
            while (data.next()) {


                Date dBaseStartDate;
                Date dBaseEndDate;
                try {
                    dBaseStartDate = formatter.parse(data.getString("start_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dBaseEndDate = formatter.parse(data.getString("end_date"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (fldStartDate.after(dBaseStartDate) && fldEndDate.before(dBaseEndDate)) {
                    obj = new Room(
                            data.getInt("id"),
                            data.getInt("hotel_id"),
                            data.getInt("lodgings_id"),
                            data.getInt("season_id"),
                            data.getString("room_type"),
                            data.getInt("stock"),
                            data.getInt("bed_number"),
                            data.getInt("sqr_meter"),
                            data.getString("other_features"),
                            data.getDouble("adult_price"),
                            data.getDouble("child_price")
                    );
                    roomByDateList.add(obj);
                }

            }
        }

       }catch(SQLException e){

            }

        return  roomByDateList;

    }






    public static  ArrayList<Room> getRoomSearchList(String name, String check_in, String check_out) throws ParseException {
        ArrayList<Room> roomSearchList=new ArrayList<>();

        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        Date fldStartDate;
        Date fldEndDate;

        try {
            fldStartDate=formatter.parse(check_in);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            fldEndDate=formatter.parse(check_out);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (Hotel obj: Hotel.getByNameList(name)){
            for (Season season : Season.getFetchByID(obj.getId())) {


                // System.out.println(obj.getHotel().getId());
                // System.out.println(obj.hotel_id);
                //System.out.println(obj.getHotel().getName().equals(name));

//name göre search eden oda listesi
                if (obj.getName().equals(name)) {

                    Date dBaseStartDate;
                    Date dBaseEndDate;
                    try {
                        dBaseStartDate = formatter.parse(season.getStart_date());
                        System.out.println(dBaseStartDate + " start date format");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        dBaseEndDate = formatter.parse(season.getEnd_date());
                        System.out.println(dBaseEndDate + " end date format");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    if (fldStartDate.after(dBaseStartDate) && fldEndDate.before(dBaseEndDate)) {
                        ArrayList <Room> objr = Room.getListByRoomID(obj.getId());
                        for (Room room : objr) {
                            room.setHotel(obj);
                            room.setSeason(season);
                            if (fldStartDate.after(formatter.parse(room.getSeason().getStart_date())) && fldEndDate.before(formatter.parse(room.getSeason().getEnd_date()))){
                            roomSearchList.add(room);
                            }
                        }


                    }

                }
            }
        }

        return roomSearchList;

    }



    public static ArrayList<Room> getListByRoomID(int hotel_id){
        Room obj=null;
        ArrayList <Room> roombyIdList=new ArrayList<>();
        String query="SELECT * FROM room WHERE hotel_id=?";

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,hotel_id);
            ResultSet data= ps.executeQuery();
            while (data.next()){
                obj=new Room();
                obj.setId(data.getInt("id"));
                obj.setHotel_id(data.getInt("hotel_id"));
                obj.setLodgings_id(data.getInt("lodgings_id"));
                obj.setSeason_id(data.getInt("season_id"));
                obj.setRoom_type(data.getString("room_type"));
                obj.setStock(data.getInt("stock"));
                obj.setBed_number(data.getInt("bed_number"));
                obj.setSqr_meter(data.getInt("sqr_meter"));
                obj.setOther_features(data.getString("other_features"));
                obj.setAdult_price(data.getDouble("adult_price"));
                obj.setChild_price(data.getDouble("child_price"));
                roombyIdList.add(obj);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return roombyIdList;
    }


    public static int getStockNumber(int id){
        String query = "SELECT stock FROM room WHERE id=?";
        int stock = 0;
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet data=ps.executeQuery();
            if(data.next()){
                stock=data.getInt("stock");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  stock;
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

    public int getLodgings_id() {
        return lodgings_id;
    }

    public void setLodgings_id(int lodgings_id) {
        this.lodgings_id = lodgings_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBed_number() {
        return bed_number;
    }

    public void setBed_number(int bed_number) {
        this.bed_number = bed_number;
    }

    public int getSqr_meter() {
        return sqr_meter;
    }

    public void setSqr_meter(int sqr_meter) {
        this.sqr_meter = sqr_meter;
    }

    public String getOther_features() {
        return other_features;
    }

    public void setOther_features(String other_features) {
        this.other_features = other_features;
    }

    public double getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(double adult_price) {
        this.adult_price = adult_price;
    }

    public double getChild_price() {
        return child_price;
    }

    public void setChild_price(double child_price) {
        this.child_price = child_price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Lodgings getLodgings() {
        return lodgings;
    }

    public void setLodgings(Lodgings lodgings) {
        this.lodgings = lodgings;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
