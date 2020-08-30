package com.company.poc.activitylog.controller;


import com.company.poc.activitylog.model.ApiRequest;
import com.company.poc.activitylog.model.ProcessRequestCorrelation;
import com.company.poc.activitylog.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityLogController {

  ActivityLogService logService;

  @Autowired
  public ActivityLogController(ActivityLogService logService){
    this.logService = logService;
  }

  @PostMapping("/activity/log")
  public ResponseEntity<ProcessRequestCorrelation> traceLog(@RequestBody ApiRequest request){
    logService.startLogging(request);
    return ResponseEntity.ok(new ProcessRequestCorrelation(request.getBusinessKey(), request.getTask()));
  }

}
