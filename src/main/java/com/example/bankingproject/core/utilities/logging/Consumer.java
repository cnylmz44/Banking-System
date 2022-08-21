package com.example.bankingproject.core.utilities.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	private String filePathString = "/home/canyilmaz/eclipse-workspace/";

	@KafkaListener(topics = "logs", groupId = "logs_group")
	public void saveLog2File(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		try {
			File file = new File(filePathString + "logs.txt");
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
