package edu.vvk_pit_21_i_nt.countyourcalories;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealPlansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealPlansFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Activity activity = getActivity();





    public MealPlansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealPlansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealPlansFragment newInstance(String param1, String param2) {
        MealPlansFragment fragment = new MealPlansFragment();
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
        View view = inflater.inflate(R.layout.fragment_meal_plans, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        TextView title = (TextView) view.findViewById(R.id.textView49);
        TextView visable1 = (TextView) view.findViewById(R.id.textView52);
        TextView visable2 = (TextView) view.findViewById(R.id.textView54);
        TextView visable3 = (TextView) view.findViewById(R.id.textView56);
        TextView visable4 = (TextView) view.findViewById(R.id.textView57);
        TextView visable5 = (TextView) view.findViewById(R.id.textView61);
        TextView dovile = (TextView) view.findViewById(R.id.textView63);
        TextView calories = (TextView) view.findViewById(R.id.textView50);
        TextView carbs = (TextView) view.findViewById(R.id.textView58);
        TextView protein = (TextView) view.findViewById(R.id.textView59);
        TextView fats = (TextView) view.findViewById(R.id.textView60);
        TextView temp = (TextView) view.findViewById(R.id.textView64);
        TextView weight = (TextView) view.findViewById(R.id.textView62);
        TextView tempCarb = (TextView) view.findViewById(R.id.textView65);
        TextView tempProt = (TextView) view.findViewById(R.id.textView66);
        TextView tempFat = (TextView) view.findViewById(R.id.textView67);
        ImageButton imgbtn = (ImageButton) view.findViewById(R.id.imageButton5);
        ImageView imgvw = (ImageView) view.findViewById(R.id.imageView13);


        dovile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,new DatabaseFoodAdd());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        ScrollView scrollView = view.findViewById(R.id.scrview);


        Slider slider = view.findViewById(R.id.sliderOG);
        slider.setValue(100);
        int bit = 0;



        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                DecimalFormat df = new DecimalFormat("#.#");
                double bit = Double.parseDouble(temp.getText().toString());
                double bitc = Double.parseDouble(tempCarb.getText().toString());
                double bitp = Double.parseDouble(tempProt.getText().toString());
                double bitf = Double.parseDouble(tempFat.getText().toString());
                int selectedValue = (int) value;
                weight.setText(Integer.toString(selectedValue)+"g");
                if (bit != 0){
                    double calc = bit * (double)selectedValue;
                    int inted = (int)calc;
                    double calcc = bitc * (double)selectedValue;
                    double calcp = bitp * (double)selectedValue;
                    double calcf = bitf * (double)selectedValue;
                    calcc = Double.parseDouble(df.format(calcc));
                    calcp = Double.parseDouble(df.format(calcp));
                    calcf = Double.parseDouble(df.format(calcf));

                    calories.setText(Integer.toString(inted));
                    carbs.setText(Double.toString(calcc));
                    protein.setText(Double.toString(calcp));
                    fats.setText(Double.toString(calcf));

                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFood(query,title,calories,carbs,protein,fats,temp,tempCarb,tempProt,tempFat,scrollView);
                searchView.setQuery("", false);
               // searchView.onActionViewCollapsed();
                slider.setValue(100);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return view;
    }



    private void searchFood(String data, TextView title,TextView calories,TextView carbs,TextView protein, TextView fats,TextView temp,TextView temp2,TextView temp3, TextView temp4, ScrollView scrollView){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Products/Sheet1");
        Query firebaseQuery = databaseRef.orderByChild("Produktas").startAt(data).endAt(data + "\uf8ff");

        firebaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<product> searchResults = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // Assuming each child node contains a "Produktas" field
                    product productName = data.getValue(product.class);
                    searchResults.add(productName);


                    String ttl = searchResults.get(0).getProduktas();
                    float  var1 = searchResults.get(0).getKilokalorijos();
                    float var2 = searchResults.get(0).getBaltymai();
                    float var3 = searchResults.get(0).getAngliavandeniai();
                    float var4 = searchResults.get(0).getRiebalai();

                    int first = (int)var1;
                    double bitcarb = (double)var3 / 100;
                    double bitprot = (double)var2 / 100;
                    double bitfat = (double)var4 /100;

                    temp2.setText(Double.toString(bitcarb));
                    temp3.setText(Double.toString(bitprot));
                    temp4.setText(Double.toString(bitfat));


                    double bit = (double)first / 100;
                    temp.setText(Double.toString(bit));

                    title.setText(ttl);
                    calories.setText(Integer.toString(first));
                    protein.setText(Float.toString(var2));
                    carbs.setText(Float.toString(var3));
                    fats.setText(Float.toString(var4));
                }

                if (searchResults.isEmpty()){
                    Snackbar.make(scrollView,"There is no such food in database!", BaseTransientBottomBar.LENGTH_SHORT).setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}