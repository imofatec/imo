import authAxiosInstance from '@/api/authAxiosInstance'
import { resolveAchievementImageSrc } from '@/lib/resolveAchievementImageSrc'
import { safeAwait } from '@/lib/safeAwait'
import type {
  Achievement,
  AchievementListItem,
  UserAchievementsResponse,
} from '@/types/achievement'
import { useCallback, useEffect, useMemo, useState } from 'react'

const emptyAchievements: UserAchievementsResponse = {
  total: 0,
  unlockedCount: 0,
  lockedCount: 0,
  items: [],
}

function formatUnlockedAt(value: string | null) {
  if (!value) return 'Ainda nao desbloqueada'

  const date = new Date(value)

  if (Number.isNaN(date.getTime())) {
    return 'Data indisponivel'
  }

  return new Intl.DateTimeFormat('pt-BR', {
    dateStyle: 'medium',
    timeStyle: 'short',
  }).format(date)
}

function mapAchievementItem(achievement: Achievement): AchievementListItem {
  return {
    ...achievement,
    imageSrc: resolveAchievementImageSrc(achievement.imageUrl),
    unlockedLabel: formatUnlockedAt(achievement.unlockedAt),
    isUnlocked: Boolean(achievement.unlockedAt),
  }
}

export function useUserAchievementsPage() {
  const [achievements, setAchievements] = useState<UserAchievementsResponse>(emptyAchievements)
  const [error, setError] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  const fetchAchievements = useCallback(async () => {
    setIsLoading(true)

    const [err, response] = await safeAwait(
      authAxiosInstance.get<UserAchievementsResponse>('/api/user/profile/achievements')
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar conquistas')
      setIsLoading(false)
      return
    }

    setAchievements(response.data)
    setError(null)
    setIsLoading(false)
  }, [])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchAchievements()
  }, [fetchAchievements])

  const items = useMemo(
    () => achievements.items.map((achievement) => mapAchievementItem(achievement)),
    [achievements.items]
  )

  return {
    achievements: {
      ...achievements,
      items,
    },
    error,
    isLoading,
    refetch: fetchAchievements,
  }
}
