package uk.gov.dwp.cis.housingbenefit.poc.mutualtooauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HousingBenefitController {

    private static final Logger log = LogManager.getLogger();

    @GetMapping(value = "/get")
    public String getClaim() {
        log.info("getClaim resource called");
        return "Get Worked!";
    }

    @PostMapping(value = "/post", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String postClaim(@RequestBody String requestBody) {
        log.info("postClaim resource called with request body: {}", requestBody);
        return "Post Worked!";
    }
}

