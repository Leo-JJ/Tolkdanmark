package com.tolkdanmarktolkapp.zeshan.tolkdanmark.Fragmenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.github.gcacace.signaturepad.views.SignaturePad;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.R;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Excelbilag_logik;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.Fragmentmanager;
import com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik.regionbilagobjekt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Jiahua on 27-09-2016.
 */
public class Tolkensunderskrift_fragment extends Fragment {

    private SignaturePad mSignaturePad;
    private ImageButton mClearButton;
    private ImageButton mSaveButton;
    private Excelbilag_logik excel = new Excelbilag_logik();
    private Uri contentUri;
    private regionbilagobjekt regionbilagindholdet;
    private Fragmentmanager fragments = new Fragmentmanager();
    public static boolean bilagsendt = false;
    private boolean emptyPad = false;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.tolkensunderskrift_view, container, false);

        mSignaturePad = (SignaturePad) rod.findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                Toast.makeText(getActivity(), "Tolken Underskriver", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                emptyPad = true;
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                emptyPad = false;
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton = (ImageButton) rod.findViewById(R.id.provigen);
        mSaveButton = (ImageButton) rod.findViewById(R.id.send);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
                Toast.makeText(getActivity(), "Underskriv igen i det grå felt", Toast.LENGTH_SHORT).show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emptyPad) {
                    Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                    if (addSignatureToGallery(signatureBitmap)) {
                        /*if (Tolkenskrift_fragment.bilagsendt) {
                            Toast.makeText(getActivity(), "Bilag Sendt, du får en bekræftelse snarest på mail", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Der skete en fejl, brug venligst et almindeligt bilag ", Toast.LENGTH_LONG).show();
                        }*/
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragments.getVelkommenfragment()).addToBackStack(fragments.getVelkommenfragment().getTag()).commit();
                } else {
                    Toast.makeText(getActivity(), "Ingen Underskrift! Underskriv i det grå felt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }

        if (getArguments() != null) {
            regionbilagindholdet = (regionbilagobjekt) getArguments().getSerializable("regionbilagindholdet");
        }
        return rod;
    }


    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs() && !file.isDirectory()) {
            Log.e("signature", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        stream.close();
    }

    public boolean addSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("Underskrift Tolkdanmark"), String.format("Underskrift_%d.png", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            contentUri = Uri.fromFile(photo);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);
            result = true;
            //excel.savetoexcel(photo,getContext(), regionbilagindholdet, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}