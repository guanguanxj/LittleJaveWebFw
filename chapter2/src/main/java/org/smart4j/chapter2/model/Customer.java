package org.smart4j.chapter2.model;

/**
 * Created by jing_xu on 2017/7/4.
 */
public class Customer {
    private  long id;
    private String name;
    private  String contract;
    private  String telephone;
    private String email;
    private String remark;

    public Customer(){}
    public Customer(long id, String name, String contract, String telephone, String email, String remark) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.telephone = telephone;
        this.email = email;
        this.remark = remark;
    }

    public long getId() {
        return id;

    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



}
