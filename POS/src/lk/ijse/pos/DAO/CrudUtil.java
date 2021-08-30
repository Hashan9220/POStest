package lk.ijse.pos.DAO;

import lk.ijse.pos.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    private static PreparedStatement getPreparedStatement(String sql, Object... params) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject((i + 1), params[i]);
        }
        return pstm;
    }

    public static boolean executeUpdate(String sql, Object... params) throws Exception {
        PreparedStatement pstm = getPreparedStatement(sql, params);
        return pstm.executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql, Object... params) throws Exception {
        PreparedStatement pstm = getPreparedStatement(sql, params);
        return pstm.executeQuery();
    }
}
