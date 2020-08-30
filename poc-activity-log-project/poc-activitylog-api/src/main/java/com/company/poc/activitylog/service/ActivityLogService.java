package com.company.poc.activitylog.service;

import com.company.poc.activitylog.model.ApiRequest;
import com.company.poc.activitylog.model.ProcessRequestCorrelation;
import com.company.poc.activitylog.model.ProcessRequestVariables;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogService {

  private static Logger LOGGER = LoggerFactory.getLogger(ActivityLogService.class);

  @Value("${process.uri}")
  private String processUri ;

  @Value("${log.filePath}")
  private String filePath ;

  /**
   * @param request
   */
  public void startLogging(ApiRequest request) {

    LocalDateTime twoSecondsLater = LocalDateTime.now().plusSeconds(10);
    Runnable runnableTask = () -> {
      try {
        while (twoSecondsLater.isAfter(LocalDateTime.now())) {
          String out = request.getValue().concat(":").concat(String.valueOf(LocalDateTime.now()))
              .concat("\r\n");
          Files.write(Paths.get(filePath.concat(request.getTask()).concat(".log")), (out).getBytes(StandardCharsets.UTF_8),
              StandardOpenOption.APPEND);
        }

      } catch (Exception e) {
        LOGGER.error("startLogging > error writing > : {}" , e.getStackTrace());
      } finally {
        try {
          correlateMessage(buildCorrelateMessage(request.getBusinessKey(), request.getTask()));
        } catch (Exception e) {
          System.out.println("writeLog > error correlation > :" + e.getStackTrace());
        }
      }
    };

    Executors.newFixedThreadPool(10).execute(runnableTask);
  }


  /**
   * @param body
   * @return
   * @throws IOException
   * @throws URISyntaxException
   */
  public void correlateMessage(final ProcessRequestCorrelation body)
      throws IOException, URISyntaxException, InterruptedException {
    String requestBody = new ObjectMapper().writerWithDefaultPrettyPrinter()
        .writeValueAsString(body);

    LOGGER.info("correlateMessage > request > : {}" ,  new ObjectMapper().writerWithDefaultPrettyPrinter()
        .writeValueAsString(body));

    HttpRequest request = HttpRequest
        .newBuilder(new URI(processUri))
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(requestBody))
        .build();

    HttpResponse<String> response  = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
    LOGGER.info("correlateMessage > response > : {}" , response);

  }



  /**
   * @param businessKey
   * @param taskId
   * @return
   */
  private ProcessRequestCorrelation buildCorrelateMessage(final String businessKey, final String taskId) {
    ProcessRequestCorrelation response = new ProcessRequestCorrelation(businessKey,
        businessKey.concat("-").concat(taskId));
    response.getProcessVariables().put("var1", new ProcessRequestVariables("success", "String"));
    return response;
  }

}
