package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;

/**
 * Created by Zeshan on 15-12-2015.
 */
public class Dato_logik {

    public static String Getdatonavn(int dato){
        String datoen="";
        switch(dato){
            case 0:
                datoen="Januar";
                break;
            case 1:
                datoen="februar";
                break;
            case 2:
                datoen="marts";
                break;
            case 3:
                datoen="april";
                break;
            case 4:
                datoen="maj";
                break;
            case 5:
                datoen="juni";
                break;
            case 6:
                datoen="juli";
                break;
            case 7:
                datoen="august";
                break;
            case 8:
                datoen="september";
                break;
            case 9:
                datoen="oktober";
                break;
            case 10:
                datoen="november";
                break;
            case 11:
                datoen="december";
                break;
        }
        return datoen;
    }


}
