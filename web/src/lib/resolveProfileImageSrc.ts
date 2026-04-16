import { baseURL } from '@/api/environment'

function hasAbsoluteScheme(value: string) {
  return /^[a-z][a-z\d+.-]*:/i.test(value)
}

export function resolveProfileImageSrc(value: string | null) {
  const trimmedValue = value?.trim()

  if (!trimmedValue) {
    return null
  }

  if (hasAbsoluteScheme(trimmedValue)) {
    return trimmedValue
  }

  const trimmedBaseURL = baseURL?.trim().replace(/\/+$/, '')

  return `${trimmedBaseURL}/uploads/${trimmedValue}`
}
