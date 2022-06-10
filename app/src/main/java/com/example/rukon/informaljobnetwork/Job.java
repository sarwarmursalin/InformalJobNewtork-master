package com.example.rukon.informaljobnetwork;

import java.util.Comparator;

public class Job implements Comparable<Job>{

     String job_title;
     String job_description;
     String skill_required;
     String address;
     String job_provider_name;

    private int quantity;

    public Job(){}

    public Job(String job_title, String job_description,String skill_required, String address, String job_provider_name, int quantity) {
        super();
        this.job_title = job_title;
        this.job_description = job_description;
        this.skill_required = skill_required;
        this.address = address;
        this.job_provider_name = job_provider_name;
        this.quantity = quantity;
    }

    public String getJob_title() {
        return job_title;
    }
    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_description() {
        return job_description;
    }
    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getSkill_required() {
        return skill_required;
    }
    public void setSkill_required(String skill_required) {
        this.skill_required = skill_required;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob_provider_name() {
        return job_provider_name;
    }
    public void setJob_provider_name(String job_provider_name) {
        this.job_provider_name = job_provider_name;
    }


    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int compareTo(Job compareJob) {

        int compareQuantity = ((Job) compareJob).getQuantity();

        //ascending order
        return this.quantity - compareQuantity;

        //descending order
        //return compareQuantity - this.quantity;

    }

    public static Comparator<Job> jobComparator = new Comparator<Job>() {

        public int compare(Job job1, Job job2) {

            String job_title1 = job1.getJob_title().toUpperCase();
            String job_title2 = job2.getJob_title().toUpperCase();

            //ascending order
            return job_title1.compareTo(job_title2);

            //descending order
            //return job_title2.compareTo(job_title1);
        }

    };
}
