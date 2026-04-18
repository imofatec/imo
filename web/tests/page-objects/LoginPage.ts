import { expect, type Page } from '@playwright/test'
import type { UserData } from '../helpers/auth'

export class LoginPage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto('/login')
    await expect(this.page.getByRole('heading', { name: 'Login' })).toBeVisible()
  }

  async loginAs(user: Pick<UserData, 'email' | 'password'>) {
    await this.page.getByLabel('Email').fill(user.email)
    await this.page.getByLabel('Senha', { exact: true }).fill(user.password)
    await this.page.getByRole('button', { name: 'Entrar' }).click()
  }
}
