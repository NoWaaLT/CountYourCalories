package edu.vvk_pit_21_i_nt.countyourcalories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDiaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyDiaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDiaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDiaryFragment newInstance(String param1, String param2) {
        MyDiaryFragment fragment = new MyDiaryFragment();
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

        View view = inflater.inflate(R.layout.fragment_my_diary, container, false);


        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        String timeOfDay = TimeOfDayDeterminer();
        textView2.setText("Good " + timeOfDay + "!");

        TextView Diena1 = (TextView)  view.findViewById(R.id.textView10);
        TextView Diena2 = (TextView)  view.findViewById(R.id.textView21);
        TextView Diena3 = (TextView)  view.findViewById(R.id.textView6);
        TextView Diena4 = (TextView)  view.findViewById(R.id.textView22);
        TextView Diena5 = (TextView)  view.findViewById(R.id.textView7);
        TextView Diena6 = (TextView)  view.findViewById(R.id.textView8);
        TextView Diena7 = (TextView)  view.findViewById(R.id.textView9);
        TextView Diena11 = (TextView)  view.findViewById(R.id.textView11);
        TextView Diena12 = (TextView)  view.findViewById(R.id.textView12);
        TextView Diena13 = (TextView)  view.findViewById(R.id.textView13);
        TextView Diena14 = (TextView)  view.findViewById(R.id.textView14);
        TextView Diena15 = (TextView)  view.findViewById(R.id.textView15);
        TextView Diena16 = (TextView)  view.findViewById(R.id.textView16);
        TextView Diena17 = (TextView)  view.findViewById(R.id.textView17);
        int[] days = getLastSevenDaysOfMonth();
        Diena1.setText(Integer.toString(days[6]));
        Diena2.setText(Integer.toString(days[5]));
        Diena3.setText(Integer.toString(days[4]));
        Diena4.setText(Integer.toString(days[3]));
        Diena5.setText(Integer.toString(days[2]));
        Diena6.setText(Integer.toString(days[1]));
        Diena7.setText(Integer.toString(days[0]));
        String[] days2 = getLastSixDays();
        Diena11.setText(days2[6]);
        Diena12.setText(days2[5]);
        Diena13.setText(days2[4]);
        Diena14.setText(days2[3]);
        Diena15.setText(days2[2]);
        Diena16.setText(days2[1]);
        Diena17.setText(days2[0]);



        return view;

    }




    private int calcTargetProtein(int goal, int targetKcal) {
        int proteins;
        if (goal == 0) {
            proteins = targetKcal / 100 * 20;
        } else if (goal == 1) {
            proteins = targetKcal / 100 * 30;
        } else {
            proteins = targetKcal / 100 * 20;
        }

        return proteins;
    }

    private int calcTargetCarbs(int goal, int targetKcal) {
        int carbs;
        if (goal == 0) {
            carbs = targetKcal / 100 * 55;
        } else if (goal == 1) {
            carbs = targetKcal / 100 * 45;
        } else {
            carbs = targetKcal / 100 * 55;
        }

        return carbs;
    }

    private int calcTargetFat(int goal, int targetKcal) {
        int fat;
        if (goal == 0) {
            fat = targetKcal / 100 * 25;
        } else if (goal == 1) {
            fat = targetKcal / 100 * 25;
        } else {
            fat = targetKcal / 100 * 25;
        }

        return fat;
    }
    private String TimeOfDayDeterminer (){

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            if (hour >= 6 && hour < 12) {
                return "Morning";
            } else if (hour >= 12 && hour < 18) {
                return "Day";
            } else if (hour >= 18 && hour < 22) {
                return "Evening";
            } else {
                return "Night";
            }

    }

    public static int[] getLastSevenDaysOfMonth() {
        int[] daysOfMonth = new int[7];
        Calendar calendar = Calendar.getInstance();

        // Get today's day of the month
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        daysOfMonth[0] = today;

        // Get the day of the month for the last six days
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1); // Go back one day
            daysOfMonth[i] = calendar.get(Calendar.DAY_OF_MONTH);
        }

        return daysOfMonth;
    }

    public static String[] getLastSixDays() {
        String[] weekDays = new String[7];
        Calendar calendar = Calendar.getInstance();

        // Get today's day of the week
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        weekDays[0] = getFirstLetterOfDayOfWeek(today);

        // Get the last six days
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            weekDays[i] = getFirstLetterOfDayOfWeek(dayOfWeek);
        }

        return weekDays;
    }

    private static String getFirstLetterOfDayOfWeek(int dayOfWeek) {
        String[] dayNames = {"S", "M", "T", "W", "T", "F", "S"};
        return dayNames[dayOfWeek - 1];
    }
}