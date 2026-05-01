import { useEffect, useRef } from 'react'
import { toast } from 'sonner'
import { createAuthEventSource } from '@/api/authEventSourceInstance'
import AchievementNotificationToast from '@/components/watch/AchievementNotificationToast'
import type { AchievementUnlockedNotification } from '@/types/notification'

function parseEventData<T>(data: string) {
  try {
    return JSON.parse(data) as T
  } catch {
    return null
  }
}

function showAchievementToast(achievement: AchievementUnlockedNotification) {
  toast.custom(() => <AchievementNotificationToast achievement={achievement} />, {
    duration: 6000,
    position: 'bottom-right',
  })
}

export function useAchievementNotificationStream() {
  const shownAchievementKeysRef = useRef<Set<string>>(new Set())

  useEffect(() => {
    const stream = createAuthEventSource(
      (token) => `/api/notification/stream/${encodeURIComponent(token)}`
    )

    if (!stream) return

    function handleAchievementUnlocked(event: MessageEvent<string>) {
      const achievement = parseEventData<AchievementUnlockedNotification>(event.data)

      if (!achievement || shownAchievementKeysRef.current.has(achievement.key)) return

      shownAchievementKeysRef.current.add(achievement.key)
      showAchievementToast(achievement)
    }

    stream.addEventListener('achievement-unlocked', handleAchievementUnlocked)

    return () => {
      stream.removeEventListener('achievement-unlocked', handleAchievementUnlocked)
      stream.close()
    }
  }, [])
}
