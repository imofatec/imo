import { resolveAchievementImageSrc } from '@/lib/resolveAchievementImageSrc'
import type { AchievementListItem } from '@/types/achievement'

type SocialProfileMockContent = {
  bio: string
  featuredAchievements: AchievementListItem[]
}

const defaultSocialProfileContent: SocialProfileMockContent = {
  bio: 'Apaixonado por tecnologia, aprendizado continuo e por compartilhar descobertas que tornam a jornada de estudo mais leve e divertida. Estou sempre explorando novas trilhas entre desenvolvimento, dados e inteligencia artificial.',
  featuredAchievements: [
    {
      trigger: 'mock-featured-1',
      key: 'profile-highlight-1',
      title: 'Assista sua primeira aula',
      description: 'Assisti sua primeira aula.',
      imageUrl: '',
      imageSrc: resolveAchievementImageSrc(null),
      unlockedAt: '2026-03-04T14:20:00.000Z',
      unlockedLabel: 'Primeira aula assistida',
      currentValue: 12,
      targetValue: 12,
      progressPercentage: 100,
      isUnlocked: true,
    },
    {
      trigger: 'mock-featured-2',
      key: 'profile-highlight-2',
      title: 'Vitrine de Perfil',
      description: 'Visitou seu perfil por 7 dias consecutivos.',
      imageUrl: '',
      imageSrc: resolveAchievementImageSrc(null),
      unlockedAt: '2026-02-17T09:15:00.000Z',
      unlockedLabel: 'Escolhida para vitrine',
      currentValue: 8,
      targetValue: 8,
      progressPercentage: 100,
      isUnlocked: true,
    },
    {
      trigger: 'mock-featured-3',
      key: 'profile-highlight-3',
      title: 'Terminou o primeiro curso',
      description: 'Concluiu o primeiro curso da plataforma.',
      imageUrl: '',
      imageSrc: resolveAchievementImageSrc(null),
      unlockedAt: '2026-01-28T18:40:00.000Z',
      unlockedLabel: 'Primeiro curso concluido',
      currentValue: 5,
      targetValue: 5,
      progressPercentage: 100,
      isUnlocked: true,
    },
  ],
}

const socialProfileMockContentById: Record<string, SocialProfileMockContent> = {
  default: defaultSocialProfileContent,
}

export function getSocialProfileMockContent(userId: string | undefined) {
  if (!userId) {
    return socialProfileMockContentById.default
  }

  return socialProfileMockContentById[userId] ?? socialProfileMockContentById.default
}
