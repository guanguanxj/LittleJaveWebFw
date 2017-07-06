package org.smart4j.chapter2.helper;


import com.sun.org.apache.bcel.internal.generic.VariableLengthInstruction;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.ColletionUtils;
import org.smart4j.chapter2.util.PropsUtil;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jing_xu on 2017/7/5.
 * 数据库操作助手
 */
public final class DatabaseHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);

    //确保一个线程中只有一个Connection
    private  static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final String DRIVER;
    private static final String URL;
    private static final String PASSWORD;
    private static final String USERNAME;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn==null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("GET CONNECTION FAILURE", e);
                throw new RuntimeException(e);
            }
            finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return  conn;
    }
    /**
     * 关闭数据库连接
     * @return
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn!=null)
        {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("CLOSE CONNECTION FAILURE",e);
                throw new RuntimeException(e);
            }
            finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        List<T> entityList =null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
        return  entityList;
    }
    /**
     * 查询单个实体
     */
    public  static  <T> T queryEntity(Class<T> entityClass,String sql, Object... params){
        T entity = null;
        Connection conn = getConnection();
        try {
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询语句
     */
    public  static List<Map<String, Object>> executeQuery(String sql,Object... params){
        List<Map<String, Object>> result ;
        Connection conn = getConnection();
        try {
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("executeQuery failure",e);
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 执行sql语句(insert,update,delete)
     */
    public  static int executeUpdate(String sql,Object... params){
        int rows =0;
        Connection conn = getConnection();
        try {
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("executeUpdate failure",e);
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> filedMap){
        boolean result = false;
        if(ColletionUtils.isEmpty(filedMap)){
            LOGGER.error("can not insert entity: filemap is empty");
            return  result;
        }
        String sql = "INSERT INTO "+ getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String filedName : filedMap.keySet())
        {
            columns.append(filedName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql = sql + columns +" VALUES "+ values;

        Object[] params = filedMap.values().toArray();
        result = executeUpdate(sql,params) == 1;
        return result;
    }

    /**
     * 更新实体
     *
     */
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(ColletionUtils.isEmpty(fieldMap)){
            LOGGER.error("can not update entity: fieldMap is empty");
            return false;
        }
        String sql = "UPDATE "+ getTableName(entityClass) + "SET ";
        StringBuilder columns = new StringBuilder();
        for(String fieldName:fieldMap.keySet())
        {
            columns.append(fieldName+"=?,");
        }
        sql += columns.replace(columns.lastIndexOf(","),columns.length(),")") + " WHERE id =?";
        List<Object> paramslist = new ArrayList<Object>();
        paramslist.addAll(fieldMap.values());
        paramslist.add(id);
        Object[] params = paramslist.toArray();
        return  executeUpdate(sql,params) ==1 ;
    }

    /**
     * 删除实体
     */
    public  static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql = "DELETE FROM " + getTableName(entityClass) +" WHERE ID=?";
        return executeUpdate(sql,id)==1;
    }

    private  static  String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }
}
