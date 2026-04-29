package com.imo.backend.contexts.learning_path.recommendation.events;

import java.util.List;

public record RecommendationContextChangedEvent(String courseId, List<String> skillIds) {}
