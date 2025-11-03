package com.example.demo.AWS;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3Entity;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class TextFileProcessor implements RequestHandler<S3Event, String> {

	private final S3Client s3 = S3Client.create();
	private final DynamoDbClient dynamoDB = DynamoDbClient.create();

	@Override
	public String handleRequest(S3Event event, Context context) {
		try {
			context.getLogger().log("Event received: " + event.toString());

			S3Entity s3Entity = event.getRecords().get(0).getS3();
			String bucketName = s3Entity.getBucket().getName();
			String key = s3Entity.getObject().getKey();

			context.getLogger().log("Processing file: " + key + " from bucket: " + bucketName);

			// Read file content from S3
			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

			InputStream objectData = s3.getObject(getObjectRequest);

			String content;
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(objectData, StandardCharsets.UTF_8))) {
				content = reader.lines().collect(Collectors.joining("\n"));
			}

			// Process text
			int lineCount = (int) content.lines().count();
			int wordCount = content.split("\\s+").length;
			int charCount = content.length();
			String preview = content.length() > 100 ? content.substring(0, 100) : content;

			Map<String, AttributeValue> item = new HashMap<>();
			item.put("filename", AttributeValue.builder().s(key).build());
			item.put("lineCount", AttributeValue.builder().n(String.valueOf(lineCount)).build());
			item.put("wordCount", AttributeValue.builder().n(String.valueOf(wordCount)).build());
			item.put("charCount", AttributeValue.builder().n(String.valueOf(charCount)).build());
			item.put("preview", AttributeValue.builder().s(preview).build());
			item.put("processedDate", AttributeValue.builder().s(Instant.now().toString()).build());

			PutItemRequest request = PutItemRequest.builder().tableName("file-processing-results-omkar").item(item).build();

			dynamoDB.putItem(request);
			context.getLogger().log("Successfully saved result for file: " + key);

			return "Success";

		} catch (Exception e) {
			context.getLogger().log("Error processing file: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
