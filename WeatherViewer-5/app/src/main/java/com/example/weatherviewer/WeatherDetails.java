package com.example.weatherviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherDetails implements Parcelable {

    private int currentTemperature;
    private int highTemperature;
    private int lowTemperature;
    private String description;
    private int currentTemperature1;
    private int highTemperature1;
    private int lowTemperature1;
    private String description1;
    private String city;

    public WeatherDetails(
            int currentTemperature,
            int highTemperature,
            int lowTemperature,
            String description,
            int currentTemperature1,
            int highTemperature1,
            int lowTemperature1,
            String description1,
            String city
    ) {
        this.currentTemperature = currentTemperature;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.description = description;
        this.currentTemperature1 = currentTemperature1;
        this.highTemperature1= highTemperature1;
        this.lowTemperature1 = lowTemperature1;
        this.description1 = description1;
        this.city = city;
    }

    public WeatherDetails(Parcel in) {
       this(

               in.readInt(),
               in.readInt(),
               in.readInt(),
               in.readString(),
               in.readInt(),
               in.readInt(),
               in.readInt(),
               in.readString(),
           in.readString()
       );
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public String getDescription() {
        return description;
    }

    public int getCurrentTemperature1() {
        return currentTemperature1;
    }

    public int getHighTemperature1() {
        return highTemperature1;
    }

    public int getLowTemperature1() {
        return lowTemperature1;
    }

    public String getDescription1() {
        return description1;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getCurrentTemperature());
        dest.writeInt(getHighTemperature());
        dest.writeInt(getLowTemperature());
        dest.writeString(getDescription());
        dest.writeInt(getCurrentTemperature1());
        dest.writeInt(getHighTemperature1());
        dest.writeInt(getLowTemperature1());
        dest.writeString(getDescription1());
        dest.writeString(getCity());
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator<WeatherDetails>() {

        @Override
        public WeatherDetails createFromParcel(Parcel source) {
            return new WeatherDetails(source);
        }

        @Override
        public WeatherDetails[] newArray(int size) {
            return new WeatherDetails[0];
        }
    };
}
