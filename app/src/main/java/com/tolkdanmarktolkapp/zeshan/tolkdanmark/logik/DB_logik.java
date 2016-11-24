package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Signatur_activity_demo;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter.Tolkensunderskrift_fragment;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

/**
 * Created by Zeshan on 05-01-2016.
 */
public class DB_logik {
    JsonLogik Logik = new JsonLogik();
    public static JSONArray BeskederJSONArray;
    public static JSONArray OpgaverJSONArray;
    public static JSONArray allOpgaverJSONArray;
    public static JSONArray GetOpenofferSONArray;
    public static JSONArray GettimeofferSONArray;

    public static boolean datahentet;
    public static boolean Opgavedatahentet;
    public static boolean kalenderopgaver;
    public static boolean GetOpenoffer;
    public static boolean Gettimeoffer;

    public ArrayList<String> Getdagensopagver(String UserID, Calendar Today) throws TimeoutException {
        ArrayList<String> Dagensopgaver = new ArrayList<String>();
        try
        {

            String responsejson=Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/Opgave.asmx/GetTodayOpgave?userid="+ UserID+"&startdate="+(Today.get(Calendar.DAY_OF_MONTH))+"-"+(Today.get(Calendar.MONTH) + 1)+"-"+Today.get(Calendar.YEAR)+"&enddate="+(Today.get(Calendar.DAY_OF_MONTH))+"-"+(Today.get(Calendar.MONTH) + 1)+"-"+Today.get(Calendar.YEAR));
            JSONArray response = new JSONArray(responsejson);
            OpgaverJSONArray = response;
            for (int i = 0; i < response.length(); i++) {
                Dagensopgaver.add(
                         "Institut: " + response.getJSONObject(i).getString("institute") + "\n"
                                + "Adresse: " + response.getJSONObject(i).getString("address") + "\n"
                );
            }
            Opgavedatahentet=true;
        }
        catch(Exception e)
        {
            throw new TimeoutException();
        }
        return Dagensopgaver;
    }


    public static void Generatetolkbilg(final String kilentnavn, final String cprnr,
                                        final String dato, final String antaltimer,
                                        final String tidtil, final String tolkebruger, final String tidfra,
                                        final String tolk, final String FrakturaOplysninger, final File ProfilePic,
                                        final String id, final String email, final String interpreter_email,
                                        final String sprog, final String reference_id, final String institution, final Activity activity
                                        ){
        class AsyncTask1 extends AsyncTask {
            @Override

            protected Object doInBackground(Object... arg0) {

                try {
                    String NAMESPACE = "http://tempuri.org/";
                    String URL = "http://www.tolkdanmark.dk/andriod/v1/GenerateAndSendTolkbilag.asmx";
                    String SOAP_ACTION = "http://tempuri.org/genaratebilag_new";
                    String METHOD_NAME = "genaratebilag_new";

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    //parameter
                    request.addProperty("id", id);
                    request.addProperty("reference_id", reference_id);
                    request.addProperty("kilentnavn", kilentnavn);
                    request.addProperty("cprnr", cprnr);
                    request.addProperty("sprog", sprog);
                    request.addProperty("dato", dato);
                    request.addProperty("tidfra", tidfra);
                    request.addProperty("tidtil", tidtil);
                    request.addProperty("antaltimer", antaltimer);
                    request.addProperty("tolkebruger", tolkebruger);
                    request.addProperty("tolk", tolk);
                    request.addProperty("institution",institution);
                    request.addProperty("FrakturaOplysninger", FrakturaOplysninger);

                    Bitmap bm = null;
                    bm = getScaledBitmap(ProfilePic.toString(), 343, 355);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    request.addProperty("ProfilePic", encodedImage);
                    request.addProperty("email", email);
                    request.addProperty("interpreter_email", interpreter_email);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                    Signatur_activity_demo.bilagsendt = true;
                    return resultsRequestSOAP.toString();

                }
                catch (XmlPullParserException x){
                    Signatur_activity_demo.bilagsendt = true;
                    return "noshouts ";
                }
                catch (Exception e) {
                    Signatur_activity_demo.bilagsendt = false;
                    e.printStackTrace();
                    return "noshouts ";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                if(Signatur_activity_demo.bilagsendt ) {
                    Toast.makeText(activity, "Bilag Sendt, du får en bekræftelse snarest på mail ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(activity, "Der skete en fejl, brug venligst et almindeligt bilag ", Toast.LENGTH_LONG).show();
                }
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    public static void GenerateLaegetolkbilg(final String id, final String reference_id, final String tolknavn, final String tolkcpr,
                                             final String klientnavn, final String klientcpr, final String adresse, final String postnr,
                                             final String by, final String forbindelsevalue, final String omfangevalue,
                                             final String dato, final String tidfra, final String tidtil, final String antaltimer,
                                             final String sprog, final String evaluering1, final String evaluering2,
                                             final String evaluering3, final String evaluering4, final String evalueringof,
                                             final String laegesydernummer, final String laegensunderskrift, final File tolksunderskrift,
                                             final String email, final String interpreter_email, final Activity activity
    ){
        class AsyncTask1 extends AsyncTask {
            @Override

            protected Object doInBackground(Object... arg0) {

                try {
                    String NAMESPACE = "http://tempuri.org/";
                    String URL = "http://www.tolkdanmark.dk/andriod/v1/GenerateAndSendTolkbilag.asmx";
                    String SOAP_ACTION = "http://tempuri.org/genaratebilag_new";
                    String METHOD_NAME = "genaratebilag_new";

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    //parameter
                    request.addProperty("id", id);
                    request.addProperty("reference_id", reference_id);
                    request.addProperty("tolknavn", tolknavn);
                    request.addProperty("tolkcpr", tolkcpr);
                    request.addProperty("klientnavn", klientnavn);
                    request.addProperty("klientcpr", klientcpr);
                    request.addProperty("adresse", adresse);
                    request.addProperty("postnr", postnr);
                    request.addProperty("by", by);
                    request.addProperty("forbindelsevalue", forbindelsevalue);
                    request.addProperty("omfangvalue", omfangevalue);
                    request.addProperty("dato", dato);
                    request.addProperty("tidfra", tidfra);
                    request.addProperty("tidtil", tidtil);
                    request.addProperty("antaltimer", antaltimer);
                    request.addProperty("sprog", sprog);
                    request.addProperty("evaluering1", evaluering1);
                    request.addProperty("evaluering2", evaluering2);
                    request.addProperty("evaluering3", evaluering3);
                    request.addProperty("evaluering4", evaluering4);
                    request.addProperty("evalueringof", evalueringof);
                    request.addProperty("laegensydernummer", laegesydernummer);
                    request.addProperty("laegenunderskrift", laegensunderskrift);

                    Bitmap bm = null;
                    bm = getScaledBitmap(tolksunderskrift.toString(), 343, 355);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    request.addProperty("tolksunderskfrift", encodedImage);
                    request.addProperty("email", email);
                    request.addProperty("interpreter_email", interpreter_email);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                    Tolkensunderskrift_fragment.bilagsendt = true;
                    return resultsRequestSOAP.toString();

                }
                catch (XmlPullParserException x){
                    Tolkensunderskrift_fragment.bilagsendt = true;
                    return "noshouts ";
                }
                catch (Exception e) {
                    Tolkensunderskrift_fragment.bilagsendt = false;
                    e.printStackTrace();
                    return "noshouts ";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                if(Tolkensunderskrift_fragment.bilagsendt ) {
                    Toast.makeText(activity, "Bilag Sendt, du får en bekræftelse snarest på mail ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(activity, "Der skete en fejl, brug venligst et almindeligt bilag ", Toast.LENGTH_LONG).show();
                }
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    public ArrayList<String> Getopgaver(String UserID, Calendar Today) throws TimeoutException {
        ArrayList<String> kalenderopgaver = new ArrayList<>();
        try
        {
            int start= Today.get(Calendar.YEAR)-2;
            int slut =Today.get(Calendar.YEAR)+2;
            String responsejson=Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/Opgave.asmx/GetTodayOpgave?userid="+ UserID+"&startdate="+Today.get(Calendar.DAY_OF_MONTH)+"-"+(Today.get(Calendar.MONTH) + 1)+"-"+start+"&enddate="+Today.get(Calendar.DAY_OF_MONTH)+"-"+(Today.get(Calendar.MONTH) + 1)+"-"+slut);
            JSONArray response = new JSONArray(responsejson);
            allOpgaverJSONArray = response;
            for (int i = 0; i < response.length(); i++) {
                kalenderopgaver.add(response.getJSONObject(i).getString("startdatetime"));
            }
            DB_logik.kalenderopgaver =true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new TimeoutException();
        }
        return kalenderopgaver;
    }

    public ArrayList<String> Getbeskeder(String UserID)
    {
        ArrayList<String> Beskeder = new ArrayList<>();
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/tolkbiskilla.asmx/GettolkmessagebyuserId?userid="+UserID);
        try
        {
            BeskederJSONArray = new JSONArray(responsejson);
            for (int i = 0; i < BeskederJSONArray.length(); i++) {
                System.out.println(UserID);
                Beskeder.add(BeskederJSONArray.getJSONObject(i).getString("subject") + "\n"
                        + BeskederJSONArray.getJSONObject(i).getString("body").substring(0, 3) + "....");
            }
            datahentet=true;
        }
        catch(Exception e)
        {
            System.out.println("Fejl getbeskeder1");
        }
        return Beskeder;
    }

    public void Setbeskedlaest(String Beskedid) {
        Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/tolkbiskilla.asmx/GettolkmessagebyuserId?userid=" + Beskedid);
    }


    public void setKalenderopgaver(final String starttime,final String endtime,final String startdate, final String enddate,final String interprator_id,final String desc,final String postcode,final String status) {
        class AsyncTask1 extends AsyncTask {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/insertCalender.asmx/dbInsertEvent?starttime=" + starttime + "&endtime=" + endtime + "&startdate=" + startdate + "&enddate=" + enddate + "&interprator_id=" + interprator_id + "&desc=" + desc + "&postcode=" + postcode + "&status=" + status);
                    Getopgaver(interprator_id, Calendar.getInstance(Locale.getDefault()));
                    return "";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "kalender blev ikke opdateret korrekt";
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
            }
        }
        AsyncTask1 as = new AsyncTask1();
        as.execute();
    }

    public  ArrayList<String> GetOpenoffer(String UserID)
    {
        ArrayList<String> Offers = new ArrayList<>();
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/TolkOffers.asmx/FillOpenofferGridview?interpretorid="+UserID);
        try
        {
            GetOpenofferSONArray = new JSONArray(responsejson);
            for (int i = 0; i < GetOpenofferSONArray.length(); i++) {
                Offers.add(GetOpenofferSONArray.getJSONObject(i).getString("starttime"));
            }
            GetOpenoffer=true;
        }
        catch(Exception e)
        {
            System.out.println("Fejl getbeskeder1");
        }
        return Offers;
    }

    public  ArrayList<String> Gettimeoffer(String UserID)
    {
        ArrayList<String> TimedOffers = new ArrayList<>();
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/TolkOffers.asmx/FilltimeofferGridview?interpretorid="+UserID);
        try
        {
            GettimeofferSONArray = new JSONArray(responsejson);
            for (int i = 0; i < GettimeofferSONArray.length(); i++) {
                TimedOffers.add(GettimeofferSONArray.getJSONObject(i).getString("starttime"));
            }
            Gettimeoffer=true;
        }
        catch(Exception e)
        {
            System.out.println("Fejl getbeskeder1");
        }
        return TimedOffers;
    }

    public String setacceptofferingroup(String offerid, String offertype,String UserID,String packet_id)
    {
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/TolkOffers.asmx/acceptoffers2?openortimed="+offertype+"&order_tilbud_24_id="+offerid+"&interpreter_id="+UserID+"&packet_id="+packet_id);
        try
        {
            return responsejson.replace("[", "").replace("]","");
        }
        catch(Exception e)
        {
            System.out.println("Fejl accepteroffer");
        }
        return " ";
    }

    public String setacceptoffer(String offerid, String offertype)
    {
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/TolkOffers.asmx/acceptoffers1?openortimed="+offertype+"&order_tilbud_24_id="+offerid);
        try
        {
            return responsejson.replace("[", "").replace("]","");
        }
        catch(Exception e)
        {
            System.out.println("Fejl accepteroffer");
        }
        return " ";
    }
    public boolean setrejectedoffer(String offerid, String offertype)
    {
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/TolkOffers.asmx/openofferrejected?openortimed="+offertype+"&order_tilbud_24_id="+offerid);
        try
        {
            return responsejson.equals("[" + "\"" + "rejected" + "\"" + "]");
        }
        catch(Exception e)
        {
            System.out.println("Fejl accepteroffer");
        }
        return false;
    }

    public  JSONArray Getpofiledata(String UserID)
    {
        JSONArray response=null;
        String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/tolkedit.asmx/Gettolkbyid?id="+UserID);
        try
        {
            response = new JSONArray(responsejson);
        }
        catch(Exception e)
        {
            System.out.println("Fejl getbeskeder1");
        }
        return response;
    }

    public boolean validateadress(String adress)
    {
        try
        {
            String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/tolkedit.asmx/Verifyadd?address="+adress.replace(" ","%20" ));
            if(responsejson.equals("\""+"ok"+"\"")){
                return true;
            }
        }
        catch(Exception e)
        {
            return false;
        }
        return false;
    }
    public boolean gemprofildata(String name,String cprnr, String adresse, String etage,String  postnr,String  byen, String telefonrnr, String telefonr2,String  email, String regnr,String  kontonr,String  banknavn,String  kørekortnr, String registreringsnr,String kommentar,boolean harkort, boolean harbil, String userid)
    {
        try
        {
            String responsejson = Logik.getJSON("http://www.tolkdanmark.dk/andriod/v1/tolkedit.asmx/updatetolk?car_regnr=" + registreringsnr.replace(" ", "%20") + "&has_car=" + harbil +
                    "&has_driving_license=" + harkort + "&driving_license_nr=" + kørekortnr.replace(" ", "%20") +"&id=" + userid.replace(" ", "%20") + "&name=" + name.replace(" ", "%20") + "&cpr=" + cprnr.replace(" ", "%20") + "&address=" + adresse.replace(" ", "%20") + "&etage=" + etage.replace(" ", "%20") + "&city=" + byen.replace(" ", "%20") + "&postalCode=" + postnr.replace(" ", "%20") +
                    "&telephone=" + telefonrnr.replace(" ", "%20") + "&mobile=" + telefonr2.replace(" ", "%20") + "&email=" + email.replace(" ", "%20") + "&regnr=" + regnr.replace(" ", "%20") + "&description=" + kommentar.replace(" ", "%20") + "&bank_no=" + banknavn.replace(" ", "%20") + "&last_saved_user=" + userid.replace(" ", "%20") + "&kontonr=" + kontonr.replace(" ", "%20"));
            if(responsejson.equals("\""+"ok"+"\"")){
                return true;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Fejl validate adresse");
        }
        return false;
    }
    public static Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
