package com.tourismagency.Helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    //Değerlendirme Formu 6
    private Connection connect=null;
    public Connection connectDB(){
        try {
            this.connect=DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connect;
    }
    // Veritabanına farklı yerlerde bağlanmak istediğimizde sürekli nesne oluşturma işlemleri
    // yapmamak için metot içinde bir kez yapıp diğer yerlerde metodu çağıma işlemi yapılacak.
    public static Connection getInstance(){
        DBConnector db=new DBConnector();
        return db.connectDB();
    }
}
