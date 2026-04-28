package com.imo.backend.contexts.recommendation.events;

import java.util.List;

public record RecommendationContextChangedEvent(String courseId, List<String> skillIds) {}
