import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { Category } from '@/types/category'

export function useCategories() {
  const [categories, setCategories] = useState<Category[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchCategories = useCallback(async () => {
    setLoading(true)

    const [err, response] = await safeAwait(
      authAxiosInstance.get<Category[]>('/api/course/categories')
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar categorias')
      setLoading(false)
      return
    }

    setCategories(response.data || [])
    setError(null)
    setLoading(false)
  }, [])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchCategories()
  }, [fetchCategories])

  return { categories, loading, error, refetch: fetchCategories }
}
