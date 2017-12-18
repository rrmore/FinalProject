package com.example.futzm.finalproject.POJO;

/**
 * Created by futzm on 12/8/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;






    public class Geometry {

        @SerializedName("location")
        @Expose
        private Location location;

        /**
         *
         * @return
         * The location
         */
        public Location getLocation() {
            return location;
        }

        /**
         *
         * @param location
         * The location
         */
        public void setLocation(Location location) {
            this.location = location;
        }

    }

