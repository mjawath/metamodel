package claude;

import com.mycompany.metamodel.DataSource;
import com.mycompany.metamodel.pojo.DomainModel;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseRepoConfiguration {

    @Bean
    public BaseRepository getBaseRepository() {
        HikariDataSource dataSource = DataSource.ds; // Implement this method
        DomainModel instance = DomainModel.getInstance();
        BaseRepository baseRepository = new BaseRepository(DataSource.ds, instance);
        baseRepository.setExecutor(new Executor(dataSource,instance));
        return baseRepository;
    }

}
