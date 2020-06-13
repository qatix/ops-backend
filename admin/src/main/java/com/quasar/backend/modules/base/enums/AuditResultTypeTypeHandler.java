package com.quasar.backend.modules.base.enums;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class AuditResultTypeTypeHandler extends BaseTypeHandler<AuditResultType> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, AuditResultType auditResultType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, auditResultType.getVal());
    }

    @Override
    public AuditResultType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        log.info("type-handler1:{}", resultSet.getString(s));
        return AuditResultType.fromValue(resultSet.getString(s));
    }

    @Override
    public AuditResultType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        log.info("type-handler2:{}", resultSet.getString(i));
        return AuditResultType.fromValue(resultSet.getString(i));
    }

    @Override
    public AuditResultType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        log.info("type-handler3:{}", callableStatement.getString(i));
        return AuditResultType.fromValue(callableStatement.getString(i));
    }
}
