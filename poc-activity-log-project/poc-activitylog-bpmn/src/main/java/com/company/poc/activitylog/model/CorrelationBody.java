package com.company.poc.activitylog.model;

import java.util.Map;

public class CorrelationBody {

  private String businessKey;
  private String messageName;
  private Map<String, CorrelationBodyVars> processVariables;

  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public Map<String, CorrelationBodyVars> getProcessVariables() {
    return processVariables;
  }

  public void setProcessVariables(Map<String, CorrelationBodyVars> processVariables) {
    this.processVariables = processVariables;
  }



}

class CorrelationBodyVars {

  String value ;
  String type ;

  public CorrelationBodyVars() {

  }

  public CorrelationBodyVars(String value, String type) {
    this.value = value;
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}