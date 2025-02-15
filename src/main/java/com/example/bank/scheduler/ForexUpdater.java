//package com.example.bank.scheduler;
//
//import com.example.bank.dto.ForexResponse;
//import com.example.bank.service.ForexService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class ForexUpdater {
//
//    private final ForexService forexService;
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public ForexUpdater(ForexService forexService) {
//        this.forexService = forexService;
//        this.restTemplate = new RestTemplate(); // Alternatively, inject a shared RestTemplate bean
//    }
//
////    /**
////     * This cron expression "0 */5 * * * *" means every 5 minutes.
////            */
//@Scheduled(cron = "0 */5 * * * *")
//public void updateForexRates() {
//    String url = "https://api.exchangerate.host/latest?access_key=abca1c7325db958a3a6dd79407667674&base=EUR";
//    try {
//        ForexResponse response = restTemplate.getForObject(url, ForexResponse.class);
//        if (response != null && response.isSuccess() && response.getRates() != null) {
//            forexService.updateForexRates(response.getRates());
//            System.out.println("Forex rates updated successfully at " + response.getDate());
//        } else {
//            System.err.println("Failed to retrieve forex rates: response is null or unsuccessful.");
//        }
//    } catch (Exception e) {
//        System.err.println("Error updating forex rates: " + e.getMessage());
//    }
//}
//
//}
//
//
package com.example.bank.scheduler;

import com.example.bank.dto.ForexResponse;
import com.example.bank.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ForexUpdater {

    private final ForexService forexService;
    private final RestTemplate restTemplate;

    @Autowired
    public ForexUpdater(ForexService forexService) {
        this.forexService = forexService;
        this.restTemplate = new RestTemplate();
    }

    // initialDelay=0 makes the method run immediately on startup,
    // and fixedRate=3600000 makes it run every 1 hour (3600000 milliseconds)
    @Scheduled(initialDelay = 0, fixedRate = 3600000)
    public void updateForexRates() {
        String url = "https://api.exchangerate.host/latest?access_key=abca1c7325db958a3a6dd79407667674&base=EUR";
        try {
            ForexResponse response = restTemplate.getForObject(url, ForexResponse.class);
            if (response != null && response.isSuccess() && response.getRates() != null) {
                forexService.updateForexRates(response.getRates());
                System.out.println("Forex rates updated successfully at " + response.getDate());
            } else {
                System.err.println("Failed to retrieve forex rates: response is null or unsuccessful.");
            }
        } catch (Exception e) {
            System.err.println("Error updating forex rates: " + e.getMessage());
        }
    }
}
