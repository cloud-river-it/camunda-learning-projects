package com.company.poc.activitylog.controller;


import com.company.poc.activitylog.model.Request;
import com.company.poc.activitylog.model.ResponseCorrelation;
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

  @PostMapping("/activity")
  public ResponseEntity<ResponseCorrelation> traceLog(@RequestBody Request request){
    logService.startLogging(request);
    return ResponseEntity.ok(new ResponseCorrelation(request.getBusinessKey(), request.getTask()));
  }

}
