package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "humidityTable")
public class humidity {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "relativeHumidity")
    private float humidity;

    public humidity(float humidity, String date, String time){
        this.date = date;
        this.time = time;
        this.humidity = humidity;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
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

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
