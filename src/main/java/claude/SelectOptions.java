package claude;

import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class SelectOptions {
    @Builder.Default
    private List<String> columns = new ArrayList<>();
    @Builder.Default
    private List<JoinClause> joins = new ArrayList<>();
    @Builder.Default
    private Map<String, Object> criteria = new HashMap<>();
    @Builder.Default
    private Map<String, Object> params = new HashMap<>();
    @Builder.Default
    private List<WhereClause> whereClauses = new LinkedList<>();


}

 class JoinClause {
    private String type;
    private String table;
    private String condition;

    public JoinClause(String type, String table, String condition) {
        this.type = type;
        this.table = table;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return type + " JOIN " + table + " ON " + condition;
    }
}