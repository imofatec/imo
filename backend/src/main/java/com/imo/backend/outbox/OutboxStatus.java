package com.imo.backend.outbox;

public enum OutboxStatus {
  PENDING,
  WAITING_TO_UPDATE_PASSWORD,
  SENT,
}
