package co.mastersindia.autotax.model;

/**
 * Created by Pandu on 9/2/2017.
 */

public class Sup_Model {
    private int id;
    private String type;
    private String gstin;
    private String bname;
    private String contact;
    private String mail;
    private String name;
    private String address;
    public Sup_Model(){

    }
    public Sup_Model(int id,String type,String gstin,String bname,String contact,String mail,String name,String address){
        this.id=id;
        this.type=type;
        this.gstin=gstin;
        this.bname=bname;
        this.contact=contact;
        this.mail=mail;
        this.name=name;
        this.address=address;
    }
    public int getId(){return this.id;}
    public String getType() {return this.type;}
    public String getGstin() {return this.gstin;}
    public String getBname() {return this.bname;}
    public String getContact() {return this.contact;}
    public String getMail() {return this.mail;}
    public String getName() {return this.name;}
    public String getAddress() {return this.address;}

    public void setId(int id){this.id=id;}
    public void setType(String type) {this.type=type;}
    public void setGstin(String gstin) {this.gstin=gstin;}
    public void setBname(String bname) {this.bname=bname;}
    public void setContact(String contact) {this.contact=contact;}
    public void setMail(String mail) {this.mail=mail;}
    public void setName(String name) {this.name=name;}
    public void setAddress(String address) {this.address=address;}
}
