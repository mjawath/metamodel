package claude;

import java.util.List;

public class RelationshipDefinition {
    private String type;
    private String targetEntity;
    private String mappedBy;
    private String joinColumn;
    private List<String> cascade;

    // ... getters and setters ...
}