import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type UpdateUserRequestData = {
  email?: string
  name?: string
  bio?: string
  oldPassword?: string
  password?: string
  birthDate?: string
  availableTimePerDay?: string
  academicDegree?: string
  experienceLevel?: string
  categoriesOfInterest?: string[]
}

export async function updateUserRequest(data: UpdateUserRequestData) {
  const [error, response] = await safeAwait(authAxiosInstance.put('/api/user', data))

  if (error || !response) throw error

  return response.data
}
