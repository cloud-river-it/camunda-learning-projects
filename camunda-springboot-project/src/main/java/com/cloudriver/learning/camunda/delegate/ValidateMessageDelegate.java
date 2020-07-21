package com.cloudriver.learning.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ValidateMessageDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) {
    delegateExecution.setVariable("payload", "payload" );
  }
}
