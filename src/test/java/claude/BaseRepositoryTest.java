package claude;

import com.mycompany.metamodel.DataSource;
import com.mycompany.metamodel.MetamodelApplication;
import com.mycompany.metamodel.MetamodelLoader;
import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.testdomain.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MetamodelApplication.class})
class BaseRepositoryTest {
    @BeforeAll
    static void setUp() {
        MetamodelLoader loader = new MetamodelLoader();
        loader.load();
        DomainModel domainModel=DomainModel.getInstance();
        HikariDataSource dataSource = DataSource.ds; // Implement this method
    }

    @Autowired
    private BaseRepository baseRepository;

    @Test
    void findById() {
        Customer oneCustomer = baseRepository.findById(Customer.class,"123");
        assertNotNull(oneCustomer);
    }

    @Test
    void findByIdNotPresent() {
        Customer oneCustomer = baseRepository.findById(Customer.class,"123treeeefffffffffffffffffff");
        assertNull(oneCustomer);
    }
    @Test
    void findAll() {
        List<Customer> allCustomers = baseRepository.findAll(Customer.class);
        assertNotNull(allCustomers);
    }

    @Test
    void findByWhere() {
        WhereClause whereOptions = WhereClause.builder()
                .attribute("name")
                .value("John")
                .operation("=")
                .build();

        List<Customer> allCustomers = baseRepository.findAll(Customer.class,
                Arrays.asList(whereOptions));
        assertNotNull(allCustomers);
    }
}