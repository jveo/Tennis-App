package com.example.tennisapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tennisapp.pojo.racquetTypes;


public class CreateUpdateFragment extends Fragment {

    racquetTypes rackets;

    public static final int UPDATE = 1;
    public static final int CREATE = 2;
    public static final String RACQUET = "racquet";
    public static final String ACTION_TYPE = "action_type";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_update, container, false);

        EditText name = view.findViewById(R.id.racquetName);
        EditText description = view.findViewById(R.id.racquetDescription);
        EditText website = view.findViewById(R.id.racquetWebsite);
        Button submit = view.findViewById(R.id.submitButton);


        //if we have a bundle
        if (getArguments() != null) {
            //if the user want to update a location
            if (getArguments().getInt(ACTION_TYPE) == UPDATE) {
                rackets = getArguments().getParcelable(RACQUET);
                submit.setText("UPDATE RACQUET");
                if (rackets != null) {
                    //populate the current locations values into the respective fields
                    name.setText(rackets.getName());
                    description.setText(rackets.getDescription());
                    website.setText(rackets.getWebsite());

                }
            } else {
                rackets = new racquetTypes();
                submit.setText("CREATE RACQUET");
            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Populate the racquet object with the values on the screen
                    rackets.setName(name.getText().toString());
                    rackets.setDescription(description.getText().toString());
                    rackets.setWebsite(website.getText().toString());


                    Database db = new Database(getContext());
                    if (getArguments().getInt(ACTION_TYPE) == UPDATE) {
                        db.updateLocation(rackets);
                    } else if (getArguments().getInt(ACTION_TYPE) == CREATE) {
                        db.addRacquet(rackets);
                    }
                    db.close();
                    Navigation.findNavController(view).popBackStack();
                }
            });
        }
        return view;
    }
}