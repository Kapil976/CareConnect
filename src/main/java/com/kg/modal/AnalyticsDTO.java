package com.kg.modal;


public class AnalyticsDTO {
    private int totalPatients;
    private int[] genderDistribution; // Array holding [male, female, other]
    private int[] ageDistribution; // Array holding age distribution data

    // Getters and Setters
    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public int[] getGenderDistribution() {
        return genderDistribution;
    }

    public void setGenderDistribution(int[] genderDistribution) {
        this.genderDistribution = genderDistribution;
    }

    public int[] getAgeDistribution() {
        return ageDistribution;
    }

    public void setAgeDistribution(int[] ageDistribution) {
        this.ageDistribution = ageDistribution;
    }
}

