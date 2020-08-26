package com.company.poc.activitylog.controller;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorrelationController {

  final RuntimeService runtimeService;

  public CorrelationController(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @GetMapping("/activity/{businessKey}")
  public void correlate(@PathVariable final String businessKey){
    runtimeService.createMessageCorrelation(businessKey).processInstanceBusinessKey(businessKey).correlateAll();
  }

}
