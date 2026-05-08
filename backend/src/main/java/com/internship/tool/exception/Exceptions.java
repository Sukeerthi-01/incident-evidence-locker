package com.internship.tool.exception;

/**
 * Custom exception classes for Tool-36.
 * Built fully on Day 3 (Service layer) and Day 8 (@ControllerAdvice).
 *
 * Exceptions defined here:
 *   - ResourceNotFoundException  → HTTP 404
 *   - BadRequestException        → HTTP 400
 *   - UnauthorizedException      → HTTP 401
 *   - ConflictException          → HTTP 409
 */

// ── 404 ──────────────────────────────────────────────────────────────────────
class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " not found with id: " + id);
    }
}

// ── 400 ──────────────────────────────────────────────────────────────────────
class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

// ── 401 ──────────────────────────────────────────────────────────────────────
class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

// ── 409 ──────────────────────────────────────────────────────────────────────
class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
