package utils.db;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {
    ConnectDB db = new ConnectDB();

    /*Select model will fetch all the data related to one table and store it in specified model*/
    public static <T, E> List<T> selectModel(Class<T> type, String db, String table, String[] field,
                                      String[] where, E[] where_val) throws Exception {
        java.sql.Connection conn = null;
        List<T> data = new ArrayList<>();
        try {
            String query = select(db, table, field, where, where_val);

            conn = ConnectDB.createConnection();
            java.sql.Statement stmt = conn.createStatement();
            stmt.execute("use " + db);
            ResultSet r = stmt.executeQuery(query);

            ResultSetMetaData rmd = r.getMetaData();
            int columnCount = rmd.getColumnCount();

            while (r.next()) {
                T t = type.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String colName = rmd.getColumnName(i).toLowerCase();
                    BeanUtils.setProperty(t, colName, r.getObject(i));
                }
                data.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public static <T> String select(String db, String table, String[] field, String[] where, T[] where_val) throws Exception {

        int select_length = 0;

        int where_length = 0, where_val_length = 0;
        if (where != null) {
            where_length = where.length;
        }
        if (where_val != null) {
            where_val_length = where_val.length;
        }
        if (field != null) {
            select_length = field.length;
        }

        String selectQuery = null;
        if (db == null || table == null) {

            throw new Exception("Table or Database name null");

        } else {
            if (field != null) {
                select_length = field.length;
                selectQuery = "Select ";
            } else {
                selectQuery = "Select * ";
            }

            if (where_length != where_val_length) {

                throw new Exception("Where array length is not equal to Where_val array length");
            } else {

                String select_clause = "";
                String where_clause = "";

                if (where_val != null && where != null) {
                    if (where_val[0] instanceof Integer || where_val[0] instanceof Double
                            || where_val[0] instanceof Float || where_val[0] instanceof Short) {
                        for (int i = 0; i < where.length; i++) {
                            if (i == where.length - 1)
                                where_clause = where_clause + where[i] + " = " + where_val[i] + ";";
                            else
                                where_clause = where_clause + where[i] + " = " + where_val[i] + " and ";
                        }
                    } else {
                        for (int i = 0; i < where.length; i++) {
                            if (i == where.length - 1)
                                where_clause = where_clause + where[i] + " = '" + where_val[i] + "';";
                            else
                                where_clause = where_clause + where[i] + " = '" + where_val[i] + "' and ";
                        }
                    }
                }
                for (int i = 0; i < select_length; i++) {
                    if (i == select_length - 1)
                        select_clause = select_clause + field[i];
                    else
                        select_clause = select_clause + field[i] + " , ";
                }
                if (where != null && where_val != null)
                    selectQuery = selectQuery + select_clause + " From " + db + "." + table + " where " + where_clause;
                else
                    selectQuery = selectQuery + select_clause + " From " + db + "." + table;
            }
        }
        System.out.println(selectQuery);
        return selectQuery;
    }


    /* Execute update query on a db table */
    public static void execute_updatequery(String db, String query) throws Exception {
        System.out.println("update query : "+query);
        java.sql.Connection conn =ConnectDB.createConnection();
        java.sql.Statement stmt = conn.createStatement();
        stmt.executeQuery("use " + db);
        stmt.executeUpdate(query);
    }

    /* Execute insert query on a db table*/
    public static void execute_insertquery(String db, String query) throws Exception {
        System.out.println("update query : "+query);
        java.sql.Connection conn =ConnectDB.createConnection();
        java.sql.Statement stmt = conn.createStatement();
        stmt.executeQuery("use " + db);
        stmt.executeQuery(query);
    }


}
