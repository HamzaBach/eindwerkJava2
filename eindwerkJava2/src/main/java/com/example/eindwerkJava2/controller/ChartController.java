package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ChartController {
    @Autowired
    private final ChartService chartService;

    public ChartController(ChartService chartService){
        this.chartService=chartService;
    }

    /*@GetMapping("/chart/pie")
    public String getPieChart(Model model) {
        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("2016", 147);
        graphData.put("2017", 1256);
        graphData.put("2018", 3856);
        graphData.put("2019", 19807);
        model.addAttribute("chartData", graphData);
        return "testchart";
    }*/
    @GetMapping("/chart/pie")
    public String getPieChart(Model model) {
        //Map<String, Integer> graphData = new TreeMap<>();
        LocalDateTime startTime = LocalDateTime.now().minusDays(30);
        Map<LocalDateTime,Double> graphData = chartService.getSalesChartFromDateToNow(startTime);



        /*graphData.put("2016", 147);
        graphData.put("2017", 1256);
        graphData.put("2018", 3856);
        graphData.put("2019", 19807);*/
        model.addAttribute("chartData", graphData);
        return "testchart";
    }
}
