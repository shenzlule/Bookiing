package org.project.booking.ui;

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
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.booking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.project.booking.ui.DataBase.AppDatabase;
import org.project.booking.ui.adapters.HostelRecAdapter;
import org.project.booking.ui.models.Hostel;
import org.project.booking.ui.models.Item;
import org.project.booking.ui.models.ItemDao;
import org.project.booking.ui.utils.JsonUtils;

import java.util.List;
import java.util.concurrent.Executors;

public class HostelViewerBooked extends AppCompatActivity {
    public String hotelName ;

    private RecyclerView recyclerView;

    private ImageView profileImageView;

    private TextView hi_txt,roomtype,amount;

    private ItemDao itemDao;


    private Item item ;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_viewer_booked);
        Intent intent = getIntent();


        RatingBar ratingBar=findViewById(R.id.ratingBar_view);


        // Ensure `itemDao` is properly initialized
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "app_database").build();
        itemDao = database.itemDao();


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, AuthSignInActivity.class));
            finish();
        }


        profileImageView = findViewById(R.id.profileImage);



        hi_txt = findViewById(R.id.hi);
        int hostelIndex = intent.getIntExtra("indexx",1);

        // Load profile picture using Glide (make sure to include Glide in your dependencies)
        if (currentUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(currentUser.getPhotoUrl()) // Use the photo URL from FirebaseAuth
                    .circleCrop() // To make it round
                    .into(profileImageView);
        }
        loadUserProfile();
        List<Hostel> hostelList = JsonUtils.loadHostelsFromAsset(this, "hostels.json");

        Hostel hostel = hostelList.get(hostelIndex-1);


        //get the hostel

        TextView View_contact,View_title,View_location,View_rating,View_features,View_title2;

        View_contact=findViewById(R.id.View_contact);
        View_title=findViewById(R.id.View_title);
        View_location=findViewById(R.id.View_location);
        View_rating=findViewById(R.id.View_rating__);
        View_features=findViewById(R.id.View_features);
        View view =findViewById(R.id.viewview);


        roomtype=findViewById(R.id.roomtype);

        amount=findViewById(R.id.amount);

        View_title2=findViewById(R.id.titleview5);
        View_title2.setText(hostel.getName());

        View_contact.setText("Contact: "+hostel.getContact());
        View_title.setText(hostel.getName());
        View_location.setText("Location: "+hostel.getLocation());
        View_rating.setText("Rating: "+hostel.getRating());
        View_features.setText("Features: "+hostel.getFeats());


        getItemInBackground(hostelIndex,currentUser.getEmail());



        ratingBar.setRating(Float.parseFloat(hostel.getRating()));


        if(hostel.getId() == 1){


            view.setBackgroundResource(R.drawable.campbell);
        }
        if(hostel.getId() == 2){
            view.setBackgroundResource(R.drawable.katonga); // fallback
        }
        if(hostel.getId() == 3){
            view.setBackgroundResource(R.drawable.bossa); // fallback
        }
        if(hostel.getId() == 4){
            view.setBackgroundResource(R.drawable.a4); // fallback
        }
        if(hostel.getId() == 5){
            view.setBackgroundResource(R.drawable.a5); // fallback
        }
        if(hostel.getId() == 6){
            view.setBackgroundResource(R.drawable.a6); // fallback
        }
        if(hostel.getId() == 7){
            view.setBackgroundResource(R.drawable.a7); // fallback
        }
        if(hostel.getId() == 8){
            view.setBackgroundResource(R.drawable.a8); // fallback
        }
        if(hostel.getId() == 9){
            view.setBackgroundResource(R.drawable.a11); // fallback
        }
        if(hostel.getId() == 10){
            view.setBackgroundResource(R.drawable.a9); // fallback
        }

        //Initializing Recommendation RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rec_recycler_view);

        HostelRecAdapter adapter = new HostelRecAdapter(this, hostelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Button View_book = findViewById(R.id.View_book);
        View_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteItem(hostelIndex,currentUser.getEmail());
            }
        });



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
    private void getItemInBackground(int itemNum, String userId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();
            ItemDao itemDao = db.itemDao();

            Item itemBooked = itemDao.getItemByUserIdAndHostelIndex(userId, itemNum);

            // Switch back to main thread to update UI
            runOnUiThread(() -> {
                if (itemBooked != null) {
                    amount.setText("Amount : UGX " + itemBooked.getAmount());
                    roomtype.setText("Room Type: " + itemBooked.getRoomtype()); // assuming getRoomType() exists
                } else {
                }
            });
        });
    }


    private void deleteItem(int itemNum,String ifuserr) {
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "app_database").build();
        ItemDao itemDao = db.itemDao();

        new Thread(() -> {
            // Check if the item exists before deleting
            Item existingItem = itemDao.getItemByUserIdAndHostelIndex( ifuserr,itemNum);

            if (existingItem != null) {
                itemDao.delete(existingItem);

                this.runOnUiThread(() -> {
                    Toast.makeText(this, "Hostel has successfully been removed", Toast.LENGTH_SHORT).show();
                });
            } else {
                this.runOnUiThread(() -> {
                    Toast.makeText(this, "No booking found to remove", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();

    }

    private Item GetItem(int itemNum,String ifuserr) {
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "app_database").build();
        ItemDao itemDao = db.itemDao();

        return itemDao.getItemByUserIdAndHostelIndex( ifuserr,itemNum);



    }





}