package nantha91.com.simpleapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseFile;

import java.util.ArrayList;

/**
 * Created by nantha on 23/4/15.
 */
public class ContentEntry implements Parcelable {
    //default params for all category
    public static final String NAME = "Name";
    public static final String WEBSITE = "Website";
    public static final String ADDRESS = "Address";
    public static final String MOBILENO = "MobileNo";
    public static final String LOCATION = "Location";
    public static final String PHOTO = "Photo";
    public static final String PHOTO1 = "Photo1";
    public static final String PHOTO2 = "Photo2";
    public static final String PHOTO3 = "Photo3";

    //other parameters
    public static final String TIMINGS = "Timings";
    public static final String CUISINE = "Cuisine";
    public static final String MAPLINK = "MapLink";
    public static final String REVIEWS = "Reviews";


    private String name, website, address, mobileno, timings, cuisine, maplink, reviews;
    private byte[] photo1, photo2, photo3;
    private int photo1length, photo2length, photo3length;

    public static final Creator<ContentEntry> CREATOR = new Creator<ContentEntry>() {
        @Override
        public ContentEntry createFromParcel(Parcel parcel) {
            ContentEntry entry = new ContentEntry();
            entry.name = parcel.readString();
            entry.website = parcel.readString();
            entry.address = parcel.readString();
            entry.mobileno = parcel.readString();
            entry.timings = parcel.readString();
            entry.cuisine = parcel.readString();
            entry.maplink = parcel.readString();
            entry.reviews = parcel.readString();
            entry.photo1length = parcel.readInt();
            entry.photo2length = parcel.readInt();
            entry.photo3length = parcel.readInt();
            entry.photo1 = new byte[entry.photo1length];
            entry.photo2 = new byte[entry.photo2length];
            entry.photo3 = new byte[entry.photo3length];
            if(entry.photo1.length>0)
            parcel.readByteArray(entry.photo1);
            if(entry.photo2.length>0)
            parcel.readByteArray(entry.photo2);
            if(entry.photo3.length>0)
            parcel.readByteArray(entry.photo3);

            return entry;
        }

        @Override
        public ContentEntry[] newArray(int size) {
            return new ContentEntry[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(website);
        parcel.writeString(address);
        parcel.writeString(mobileno);
        parcel.writeString(timings);
        parcel.writeString(cuisine);
        parcel.writeString(maplink);
        parcel.writeString(reviews);
        parcel.writeInt(photo1length);
        parcel.writeInt(photo2length);
        parcel.writeInt(photo3length);
        parcel.writeByteArray(photo1);
        parcel.writeByteArray(photo2);
        parcel.writeByteArray(photo3);

    }

    //getters


    public int getPhoto1length() {
        return photo1length;
    }

    public int getPhoto2length() {
        return photo2length;
    }

    public int getPhoto3length() {
        return photo3length;
    }

    public byte[] getPhoto1() {
        return photo1;
    }

    public byte[] getPhoto2() {
        return photo2;
    }

    public byte[] getPhoto3() {
        return photo3;
    }

    public String getReviews() {
        return reviews;
    }

    public String getMaplink() {
        return maplink;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getName() {
        return name;
    }

    public String getTimings() {
        return timings;
    }

    //setters
    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public void setMaplink(String maplink) {
        this.maplink = maplink;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhoto1(byte[] photo1) {
        this.photo1 = photo1;
    }

    public void setPhoto2(byte[] photo2) {
        this.photo2 = photo2;
    }

    public void setPhoto3(byte[] photo3) {
        this.photo3 = photo3;
    }

    public void setPhoto1length(int photo1length) {
        this.photo1length = photo1length;
    }

    public void setPhoto2length(int photo2length) {
        this.photo2length = photo2length;
    }

    public void setPhoto3length(int photo3length) {
        this.photo3length = photo3length;
    }
}
