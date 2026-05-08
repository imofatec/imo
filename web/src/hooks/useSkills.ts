import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { Skill } from '@/types/skill'

export function useSkills(categorySlug?: string) {
  const [skills, setSkills] = useState<Skill[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchSkills = useCallback(async () => {
    if (!categorySlug) {
      setSkills([])
      setError(null)
      setLoading(false)
      return
    }

    setLoading(true)

    const [err, response] = await safeAwait(
      authAxiosInstance.get<Skill[]>(`/api/skill/${categorySlug}`)
    )

    if (err || !response) {
      setSkills([])
      setError(err?.message || 'Erro ao buscar skills')
      setLoading(false)
      return
    }

    setSkills(response.data || [])
    setError(null)
    setLoading(false)
  }, [categorySlug])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchSkills()
  }, [fetchSkills])

  return { skills, loading, error, refetch: fetchSkills }
}
