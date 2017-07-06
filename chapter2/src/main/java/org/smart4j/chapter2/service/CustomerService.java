package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jing_xu on 2017/7/4.
 * 提供数据服务
 * 《1》DriverManager需要使用大量静态变量，改为新建一个DatabaseHelper包含一些静态方法，封装数据库的相关操作。
 * 《2》每个方法都需要开启/关闭数据库链接，如何隐藏掉呢。方法参见《3》
 * 《3》确保一个线程中只有一个Connection，使用ThreadLoacl来存放本地线程变量，修改在DatabaseHelper中
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

//    private static final String DRIVER;
//    private static final String URL;
//    private static final String PASSWORD;
//    private static final String USERNAME;
//
//    static {
//        Properties conf = PropsUtil.loadProps("config.properties");
//        DRIVER = conf.getProperty("jdbc.driver");
//        URL = conf.getProperty("jdbc.url");
//        USERNAME = conf.getProperty("jdbc.username");
//        PASSWORD = conf.getProperty("jdbc.password");
//
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("can not load jdbc driver", e);
//        }
//    } 《1》

    public List<Customer> getCustomerList(String keyword) {
       // Connection conn = null;
        try {
            //conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); 《1》
            // PreparedStatement statement = conn.prepareStatement(sql);《1》
            // ResultSet result = statement.executeQuery();《1》

            //conn = DatabaseHelper.getConnection();//《2》
            String sql = "SELECT * FROM customer";
            List<Customer> customerList = DatabaseHelper.queryEntityList(Customer.class, sql);
            return customerList;
        } finally {
            DatabaseHelper.closeConnection();
        }
    }

    public Customer getCustomer(long id) {
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return false;
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return false;
    }

    public boolean deleteCustomer(long id) {
        return false;
    }
}
