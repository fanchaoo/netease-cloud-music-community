
package me.fanchaoo.web.config.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

public class DateTimeTypeHandler extends BaseTypeHandler<DateTime> {
    public DateTimeTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, DateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new Timestamp(parameter.getMillis()));
    }

    public DateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        return sqlTimestamp != null ? new DateTime(sqlTimestamp.getTime()) : null;
    }

    public DateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
        return sqlTimestamp != null ? new DateTime(sqlTimestamp.getTime()) : null;
    }

    public DateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
        return sqlTimestamp != null ? new DateTime(sqlTimestamp.getTime()) : null;
    }
}
