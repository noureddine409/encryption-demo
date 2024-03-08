package com.adria.dbencryption.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class DatabaseInitializer {

    private final DataSource dataSource;

    @Value("${spring.application.name}")
    private String authorizedAppName;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() throws IOException {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        // Read the contents of the SQL script file and replace the placeholder
        StringBuilder sqlScriptContentBuilder = new StringBuilder();
        try (var lines = Files.lines(Paths.get("src/main/resources/data/disable-direct-updates.sql"), StandardCharsets.UTF_8)) {
            lines.map(line -> line.replace("//YOUR_APPLICATION_NAME//", authorizedAppName))
                    .forEach(line -> sqlScriptContentBuilder.append(line).append("\n"));
        }

        // Create a Resource object with the modified SQL script content
        ByteArrayResource resource = new ByteArrayResource(sqlScriptContentBuilder.toString().getBytes(StandardCharsets.UTF_8));

        // Set the modified SQL script content to the ResourceDatabasePopulator
        populator.addScript(resource);

        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
