package roomSensors.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rotationTable")
public class rotation {

    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "dateTime")
    private String dateTime;

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

    public rotation(float xsin, float ysin, float zsin, float cos, float sha, String dateTime){
        this.cos = cos;
        this.dateTime = dateTime;
        this.sha = sha;
        this.xsin = xsin;
        this.ysin = ysin;
        this.zsin = zsin;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }
}
