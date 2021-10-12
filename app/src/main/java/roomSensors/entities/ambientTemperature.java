package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "temperatureTable")
public class ambientTemperature {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "temp")
    private float temperature;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public ambientTemperature(float temperature, String date, String time){
        this.temperature = temperature;
        this.date = date;
        this.time = time;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
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
}
