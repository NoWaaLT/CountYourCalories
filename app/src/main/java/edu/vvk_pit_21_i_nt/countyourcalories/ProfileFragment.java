package edu.vvk_pit_21_i_nt.countyourcalories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
    public static boolean isEditing;

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
    private final String[] genders = {"A Man", "A Woman"};
    private final String[] genderName = {"Man", "Woman"};
    private final float[] myActivityLevels = {1.2f, 1.375f, 1.55f, 1.725f, 1.9f};
    private final String[] myActivityLevelDescription = {"Sedentary, 0-1 per week", "Lightly Active, 2-3 per week", "Moderately Active, 4-5 per week", "Very Active, 6-7 per week", "Super Active, 2 workout per day"};
    private final int[] myDifference = {300, -300, 0};
    private final String[] myGoalDescription = {"Gain weight", "Lose weight", "Maintain weight"};
    static int animationDuration = 500;

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

    private void userDataPut_Str(String value) {
        ((MenuActivity) requireActivity()).updateUserData("gender", value);
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
        TextView profile_Title =  view.findViewById(R.id.profile_title);
        ScrollView scroll_Profile = view.findViewById(R.id.scroll_view_profile);
        ScrollView scroll_Edit_Profile =  view.findViewById(R.id.scroll_view_profile_edit);
        EditText edit_profile_age =  view.findViewById(R.id.edit_profile_age);
        EditText edit_profile_height =  view.findViewById(R.id.edit_profile_height);
        EditText edit_profile_weight =  view.findViewById(R.id.edit_profile_weight);
        TextView profile_Desc_Title =  view.findViewById(R.id.profile_desc_title);
        TextView profile_Description =  view.findViewById(R.id.profile_description);
        TextView edit_age_text =  view.findViewById(R.id.edit_age_text);
        TextView edit_height_text =  view.findViewById(R.id.edit_height_text);
        TextView edit_weight_text =  view.findViewById(R.id.edit_weight_text);
        TextView edit_profile_gender = view.findViewById(R.id.edit_profile_gender);
        TextView edit_gender_text =  view.findViewById(R.id.edit_gender_text);
        TextView edit_activity_level_text =  view.findViewById(R.id.edit_activity_level_text);
        TextView edit_profile_activity_level =  view.findViewById(R.id.edit_profile_activity_level);
        TextView edit_profile_goal =  view.findViewById(R.id.edit_profile_goal);
        TextView edit_goal_text =  view.findViewById(R.id.edit_goal_text);
        TextView edit_profile_target = view.findViewById(R.id.edit_profile_target);
        TextView edit_target_text = view.findViewById(R.id.edit_target_text);
        TextView edit_bmr_text =  view.findViewById(R.id.edit_bmr_text);
        TextView edit_profile_bmr =  view.findViewById(R.id.edit_profile_bmr);

        Button editProfile =  view.findViewById(R.id.edit_profile);
        profile_Title.setText(profile_Title());



        ImageView genderIconMale = view.findViewById(R.id.genderIconMale);
        ImageView genderIconFemale =  view.findViewById(R.id.genderIconFemale);

        scroll_Edit_Profile.setVisibility(View.INVISIBLE);
        scroll_Profile.setVisibility(View.VISIBLE);
        edit_bmr_text.setText("BMR (Basal Metabolic Rate)");
        edit_profile_bmr.setText("" + myBmr);
        edit_goal_text.setText("Edit Goal");
        edit_profile_goal.setText(gal_Description());
        edit_profile_target.setText("" + myTarget);

        profile_Description.setText(profile_description());
        profile_Desc_Title.setText(profile_desc_title());
        edit_age_text.setText("Edit Age");
        edit_profile_age.setText("" + myAge);
        edit_height_text.setText("Edit Height (cm)");
        edit_profile_height.setText("" + myHeight);
        edit_weight_text.setText("Edit Weight (kg)");
        edit_profile_weight.setText("" + myWeight);
        edit_target_text.setText("Target Calories");
        edit_profile_target.setText("" + myTarget);


        Animation animation_Scroll_Profile = new TranslateAnimation(0, 0, 10000, 0);
        animation_Scroll_Profile.setDuration(animationDuration());
        animation_Scroll_Profile.setFillAfter(true);
        scroll_Profile.startAnimation(animation_Scroll_Profile);

        Animation animation_Prof = new TranslateAnimation(-10000, 0, 0, 0);
        animation_Prof.setDuration(animationDuration());
        animation_Prof.setFillAfter(true);
        profile_Title.startAnimation(animation_Prof);

        Animation animation_Prof_title = new TranslateAnimation(0, 0, 10000, 0);
        animation_Prof_title.setDuration(animationDuration());
        animation_Prof_title.setFillAfter(true);
        profile_Desc_Title.startAnimation(animation_Prof_title);

        Animation animation_Prof_desc = new TranslateAnimation(0, 0, 10000, 0);
        animation_Prof_desc.setDuration(animationDuration());
        animation_Prof_desc.setFillAfter(true);
        profile_Description.startAnimation(animation_Prof_desc);

        Animation animation_Button_Edit = new TranslateAnimation(10000, 0, 0, 0);
        animation_Button_Edit.setDuration(animationDuration());
        animation_Button_Edit.setFillAfter(true);
        editProfile.startAnimation(animation_Button_Edit);

        Animation animation_image = new TranslateAnimation(0, -300, -1000, 0);
        animation_image.setDuration(animationDuration());
        animation_image.setFillAfter(true);

        if (Objects.equals(myGender, "A Man")) {
            genderIconMale.setVisibility(View.VISIBLE);
            genderIconFemale.setVisibility(View.INVISIBLE);
            genderIconMale.startAnimation(animation_image);
        } else {
            genderIconMale.setVisibility(View.INVISIBLE);
            genderIconFemale.setVisibility(View.VISIBLE);
            genderIconFemale.startAnimation(animation_image);
        }

        editProfile.setOnClickListener(v -> {

            Animation animation_image_back = new TranslateAnimation(-300, -10000, 0, -10000);
            animation_image_back.setDuration(animationDuration());
            animation_image_back.setFillAfter(true);

            Animation animation_Prof_back = new TranslateAnimation(0, -10000, 0, 0);
            animation_Prof_back.setDuration(animationDuration());
            animation_Prof_back.setFillAfter(true);
            profile_Title.startAnimation(animation_Prof_back);

            if (Objects.equals(myGender, "A Man")) {
                animation_image_back.setDuration(animationDuration());
                animation_image_back.setFillAfter(true);
                genderIconMale.setVisibility(View.VISIBLE);
                genderIconFemale.setVisibility(View.INVISIBLE);
                genderIconMale.startAnimation(animation_image_back);
            } else {
                animation_image_back.setDuration(animationDuration());
                animation_image_back.setFillAfter(true);
                genderIconMale.setVisibility(View.INVISIBLE);
                genderIconFemale.setVisibility(View.VISIBLE);
                genderIconFemale.startAnimation(animation_image_back);
            }

            Animation animation_Button_Edit_back = new TranslateAnimation(0, 10000, 0, 0);
            animation_Button_Edit_back.setDuration(animationDuration());
            animation_Button_Edit_back.setFillAfter(true);
            editProfile.startAnimation(animation_Button_Edit_back);

            Animation animation_Prof_desc_back = new TranslateAnimation(0, 0, 0, 10000);
            animation_Prof_desc_back.setDuration(animationDuration());
            animation_Prof_desc_back.setFillAfter(true);
            profile_Description.startAnimation(animation_Prof_desc_back);

            Animation animation_Prof_title_back = new TranslateAnimation(0, 0, 0, 10000);
            animation_Prof_title_back.setDuration(animationDuration());
            animation_Prof_title_back.setFillAfter(true);
            profile_Desc_Title.startAnimation(animation_Prof_title_back);

            Animation animation_Scroll_Profile_back = new TranslateAnimation(0, 0, 0, 10000);
            animation_Scroll_Profile_back.setDuration(animationDuration());
            animation_Scroll_Profile_back.setFillAfter(true);
            scroll_Profile.startAnimation(animation_Scroll_Profile_back);

            animationDuration = 1500;

            Animation animation_Scroll_Profile_edit = new TranslateAnimation(3000, 0, 0, 0);
            animation_Scroll_Profile_edit.setDuration(animationDuration());
            animation_Scroll_Profile_edit.setFillAfter(true);
            scroll_Edit_Profile.startAnimation(animation_Scroll_Profile_edit);

            Animation animation_age_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_age_profile.setDuration(animationDuration());
            animation_age_profile.setFillAfter(true);
            edit_profile_age.startAnimation(animation_age_profile);

            Animation animation_age_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_age_text.setDuration(animationDuration());
            animation_age_text.setFillAfter(true);
            edit_age_text.startAnimation(animation_age_text);

            Animation animation_height_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_height_profile.setDuration(animationDuration());
            animation_height_profile.setFillAfter(true);
            edit_profile_height.startAnimation(animation_height_profile);

            Animation animation_height_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_height_text.setDuration(animationDuration());
            animation_height_text.setFillAfter(true);
            edit_height_text.startAnimation(animation_height_text);

            Animation animation_weight_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_weight_profile.setDuration(animationDuration());
            animation_weight_profile.setFillAfter(true);
            edit_profile_weight.startAnimation(animation_weight_profile);

            Animation animation_weight_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_weight_text.setDuration(animationDuration());
            animation_weight_text.setFillAfter(true);
            edit_weight_text.startAnimation(animation_weight_text);

            Animation animation_gender_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_gender_profile.setDuration(animationDuration());
            animation_gender_profile.setFillAfter(true);
            edit_profile_gender.startAnimation(animation_gender_profile);

            Animation animation_gender_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_gender_text.setDuration(animationDuration());
            animation_gender_text.setFillAfter(true);
            edit_gender_text.startAnimation(animation_gender_text);

            Animation animation_activity_level_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_activity_level_profile.setDuration(animationDuration());
            animation_activity_level_profile.setFillAfter(true);
            edit_profile_activity_level.startAnimation(animation_activity_level_profile);

            Animation animation_activity_level_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_activity_level_text.setDuration(animationDuration());
            animation_activity_level_text.setFillAfter(true);
            edit_activity_level_text.startAnimation(animation_activity_level_text);

            Animation animation_goal_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_goal_profile.setDuration(animationDuration());
            animation_goal_profile.setFillAfter(true);
            edit_profile_goal.startAnimation(animation_goal_profile);

            Animation animation_goal_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_goal_text.setDuration(animationDuration());
            animation_goal_text.setFillAfter(true);
            edit_goal_text.startAnimation(animation_goal_text);

            Animation animation_bmr_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_bmr_profile.setDuration(animationDuration());
            animation_bmr_profile.setFillAfter(true);
            edit_profile_bmr.startAnimation(animation_bmr_profile);

            Animation animation_bmr_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_bmr_text.setDuration(animationDuration());
            animation_bmr_text.setFillAfter(true);
            edit_bmr_text.startAnimation(animation_bmr_text);

            Animation animation_target_profile = new TranslateAnimation(3000, 0, 10000, 0);
            animation_target_profile.setDuration(animationDuration());
            animation_target_profile.setFillAfter(true);
            edit_profile_target.startAnimation(animation_target_profile);

            Animation animation_target_text = new TranslateAnimation(3000, 0, 10000, 0);
            animation_target_text.setDuration(animationDuration());
            animation_target_text.setFillAfter(true);
            edit_target_text.startAnimation(animation_target_text);

            Animation animation_Profile_Edit = new TranslateAnimation(10000, 0, 0, 0);
            animation_Profile_Edit.setDuration(animationDuration());
            animation_Profile_Edit.setFillAfter(true);
            profile_Title.setText(profile_Edit());
            profile_Title.startAnimation(animation_Profile_Edit);

            if (isEditing) {
                isEditing = false;
                editProfile.setText("Edit");
                editProfile.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
//                scroll_Edit_Profile.setVisibility(View.INVISIBLE);
                scroll_Profile.setVisibility(View.VISIBLE);

            } else {
                isEditing = true;
//

//                editProfile.setText("Back");
//                editProfile.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
//                scroll_Profile.setVisibility(View.INVISIBLE);
                scroll_Edit_Profile.setVisibility(View.VISIBLE);
//                editProfile.setVisibility(View.INVISIBLE);

            }
        });

        edit_gender_text.setText("Edit Gender");
        if (Objects.equals(myGender, genders[0])) {
            edit_profile_gender.setText(genderName[0]);
        } else {
            edit_profile_gender.setText(genderName[1]);
        }

        edit_activity_level_text.setText("Edit Activity Level");
        edit_profile_activity_level.setText("" + myActivityLevel);
        for (int i = 0; i < myActivityLevels.length; i++) {
            if (myActivityLevel == myActivityLevels[i]) {
                edit_profile_activity_level.setText(myActivityLevelDescription[i]);
            }
        }


        edit_profile_age.setOnClickListener(v -> {
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
            edit_profile_age.setSelection(edit_profile_age.getText().length());
        });

        edit_profile_height.setOnClickListener(v -> {
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
            edit_profile_height.setSelection(edit_profile_height.getText().length());
        });

        edit_profile_weight.setOnClickListener(v -> {
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
            edit_profile_weight.setSelection(edit_profile_weight.getText().length());
        });

        edit_profile_gender.setOnClickListener(v -> {
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
                userDataPut_Str(genders[1]);
                edit_profile_bmr.setText(calcNewBmr());
            } else {
                edit_profile_gender.setText(genderName[0]);
                userDataPut_Str(genders[0]);
                edit_profile_bmr.setText(calcNewBmr());
            }
        });

        edit_profile_activity_level.setOnClickListener(v -> {
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

        edit_profile_goal.setOnClickListener(v -> {
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
        return view;
    }

    private long animationDuration() {
        return animationDuration += 400;
    }


    @NonNull
    @Contract(pure = true)
    private String profile_desc_title() {
        return "Hi " + myDisplayName + "! We've compiled some information about your health profile:\n\n";
    }

    private CharSequence gal_Description() {
        return myGoalDescription[myGoal];
    }

    @NonNull
    private String profile_description() {

        return "Yoa a age is " + myAge + " years. Age is an important factor in determining health and nutritional needs. \nFor " + myGender + " weight can influence metabolism and nutrient requirements. Your weight " + myWeight + " kg. This is crucial for assessing overall health and setting weight-related goals. \nYour height: " + myHeight + " cm. Height is necessary for calculating certain health indicators like body mass index (BMI). \nYour activity level " + nowActivity() + ".  This indicates how much physical activity he engages in daily, which impacts calorie needs. \nBMR (Basal Metabolic Rate): " + myBmr + " represents the number of calories the body needs at rest to maintain vital functions such as breathing and circulation. \nYour goal to " + gal_Description() + ". This information is crucial for tailoring dietary recommendations and setting calorie targets, to achieve the goal of " + gal_Description() + ". You should consume " + myTarget + " calories per day. This figure considers his BMR, activity level, and the desired outcome of " + gal_Description() + "\n\n In summary, this message provides a snapshot of " + myDisplayName + " health profile, including his basic information, activity level, and dietary goal, along with a recommended calorie intake to support his objective of " + gal_Description() + ".";
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
    @Contract(pure = true)
    private String profile_Title() {
        return "My Profile";
    }

    private String profile_Edit() {
        return "Edit My Data";
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