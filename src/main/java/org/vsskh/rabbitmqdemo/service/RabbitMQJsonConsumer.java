package org.vsskh.rabbitmqdemo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.vsskh.rabbitmqdemo.model.BatchUpload;
import org.vsskh.rabbitmqdemo.model.Invoice;
import org.vsskh.rabbitmqdemo.model.User;
import org.vsskh.rabbitmqdemo.repository.BatchUploadRepository;
import org.vsskh.rabbitmqdemo.repository.InvoiceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RabbitMQJsonConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
    private final BatchUploadRepository batchUploadRepository;
    private final InvoiceRepository invoiceRepository;

    public RabbitMQJsonConsumer(BatchUploadRepository batchUploadRepository, InvoiceRepository invoiceRepository) {
        this.batchUploadRepository = batchUploadRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.jsonqueue}"})
    public void consumeJsonMessage(List<Invoice> invoices) {
        // 1. Create a new batch record
        BatchUpload batch = new BatchUpload();
        batch.setBatchName("Batch-" + UUID.randomUUID());
        batch.setUploadTime(LocalDateTime.now());
        batch.setStatus("COMPLETED");
        batch = batchUploadRepository.save(batch); // Save batch to DB
        // 2. Save each invoice linked to the batch
        for (Invoice invoice : invoices) {
            invoice.setBatch(batch);
            invoiceRepository.save(invoice);
        }
        System.out.println("Batch processed and saved successfully!");
    }


//    @RabbitListener(queues = {"${rabbitmq.queue.jsonqueue}"})
//    public void consumeJsonMessage(User user) {
//        LOGGER.info(String.format("Received user: %s", user.toString()));
//    }
}
