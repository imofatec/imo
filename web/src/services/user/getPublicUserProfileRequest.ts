import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { PublicUserProfile } from '@/types/publicUserProfile'

export async function getPublicUserProfileRequest(userId: string) {
  const [error, response] = await safeAwait(
    axiosInstance.get<PublicUserProfile>(`/api/user/public/${userId}`)
  )

  if (error || !response) throw error

  return response.data
}
