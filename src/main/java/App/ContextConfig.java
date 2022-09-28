package App;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class ContextConfig {

    @Bean
    public ConfiguraionManager configuraionManager() {
        return new ConfiguraionManager();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(configuraionManager().getProperty("db.local.url"));
        dataSource.setUsername(configuraionManager().getProperty("db.local.user"));
        dataSource.setPassword(configuraionManager().getProperty("db.local.password"));
        dataSource.setDriverClassName(configuraionManager().getProperty("db.local.driver"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
