package uk.gov.dwp.cis.housingbenefit.poc.mutualtooauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class HousingBenefitController {

    private static final Logger log = LogManager.getLogger();

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/get")
    public String getClaim(Principal principal) {
        log.info("getClaim resource called");

        String user = principal.getName();
        log.info("getClaim principal: {}", user);

        return "Get Worked!";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(value = "/post", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String postClaim(@RequestBody String requestBody, Principal principal) {
        log.info("postClaim resource called with request body: {}", requestBody);

        String user = principal.getName();
        log.info("getClaim principal: {}", user);
        return "Post Worked!";
    }
}

