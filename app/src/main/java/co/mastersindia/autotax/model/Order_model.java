package co.mastersindia.autotax.model;

/**
 * Created by Pandu on 9/2/2017.
 */

public class Order_model {
    private int id;
    private String name;
    private double qty;
    private String date;
    private int sup_id;
    private String status;
    public Order_model(){

    }
    public Order_model(int id,String name,double qty,String date,int sup_id,String status){
        this.id=id;
        this.name=name;
        this.qty=qty;
        this.date=date;
        this.sup_id=sup_id;
        this.status=status;
    }
    public int getId(){return this.id;}
    public String getName() {return this.name;}
    public double getQty() {return this.qty;}
    public String getDate() {return this.date;}
    public double getSup_id() {return this.sup_id;}
    public String getStatus() {return this.status;}

    public void setId(int id){this.id=id;}
    public void setName(String name) {this.name=name;}
    public void setQty(double qty) {this.qty=qty;}
    public void setDate(String date) {this.date=date;}
    public void setSup_id(int sup_id) {this.sup_id=sup_id;}
    public void setStatus(String status) {this.status=status;}
}
