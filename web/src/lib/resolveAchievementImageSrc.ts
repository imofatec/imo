import { baseURL } from '@/api/environment'

function hasAbsoluteScheme(value: string) {
  return /^[a-z][a-z\d+.-]*:/i.test(value)
}

export function resolveAchievementImageSrc(value: string | null) {
  const trimmedValue = value?.trim()

  if (!trimmedValue) {
    return null
  }

  if (hasAbsoluteScheme(trimmedValue)) {
    return trimmedValue
  }

  const trimmedBaseURL = baseURL?.trim().replace(/\/+$/, '')

  if (!trimmedBaseURL) {
    return trimmedValue
  }

  return trimmedValue.startsWith('/')
    ? `${trimmedBaseURL}${trimmedValue}`
    : `${trimmedBaseURL}/${trimmedValue}`
}
