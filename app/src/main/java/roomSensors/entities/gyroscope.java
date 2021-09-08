package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gyroscopeTable")
public class gyroscope {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "wx")
    private float wx;

    @ColumnInfo(name = "wy")
    private float wy;

    @ColumnInfo(name = "wz")
    private float wz;

    @ColumnInfo(name = "dateTime")
    private String dateTime;

    public gyroscope(float wx, float wy, float wz, String dateTime){
        this.wx = wx;
        this.wy = wy;
        this.wz = wz;
        this.dateTime = dateTime;
    }

    public void setWz(float wz) {
        this.wz = wz;
    }

    public void setWy(float wy) {
        this.wy = wy;
    }

    public void setWx(float wx) {
        this.wx = wx;
    }

    public float getWz() {
        return wz;
    }

    public float getWy() {
        return wy;
    }

    public float getWx() {
        return wx;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
