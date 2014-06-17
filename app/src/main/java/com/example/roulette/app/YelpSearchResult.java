package com.example.roulette.app;

/**
 * Created by zach on 6/10/14.
 */

import android.graphics.Region;

import com.example.v2.Business;

import java.util.List;

public class YelpSearchResult {
    private Region region;
    private int total;
    private List<Business> businesses;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

}
