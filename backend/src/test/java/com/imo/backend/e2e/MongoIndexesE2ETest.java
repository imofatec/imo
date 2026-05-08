package com.imo.backend.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.index.IndexInfo;

class MongoIndexesE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("startup: cria os índices únicos esperados")
  void shouldCreateExpectedUniqueIndexes() {
    assertUniqueIndex(User.class, "uk_users_email");
    assertUniqueIndex(SkillProfile.class, "uk_skill_profile_user_skill");
    assertUniqueIndex(Progress.class, "uk_progress_user_course");
    assertUniqueIndex(Course.class, "uk_courses_contributor_slug");
    assertUniqueIndex(Lesson.class, "uk_lessons_course_index");
    assertUniqueIndex(Lesson.class, "uk_lessons_course_title");
    assertUniqueIndex(Lesson.class, "uk_lessons_course_youtube_link");
  }

  private void assertUniqueIndex(Class<?> entityClass, String indexName) {
    IndexInfo indexInfo =
        this.mongoTemplate.indexOps(entityClass).getIndexInfo().stream()
            .filter(index -> indexName.equals(index.getName()))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Índice não encontrado: " + indexName));

    assertTrue(indexInfo.isUnique(), "Índice deveria ser único: " + indexName);
  }
}
