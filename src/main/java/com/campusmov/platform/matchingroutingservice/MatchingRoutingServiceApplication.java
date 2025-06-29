package com.campusmov.platform.matchingroutingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableNeo4jAuditing
@EnableJpaRepositories
@EnableNeo4jRepositories
public class MatchingRoutingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchingRoutingServiceApplication.class, args);
    }

}
