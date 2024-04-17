package edu.vvk_pit_21_i_nt.countyourcalories;

public class UserDb {


    private String email;

    private String displayName;
    private float weight;
    private int height;
    private float activityLevel;
    private int age;
    private String gender;
    private float bmr;
    private int goal;
    private int target;
    private int difference;
    public UserDb() {}
    public UserDb(String email, String displayName, float weight, int height, float activityLevel, int age, String gender, float bmr, int goal, int target,int difference) {
        this.email = email;
        this.displayName = displayName;
        this.weight = weight;
        this.height = height;
        this.activityLevel = activityLevel;
        this.age = age;
        this.gender = gender;
        this.bmr = bmr;
        this.goal = goal;
        this.target = target;
        this.difference = difference;


    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public float getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public float getActivityLevel() {
        return activityLevel;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public float getBmr() {
        return bmr;
    }

    public int getGoal() {
        return goal;
    }

    public int getTarget() {
        return target;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setActivityLevel(float activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBmr(float bmr) {
        this.bmr = bmr;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
