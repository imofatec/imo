import { expect, type APIRequestContext, type Page } from '@playwright/test'
import { loadEnv } from 'vite'

const env = loadEnv('test', process.cwd(), '')

export const apiBaseUrl = env.VITE_API_BASE_URL
export const defaultPassword = 'Joao123.'

export type UserData = {
  name: string
  email: string
  password: string
  confPassword: string
}

export function createUserData(): UserData {
  const uniqueSuffix = `${Date.now()}-${Math.random().toString(36).slice(2, 8)}`

  return {
    name: 'Joao da Silva',
    email: `joao-${uniqueSuffix}@email.com`,
    password: defaultPassword,
    confPassword: defaultPassword,
  }
}

export async function fillRegisterForm(page: Page, user: UserData) {
  await page.getByLabel('Nome').fill(user.name)
  await page.getByLabel('Email').fill(user.email)
  await page.getByLabel('Senha', { exact: true }).fill(user.password)
  await page.getByLabel('Confirmar senha').fill(user.confPassword)
}

export async function fillLoginForm(page: Page, user: Pick<UserData, 'email' | 'password'>) {
  await page.getByLabel('Email').fill(user.email)
  await page.getByLabel('Senha', { exact: true }).fill(user.password)
}

export async function registerUserByApi(request: APIRequestContext, user: UserData) {
  const response = await request.post(`${apiBaseUrl}/api/user`, {
    data: user,
  })

  expect(response.ok()).toBeTruthy()

  return user
}
