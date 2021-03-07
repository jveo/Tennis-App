package com.example.tennisapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Required Format for any new or existing racquet types:
 *
 * name (String)
 * description (String)
 *
 */
public class racquetTypes implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String website;

    //insert into DB
    public racquetTypes(String name, String description, String website) {
        this.name = name;
        this.description = description;
        this.website = website;
    }

    //read from DB
    public racquetTypes(int id, String name, String description, String website) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
    }

    protected racquetTypes(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        website = in.readString();
    }

    public racquetTypes(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(website);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<racquetTypes> CREATOR = new Parcelable.Creator<racquetTypes>() {
        @Override
        public racquetTypes createFromParcel(Parcel in) {
            return new racquetTypes(in);
        }

        @Override
        public racquetTypes[] newArray(int size) {
            return new racquetTypes[size];
        }
    };


    public String getWebsite(){
        return website;
    }

    public String setWebsite(String website){
       return this.website = website;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    @Override
    public String toString() {
        return this.name;
    }
}