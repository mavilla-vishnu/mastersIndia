package co.mastersindia.autotax.model;

/**
 * Created by Pandu on 8/31/2017.
 */

public class Bus_model {
    private String title,descp,gstin,cdate;

    public Bus_model() {
    }

    public Bus_model(String title, String descp, String gstin,String cdate) {
        this.title = title;
        this.descp = descp;
        this.gstin = gstin;
        this.cdate = cdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

}