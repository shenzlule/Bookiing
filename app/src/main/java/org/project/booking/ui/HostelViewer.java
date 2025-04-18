package org.project.booking.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.booking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.project.booking.ui.DataBase.AppDatabase;
import org.project.booking.ui.adapters.HostelRecAdapter;
import org.project.booking.ui.models.Hostel;
import org.project.booking.ui.models.Item;
import org.project.booking.ui.utils.JsonUtils;

import java.util.List;
import java.util.Random;

public class HostelViewer extends AppCompatActivity {
    public String hotelName;

    private RecyclerView recyclerView;

    private ImageView profileImageView;

    private TextView hi_txt;

    private AppDatabase db;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.booking.R.layout.activity_hotel_viewer);
        Intent intent = getIntent();


        Button btnSingleBedroom, btnDoubleBedroom, btnSingleSelf, btnDoubleSelf;

        RatingBar ratingBar = findViewById(R.id.ratingBar_view);

        db = AppDatabase.getDatabase(this); // Initialize Room database


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, AuthSignInActivity.class));
            finish();
        }


        profileImageView = findViewById(com.example.booking.R.id.profileImage);


        hi_txt = findViewById(R.id.hi);
        int hostelIndex = intent.getIntExtra("indexx", 1);

        // Load profile picture using Glide (make sure to include Glide in your dependencies)
        if (currentUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(currentUser.getPhotoUrl()) // Use the photo URL from FirebaseAuth
                    .circleCrop() // To make it round
                    .into(profileImageView);
        }
        loadUserProfile();
        List<Hostel> hostelList = JsonUtils.loadHostelsFromAsset(this, "hostels.json");

        Hostel hostel = hostelList.get(hostelIndex - 1);

        //get the hostel

        TextView View_contact, View_title, View_location, View_rating, View_features, View_title2;

        View view = findViewById(R.id.viewview);
        View_contact = findViewById(R.id.View_contact);
        View_title = findViewById(R.id.View_title);
        View_location = findViewById(R.id.View_location);
        View_rating = findViewById(R.id.View_rating__);
        View_features = findViewById(R.id.View_features);
        View_title2 = findViewById(R.id.titleview2);

        View_contact.setText("Contact: " + hostel.getContact());
        View_title.setText(hostel.getName());
        View_title2.setText(hostel.getName());
        View_location.setText("Location: " + hostel.getLocation());
        View_rating.setText("Rating: " + hostel.getRating());
        View_features.setText("Features: " + hostel.getFeats());


        ratingBar.setRating(Float.parseFloat(hostel.getRating()));

        btnSingleBedroom = findViewById(R.id.btn_single_bedroom);
        btnDoubleBedroom = findViewById(R.id.btn_double_bedroom);
        btnSingleSelf = findViewById(R.id.btn_single_self);
        btnDoubleSelf = findViewById(R.id.btn_double_self);

        setButtonListener(btnSingleBedroom, "Single Bedroom", 1,hostelIndex);
        setButtonListener(btnDoubleBedroom, "Double Bedroom", 2,hostelIndex);
        setButtonListener(btnSingleSelf, "Single Self Contained Room", 3,hostelIndex);
        setButtonListener(btnDoubleSelf, "Double Self Contained Room", 4,hostelIndex);


        if(hostel.getId() == 1){


            view.setBackgroundResource(R.drawable.campbell);
        }
        if(hostel.getId() == 2){
            view.setBackgroundResource(R.drawable.katonga); // fallback
        }
        if(hostel.getId() == 3){
            view.setBackgroundResource(R.drawable.bossa); // fallback
        }
        if (hostel.getId() == 4) {
            view.setBackgroundResource(R.drawable.a4); // fallback
        }
        if (hostel.getId() == 5) {
            view.setBackgroundResource(R.drawable.a5); // fallback
        }
        if (hostel.getId() == 6) {
            view.setBackgroundResource(R.drawable.a6); // fallback
        }
        if (hostel.getId() == 7) {
            view.setBackgroundResource(R.drawable.a7); // fallback
        }
        if (hostel.getId() == 8) {
            view.setBackgroundResource(R.drawable.a8); // fallback
        }
        if (hostel.getId() == 9) {
            view.setBackgroundResource(R.drawable.a11); // fallback
        }
        if (hostel.getId() == 10) {
            view.setBackgroundResource(R.drawable.a9); // fallback
        }


        //Initializing Recommendation RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rec_recycler_view);

        HostelRecAdapter adapter = new HostelRecAdapter(this, hostelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hotel, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    private void loadUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {


            // Get full display name
            String fullName = user.getDisplayName();
            String firstName = "Hi";

            if (fullName != null && !fullName.isEmpty()) {
                // Extract the first word (assumed to be first name)
                String[] nameParts = fullName.split(" ");
                firstName += " " + nameParts[0]; // Get only first name

                // If the name is too long, truncate with "..."
                if (firstName.length() > 12) {
                    firstName = firstName.substring(0, 9) + "...";
                }
            }

            firstName += "!";
            hi_txt.setText(firstName); // Set formatted name


        }


    }


    // Save the item to the local database
    private void saveItemToDatabase(int HostelIndex,int amount,String roomtype) {

        // Get the current logged-in user's email from Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getEmail() : "unknown_user";  // Use a default value if the user is not logged in

        // Generate the timestamp as the serial number
        String serialNumber = String.valueOf(System.currentTimeMillis());  // Using current timestamp as serial number


// Create an Item object with the new fields
        Item item = new Item(userId, HostelIndex,amount,roomtype);

        new Thread(() -> {
            // Check if the item already exists
            Item existingItem = db.itemDao().getItemByUserIdAndHostelIndex(userId, HostelIndex);

            if (existingItem == null) {
                db.itemDao().insert(item);

                this.runOnUiThread(() -> {
                    Toast.makeText(this, "Hostel Successfully Booked", Toast.LENGTH_SHORT).show();
                });
            } else {
                this.runOnUiThread(() -> {
                    Toast.makeText(this, "You have already booked this hostel.", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();

    }


    private void setButtonListener(Button button, String roomType, int index,int hostelIndex) {
        button.setOnClickListener(v -> {
            int amount = generateRandomAmount(30000, 50000); // UGX range example

            if (index == 2) {
                amount = generateRandomAmount(50000, 70000); // UGX range example
            }
            if (index == 3) {
                amount = generateRandomAmount(100000, 120000); // UGX range example
            }
            if (index == 4) {
                amount = generateRandomAmount(120000, 200000); // UGX range example
            }
            showBookingDialog(hostelIndex,roomType, amount);
        });
    }

    private int generateRandomAmount(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }


    private void showBookingDialog(int hostelIndex,String roomType, int amount) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Booking")
                .setMessage("Amount for " + roomType + ": UGX " + amount + "\nProceed to book?")
                .setPositiveButton("Book", (dialog, which) -> {
                    Toast.makeText(this, "Booked " + roomType + " for UGX " + amount, Toast.LENGTH_SHORT).show();
                    saveItemToDatabase(hostelIndex,amount,roomType);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}