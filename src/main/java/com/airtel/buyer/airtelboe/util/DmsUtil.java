package com.airtel.buyer.airtelboe.util;


import com.airtel.buyer.airtelboe.dto.document.request.DocumentRequest;
import com.airtel.buyer.airtelboe.dto.document.request.DocumentUrlRequest;
import com.airtel.buyer.airtelboe.dto.document.response.DocumentResponse;
import com.airtel.buyer.airtelboe.dto.document.response.DocumentUrlResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DmsUtil {

    private static String TRACE_HEADER = "X-App-Trace-Id";

    public static List<DocumentResponse> uploadDoc(List<String> docsPathList, String url, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {

        if (docsPathList != null && !docsPathList.isEmpty()) {

            List<DocumentRequest> documentRequestList = docsPathList.stream().map(path -> new DocumentRequest(path))
                    .collect(Collectors.toList());

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            ObjectMapper objectMapper = new ObjectMapper();

            List<DocumentResponse> documentResponseList = null;

            StringEntity input;
            try {

                String jsonStr = objectMapper.writeValueAsString(documentRequestList);

                log.info("Upload Document API Request : " + jsonStr);

                input = new StringEntity(jsonStr);
                httpPost.setEntity(input);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader(TRACE_HEADER, httpServletResponse.getHeader(TRACE_HEADER));
                httpPost.setHeader("Authorization", httpServletRequest.getHeader("Authorization"));
                httpPost.setHeader("Host", "partners.airtel.in");
                httpPost.setHeader("x-forwarded-host", "partners.airtel.in");

                HttpResponse response = httpclient.execute(httpPost);
                String result = EntityUtils.toString(response.getEntity());

                log.info("Upload Document API Response : " + result);

//                documentResponseList = objectMapper.readValue(result, DocumentResponse.class);

//                documentResponseList = objectMapper.convertValue(result, new TypeReference<List<DocumentResponse>>() { });

                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, DocumentResponse.class);
                documentResponseList = objectMapper.readValue(result, listType);

                log.info("Upload Document API Response : " + documentResponseList);

                return documentResponseList;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public static List<DocumentUrlResponse> fetchUrlFromContentId(List<DocumentUrlRequest> documentUrlRequestList, String url, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {

        if (documentUrlRequestList != null && !documentUrlRequestList.isEmpty()) {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            ObjectMapper objectMapper = new ObjectMapper();

            List<DocumentUrlResponse> documentUrlResponseList = null;

            StringEntity input;
            try {

                String jsonStr = objectMapper.writeValueAsString(documentUrlRequestList);

                log.info("Fetch Document URL API Request : " + jsonStr);

                input = new StringEntity(jsonStr);
                httpPost.setEntity(input);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader(TRACE_HEADER, httpServletResponse.getHeader(TRACE_HEADER));
                httpPost.setHeader("Authorization", httpServletRequest.getHeader("Authorization"));
                httpPost.setHeader("Host", "partners.airtel.in");
                httpPost.setHeader("x-forwarded-host", "partners.airtel.in");

                HttpResponse response = httpclient.execute(httpPost);
                String result = EntityUtils.toString(response.getEntity());

                log.info("Upload Document API Response : " + result);

                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, DocumentUrlResponse.class);
                documentUrlResponseList = objectMapper.readValue(result, listType);

                log.info("Fetch Document URL API Response : " + documentUrlResponseList);

                return documentUrlResponseList;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
