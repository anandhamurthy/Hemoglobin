package com.hemoglobin.hemoglobin;

public class Donations {

    public String blood_bank_name, blood_bank_place, donated_date, donated_time, exp_date, user_id;

    public Donations() {
    }

    public Donations(String blood_bank_name, String blood_bank_place, String donated_date, String donated_time, String exp_date, String user_id) {
        this.blood_bank_name = blood_bank_name;
        this.blood_bank_place = blood_bank_place;
        this.donated_date = donated_date;
        this.donated_time = donated_time;
        this.exp_date = exp_date;
        this.user_id = user_id;
    }

    public String getBlood_bank_name() {
        return blood_bank_name;
    }

    public void setBlood_bank_name(String blood_bank_name) {
        this.blood_bank_name = blood_bank_name;
    }

    public String getBlood_bank_place() {
        return blood_bank_place;
    }

    public void setBlood_bank_place(String blood_bank_place) {
        this.blood_bank_place = blood_bank_place;
    }

    public String getDonated_date() {
        return donated_date;
    }

    public void setDonated_date(String donated_date) {
        this.donated_date = donated_date;
    }

    public String getDonated_time() {
        return donated_time;
    }

    public void setDonated_time(String donated_time) {
        this.donated_time = donated_time;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}