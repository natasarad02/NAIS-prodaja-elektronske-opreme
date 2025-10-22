package ticket_service.com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.serviceticket.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.host:localhost}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port:9200}")
    private int elasticsearchPort;

    @Value("${elasticsearch.username:}")
    private String username;

    @Value("${elasticsearch.password:}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            return ClientConfiguration.builder()
                    .connectedTo(elasticsearchHost + ":" + elasticsearchPort)
                    .withBasicAuth(username, password)
                    .build();
        }
        
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchHost + ":" + elasticsearchPort)
                .build();
    }
}
