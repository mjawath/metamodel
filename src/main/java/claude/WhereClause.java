package claude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WhereClause {
    private String attribute;
    private Object value;
    private String operator;
    private String operation;

}