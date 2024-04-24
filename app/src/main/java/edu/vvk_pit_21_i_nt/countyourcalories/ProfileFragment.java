package edu.vvk_pit_21_i_nt.countyourcalories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String myDisplayName;
    private float myWeight;
    private int myHeight;
    private float myActivityLevel;
    private int myAge;
    private String myGender;
    private float myBmr;
    private int myGoal;
    private int myTarget;
    private float newBmr;
    private boolean isEditing = false;
    private final String[] genders = {"A Man", "A Woman"};
    private final String[] genderName = {"Man", "Woman"};
    private final float[] myActivityLevels = {1.2f, 1.375f, 1.55f, 1.725f, 1.9f};
    private final String[] myActivityLevelDescription = {"Sedentary, 0-1 per week", "Lightly Active, 2-3 per week", "Moderately Active, 4-5 per week", "Very Active, 6-7 per week", "Super Active, 2 workout per day"};
    private final int[] myDifference = {300, -300, 0};
    private final String[] myGoalDescription = {"Gain weight", "Lose weight", "Maintain weight"};

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    @NonNull
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        userDataRead();
    }

    private void userDataPut_Str(String key, String value) {
        ((MenuActivity) requireActivity()).updateUserData(key, value);
    }

    private void userDataPut_Int(String key, int value) {
        ((MenuActivity) requireActivity()).updateUserData(key, value);
    }

    private void userDataPut_Float(String key, float value) {
        ((MenuActivity) requireActivity()).updateUserData(key, value);
    }

    private void userDataRead() {
        myDisplayName = ((MenuActivity) requireActivity()).userDb.getDisplayName();
        myWeight = (float) (Math.round(((MenuActivity) requireActivity()).userDb.getWeight() * 100.0) / 100.0);
        myHeight = ((MenuActivity) requireActivity()).userDb.getHeight();
        myActivityLevel = ((MenuActivity) requireActivity()).userDb.getActivityLevel();
        myAge = ((MenuActivity) requireActivity()).userDb.getAge();
        myGender = ((MenuActivity) requireActivity()).userDb.getGender();
        myBmr = ((MenuActivity) requireActivity()).userDb.getBmr();
        myGoal = ((MenuActivity) requireActivity()).userDb.getGoal();
        myTarget = ((MenuActivity) requireActivity()).userDb.getTarget();
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForColorStateLists"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        TextView profile_Title = (TextView) view.findViewById(R.id.profile_title);
        EditText edit_profile_age = (EditText) view.findViewById(R.id.edit_profile_age);
        EditText edit_profile_height = (EditText) view.findViewById(R.id.edit_profile_height);
        EditText edit_profile_weight = (EditText) view.findViewById(R.id.edit_profile_weight);
        TextView profile_Description = (TextView) view.findViewById(R.id.profile_description);
        TextView profile_Desc_Title = (TextView) view.findViewById(R.id.profile_desc_title);
        TextView edit_age_text = (TextView) view.findViewById(R.id.edit_age_text);
        TextView edit_height_text = (TextView) view.findViewById(R.id.edit_height_text);
        TextView edit_weight_text = (TextView) view.findViewById(R.id.edit_weight_text);
        TextView edit_profile_gender = (TextView) view.findViewById(R.id.edit_profile_gender);
        TextView edit_gender_text = (TextView) view.findViewById(R.id.edit_gender_text);
        TextView edit_activity_level_text = (TextView) view.findViewById(R.id.edit_activity_level_text);
        TextView edit_profile_activity_level = (TextView) view.findViewById(R.id.edit_profile_activity_level);
        TextView edit_profile_goal = (TextView) view.findViewById(R.id.edit_profile_goal);
        TextView edit_goal_text = (TextView) view.findViewById(R.id.edit_goal_text);
        TextView edit_profile_target = (TextView) view.findViewById(R.id.edit_profile_target);
        TextView edit_target_text = (TextView) view.findViewById(R.id.edit_target_text);
        TextView edit_bmr_text = (TextView) view.findViewById(R.id.edit_bmr_text);
        TextView edit_profile_bmr = (TextView) view.findViewById(R.id.edit_profile_bmr);

        ScrollView scroll_Profile = (ScrollView) view.findViewById(R.id.scroll_view_profile);
        ScrollView scroll_Edit_Profile = (ScrollView) view.findViewById(R.id.scroll_view_profile_edit);

        Button editProfile = (Button) view.findViewById(R.id.edit_profile);
        profile_Title.setText(profile_Title());

        ImageView genderIconMale = (ImageView) view.findViewById(R.id.genderIconMale);
        ImageView genderIconFemale = (ImageView) view.findViewById(R.id.genderIconFemale);
        if (Objects.equals(myGender, "A Man")) {
            genderIconMale.setVisibility(View.VISIBLE);
            genderIconFemale.setVisibility(View.INVISIBLE);
        } else {
            genderIconMale.setVisibility(View.INVISIBLE);
            genderIconFemale.setVisibility(View.VISIBLE);
        }

        scroll_Edit_Profile.setVisibility(View.INVISIBLE);
        scroll_Profile.setVisibility(View.VISIBLE);
        edit_bmr_text.setText("BMR (Basal Metabolic Rate)");
        edit_goal_text.setText("Edit Goal");
        edit_profile_goal.setText(gal_Description());
        profile_Description.setText(profile_description());
        profile_Desc_Title.setText(profile_desc_title());
        edit_age_text.setText("Edit Age");
        edit_height_text.setText("Edit Height (cm)");
        edit_weight_text.setText("Edit Weight (kg)");
        edit_gender_text.setText("Edit Gender");
        edit_activity_level_text.setText("Edit Activity Level");
        edit_target_text.setText("Target Calories");
        userDataRead();
        if (Objects.equals(myGender, genders[0])) {
            edit_profile_gender.setText(genderName[0]);
        } else {
            edit_profile_gender.setText(genderName[1]);
        }
        edit_profile_target.setText("" + myTarget);
        edit_profile_bmr.setText("" + myBmr);
        edit_profile_age.setText("" + myAge);
        edit_profile_height.setText("" + myHeight);
        edit_profile_weight.setText("" + myWeight);

        edit_profile_activity_level.setText("" + myActivityLevel);
        for (int i = 0; i < myActivityLevels.length; i++) {
            if (myActivityLevel == myActivityLevels[i]) {
                edit_profile_activity_level.setText(myActivityLevelDescription[i]);
            }
        }

        actionEditButton(editProfile, scroll_Edit_Profile, scroll_Profile);
        actionEditAge(edit_profile_age, edit_age_text, edit_profile_bmr, edit_profile_target);
        actionEditHeight(edit_profile_height, edit_height_text, edit_profile_bmr, edit_profile_target);
        actionEditWeight(edit_profile_weight, edit_weight_text, edit_profile_bmr, edit_profile_target);
        actionEditGender(edit_profile_gender, genderIconMale, genderIconFemale, edit_profile_bmr, edit_profile_target);
        actionEditActivityLevel(edit_profile_activity_level, edit_profile_target);
        actionCalcGoal(edit_profile_goal, edit_profile_target);
        actionCalcTarget(edit_profile_target);

        return view;
    }

    private void actionCalcTarget(@NonNull TextView edit_profile_target) {
        edit_profile_target.setOnClickListener(v -> {
            for (int i = 0; i < myDifference.length; i++) {
                if (myTarget == myBmr * myActivityLevel + myDifference[i]) {
                    if (i == myDifference.length - 1) {
                        edit_profile_target.setText("" + (myBmr * myActivityLevel + myDifference[0]));
                        userDataPut_Float("target", myBmr * myActivityLevel + myDifference[0]);
                        userDataPut_Int("difference", myDifference[0]);
                        edit_profile_target.setText(calcNewTarget());
                    } else {
                        edit_profile_target.setText("" + (myBmr * myActivityLevel + myDifference[i + 1]));
                        userDataPut_Float("target", myBmr * myActivityLevel + myDifference[i + 1]);
                        userDataPut_Int("difference", myDifference[i + 1]);
                        edit_profile_target.setText(calcNewTarget());
                    }
                    break;
                }
            }
        });
    }

    private void actionCalcGoal(@NonNull TextView edit_profile_goal, TextView edit_profile_target) {
        edit_profile_goal.setOnClickListener(v -> { //edit goal
            for (int i = 0; i < myGoalDescription.length; i++) {
                if (Objects.equals(gal_Description(), myGoalDescription[i])) {
                    if (i == myGoalDescription.length - 1) {
                        edit_profile_goal.setText(myGoalDescription[0]);
                        userDataPut_Int("goal", 0);
                        userDataPut_Int("difference", myDifference[0]);
                        edit_profile_target.setText(calcNewTarget());
                    } else {
                        edit_profile_goal.setText(myGoalDescription[i + 1]);
                        userDataPut_Int("goal", i + 1);
                        userDataPut_Int("difference", myDifference[i + 1]);
                        edit_profile_target.setText(calcNewTarget());
                    }
                    break;
                }
            }
        });
    }

    private void actionEditActivityLevel(@NonNull TextView edit_profile_activity_level, TextView edit_profile_target) {
        edit_profile_activity_level.setOnClickListener(v -> { //edit activity level
            edit_profile_target.setText(calcNewTarget());
            for (int i = 0; i < myActivityLevels.length; i++) {
                if (myActivityLevel == myActivityLevels[i]) {
                    if (i == myActivityLevels.length - 1) {
                        edit_profile_activity_level.setText(myActivityLevelDescription[0]);
                        userDataPut_Float("activityLevel", myActivityLevels[0]);
                    } else {
                        edit_profile_activity_level.setText(myActivityLevelDescription[i + 1]);
                        userDataPut_Float("activityLevel", myActivityLevels[i + 1]);
                    }
                    edit_profile_target.setText(calcNewTarget());
                    break;
                }
            }
        });
    }

    private void actionEditGender(@NonNull TextView edit_profile_gender, ImageView genderIconMale, ImageView genderIconFemale, TextView edit_profile_bmr, TextView edit_profile_target) {
        edit_profile_gender.setOnClickListener(v -> { // edit gender
            edit_profile_bmr.setText(calcNewBmr());
            if (newBmr != myBmr) {
                userDataPut_Float("bmr", newBmr);
                edit_profile_target.setText(calcNewTarget());
                edit_profile_bmr.setText(calcNewBmr());
            }
            if (Objects.equals(myGender, "A Man")) {
                genderIconMale.setVisibility(View.INVISIBLE);
                genderIconFemale.setVisibility(View.VISIBLE);
            } else {
                genderIconMale.setVisibility(View.VISIBLE);
                genderIconFemale.setVisibility(View.INVISIBLE);
            }
            if (Objects.equals(myGender, genders[0])) {
                edit_profile_gender.setText(genderName[1]);
                userDataPut_Str("gender", genders[1]);
                edit_profile_bmr.setText(calcNewBmr());
            } else {
                edit_profile_gender.setText(genderName[0]);
                userDataPut_Str("gender", genders[0]);
                edit_profile_bmr.setText(calcNewBmr());
            }
        });
    }

    private void actionEditWeight(@NonNull EditText edit_profile_weight, TextView edit_weight_text, TextView edit_profile_bmr, TextView edit_profile_target) {
        edit_profile_weight.setOnClickListener(v -> { // edit weight
            edit_weight_text.setText("Edit Weight (kg)");
            String content = edit_profile_weight.getText().toString();
            edit_profile_weight.setText(content);
            if (Float.parseFloat(content) > 0 && Float.parseFloat(content) < 300) {
                content = String.valueOf((float) (Math.round(Float.parseFloat(content) * 100.0) / 100.0));
                edit_profile_weight.setText(content);
                userDataPut_Float("weight", Float.parseFloat(content));
                edit_profile_bmr.setText(calcNewBmr());
                if (newBmr != myBmr) {
                    userDataPut_Float("bmr", newBmr);
                    edit_profile_target.setText(calcNewTarget());
                    edit_profile_bmr.setText(calcNewBmr());
                }
                @SuppressLint("UseRequireInsteadOfGet") InputMethodManager mgr = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(edit_profile_weight.getWindowToken(), 0);
            }
        });
    }

    private void actionEditHeight(@NonNull EditText edit_profile_height, TextView edit_height_text, TextView edit_profile_bmr, TextView edit_profile_target) {
        edit_profile_height.setOnClickListener(v -> { // edit height
            edit_height_text.setText("Edit Height (cm)");
            String content = edit_profile_height.getText().toString();
            edit_profile_height.setText(content);
            if (Integer.parseInt(content) > 0 && Integer.parseInt(content) < 300) {
                edit_profile_bmr.setText(calcNewBmr());
                if (newBmr != myBmr) {
                    userDataPut_Float("bmr", newBmr);
                    edit_profile_target.setText(calcNewTarget());
                    edit_profile_bmr.setText(calcNewBmr());
                }
                userDataPut_Int("height", Integer.parseInt(content));
                edit_profile_bmr.setText(calcNewBmr());
                calcNewBmr();
                @SuppressLint("UseRequireInsteadOfGet") InputMethodManager mgr = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(edit_profile_height.getWindowToken(), 0);
            }
        });
    }

    private void actionEditAge(@NonNull EditText edit_profile_age, TextView edit_age_text, TextView edit_profile_bmr, TextView edit_profile_target) {
        edit_profile_age.setOnClickListener(v -> { //edit age
            edit_age_text.setText("Edit Age");
            String content = edit_profile_age.getText().toString();
            edit_profile_age.setText(content);
            if (Integer.parseInt(content) > 0 && Integer.parseInt(content) < 120) {
                if (newBmr != myBmr) {
                    userDataPut_Float("bmr", newBmr);
                    edit_profile_target.setText(calcNewTarget());
                    edit_profile_bmr.setText(calcNewBmr());
                }
                userDataPut_Int("age", Integer.parseInt(content));
                edit_profile_bmr.setText(calcNewBmr());
                calcNewBmr();

                @SuppressLint("UseRequireInsteadOfGet") InputMethodManager mgr = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(edit_profile_age.getWindowToken(), 0);
            }
        });
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void actionEditButton(@NonNull Button editProfile, ScrollView scrollEditProfile, ScrollView scrollProfile) {
        editProfile.setOnClickListener(v -> {
            if (isEditing) {
                isEditing = false;
                editProfile.setText("Edit");
                editProfile.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
                scrollEditProfile.setVisibility(View.INVISIBLE);
                scrollProfile.setVisibility(View.VISIBLE);
            } else {
                isEditing = true;
                editProfile.setText("Back");
                editProfile.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
                scrollProfile.setVisibility(View.INVISIBLE);
                scrollEditProfile.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.INVISIBLE);
            }
        });
    }

    @NonNull
    @Contract(pure = true)
    private String profile_Title() {
        return "My Profile";
    }

    @NonNull
    @Contract(pure = true)
    private String profile_desc_title() {
        return "Hi " + myDisplayName + "! We've compiled some information about your health profile:\n\n";
    }

    @NonNull
    private String profile_description() {
        return "Yoa a age is " + myAge + " years. Age is an important factor in determining health and nutritional needs. \nFor " + myGender + " weight can influence metabolism and nutrient requirements. Your weight " + myWeight + " kg. This is crucial for assessing overall health and setting weight-related goals. \nYour height: " + myHeight + " cm. Height is necessary for calculating certain health indicators like body mass index (BMI). \nYour activity level " + nowActivity() + ".  This indicates how much physical activity he engages in daily, which impacts calorie needs. \nBMR (Basal Metabolic Rate): " + myBmr + " represents the number of calories the body needs at rest to maintain vital functions such as breathing and circulation. \nYour goal to " + gal_Description() + ". This information is crucial for tailoring dietary recommendations and setting calorie targets, to achieve the goal of " + gal_Description() + ". You should consume " + myTarget + " calories per day. This figure considers his BMR, activity level, and the desired outcome of " + gal_Description() + "\n\n In summary, this message provides a snapshot of " + myDisplayName + " health profile, including his basic information, activity level, and dietary goal, along with a recommended calorie intake to support his objective of " + gal_Description() + ".";
    }

    private CharSequence gal_Description() {
        return myGoalDescription[myGoal];
    }

    @Nullable
    @Contract(pure = true)
    private String nowActivity() {
        for (int i = 0; i < myActivityLevels.length; i++) {
            if (myActivityLevel == myActivityLevels[i]) {
                return myActivityLevelDescription[i];
            }
        }
        return null;
    }

    @NonNull
    private String calcNewBmr() {
        userDataRead();
        newBmr = (10f * myWeight) + (6.25f * myHeight) - (5f * myAge) + ((Objects.equals(myGender, "A Man") ? 5f : -16));
        userDataPut_Float("bmr", newBmr);
        return newBmr + "";
    }

    @NonNull
    private String calcNewTarget() {
        userDataRead();
        int newTarget = (int) (myBmr * myActivityLevel + myDifference[myGoal]);
        userDataPut_Int("target", newTarget);
        return newTarget + "";
    }
}