package hhu.com.warehouse_fire_warning.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Log extends LitePalSupport {

    @Column(nullable = false,defaultValue = "2018-01-09")
    private String time;

    @Column(nullable = false)
    private int tem;

    private int hum;

    private int smoke;

    public Log(){}

    public Log(String time, int tem, int hum, int smoke) {
        this.time = time;
        this.tem = tem;
        this.hum = hum;
        this.smoke = smoke;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTem() {
        return tem;
    }

    public void setTem(int tem) {
        this.tem = tem;
    }

    public int getHum() {
        return hum;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }

    public int getSmoke() {
        return smoke;
    }

    public void setSmoke(int smoke) {
        this.smoke = smoke;
    }
}
