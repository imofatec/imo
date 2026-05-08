import { expect, type Page } from '@playwright/test'
import type { Course } from '../../src/types/course'

export class CoursesCatalogPage {
  constructor(private readonly page: Page) {}

  async goto(searchName?: string) {
    const query = searchName ? `?name=${encodeURIComponent(searchName)}` : ''
    await this.page.goto(`/categorias${query}`)
    await expect(this.page.getByRole('heading', { name: 'Cursos' })).toBeVisible()
  }

  async filterByCategory(categoryName: string) {
    await this.page.getByRole('button', { name: 'Filtrar' }).click()
    await this.page.getByRole('link', { name: categoryName, exact: true }).click()
  }

  async openCourseModal(courseName: string) {
    await this.page.getByRole('heading', { name: courseName, exact: true }).first().click()
  }

  async expectCourseVisible(courseName: string) {
    await expect(this.page.getByRole('heading', { name: courseName, exact: true }).first()).toBeVisible()
  }

  async expectCourseHidden(courseName: string) {
    await expect(this.page.getByRole('heading', { name: courseName, exact: true })).toHaveCount(0)
  }

  async expectModalToShowCourse(course: Course) {
    const modal = this.page.locator('.fixed.inset-0')

    await expect(modal.getByRole('heading', { name: course.name.name, exact: true })).toBeVisible()
    await expect(modal.getByText(course.description, { exact: true })).toBeVisible()
    await expect(modal.getByText(course.category.name, { exact: true })).toBeVisible()
    await expect(modal.getByText(course.level.name, { exact: true })).toBeVisible()
    await expect(modal.getByText(`${course.lessonsCount} aula`, { exact: false })).toBeVisible()
    await expect(modal.getByText(course.isActive ? 'Ativo' : 'Inativo', { exact: true })).toBeVisible()
  }
}
