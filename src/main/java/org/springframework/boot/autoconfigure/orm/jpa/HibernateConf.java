package org.springframework.boot.autoconfigure.orm.jpa;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@Configuration
public class HibernateConf extends HibernateJpaConfiguration {

    HibernateConf(DataSource dataSource, JpaProperties jpaProperties, ConfigurableListableBeanFactory beanFactory, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers, HibernateProperties hibernateProperties, ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders, ObjectProvider<SchemaManagementProvider> providers, ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy, ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy, ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        super(dataSource, jpaProperties, beanFactory, jtaTransactionManager, transactionManagerCustomizers, hibernateProperties, metadataProviders, providers, physicalNamingStrategy, implicitNamingStrategy, hibernatePropertiesCustomizers);
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        Map<String, Object> vendorProperties = getVendorProperties();
        customizeVendorProperties(vendorProperties);
        Properties properties = new Properties();
        properties.putAll(vendorProperties);
        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages(getPackagesToScan())
                .addProperties(properties).buildSessionFactory();
    }

}
