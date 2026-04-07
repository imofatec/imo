import { useCallback, useEffect, useMemo, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { User } from '@/types/user'

type UseUsersByIdsParams = {
  enabled?: boolean
}

export function useUsersByIds(ids: string[], { enabled = true }: UseUsersByIdsParams = {}) {
  const [users, setUsers] = useState<User[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const uniqueIds = useMemo(() => Array.from(new Set(ids.filter(Boolean))), [ids])

  const fetchUsers = useCallback(async () => {
    if (!enabled || uniqueIds.length === 0) {
      setUsers([])
      setError(null)
      setLoading(false)
      return
    }

    setLoading(true)

    const query = new URLSearchParams({
      ids: uniqueIds.join(','),
    }).toString()

    const [err, response] = await safeAwait(authAxiosInstance.get<User[]>(`/api/user/ids?${query}`))

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar usuários')
      setLoading(false)
      return
    }

    setUsers(response.data || [])
    setError(null)
    setLoading(false)
  }, [enabled, uniqueIds])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchUsers()
  }, [fetchUsers])

  const usersById = useMemo(
    () =>
      users.reduce<Record<string, User>>((acc, user) => {
        acc[user.id] = user
        return acc
      }, {}),
    [users]
  )

  return { users, usersById, loading, error, refetch: fetchUsers }
}
