package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gravityTable")
public class gravity {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "gx")
    private float gx;

    @ColumnInfo(name = "gy")
    private float gy;

    @ColumnInfo(name = "gz")
    private float gz;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public gravity(float gx, float gy, float gz, String date, String time){
        this.date = date;
        this.time = time;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }

    public int getPk_id() {
        return pk_id;
    }

    public float getGx() {
        return gx;
    }

    public float getGy() {
        return gy;
    }

    public float getGz() {
        return gz;
    }

    public void setGx(float gx) {
        this.gx = gx;
    }

    public void setGy(float gy) {
        this.gy = gy;
    }

    public void setGz(float gz) {
        this.gz = gz;
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
