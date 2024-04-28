package edu.vvk_pit_21_i_nt.countyourcalories;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


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
    private TextView caloriesConsumed;
    private TextView caloriesRemained;
    private TextView carbsConsumed;
    private TextView proteinsConsumed;
    private TextView fatConsumed;
    private TextView waterConsumed;
    private List<TextView> listOfDays;
    private ProgressBar calsCycle;
    private ProgressBar carbsBar;
    private ProgressBar proteinBar;
    private ProgressBar fatBar;

    public int setOrNot = 0;

    TextView editTextText ;

    private boolean timerRunning;



    private CountDownTimer countDownTimer;
    private long totalTimeInMillis = 16 * 60 * 60 * 1000;

    private long timeLeftInMillis = totalTimeInMillis;

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
        //((MenuActivity) requireActivity()).updateUserHistory("2024-04-20", "kcal", 1586);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
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
        ImageButton imgbtn = (ImageButton) view.findViewById(R.id.imageButton);
        ImageButton imgbtn2 = (ImageButton) view.findViewById(R.id.imageButton2);
        CardView crd3 = (CardView) view.findViewById(R.id.cardView3);
        CardView crd2 = (CardView) view.findViewById(R.id.cardView2);
        CardView crd5 = (CardView) view.findViewById(R.id.cardView5);
        TextView textView36 = (TextView) view.findViewById(R.id.textView36);
        editTextText = (TextView) view.findViewById(R.id.editTextText);


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
        caloriesConsumed = view.findViewById(R.id.textView5);
        caloriesRemained = view.findViewById(R.id.textView32);
        carbsConsumed = view.findViewById(R.id.textView29);
        proteinsConsumed = view.findViewById(R.id.textView30);
        fatConsumed = view.findViewById(R.id.textView31);
        calsCycle = view.findViewById(R.id.progressBar4);
        carbsBar = view.findViewById(R.id.progressBar);
        proteinBar = view.findViewById(R.id.progressBar2);
        fatBar = view.findViewById(R.id.progressBar3);
        waterConsumed = view.findViewById(R.id.textView47);
        listOfDays = new ArrayList<>();
        addDayListeners(Diena1);
        addDayListeners(Diena2);
        addDayListeners(Diena3);
        addDayListeners(Diena4);
        addDayListeners(Diena5);
        addDayListeners(Diena6);
        addDayListeners(Diena7);
        listOfDays.add(Diena1);
        listOfDays.add(Diena2);
        listOfDays.add(Diena3);
        listOfDays.add(Diena4);
        listOfDays.add(Diena5);
        listOfDays.add(Diena6);
        listOfDays.add(Diena7);


        textView36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setOrNot == 0) {
                    textView36.setText("In Progress..");
                    textView36.setBackgroundResource(R.drawable.edittext_yellow);
                    crd2.setCardBackgroundColor(getResources().getColor(R.color.yellow_light));
                    editTextText.setVisibility(View.VISIBLE);
                    editTextText.setBackgroundResource(R.drawable.edittext_yellow);
                    startTimer(editTextText);
                    editTextText.setVisibility(View.VISIBLE);
                    setOrNot++;
                }
            }
        });

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtn.setImageResource(R.drawable.add_complete);
                imgbtn.setBackgroundResource(R.drawable.edittext_complete);
                crd3.setCardBackgroundColor(getResources().getColor(R.color.UI_complete));

            }
        });

        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtn2.setImageResource(R.drawable.add_complete);
                imgbtn2.setBackgroundResource(R.drawable.edittext_complete);
                crd5.setCardBackgroundColor(getResources().getColor(R.color.UI_complete));
            }
        });





        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (timerRunning) {
            startTimer(editTextText);
            editTextText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("TimeLeftInMillis", timeLeftInMillis);
        outState.putBoolean("TimerRunning", timerRunning);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            timeLeftInMillis = savedInstanceState.getLong("TimeLeftInMillis", totalTimeInMillis);
            timerRunning = savedInstanceState.getBoolean("TimerRunning", false);
            if (timerRunning) {
                startTimer(editTextText);
            }
        }
    }


    private void startTimer(TextView data) {

        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis =  millisUntilFinished;
                updateTimerText(millisUntilFinished,data);
            }


            @Override
            public void onFinish() {
                timerRunning = false;
                data.setText("00:00:00");
            }

        }.start();

        timerRunning = true;
    }

    private void updateTimerText(long millisUntilFinished, TextView og) {
        int hours = (int) (millisUntilFinished / 1000) / 3600;
        int minutes = (int) ((millisUntilFinished / 1000) % 3600) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        og.setText(timeLeftFormatted);
    }


    protected void showHistory(TextView txt) {
        String day = (String) txt.getText();
        UserHistory uh = null;
        for (Map.Entry<String, UserHistory> set: ((MenuActivity) requireActivity()).userHistoryHashMap.entrySet()) {
            String date = set.getKey();
            if (date.endsWith(day)) {
                uh = set.getValue();
                break;
            }
        }
        int target = ((MenuActivity) requireActivity()).userDb.getTarget();
        int goal = ((MenuActivity) requireActivity()).userDb.getGoal();
        int targetCarbo = calcTargetCarbs(goal, target);
        int targetProtein = calcTargetProtein(goal, target);
        int targetFat = calcTargetFat(goal, target);

        if (uh != null) {
            String carboText = String.format(Locale.UK,"%d/%d", uh.getCarbo(), targetCarbo);
            String proteinText = String.format(Locale.UK, "%d/%d", uh.getProtein(), targetProtein);
            String fatText = String.format(Locale.UK, "%d/%d", uh.getFat(), targetFat);
            caloriesConsumed.setText(String.valueOf(uh.getKcal()));
            carbsConsumed.setText(carboText);
            proteinsConsumed.setText(proteinText);
            fatConsumed.setText(fatText);
            waterConsumed.setText(String.valueOf(uh.getWater()));
            int remain = target - uh.getKcal();
            caloriesRemained.setText(String.valueOf(remain));
            int calcPercent = (uh.getKcal() * 100) / target;
            int carbsPercent = (uh.getCarbo() * 100) / targetCarbo;
            int proteinPercent = (uh.getProtein() * 100) / targetProtein;
            int fatPercent = (uh.getFat() * 100) / targetFat;
            if (calcPercent <= 100) {
                calsCycle.setProgress(calcPercent);
            }
            else {
                calsCycle.setProgress(100);
            }
            if (carbsPercent <= 100) {
                carbsBar.setProgress(carbsPercent);
            }
            else {
                carbsBar.setProgress(100);
            }
            if (proteinPercent <= 100) {
                proteinBar.setProgress(proteinPercent);
            }
            else {
                proteinBar.setProgress(100);
            }
            if (fatPercent <= 100) {
                fatBar.setProgress(fatPercent);
            }
            else {
                fatBar.setProgress(100);
            }
        }
        else {
            caloriesConsumed.setText(String.valueOf(0));
            carbsConsumed.setText(String.valueOf(0));
            proteinsConsumed.setText((String.valueOf(0)));
            fatConsumed.setText(String.valueOf(0));
            caloriesRemained.setText(String.valueOf(target));
            waterConsumed.setText(String.valueOf(0));
            calsCycle.setProgress(0);
            carbsBar.setProgress(0);
            proteinBar.setProgress(0);
            fatBar.setProgress(0);
        }


    }

    protected void addDayListeners(TextView day) {
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistory(day);
                day.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.edittext_dark));
                for (TextView txt : listOfDays) {
                    if (txt.getId() != day.getId()) {
                        txt.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.edittext_gray));
                    }
                }
            }
        });
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