package com.company.poc.activitylog.model;


public class ProcessRequestVariables {

  String value ;
  String type ;

  public ProcessRequestVariables() {

  }

  public ProcessRequestVariables(String value, String type) {
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
