package com.mycompany.metamodel.testdomain;

import com.mycompany.metamodel.pojo.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Order extends BaseEntity<String> {
    private LocalDate orderDate;
    private BigDecimal total;
    // Other fields and methods specific to Order entity
}
