package com.ashwin.android.retrofitjsonfield.model;

public class POST_PHOTO {

    private String RESULT;

    // Approach 1: Use HashMap
    //private Map<String, Object> address;

    // Approach 2: Use GsonDeserializer
    private String code;

    public POST_PHOTO() {
    }

    public String getId() {
        return RESULT;
    }

    public void setId(String RESULT) {
        this.RESULT = RESULT;
    }




    /*public Map<String, Object> getAddress() {
        return address;
    }*/

    public String getAddress() {
        return code;
    }

    /*public void setAddress(Map<String, Object> address) {
        this.address = address;
    }*/

    public void setAddress(String address) {
        this.code = address;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "RESULT='" + RESULT + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
