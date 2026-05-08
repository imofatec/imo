import axios from 'axios'
import { formatSocialProfileDateTime } from '@/lib/socialProfile'
import { getPublicUserProfileRequest } from '@/services/user/getPublicUserProfileRequest'
import type { AchievementListItem } from '@/types/achievement'
import type { PublicUserProfile } from '@/types/publicUserProfile'
import { useCallback, useEffect, useMemo, useState } from 'react'

type UseSocialProfilePageParams = {
  userId: string | undefined
}

type SocialProfileErrorType = 'not_found' | 'request_error' | null

function mapAchievementItem(
  achievement: PublicUserProfile['highlightedAchievements'][number]
): AchievementListItem {
  return {
    trigger: achievement.key,
    key: achievement.key,
    title: achievement.title,
    description: achievement.description,
    imageUrl: achievement.imageUrl ?? '',
    unlockedAt: achievement.unlockedAt,
    currentValue: 1,
    targetValue: 1,
    progressPercentage: 100,
    unlockedLabel: formatSocialProfileDateTime(achievement.unlockedAt, 'Data não encontrada'),
    isUnlocked: Boolean(achievement.unlockedAt),
  }
}

export function useSocialProfilePage({ userId }: UseSocialProfilePageParams) {
  const [profile, setProfile] = useState<PublicUserProfile | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [errorType, setErrorType] = useState<SocialProfileErrorType>(null)

  const fetchProfile = useCallback(async () => {
    if (!userId) {
      setProfile(null)
      setError('Perfil invalido')
      setErrorType('request_error')
      setIsLoading(false)
      return
    }

    setIsLoading(true)

    try {
      const responseProfile = await getPublicUserProfileRequest(userId)

      setProfile(responseProfile)
      setError(null)
      setErrorType(null)
    } catch (requestError) {
      if (axios.isAxiosError(requestError) && requestError.response?.status === 404) {
        setProfile(null)
        setError(requestError.response?.data?.message || 'Usuario nao encontrado')
        setErrorType('not_found')
        return
      }

      const message = requestError instanceof Error ? requestError.message : 'Erro ao buscar perfil'

      setProfile(null)
      setError(message)
      setErrorType('request_error')
    } finally {
      setIsLoading(false)
    }
  }, [userId])

  useEffect(() => {
    void fetchProfile()
  }, [fetchProfile])

  const highlightedAchievements = useMemo(
    () => profile?.highlightedAchievements.map((achievement) => mapAchievementItem(achievement)) ?? [],
    [profile?.highlightedAchievements]
  )

  return {
    profile,
    isLoading,
    error,
    errorType,
    highlightedAchievements,
    refetch: fetchProfile,
  }
}
