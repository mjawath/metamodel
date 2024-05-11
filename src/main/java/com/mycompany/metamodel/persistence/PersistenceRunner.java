package com.mycompany.metamodel.persistence;

import com.mycompany.metamodel.pojo.BuilderResult;
import com.mycompany.metamodel.pojo.PersistenceResult;

public interface PersistenceRunner {
    public PersistenceResult insert(BuilderResult object);

}
