package com.internship.tool.repository;

import com.internship.tool.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Incident Repository — all database queries for the incidents table.
 *
 * JpaRepository gives us free: save, findById, findAll, delete, count, etc.
 * Custom methods below handle search, filter, and stats.
 *
 * Day 2 — Java Developer 1
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    // ── Basic Finders ─────────────────────────────────────────────

    /**
     * Find one incident by ID — only if not soft deleted.
     * Used by GET /{id} endpoint (returns 404 if not found).
     */
    Optional<Incident> findByIdAndIsDeletedFalse(Long id);

    /**
     * Find all incidents — paginated, not soft deleted.
     * Used by GET /all endpoint.
     */
    Page<Incident> findAllByIsDeletedFalse(Pageable pageable);

    /**
     * Find all incidents by status — paginated.
     * Used by filter dropdown on the frontend.
     */
    Page<Incident> findAllByStatusAndIsDeletedFalse(String status, Pageable pageable);

    /**
     * Find all incidents by severity — paginated.
     */
    Page<Incident> findAllBySeverityAndIsDeletedFalse(String severity, Pageable pageable);

    /**
     * Find all incidents assigned to a specific person.
     */
    List<Incident> findAllByAssignedToAndIsDeletedFalse(String assignedTo);

    /**
     * Find all incidents reported by a specific person.
     */
    List<Incident> findAllByReportedByAndIsDeletedFalse(String reportedBy);

    // ── Search ────────────────────────────────────────────────────

    /**
     * Full text search across title, description, incidentType, and reportedBy.
     * Used by GET /search?q= endpoint.
     * ILIKE = case-insensitive LIKE in PostgreSQL.
     */
    @Query("""
            SELECT i FROM Incident i
            WHERE i.isDeleted = false
            AND (
                LOWER(i.title)        LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(i.description)  LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(i.incidentType) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(i.reportedBy)   LIKE LOWER(CONCAT('%', :query, '%'))
            )
            """)
    Page<Incident> searchIncidents(@Param("query") String query, Pageable pageable);

    // ── Date Range Filter ─────────────────────────────────────────

    /**
     * Find incidents that occurred between two dates.
     * Used by date range filter on the frontend.
     */
    @Query("""
            SELECT i FROM Incident i
            WHERE i.isDeleted = false
            AND i.incidentDate BETWEEN :startDate AND :endDate
            """)
    Page<Incident> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // ── Stats Queries (for Dashboard KPI cards) ───────────────────

    /**
     * Count total active (not deleted) incidents.
     */
    long countByIsDeletedFalse();

    /**
     * Count incidents by status.
     * Example: countByStatusAndIsDeletedFalse("OPEN") → 5
     */
    long countByStatusAndIsDeletedFalse(String status);

    /**
     * Count incidents by severity.
     */
    long countBySeverityAndIsDeletedFalse(String severity);

    /**
     * Count incidents created after a specific date.
     * Used for "incidents this week/month" KPI card.
     */
    long countByCreatedAtAfterAndIsDeletedFalse(LocalDateTime date);

    // ── AI Fields ─────────────────────────────────────────────────

    /**
     * Find incidents that have NOT been processed by AI yet.
     * Used to queue records for AI processing.
     */
    List<Incident> findAllByAiDescriptionIsNullAndIsDeletedFalse();

    /**
     * Find incidents where AI was generated before a certain time.
     * Used to re-process stale AI results.
     */
    @Query("""
            SELECT i FROM Incident i
            WHERE i.isDeleted = false
            AND i.aiGeneratedAt < :cutoffTime
            """)
    List<Incident> findIncidentsWithStaleAi(@Param("cutoffTime") LocalDateTime cutoffTime);
}
