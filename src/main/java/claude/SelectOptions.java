package claude;

import lombok.Data;

import java.util.*;

@Data
public class SelectOptions {
    private List<String> columns = new ArrayList<>();
    private List<JoinClause> joins = new ArrayList<>();
    private Map<String, Object> criteria = new HashMap<>();

    // Getters and setters

    public static class Builder {
        private SelectOptions options = new SelectOptions();

        public Builder columns(String... cols) {
            options.columns.addAll(Arrays.asList(cols));
            return this;
        }

        public Builder join(JoinClause join) {
            options.joins.add(join);
            return this;
        }

        public Builder criteria(String key, Object value) {
            options.criteria.put(key, value);
            return this;
        }

        public SelectOptions build() {
            return options;
        }
    }
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