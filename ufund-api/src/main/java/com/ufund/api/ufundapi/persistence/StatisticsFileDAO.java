package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Statistics;
@Component
public class StatisticsFileDAO implements StatisticsDAO{
    private static final Logger LOG = Logger.getLogger(StatisticsFileDAO.class.getName());
    private String filename;
    private ObjectMapper objectMapper;
    private Statistics statistics;
            
            
    public StatisticsFileDAO(@Value("${statistics.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    


    private boolean save() throws IOException {
        // Serializes the Statistics object to JSON and writes to the file
        objectMapper.writeValue(new File(filename), this.statistics);
        return true;
    }

   
    private boolean load() throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            this.statistics = objectMapper.readValue(file, Statistics.class);
        } else {
            this.statistics = new Statistics(0,0);  
            
        }
        return true;
    }
    
    @Override
    public void updateRevenue(double num) throws IOException {
        this.statistics.addTotalRevenue(num); 
        save();  
    }

    @Override
    public  void updateNumSold(int num) throws IOException {
        this.statistics.addTotalItemsSold(num); 
        save();  
    }

    @Override
    public double getRevenue() throws IOException {
        return this.statistics.getTotalRevenue(); 
    } 
    @Override
    public int getNumSold() throws IOException {
        return this.statistics.getTotalItemsSold();  
    }
    
}
