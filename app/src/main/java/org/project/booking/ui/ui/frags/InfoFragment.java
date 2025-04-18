package org.project.booking.ui.ui.frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.booking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.project.booking.ui.models.Item;
import org.project.booking.ui.models.ItemDao;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;
    private List<Item> itemList;
    private FirebaseFirestore db;
    private Button myUploadsButton, uploadButton,contactSupportButton;

    private ImageView profileImageView;

    private LinearLayout contactl1;
    private RelativeLayout contact;
    private TextView noItem;
    private ItemDao itemDao;
    private FirebaseAuth mAuth;


    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(com.example.booking.R.layout.fragment_about, container, false);

        ImageView imageView1 ,imageView2,imageView3,imageView4;
        imageView1=view.findViewById(R.id.profile_image1);
        imageView2=view.findViewById(R.id.profile_image2);
        imageView3=view.findViewById(R.id.profile_image3);
        imageView4=view.findViewById(R.id.profile_image4);
        Glide.with(this)
                .load(R.drawable.image1)
                .placeholder(R.drawable.ic_launcher_background) // Default image
                .circleCrop() // Ensures the image is circular
                .into(imageView1);
        Glide.with(this)
                .load(R.drawable.image2)
                .placeholder(R.drawable.ic_launcher_background) // Default image
                .circleCrop() // Ensures the image is circular
                .into(imageView2);
        Glide.with(this)
                .load(R.drawable.image3)
                .placeholder(R.drawable.ic_launcher_background) // Default image
                .circleCrop() // Ensures the image is circular
                .into(imageView3);
        Glide.with(this)
                .load(R.drawable.image4)
                .placeholder(R.drawable.ic_launcher_background) // Default image
                .circleCrop() // Ensures the image is circular
                .into(imageView4);

        return  view;
    }



}