package com.lin.apiclientsdk;

import com.lin.apiclientsdk.client.ApiClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = "com.lin.apiclientsdk")
public class ApiClientConfig {

    private final ApiClientProperties properties;

    public ApiClientConfig(ApiClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient(properties);
    }
}