import { baseURL } from './environment'

function buildEventSourceUrl(path: string) {
  const trimmedBaseURL = baseURL?.trim().replace(/\/+$/, '')
  const normalizedPath = path.startsWith('/') ? path : `/${path}`

  return trimmedBaseURL ? `${trimmedBaseURL}${normalizedPath}` : normalizedPath
}

export function createAuthEventSource(getPath: (token: string) => string) {
  const token = localStorage.getItem('token')?.trim()

  if (!token) {
    return null
  }

  return new EventSource(buildEventSourceUrl(getPath(token)))
}
