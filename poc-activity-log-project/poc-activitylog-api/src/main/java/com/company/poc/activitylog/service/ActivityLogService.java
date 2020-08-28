package com.company.poc.activitylog.service;

import com.company.poc.activitylog.model.Request;
import com.company.poc.activitylog.model.ResponseCorrelation;
import com.company.poc.activitylog.model.ResponseProcessVariables;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogService {

  private static String path = "/Users/fanida/Documents/CLOUD-RIVER/PROJECTS/camunda-learning-project/poc-activity-log-project/logs/activity-logs-";

  /**
   * @param request
   */
//  public void startLogging(Request request) {
//
//    LocalDateTime twoSecondsLater = LocalDateTime.now().plusSeconds(10);
//    Runnable runnableTask = () -> {
//      try {
//        while (twoSecondsLater.isAfter(LocalDateTime.now())) {
//          String out = request.getValue().concat(":").concat(String.valueOf(LocalDateTime.now()))
//              .concat("\r\n");
//          Files.write(Paths.get(path.concat(request.getTask()).concat(".log")), (out).getBytes(StandardCharsets.UTF_8),
//              StandardOpenOption.APPEND);
//        }
//
//      } catch (Exception e) {
//        System.out.println("startLogging > error writing > :" + e.getStackTrace());
//      } finally {
//        try {
//          correlateMessage(buildCorrelateMessage(request.getBusinessKey(), request.getTask()));
//        } catch (Exception e) {
//          System.out.println("writeLog > error correlation > :" + e.getStackTrace());
//        }
//      }
//    };
//
//    Executors.newSingleThreadExecutor().execute(runnableTask);
//  }

  public void    startLogging(Request request) {
    try {
    String out = request.getValue().concat(":").concat(String.valueOf(LocalDateTime.now()))
        .concat("\r\n");
    Files.write(Paths.get(path.concat(request.getTask()).concat(".log")), (out).getBytes(StandardCharsets.UTF_8),
        StandardOpenOption.APPEND);

    }
    catch (Exception e) {
      System.out.println("startLogging > error writing > :" + e.getStackTrace());
    } finally {
      try {
        correlateMessage(buildCorrelateMessage(request.getBusinessKey(), request.getTask()));
      } catch (Exception e) {
        System.out.println("writeLog > error correlation > :" + e.getStackTrace());
      }
    }

    }

  /**
   * @param body
   * @return
   * @throws IOException
   * @throws URISyntaxException
   */
  public CompletableFuture<Void> correlateMessage(final ResponseCorrelation body)
      throws IOException, URISyntaxException {
    String requestBody = new ObjectMapper().writerWithDefaultPrettyPrinter()
        .writeValueAsString(body);

    System.out.println("request body> :" + requestBody);

    HttpRequest request = HttpRequest
        .newBuilder(new URI("http://localhost:8085/engine-rest/message"))
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(requestBody))
        .build();

    return HttpClient.newHttpClient()
        .sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::statusCode)
        .thenAccept(System.out::println);

  }

  /**
   * @param businessKey
   * @param taskId
   * @return
   */
  private ResponseCorrelation buildCorrelateMessage(final String businessKey, final String taskId) {
    ResponseCorrelation response = new ResponseCorrelation(businessKey,
        businessKey.concat("-").concat(taskId));
    response.getProcessVariables().put("var1", new ResponseProcessVariables("success", "String"));
    return response;
  }

}
