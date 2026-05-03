package com.imo.backend.contexts.notification.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Notification", description = "Endpoints de notificações em tempo real")
@RequestMapping("/api/notification")
public abstract class NotificationController {}
