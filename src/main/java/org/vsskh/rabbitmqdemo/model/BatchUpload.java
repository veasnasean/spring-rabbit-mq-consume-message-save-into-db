package org.vsskh.rabbitmqdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "batch_uploads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchName;
    private LocalDateTime uploadTime;
    private String status;
}
