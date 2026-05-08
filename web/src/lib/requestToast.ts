import axios from 'axios'
import { useEffect, useRef } from 'react'
import { toast } from 'sonner'

type RequestToastOptions = {
  title?: string
  id?: string
  duration?: number
}

export function getRequestErrorMessage(error: unknown, fallbackMessage: string) {
  if (axios.isAxiosError(error)) {
    return error.response?.data?.message || error.message || fallbackMessage
  }

  if (error instanceof Error) {
    return error.message || fallbackMessage
  }

  return fallbackMessage
}

export function showRequestErrorToast(
  error: unknown,
  fallbackMessage: string,
  options: RequestToastOptions = {}
) {
  const message = getRequestErrorMessage(error, fallbackMessage)

  toast.error(options.title || 'Ocorreu um erro', {
    id: options.id,
    description: message,
    duration: options.duration ?? 5000,
  })

  return message
}

export function useRequestErrorToast(
  errorMessage: string | null,
  options: RequestToastOptions = {}
) {
  const lastShownMessageRef = useRef<string | null>(null)

  useEffect(() => {
    if (!errorMessage || lastShownMessageRef.current === errorMessage) return

    lastShownMessageRef.current = errorMessage

    toast.error(options.title || 'Ocorreu um erro', {
      id: options.id,
      description: errorMessage,
      duration: options.duration ?? 5000,
    })
  }, [errorMessage, options.duration, options.id, options.title])

  useEffect(() => {
    if (!errorMessage) {
      lastShownMessageRef.current = null
    }
  }, [errorMessage])
}
