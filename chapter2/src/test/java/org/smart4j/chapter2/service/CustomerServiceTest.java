package org.smart4j.chapter2.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by jing_xu on 2017/7/4.
 * CustomerService单元测试
 */
public class CustomerServiceTest {
    private  final CustomerService customerService;

    public CustomerServiceTest(){
        customerService = new CustomerService();
    }
    @Before
    public void init(){

    }

    @Test
    public void getCustomerList() throws Exception {
        List<Customer> customerList = customerService.getCustomerList("");
        Assert.assertEquals(3,customerList.size());
    }

    @Test
    public void getCustomer() throws Exception {
        long id =1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomer() throws Exception {
        Map<String,Object> filedMap = new HashMap<String, Object>();
        filedMap.put("name","kuangwei");
        filedMap.put("contact","John");
        filedMap.put("telephone","13811115555");
        boolean result = customerService.createCustomer(filedMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomer() throws Exception {
        Map<String,Object> filedMap = new HashMap<String, Object>();
        filedMap.put("contact","Lily");
        boolean result = customerService.updateCustomer(1,filedMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomer() throws Exception {
        boolean result = customerService.deleteCustomer(1);
        Assert.assertTrue(result);
    }

}