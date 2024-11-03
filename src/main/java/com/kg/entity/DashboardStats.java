package com.kg.entity;


public class DashboardStats {
    private int totalPatients;
    private int upcomingAppointments;
    private int missedAppointments;

    public DashboardStats(int totalPatients, int upcomingAppointments, int missedAppointments) {
        this.totalPatients = totalPatients;
        this.upcomingAppointments = upcomingAppointments;
        this.missedAppointments = missedAppointments;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public int getUpcomingAppointments() {
        return upcomingAppointments;
    }

    public void setUpcomingAppointments(int upcomingAppointments) {
        this.upcomingAppointments = upcomingAppointments;
    }

    public int getMissedAppointments() {
        return missedAppointments;
    }

    public void setMissedAppointments(int missedAppointments) {
        this.missedAppointments = missedAppointments;
    }

    // Getters and Setters
}
