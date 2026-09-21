package com.campus.lostfound;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Repository<Item> itemRepository() {
        return new FileRepository("data/items.txt");
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository("data/users.txt");
    }

    @Bean
    public AppService appService(Repository<Item> itemRepository,
                                 UserRepository userRepository) {
        return new AppService(itemRepository, userRepository);
    }
}
