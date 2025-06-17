package com.example.OptimalYou.model.ai;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWorkoutPreferences {

    // Personal Information
    private String name;
    private int age;
    private String gender;

    // Fitness Goals
    private List<String> fitnessGoals;

    // Current Fitness Level
    private String fitnessLevel;

    // Preferences
    private List<String> workoutTypes;
    private int workoutDuration; // in minutes
    private List<String> preferredWorkoutDays;
    private String preferredTimeOfDay;

    // Health Considerations
    private List<String> healthConditions;
    private List<String> injuries;

    // Equipment and Location
    private List<String> availableEquipment;
    private String workoutLocation;

    // Additional Preferences
    private String intensityLevel; // Low, Medium, High
    private int calorieBurnTarget;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getAvailableEquipment() {
        return availableEquipment;
    }

    public void setAvailableEquipment(List<String> availableEquipment) {
        this.availableEquipment = availableEquipment;
    }

    public int getCalorieBurnTarget() {
        return calorieBurnTarget;
    }

    public void setCalorieBurnTarget(int calorieBurnTarget) {
        this.calorieBurnTarget = calorieBurnTarget;
    }

    public List<String> getFitnessGoals() {
        return fitnessGoals;
    }

    public void setFitnessGoals(List<String> fitnessGoals) {
        this.fitnessGoals = fitnessGoals;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getHealthConditions() {
        return healthConditions;
    }

    public void setHealthConditions(List<String> healthConditions) {
        this.healthConditions = healthConditions;
    }

    public List<String> getInjuries() {
        return injuries;
    }

    public void setInjuries(List<String> injuries) {
        this.injuries = injuries;
    }

    public String getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(String intensityLevel) {
        this.intensityLevel = intensityLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreferredTimeOfDay() {
        return preferredTimeOfDay;
    }

    public void setPreferredTimeOfDay(String preferredTimeOfDay) {
        this.preferredTimeOfDay = preferredTimeOfDay;
    }

    public List<String> getPreferredWorkoutDays() {
        return preferredWorkoutDays;
    }

    public void setPreferredWorkoutDays(List<String> preferredWorkoutDays) {
        this.preferredWorkoutDays = preferredWorkoutDays;
    }

    public int getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(int workoutDuration) {
        this.workoutDuration = workoutDuration;
    }

    public String getWorkoutLocation() {
        return workoutLocation;
    }

    public void setWorkoutLocation(String workoutLocation) {
        this.workoutLocation = workoutLocation;
    }

    public List<String> getWorkoutTypes() {
        return workoutTypes;
    }

    public void setWorkoutTypes(List<String> workoutTypes) {
        this.workoutTypes = workoutTypes;
    }

    @Override
    public String toString() {
        return "UserWorkoutPreferences{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", fitnessGoals=" + fitnessGoals +
                ", fitnessLevel='" + fitnessLevel + '\'' +
                ", workoutTypes=" + workoutTypes +
                ", workoutDuration=" + workoutDuration +
                ", preferredWorkoutDays=" + preferredWorkoutDays +
                ", preferredTimeOfDay='" + preferredTimeOfDay + '\'' +
                ", healthConditions=" + healthConditions +
                ", injuries=" + injuries +
                ", availableEquipment=" + availableEquipment +
                ", workoutLocation='" + workoutLocation + '\'' +
                ", intensityLevel='" + intensityLevel + '\'' +
                ", calorieBurnTarget=" + calorieBurnTarget +
                '}';
    }
}
