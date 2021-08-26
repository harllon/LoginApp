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
    @ColumnInfo(name = "dateTime")
    private String dateTime;

    public gpsLocation(@NonNull int id, @NonNull double latitude, @NonNull double longitude, @NonNull String dateTime){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
