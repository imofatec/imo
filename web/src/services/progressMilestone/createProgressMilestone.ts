import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { ProgressMilestone } from '@/types/progressMilestone'

export async function createProgressMilestoneRequest(courseId: string) {
  const [error, response] = await safeAwait(
    authAxiosInstance.put<ProgressMilestone>(`/api/progress/milestones/course/${courseId}`)
  )

  if (error || !response) throw error

  return response.data
}
