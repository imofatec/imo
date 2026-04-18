import { test as base } from '@playwright/test'
import {
  createAuthenticatedUser,
  createConfirmedUser,
  createUserData,
  registerUserByApi,
  type AuthSession,
  type UserData,
} from './helpers/auth'

type AuthFixtures = {
  registeredUser: UserData
  authenticatedUser: AuthSession
  confirmedContributor: AuthSession
}

export const test = base.extend<AuthFixtures>({
  registeredUser: async ({ request }, use) => {
    const user = createUserData()
    await registerUserByApi(request, user)
    await use(user)
  },
  authenticatedUser: async ({ request }, use) => {
    const user = await createAuthenticatedUser(request)
    await use(user)
  },
  confirmedContributor: async ({ request }, use) => {
    const contributor = await createConfirmedUser(request)
    await use(contributor)
  },
})

export { expect } from '@playwright/test'
