package claude;

import com.mycompany.metamodel.DataSource;
import com.mycompany.metamodel.MetamodelLoader;
import com.mycompany.metamodel.pojo.DomainModel;

import com.mycompany.metamodel.testdomain.Customer;
import com.zaxxer.hikari.HikariDataSource;

import java.util.*;

public class Main {



    public static void main(String[] args) {
        MetamodelLoader loader = new MetamodelLoader();
        loader.load();
        DomainModel domainModel=DomainModel.getInstance();
        HikariDataSource dataSource = DataSource.ds; // Implement this method
        Executor executor = new Executor(dataSource, domainModel);
        PersistentRehydrator rehydrator = new PersistentRehydrator(domainModel);

        // Create a JSON-compatible object
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("id", "John Doe"+new  Random().nextInt());
        customerData.put("name", "John Doe");
        customerData.put("email", "john@example.com");

        // Persist the object
        executor.persist(customerData, "customer");

        // Update the object
        customerData.put("email", "johndoe@example.com");
        executor.update(customerData, "customer", "id");



        // Query as before
        QueryBuilder queryBuilder = new QueryBuilder(domainModel, "customer");
        queryBuilder.where("email", "=", "johndoe@example.com");

        List<Customer> customers = new ArrayList<>();//executor.executeQuery(queryBuilder, "customer");

        // Update the object
        customerData.put("email", "johndoe@example.com");
        executor.update(customerData, "customer", "id");

        // Query as before
        queryBuilder.where("email", "=", "johndoe@example.com");

        SelectOptions options = new SelectOptions.Builder()
                .columns("id", "name", "email")
//                .join(new JoinClause("LEFT", "orders", "customer.id = orders.customer_id"))
                .criteria("name", "John Doe")
                .build();
         customers = rehydrator.persistanceToData(executor.executeQuery( "customer",options), "customer");
        for (Customer customer : customers) {
            System.out.println(customer.getName() + ": " + customer.getEmail());
        }
    }

}
