package roomGPS;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location")
public class gpsLocation {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "latitude")
    private double latitude;
    @NonNull
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "speed")
    private double speed;
    @ColumnInfo(name = "altitude")
    private double altitude;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "time")
    private String time;

    public gpsLocation(@NonNull int id, @NonNull double latitude, @NonNull double longitude, double speed, double altitude, @NonNull String date, String time){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
        this.altitude = altitude;
        this.speed = speed;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }
    public double getLatitude(){
        return latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }
    public int getPk_id() {
        return pk_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
