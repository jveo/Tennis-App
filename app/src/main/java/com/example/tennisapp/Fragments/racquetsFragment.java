package com.example.tennisapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tennisapp.CreateUpdateFragment;
import com.example.tennisapp.CustomRecyclerViewAdapter;
import com.example.tennisapp.Database;
import com.example.tennisapp.MainActivity;
import com.example.tennisapp.R;
import com.example.tennisapp.pojo.racquetTypes;

import java.util.ArrayList;

import static com.example.tennisapp.MainActivity.fab;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link racquetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class racquetsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public racquetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment financeTerminologyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static racquetsFragment newInstance(String param1, String param2) {
        racquetsFragment fragment = new racquetsFragment();
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

        fab.hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_racquets, container, false);
        fab.setImageResource(R.drawable.ic_baseline_add_24);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(CreateUpdateFragment.ACTION_TYPE,
                        CreateUpdateFragment.CREATE);
                Navigation.findNavController(view)
                        .navigate(R.id.createUpdateFragment, bundle);
            }
        });
        //List of items
        Database db = new Database(getContext());
        ArrayList<racquetTypes> racquets = db.getAllRacquets();
        db.close();

        //Set an adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(racquets, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Layout Manager
        /**
         * comment out when choosing another layout variation
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /**
         * Linear layout
         * uncomment for use
         */
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
//                LinearLayoutManager.HORIZONTAL,
//                false));
        /**
         * Grid layout
         */
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        /**
         * Staggered layout
         */
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
//                  LinearLayoutManager.VERTICAL));

        return view;
    }



}

