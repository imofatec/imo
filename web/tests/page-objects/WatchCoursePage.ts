import { expect, type Page } from '@playwright/test'

export class WatchCoursePage {
  constructor(private readonly page: Page) {}

  async goto(courseId: string, lessonYoutubeId: string) {
    await this.page.goto(`/cursos/${courseId}/${lessonYoutubeId}`)
    await expect(this.page.getByRole('heading', { level: 1 })).toBeVisible()
  }

  async expectCurrentLesson(title: string) {
    await expect(this.page.getByRole('heading', { name: title, exact: true })).toBeVisible()
  }

  async markLessonAsWatched(indexInCourse: number) {
    await this.page.getByLabel(`Marcar aula ${indexInCourse} como assistida`).click()
  }

  async expectWatchedCounter(count: number, total: number) {
    await expect(this.page.getByText(`${count} de ${total} aulas`, { exact: false })).toBeVisible()
  }

  async expectProgressWidth(percent: number) {
    await expect(this.page.locator('aside .h-2 > div')).toHaveAttribute(
      'style',
      `width: ${percent}%;`
    )
  }

  async submitComment(content: string) {
    await this.page.getByLabel('Novo comentario').fill(content)
    await this.page.getByRole('button', { name: 'Comentar', exact: true }).click()
  }

  async expectCommentVisible(content: string) {
    await expect(this.page.getByText(content, { exact: true })).toBeVisible()
  }

  certificateButton() {
    return this.page.getByRole('button', { name: 'Certificado' })
  }
}
