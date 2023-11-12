package com.tourismagency.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static  void setLayout(){
        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())) {// Nimbus yüklü temalarda varsa
                try {
                    UIManager.setLookAndFeel(info.getClassName()); // Tema nimbus olarak ayarlandı
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }
    public static int screenCenterPoint(String coordinate, Dimension size){
        int point;
        switch (coordinate){
            case "x":
                point=(Toolkit.getDefaultToolkit().getScreenSize().width- size.width)/2;
                break;
            case "y":
                point=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point=0;

        }
        return point;
    }
    public static boolean isFieldEmpty(JTextField field) { // Jtextfield boş mu konrol eden metot
        return field.getText().trim().isEmpty();//sağındaki solundaki boşlukları silerek boş mu diye bakıyor. Boşsa true, değilse false döner
    }


    //Değerlendirme Formu 19,20
    public static void showMsg(String str){
        String msg, title;
        switch(str){
            case "fill":
                msg="Lütfen tüm alanları doldurunuz!";
                title="HATA";
                break;
            case "done":
                msg="İşlem Başarılı";
                title="SONUÇ";
                break;
            case "error":
                msg="Bir hata oluştu!";
                title="HATA!";
                break;
            default:
                msg=str;
                title="MESAJ";
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean confirm(String str){ // Silme işlemlerinde emin misin sorusunu soran uyarı penceresi işlemi.
        optionPageTR();
        String msg;
        switch (str){
            case "sure":
                msg="Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
                break;
            default:
                msg=str;
        }
        // 0 a eşitse true dönecek.
        return JOptionPane.showConfirmDialog(null,msg,"Dikkat",JOptionPane.YES_NO_OPTION)==0;
    }

    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam"); // Uyarı kutusundaki ok ifadesi türkçeye çevrildi.
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }
}
