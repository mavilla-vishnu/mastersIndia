package co.mastersindia.autotax.model;

/**
 * Created by Pandu on 9/2/2017.
 */

public class ItemDataModel {
    private int id;
    private String name;
    private String fav;
    private String type;
    private String unit;
    private double price;
    private String code;
    private String descp;
    private double igst;
    private double cgst;
    private double sgst;
    private double cess;
    private double s_tot;
    private double s_present;
    private double s_final;
    private double disc;
    public ItemDataModel(){

    }
    public ItemDataModel(int id,String name,String fav,String type,String unit,double price,String code,String descp,double igst,double cgst,double sgst,double cess,double s_tot,double s_present,double s_final,double disc){
        this.id=id;
        this.name=name;
        this.fav=fav;
        this.type=type;
        this.unit=unit;
        this.price=price;
        this.code=code;
        this.descp=descp;
        this.igst=igst;
        this.cgst=cgst;
        this.sgst=sgst;
        this.cess=cess;
        this.s_tot=s_tot;
        this.s_present=s_present;
        this.s_final=s_final;
        this.disc=disc;
    }
    public int getId(){return this.id;}
    public String getName() {return this.name;}
    public String getFav() {return this.fav;}
    public String getType() {return this.type;}
    public String getUnit() {return this.unit;}
    public double getPrice() {return this.price;}
    public String getCode() {return this.code;}
    public String getDescp() {return this.descp;}
    public double getIgst() {return this.igst;}
    public double getCgst() {return this.cgst;}
    public double getSgst() {return this.sgst;}
    public double getCess() {return this.cess;}
    public double getS_tot() {return this.s_tot;}
    public double getS_present() {return this.s_present;}
    public double getS_final() {return this.s_final;}
    public double getDisc() {return this.disc;}

    public void setId(int id){this.id=id;}
    public void setName(String name) {this.name=name;}
    public void setFav(String fav) {this.fav=fav;}
    public void setType(String type) {this.type=type;}
    public void setUnit(String unit) {this.unit=unit;}
    public void setPrice(double price) {this.price=price;}
    public void setCode(String code) {this.code=code;}
    public void setDescp(String descp) {this.descp=descp;}
    public void setIgst(double igst) {this.igst=igst;}
    public void setCgst(double cgst) {this.cgst=cgst;}
    public void setSgst(double sgst) {this.sgst=sgst;}
    public void setCess(double cess) {this.cess=cess;}
    public void setS_tot(double s_tot) {this.s_tot=s_tot;}
    public void setS_present(double s_present) {this.s_present=s_present;}
    public void setS_final(double s_final) {this.s_final=s_final;}
    public void setDisc(double disc) {this.disc=disc;}
}
