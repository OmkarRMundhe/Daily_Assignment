# AWS S3 + Lambda + DynamoDB File Processing System

A **serverless file processing application** built using **AWS Lambda (Java)**, **Amazon S3**, and **DynamoDB**.  
This project automatically processes uploaded `.txt` files from an S3 bucket, extracts useful statistics, and stores the results in DynamoDB.

---

## Overview

When a user uploads a text file to S3, it triggers a **Lambda function** written in **Java**.  
The Lambda reads the file, calculates key metrics like line count, word count, and character count, then stores this information in **DynamoDB** along with a short file preview and timestamp.

---

## AWS Components

| Service | Purpose |
|----------|----------|
| **Amazon S3** | Stores uploaded text files |
| **AWS Lambda (Java 17)** | Processes files on upload |
| **Amazon DynamoDB** | Stores file metadata and statistics |
| **CloudWatch** | Logs Lambda execution details |

---

## Architecture Diagram

```text
        +-------------------+
        |   User Uploads    |
        |   text file (.txt)|
        +---------+---------+
                  |
                  v
        +---------+---------+
        |   Amazon S3 Bucket|
        | file-processing-results-omkar |
        +---------+---------+
                  |
        (S3 Event Trigger)
                  |
                  v
        +---------+---------+
        |  AWS Lambda (Java)|
        | TextFileProcessor  |
        +---------+---------+
                  |
                  v
        +---------+---------+
        | Amazon DynamoDB   |
        | file-processing-results-omkar |
        +-------------------+
```

Key Features

✅ Triggered automatically when a .txt file is uploaded  
✅ Calculates:

- Total number of lines
- Total number of words
- Total number of characters

✅ Extracts a 100-character preview  
✅ Saves results with a timestamp to DynamoDB  
✅ Uses CloudWatch Logs for monitoring and debugging

Tech Stack

- Java 17
- AWS Lambda
- Amazon S3
- Amazon DynamoDB
- Maven
- AWS SDK v2

Project Structure
```
AWSFileProcessor/
 ├── src/
 │   └── main/java/com/example/demo/AWS/TextFileProcessor.java
 ├── pom.xml
 ├── target/
 └── README.md
```

How It Works

User uploads a .txt file to S3 bucket → file-processing-results-omkar.

The Lambda function is triggered via S3 Event Notification.

The Lambda:

- Reads file content from S3.
- Calculates line, word, and character count.
- Generates a preview (first 100 chars).
- Saves this info to DynamoDB table file-processing-results-omkar.

Logs are available in CloudWatch.

 Example DynamoDB Record

Field | Example Value
--- | ---
id | sample1.txt
lines | 52
words | 284
characters | 1472
preview | "The quick brown fox jumps over the lazy dog..."
timestamp | 2025-11-03T10:25:00Z

Build and Deploy Instructions

 Step 1: Build JAR
```bash
mvn clean package
```

This generates a .jar file under target/.

 Step 2: Deploy on AWS Lambda

1. Go to AWS Lambda Console  
2. Create function → Author from scratch  
   - Runtime: Java 17  
3. Upload the generated JAR file (AWS-0.0.1-SNAPSHOT.jar)  
4. Set the handler as:
```
com.example.demo.AWS.TextFileProcessor::handleRequest
```
5. Add S3 trigger for your bucket:
```
file-processing-results-omkar
```
6. Assign IAM role with permissions:
- AmazonS3ReadOnlyAccess
- AmazonDynamoDBFullAccess
- CloudWatchLogsFullAccess

 Sample Output in DynamoDB
```json
{
  "id": "sample-text.txt",
  "lines": 42,
  "words": 203,
  "characters": 1042,
  "preview": "This is an example text file that demonstrates AWS Lambda processing...",
  "timestamp": "2025-11-03T10:30:15Z"
}
```
