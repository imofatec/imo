package com.imo.backend.models.outbox;

public enum OutboxStatus {
  PENDING,
  WAITING_TO_UPDATE_PASSWORD,
  SENT,
}
