import { expect, type Page } from '@playwright/test'
import type { CourseInput, CourseLessonInput } from '../helpers/course-data'

export class CreateCoursePage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto('/criar-curso')
    await expect(this.page.getByRole('heading', { name: 'Criar curso' })).toBeVisible()
  }

  async fillCourseDetails(course: CourseInput) {
    await this.page.locator('input[name="nameCourse"]').fill(course.name)
    await this.page.locator('select[name="category"]').selectOption(course.category)
    await this.page.locator('select[name="level"]').selectOption(course.level)
    await this.page.locator('textarea[name="description"]').fill(course.description)

    for (let index = 0; index < course.lessons.length; index += 1) {
      if (index > 0) {
        await this.page.getByRole('button', { name: 'Adicionar' }).click()
      }

      await this.fillLesson(index, course.lessons[index])
    }
  }

  async fillLesson(index: number, lesson: CourseLessonInput) {
    await this.page.locator(`[name="lessons.${index}.nameLesson"]`).fill(lesson.title)
    await this.page.locator(`[name="lessons.${index}.link"]`).fill(lesson.youtubeLink)
    await this.page.locator(`[name="lessons.${index}.descriptionL"]`).fill(lesson.description)
  }

  async submit() {
    await this.page.getByRole('button', { name: 'Criar Curso' }).click()
  }
}
