package claude;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private final ObjectDefinition objectDef;
    private final List<String> whereClauses = new ArrayList<>();
    private final List<Object> parameters = new ArrayList<>();

    public QueryBuilder(DomainModel config, String entityName) {
        this.objectDef = config.getObjectDefinition(entityName);
    }

    public QueryBuilder where(String propertyName, String operator, Object value) {
        PropertyDefinition prop = objectDef.getPropertyDefinition(propertyName);
        whereClauses.add(prop.getColumnName() + " " + operator + " ?");
        parameters.add(value);
        return this;
    }

    public String buildQuery() {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + objectDef.getTableName());
        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }
        return sql.toString();
    }

    public List<Object> getParameters() {
        return parameters;
    }
}