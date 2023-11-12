package com.tourismagency.Model;

import com.tourismagency.Helper.DBConnector;
import com.tourismagency.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private  String name;
    private String uname;
    private String pass;
    private String type;

    public User(){

    }

    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
    }
    //Kullanıcı girişi yaparken kişinin bilgilerini getiren metot
    public static User getCredentials(String uname,String pass){
        User obj=null;
        String query="SELECT * FROM user WHERE user_uname=? AND user_pass=?";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,uname);
            ps.setString(2,pass);
            ResultSet data=ps.executeQuery();
            if(data.next()){
                switch (data.getString("user_type")){
                    case "admin":
                        obj=new Admin();
                        break;
                    case "employee":
                        obj=new Employee();
                        break;
                    default:
                        obj=new User();
                }
                // yukarıdaki eşleşme sonucunda obj nesnesini dolduruyoruz.
                obj.setId(data.getInt("id"));
                obj.setName(data.getString("user_name"));
                obj.setUname(data.getString("user_uname"));
                obj.setPass(data.getString("user_pass"));
                obj.setType(data.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static ArrayList<User> getList(){
        ArrayList <User> userList=new ArrayList<>();
        String query="SELECT * FROM user";
        try {
            ResultSet rs=DBConnector.getInstance().prepareStatement(query).executeQuery();
            while(rs.next()){
                User obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("user_name"));
                obj.setUname(rs.getString("user_uname"));
                obj.setPass(rs.getString("user_pass"));
                obj.setType(rs.getString("user_type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    public static boolean add(String name, String userName,String password, String userType){
        String query="INSERT INTO user (user_name, user_uname, user_pass, user_type) VALUES (?,?,?,?)";

        if (getFetch(userName)!=null){
            Helper.showMsg("Bu kullanıcı ismi başkası tarafından alınmıştır.Lütfen başka bir kullanıcı adı giriniz.");
            return false;
        }


        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setString(1,name);
            pt.setString(2,userName);
            pt.setString(3,password);
            pt.setString(4,userType);
            int response= pt.executeUpdate();

            if (response==-1){
                Helper.showMsg("error");

            }
            return  response!=-1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

    }

    public static boolean delete(int id){
        String query="DELETE FROM user WHERE id=?";
        try {
            PreparedStatement pt= DBConnector.getInstance().prepareStatement(query);
            pt.setInt(1,id);


            int response=pt.executeUpdate();
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id,String name,String uname,String password,String type){

        // Tablodaki kullanıcı adlarında bir değişiklik olduğunda aynı kullanıcı adı kullanılmışsa güncelleme yapmaz.
        User findUser=User.getFetch(uname);
        if (findUser!=null && findUser.getId()!=id){
            Helper.showMsg("Bu kullanıcı ismi başkası tarafından alınmıştır.Lütfen başka bir kullanıcı adı giriniz.");
            return false;
        }


        try {
            ArrayList<String> uType=new ArrayList<>();
            uType.add("admin");
            uType.add("employee");

            if (!type.equals(uType.get(0)) && !type.equals(uType.get(1))) {
                Helper.showMsg("error");
                return false;
            }

        }catch (Exception e){

        }

        // idye göre güncelleme işleminin yapıldığı alan.
        String query="UPDATE user SET user_name=?, user_uname=?, user_pass=?, user_type=? WHERE id=? ";
        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setString(1,name);
            pt.setString(2,uname);
            pt.setString(3,password);
            pt.setString(4,type);
            pt.setInt(5,id);
            int response=pt.executeUpdate();
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    //FİLTRELEME uygulanan verileri form üzerinde gösterme işlemi.
    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList=new ArrayList<>();
        User obj;
        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            ResultSet data=pt.executeQuery();
            while (data.next()){
                obj=new User();
                obj.setId(data.getInt("id"));
                obj.setName(data.getString("user_name"));
                obj.setUname(data.getString("user_uname"));
                obj.setPass(data.getString("user_pass"));
                obj.setType(data.getString("user_type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }

    // FİLTRELEME işlemi
    public static String searchQuery(String name,String userName,String userType){
        String query="SELECT * FROM user WHERE user_name LIKE '%{{user_name}}%' AND user_uname LIKE '%{{user_uname}}%'"; // %{{a}}% : a ile başlayan ve biten tüm verileri bulur.
        query=query.replace("{{user_name}}",name); // Sorgudaki {{}} içindeki ifadeyi name ile değiştir.
        query=query.replace("{{user_uname}}",userName);
        if (!userType.isEmpty()){ // userType veritabanında enum olduğu için null durumdayken sorguda sorun çıkartabiliyor. Bu yüzden boş olmadığı durumu dikkate alarak sonradan ekledik.
            query+=" AND user_type LIKE '{{user_type}}'";
            query=query.replace("{{user_type}}",userType);
        }
        return query;
    }

    public static User getFetch(String uname){
        User obj=null;
        String query="SELECT * FROM user WHERE user_uname=?";

        try {
            PreparedStatement pt=DBConnector.getInstance().prepareStatement(query);
            pt.setString(1,uname);
            ResultSet data= pt.executeQuery();
            if (data.next()){
                obj=new User();
                obj.setId(data.getInt("id"));
                obj.setName(data.getString("user_name"));
                obj.setUname(data.getString("user_uname"));
                obj.setPass(data.getString("user_pass"));
                obj.setType(data.getString("user_type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
