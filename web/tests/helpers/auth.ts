import { expect, type APIRequestContext, type Page } from '@playwright/test'
import { loadEnv } from 'vite'
import type { User } from '../../src/types/user'

const env = loadEnv('test', process.cwd(), '')

export const apiBaseUrl = env.VITE_API_BASE_URL
export const defaultPassword = 'Joao123.'

export type UserData = {
  name: string
  email: string
  password: string
  confPassword: string
}

export type AuthSession = UserData & {
  id: string
  accessToken: string
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

export async function loginUserByApi(
  request: APIRequestContext,
  user: Pick<UserData, 'email' | 'password'>
) {
  const response = await request.post(`${apiBaseUrl}/api/user/login`, {
    data: user,
  })

  expect(response.ok()).toBeTruthy()

  return (await response.json()) as { accessToken: string }
}

export async function getUserProfileByApi(request: APIRequestContext, accessToken: string) {
  const response = await request.get(`${apiBaseUrl}/api/user/profile`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  })

  expect(response.ok()).toBeTruthy()

  return (await response.json()) as User
}

export async function confirmUserByApi(request: APIRequestContext, accessToken: string) {
  const response = await request.put(`${apiBaseUrl}/api/user/confirm`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  })

  expect(response.ok()).toBeTruthy()
}

export async function createAuthenticatedUser(request: APIRequestContext): Promise<AuthSession> {
  const user = createUserData()
  await registerUserByApi(request, user)

  const { accessToken } = await loginUserByApi(request, user)
  const profile = await getUserProfileByApi(request, accessToken)

  return {
    ...user,
    id: profile.id,
    accessToken,
  }
}

export async function createConfirmedUser(request: APIRequestContext): Promise<AuthSession> {
  const session = await createAuthenticatedUser(request)
  await confirmUserByApi(request, session.accessToken)

  return session
}

export async function authenticatePage(page: Page, accessToken: string) {
  await page.addInitScript((token: string) => {
    window.localStorage.clear()
    window.localStorage.setItem('token', token)
  }, accessToken)
}
