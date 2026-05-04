package com.internship.tool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@EntityListeners(AuditingEntityListener.class)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Incident {

    // ── Primary Key ─────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Fields ──────────────────────────────
    private String title;
    private String description;
    private String incidentType;
    private String severity;
    private String status;
    private String reportedBy;
    private String assignedTo;
    private String location;
    private String evidenceUrl;
    private LocalDateTime incidentDate;
    private LocalDateTime resolvedDate;
    private String aiDescription;
    private String aiRecommendation;
    private LocalDateTime aiGeneratedAt;

    @Builder.Default
    private Boolean isDeleted = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // ✅ Manual getters (since Lombok may not work)
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getSeverity() { return severity; }
    public String getReportedBy() { return reportedBy; }
}
