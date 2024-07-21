package claude;

import java.util.Map;

public class SQLStatement {
    private final String sql;
    private final Map<String, Object> parameters;

    public SQLStatement(String sql, Map<String, Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "SQLStatement{" +
                "sql='" + sql + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
