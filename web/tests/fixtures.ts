import { test as base } from '@playwright/test'
import { createUserData, registerUserByApi, type UserData } from './helpers/auth'

type AuthFixtures = {
  registeredUser: UserData
}

export const test = base.extend<AuthFixtures>({
  registeredUser: async ({ request }, use) => {
    const user = createUserData()
    await registerUserByApi(request, user)
    await use(user)
  },
})

export { expect } from '@playwright/test'
