package com.company.poc.activitylog.model;


public class Request {

  private String businessKey;

  private String task;

  private String value;


  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
