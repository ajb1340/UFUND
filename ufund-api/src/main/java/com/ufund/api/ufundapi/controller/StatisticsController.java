package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.persistence.FundingBasketDAO;
import com.ufund.api.ufundapi.persistence.StatisticsDAO;

@RestController
@RequestMapping("stats")
public class StatisticsController {
    
private static final Logger LOG = Logger.getLogger(StatisticsController.class.getName());
    private StatisticsDAO statisticsDAO;
    
    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param FundingBasketDao The {@link FundingBasketDAO cuboard Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public StatisticsController(StatisticsDAO StatisticsDao) {
        this.statisticsDAO = StatisticsDao;
     
    }

    @PutMapping("updateStuff")
    public ResponseEntity<Object> update(@RequestBody double[] body) throws IOException {
        LOG.info("/update " + body);
        statisticsDAO.updateRevenue(body[0]);
        statisticsDAO.updateNumSold((int) body[1]); 
        
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }
  
}
