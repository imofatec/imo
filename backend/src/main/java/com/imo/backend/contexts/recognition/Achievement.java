package com.imo.backend.contexts.recognition;

import lombok.Data;

@Data
public class Achievement {
  private String key;

  private String title;

  private String description;

  private AchievementTrigger trigger;

  private int target;

  private int displayOrder;

  private String unlockedImagePath;

  private String lockedImagePath;

  public Achievement(
      String key,
      String title,
      String description,
      AchievementTrigger trigger,
      int target,
      int displayOrder,
      String unlockedImagePath,
      String lockedImagePath) {
    this.key = key;
    this.title = title;
    this.description = description;
    this.trigger = trigger;
    this.target = target;
    this.displayOrder = displayOrder;
    this.unlockedImagePath = unlockedImagePath;
    this.lockedImagePath = lockedImagePath;
  }
}
