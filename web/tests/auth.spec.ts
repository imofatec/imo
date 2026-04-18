import { expect, test } from './fixtures'
import {
  apiBaseUrl,
  createUserData,
  defaultPassword,
  fillLoginForm,
  fillRegisterForm,
} from './helpers/auth'
import { LoginPage } from './page-objects/LoginPage'

test.describe('Auth', () => {
  test.beforeAll(() => {
    if (!apiBaseUrl) {
      throw new Error('VITE_API_BASE_URL nao foi definido no .env')
    }
  })

  test('should redirect to login after successful registration', async ({ page }) => {
    const user = createUserData()

    await page.goto('/cadastro')
    await fillRegisterForm(page, user)
    await page.getByRole('button', { name: 'Cadastrar' }).click()

    await expect(page).toHaveURL(/\/login$/)
    await expect(page.getByRole('heading', { name: 'Login' })).toBeVisible()
  })

  test('should login successfully and persist auth token in localStorage', async ({
    page,
    registeredUser,
  }) => {
    const loginPage = new LoginPage(page)

    await loginPage.goto()
    await loginPage.loginAs(registeredUser)

    await page.locator('button[aria-haspopup="menu"]').click()
    await expect(page.getByRole('button', { name: 'Sair' })).toBeVisible()

    const storedToken = await page.evaluate(() => localStorage.getItem('token'))
    expect(storedToken).toBeTruthy()
  })

  test('should logout successfully and clear the auth session', async ({
    page,
    registeredUser,
  }) => {
    const loginPage = new LoginPage(page)

    await loginPage.goto()
    await loginPage.loginAs(registeredUser)

    await page.locator('button[aria-haspopup="menu"]').click()
    await expect(page.getByRole('button', { name: 'Sair' })).toBeVisible()

    await page.getByRole('button', { name: 'Sair' }).click()

    await expect(page.getByRole('link', { name: 'Entrar', exact: true })).toBeVisible()
    await expect(page.getByRole('link', { name: 'Cadastrar', exact: true })).toBeVisible()

    const storedToken = await page.evaluate(() => localStorage.getItem('token'))
    expect(storedToken).toBeFalsy()
  })

  test('should show an error for invalid login credentials', async ({ page }) => {
    const loginPage = new LoginPage(page)

    await loginPage.goto()

    await fillLoginForm(page, {
      email: 'inexistente@email.com',
      password: defaultPassword,
    })

    await page.getByRole('button', { name: 'Entrar' }).click()

    await expect(page).toHaveURL(/\/login$/)
    await expect(page.getByText('Ocorreu um erro')).toBeVisible()

    const storedToken = await page.evaluate(() => localStorage.getItem('token'))
    expect(storedToken).toBeFalsy()
  })

  test('should show an error when registering with an existing email', async ({
    page,
    registeredUser,
  }) => {
    await page.goto('/cadastro')
    await fillRegisterForm(page, registeredUser)
    await page.getByRole('button', { name: 'Cadastrar' }).click()

    await expect(page).toHaveURL(/\/cadastro$/)
    await expect(page.getByText('Ocorreu um erro')).toBeVisible()
  })
})
