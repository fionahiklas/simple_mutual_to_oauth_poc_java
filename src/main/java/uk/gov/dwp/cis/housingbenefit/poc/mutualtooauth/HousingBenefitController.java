package uk.gov.dwp.cis.housingbenefit.poc.mutualtooauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class HousingBenefitController {

    private static final Logger log = LogManager.getLogger();

    @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String postClaim(@RequestBody String requestBody) {
        log.debug("postClaim resource called");
        return "Worked!";
    }
}

