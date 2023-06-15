package com.example.appforhotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    public static final String HOTEL_KEY_NAME = "name";
    public static final String HOTEL_KEY_PRICE = "price";
    public static final String HOTEL_KEY_ADDRESS = "addr";
    public static final String HOTEL_KEY_IMAGE = "img";
    final String[] ids = {""};
    private EditText name, addr, img, price, idVal;

    private Button add_hotel_btn, delete_hotel_btn, update_hotel_btn;
    private ArrayList<Hotel> hotelsList = new ArrayList<Hotel>();
    private HotelAdapter hotelAdapt;
    private ListView hotelsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        addr = findViewById(R.id.addr);
        img = findViewById(R.id.img);
        price = findViewById(R.id.price);
        idVal = findViewById(R.id.idVal);

        add_hotel_btn = findViewById(R.id.add_hotel_btn);
        delete_hotel_btn = findViewById(R.id.delete_hotel_btn);
        update_hotel_btn = findViewById((R.id.update_hotel_btn));

        add_hotel_btn.setOnClickListener(this);
        delete_hotel_btn.setOnClickListener(this);
        update_hotel_btn.setOnClickListener(this);
        /*
        hotelsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), HotelMainPage.class);
                intent.putExtra("img", "");
                intent.putExtra("name", "na");
                intent.putExtra("addr", "a");
                intent.putExtra("price", "2000.00");
                startActivity(intent);
            }
        });*/
        getAllHotels();
    }

    @Override
    public void onClick(View view) {
        int currId = view.getId();
        switch (currId) {
            case R.id.add_hotel_btn:
                addNewHotel();
                break;
            case R.id.delete_hotel_btn:
                deleteProductById(idVal.getText().toString());
                break;
            case R.id.update_hotel_btn:
                updateProductNameById(idVal.getText().toString());
                break;
        }
    }

    private void addNewHotel() {
        String hName = name.getText().toString().trim();
        String hAddr = addr.getText().toString().trim();
        String hImg = img.getText().toString().trim();
        String hPrice = price.getText().toString().trim();


        if (!hName.isEmpty() && !hAddr.isEmpty() && !hImg.isEmpty() && !hPrice.isEmpty()) {
            Map<String, Object> data = new HashMap<>();
            data.put(HOTEL_KEY_NAME, hName);
            data.put(HOTEL_KEY_ADDRESS, hAddr);
            data.put(HOTEL_KEY_PRICE, hPrice);
            data.put(HOTEL_KEY_IMAGE, hImg);

            database
                    .collection("hotels")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Hotel post created", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                        }
                    });

            // clear input from edit text of hotel parameters:
            name.setText("");
            addr.setText("");
            price.setText("");
            img.setText("");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error: name, address, price and image url are required", Toast.LENGTH_LONG).show();
        }

        getAllHotels();
    }

    private void deleteProductById(String hId) {
        if (!hId.isEmpty()) {
            database.collection("hotels").document(hId)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getApplicationContext(), "Hotel was deleted", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                                                }
                                            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error: id is required", Toast.LENGTH_LONG).show();
        }
        getAllHotels();
    }

    private void updateProductNameById(String pid) {
        if (!pid.isEmpty()) {
            DocumentReference document = database.collection("hotels").document(pid);
            document
                    .update(
                            "name", name.getText().toString()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Hotel's Name updated", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error: id is required", Toast.LENGTH_LONG).show();
        }
        getAllHotels();
    }
    private void getAllHotels()
    {
        database
                .collection("hotels")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            hotelsList = new ArrayList<Hotel>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Hotel hObj = document.toObject(Hotel.class);
                                hotelsList.add(hObj);
                                idVal.setText(document.getId());
                            }

                            hotelsLV = findViewById(R.id.hotelsLV);
                            hotelAdapt = new HotelAdapter(hotelsList, getApplicationContext());
                            hotelsLV.setAdapter(hotelAdapt);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}