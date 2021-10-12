package roomSensors.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rotationTable")
public class rotation {

    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "xsin")
    private float xsin;

    @ColumnInfo(name = "ysin")
    private float ysin;

    @ColumnInfo(name = "zsin")
    private float zsin;

    @ColumnInfo(name = "cos")
    private float cos;

    @ColumnInfo(name = "sha")
    private float sha;

    public rotation(float xsin, float ysin, float zsin, float cos, float sha, String date, String time){
        this.cos = cos;
        this.date = date;
        this.sha = sha;
        this.xsin = xsin;
        this.ysin = ysin;
        this.zsin = zsin;
        this.time = time;
    }

    public float getSha() {
        return sha;
    }

    public void setSha(float sha) {
        this.sha = sha;
    }

    public float getCos() {
        return cos;
    }

    public void setCos(float cos) {
        this.cos = cos;
    }

    public float getZsin() {
        return zsin;
    }

    public void setZsin(float zsin) {
        this.zsin = zsin;
    }

    public float getYsin() {
        return ysin;
    }

    public void setYsin(float ysin) {
        this.ysin = ysin;
    }

    public float getXsin() {
        return xsin;
    }

    public void setXsin(float xsin) {
        this.xsin = xsin;
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
}
