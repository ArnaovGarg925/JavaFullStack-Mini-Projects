package com.campus.lostfound;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class AppServiceTest {
    @Test
    void registrationAndLoginWork() throws Exception {
        Path items = Files.createTempFile("items", ".txt");
        Path users = Files.createTempFile("users", ".txt");

        AppService service = new AppService(
            new FileRepository(items.toString()),
            new UserRepository(users.toString())
        );

        User user = service.register("Test User", "test@example.com", "1234");
        assertNotNull(user);
        assertEquals("Test User",
            service.login("test@example.com", "1234").getName());
    }
}
