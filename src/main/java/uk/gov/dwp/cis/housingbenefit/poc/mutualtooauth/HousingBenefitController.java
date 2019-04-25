package uk.gov.dwp.cis.housingbenefit.poc.mutualtooauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;


@RestController
public class HousingBenefitController {

    private static final Logger log = LogManager.getLogger();

    @Value("${http.client.ssl.trust-store}")
    private Resource keyStore;

    @Value("${http.client.ssl.trust-store-password}")
    private String keyStorePassword;

    @Value("${kong.baseUrl}")
    private String serverBaseUrl;

    @Autowired
    private RestTemplate restClient;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/get")
    public String getClaim(Principal principal) {
        log.info("getClaim resource called");

        String user = principal.getName();
        log.info("getClaim principal: {}", user);

        log.info("getClaim calling downstream");
        String response = restClient.getForObject(serverBaseUrl+"/status", String.class);
        return "Get Worked!";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(value = "/post", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String postClaim(@RequestBody String requestBody, Principal principal) {
        log.info("postClaim resource called with request body: {}", requestBody);

        String user = principal.getName();
        log.info("postClaim principal: {}", user);
        return "Post Worked!";
    }


    @Bean
    @ConfigurationProperties("kong.oauth2.client")
    protected ClientCredentialsResourceDetails oAuthDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    protected RestTemplate restTemplate(@Autowired ClientCredentialsResourceDetails oAuthResourceDetails) {
        RestTemplate restTemplate = new OAuth2RestTemplate(oAuthResourceDetails);
        return restTemplate;
    }
}

