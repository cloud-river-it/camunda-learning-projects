package com.company.poc.activitylog.model;

import java.util.HashMap;
import java.util.Map;

public class ProcessRequestCorrelation {

  private String messageName;
  private String businessKey;
  Map<String, ProcessRequestVariables> processVariables =  new HashMap<>();


  public ProcessRequestCorrelation(){

  }

  public ProcessRequestCorrelation(String businessKey, String messageName) {
    this.messageName = messageName;
    this.businessKey = businessKey;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  public Map<String, ProcessRequestVariables> getProcessVariables() {
    return processVariables;
  }

  public void setProcessVariables(
      Map<String, ProcessRequestVariables> processVariables) {
    this.processVariables = processVariables;
  }
}
