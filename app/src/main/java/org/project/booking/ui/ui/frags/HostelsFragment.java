package org.project.booking.ui.ui.frags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.project.booking.ui.DataBase.AppDatabase;
import org.project.booking.ui.adapters.HostelAdapter;
import org.project.booking.ui.models.Hostel;
import org.project.booking.ui.models.Item;
import org.project.booking.ui.models.ItemDao;
import org.project.booking.ui.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostelsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;
    private List<Hostel> itemList;
    private HostelAdapter adapter;
    private FirebaseFirestore db;
    private Button myUploadsButton, uploadButton;

    private ImageView profileImageView;

    private TextView noItem;
    private ItemDao itemDao;
    private FirebaseAuth mAuth;


    public HostelsFragment() {
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
    public static HostelsFragment newInstance(String param1, String param2) {
        HostelsFragment fragment = new HostelsFragment();
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
        View view =inflater.inflate(com.example.booking.R.layout.fragment_timeline, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

//        List<Hostel> hostelList = new ArrayList<>();
        itemList = new ArrayList<>();

         adapter = new HostelAdapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));



        List<Hostel> hostelList  = JsonUtils.loadHostelsFromAsset(getContext(), "hostels.json");

        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").build();
         itemDao = db.itemDao();

        if (itemDao != null) {
            loadItems(hostelList);  // Only call if `itemDao` is initialized
        }

        return  view;
    }


    public void refresh() {

        List<Hostel> hostelList = new ArrayList<>();
        hostelList=JsonUtils.loadHostelsFromAsset(getContext(), "hostels.json");
        loadItems(hostelList);  // Reload the items when refreshing the fragment
    }

    private void loadItems(List<Hostel> hostelList) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = (user != null && user.getEmail() != null) ? user.getEmail() : "none";


        List<Hostel> hostelList_new=new ArrayList<>();


        if (itemDao == null) {
            return;  // Exit early if `itemDao` is not initialized
        }
        // Perform the query on a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Query the Room database for all items

                if (itemDao != null ) {

                        List<Item> itemsFromDb = itemDao.getAllItems();

                        if(!(itemsFromDb.isEmpty())){


                        for (int i = 0; i < hostelList.size(); i++) {


                            boolean save = true;


                                for (int j = 0; j < itemsFromDb.size(); j++) {


                                    if(Objects.equals(itemsFromDb.get(j).getUserId(), userEmail)) {

                                        if (itemsFromDb.get(j).getHostelIndex() == hostelList.get(i).getId()) {
                                            save = false;

                                            break;
                                        }
                                    }

                                }


                            if (save) {
                                hostelList_new.add(hostelList.get(i));
                            }

                        }


                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> {

                                itemList .clear();

                                itemList.addAll(hostelList_new);

                                adapter.notifyDataSetChanged();
                            });
                        }




                    }
                        else{
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> {
                                    itemList .clear();


                                    itemList.addAll(hostelList);
                                    adapter.notifyDataSetChanged();
                                });
                            }
                        }
                }
                else{
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {

                            itemList .clear();
                            itemList.addAll(hostelList);
                            adapter.notifyDataSetChanged();
                        });
                    }
                }

            }
        }).start();
    }

}