export function getSocialProfileOptionLabel(
  value: string | null | undefined,
  options: { label: string; value: string }[],
  fallback: string
) {
  if (!value) return fallback

  return options.find((option) => option.value === value)?.label ?? value
}

export function formatSocialProfileDateTime(
  value: string | null,
  fallback = 'Data não encontrada'
) {
  if (!value) return fallback

  const parsedDate = new Date(value)

  if (Number.isNaN(parsedDate.getTime())) {
    return fallback
  }

  return new Intl.DateTimeFormat('pt-BR', {
    dateStyle: 'medium',
    timeStyle: 'short',
  }).format(parsedDate)
}

export function getSocialProfileWatchPath(
  courseId: string,
  lessonYoutubeLink: string | null | undefined
) {
  const normalizedLessonLink = lessonYoutubeLink?.trim()

  if (!courseId || !normalizedLessonLink) {
    return null
  }

  return `/cursos/${courseId}/${normalizedLessonLink}`
}

export function getSocialProfileInitials(name: string) {
  const parts = name.trim().split(/\s+/).filter(Boolean).slice(0, 2)

  if (!parts.length) return 'U'

  return parts.map((part) => part[0]?.toUpperCase() ?? '').join('')
}
