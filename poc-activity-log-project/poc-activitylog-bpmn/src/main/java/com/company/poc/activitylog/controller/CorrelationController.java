package com.company.poc.activitylog.controller;

import com.company.poc.activitylog.model.CorrelationBody;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.OptimisticLockingException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorrelationController {

  final RuntimeService runtimeService;

  public CorrelationController(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @PostMapping("/process/correlation")
  public MessageCorrelationResult correlate(@RequestBody final CorrelationBody correlationBody) {
    return this.correlateWithException(correlationBody);
  }

  private MessageCorrelationResult correlateWithException(final CorrelationBody correlationBody) {
    try {
     return runtimeService
          .createMessageCorrelation(correlationBody.getMessageName())
          .processInstanceBusinessKey(correlationBody.getBusinessKey())
          .correlateWithResult();
    } catch (OptimisticLockingException e) {
     return runtimeService
          .createMessageCorrelation(correlationBody.getMessageName())
          .processInstanceBusinessKey(correlationBody.getBusinessKey())
          .correlateWithResult();
    }
  }

}
