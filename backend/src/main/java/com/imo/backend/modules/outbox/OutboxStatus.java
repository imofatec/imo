package com.imo.backend.modules.outbox;

public enum OutboxStatus {
  PENDING,
  WAITING_TO_UPDATE_PASSWORD,
  SENT,
}
