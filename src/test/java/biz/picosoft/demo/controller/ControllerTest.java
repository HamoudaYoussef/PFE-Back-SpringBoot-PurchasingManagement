package biz.picosoft.demo.controller;

import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.global.AuthUser;
import biz.picosoft.demo.config.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class ControllerTest {

    private static final String ENTITY_API_URL = "/build-number";

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;

    @Autowired
    private KernelService kernelService;

    @Autowired
    private WebTestClient webTestClient;

    private String token;


    @BeforeEach
    void getToken() {
        AuthUser authUser = new AuthUser(login, password);
        token = kernelService.Authorize(authUser).split(" ")[1].trim();
    }

    @Test
    void getBuildNumber() {
        webTestClient
                .get()
                .uri(ENTITY_API_URL)
                .headers(http -> http.setBearerAuth(token))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }
}