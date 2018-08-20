package ravi.developer.com.delendemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable{
    String orderID, serviceType, date, charges, serviceRequired, status, serviceManName, dp, address,documentId;


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Order(String orderID, String serviceType, String date, String charges, String serviceRequired, String status, String serviceManName, String dp, String address , String documentID) {
        this.orderID = orderID;
        this.serviceType = serviceType;
        this.date = date;
        this.charges = charges;
        this.serviceRequired = serviceRequired;
        this.status = status;

        this.serviceManName = serviceManName;
        this.dp = dp;
        this.address = address;
        this.documentId = documentID;
    }


    //parcel part
    public Order(Parcel in){
        String[] data= new String[10];

        in.readStringArray(data);
        this.orderID= data[0];
        this.serviceType= data[1];
        this.date= data[2];
        this.charges= data[3];
        this.serviceRequired= data[4];
        this.status= data[5];
        this.serviceManName= data[6];
        this.dp= data[7];
        this.address= data[8];
        this.documentId = data[9];

    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getServiceRequired() {
        return serviceRequired;
    }

    public void setServiceRequired(String serviceRequired) {
        this.serviceRequired = serviceRequired;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceManName() {
        return serviceManName;
    }

    public void setServiceManName(String serviceManName) {
        this.serviceManName = serviceManName;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.orderID,this.serviceType,this.date,this.charges,this.serviceRequired,this.status,this.serviceManName,this.dp,this.address,this.documentId});
    }

    public static final Parcelable.Creator<Order> CREATOR= new Parcelable.Creator<Order>() {

        @Override
        public Order createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new Order(source);  //using parcelable constructor
        }

        @Override
        public Order[] newArray(int size) {
// TODO Auto-generated method stub
            return new Order[size];
        }
    };

}
