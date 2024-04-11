package edu.vvk_pit_21_i_nt.countyourcalories;

public class UserDb {
    public String email;
    public String displayName;
    public long weight;
    public long height;
    public double activityLevel;
    public long age;
    public String gender;
    public long bmr;
    public long goal;
    public long target;
    public UserDb() {}
    public UserDb(String email, String displayName, long weight, long height, double activityLevel, long age, String gender, long bmr, long goal, long target) {
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

    }
}
