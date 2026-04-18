import { expect, type Page } from '@playwright/test'
import type { CourseLessonInput } from '../helpers/course-data'

type CourseUpdateInput = {
  name: string
  category: string
  level: string
  description: string
}

export class EditCoursePage {
  constructor(private readonly page: Page) {}

  async goto(courseId: string) {
    await this.page.goto(`/editar-curso/${courseId}`)
    await expect(this.page.getByRole('heading', { name: 'Editar curso' })).toBeVisible()
  }

  async updateCourseDetails(course: CourseUpdateInput) {
    await this.page.locator('input[name="nameCourse"]').fill(course.name)
    await this.page.locator('select[name="category"]').selectOption(course.category)
    await this.page.locator('select[name="level"]').selectOption(course.level)
    await this.page.locator('textarea[name="description"]').fill(course.description)
    await this.page.getByRole('button', { name: /Salvar altera/i }).first().click()
  }

  async createLesson(lesson: CourseLessonInput) {
    const lessonCount = await this.page.getByRole('button', { name: /Excluir aula/i }).count()
    await this.page.getByRole('button', { name: 'Adicionar aula' }).click()
    await this.fillLesson(lessonCount, lesson)
    await this.page.getByRole('button', { name: 'Criar aula' }).click()
  }

  async updateLesson(index: number, lesson: CourseLessonInput) {
    await this.fillLesson(index, lesson)
    await this.page.getByRole('button', { name: /Salvar altera/i }).nth(index + 1).click()
  }

  async deleteLesson(index: number) {
    await this.page.getByRole('button', { name: /Excluir aula/i }).nth(index).click()
  }

  async openDeleteCourseModal() {
    await this.page.getByRole('button', { name: 'Excluir curso' }).click()
  }

  async confirmCourseDeletion() {
    await this.page.getByRole('button', { name: /Confirmar exclus/i }).click()
  }

  async expectCoursePlaceholders(course: CourseUpdateInput) {
    await expect(this.page.locator('input[name="nameCourse"]')).toHaveAttribute(
      'placeholder',
      course.name
    )
    await expect(this.page.locator('select[name="category"]')).toHaveValue(course.category)
    await expect(this.page.locator('select[name="level"]')).toHaveValue(course.level)
    await expect(this.page.locator('textarea[name="description"]')).toHaveAttribute(
      'placeholder',
      course.description
    )
  }

  private async fillLesson(index: number, lesson: CourseLessonInput) {
    await this.page.locator(`[name="lessons.${index}.nameLesson"]`).fill(lesson.title)
    await this.page.locator(`[name="lessons.${index}.link"]`).fill(lesson.youtubeLink)
    await this.page.locator(`[name="lessons.${index}.descriptionL"]`).fill(lesson.description)
  }
}
