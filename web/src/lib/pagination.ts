export type PaginationInfo = {
  currentPage: number
  pageSize: number
  totalItems: number
  totalPages: number
  remainingPages: number
}

export type PaginatedResponse<T> = {
  data: T
  pagination: PaginationInfo
}

function isRecord(value: unknown): value is Record<string, unknown> {
  return typeof value === 'object' && value !== null
}

function isPaginationInfo(value: unknown): value is PaginationInfo {
  if (!isRecord(value)) {
    return false
  }

  return (
    typeof value.currentPage === 'number' &&
    typeof value.pageSize === 'number' &&
    typeof value.totalItems === 'number' &&
    typeof value.totalPages === 'number' &&
    typeof value.remainingPages === 'number'
  )
}

export function extractPaginatedArray<T>(payload: unknown): {
  data: T[]
  pagination: PaginationInfo | null
} {
  if (Array.isArray(payload)) {
    return {
      data: payload as T[],
      pagination: null,
    }
  }

  if (!isRecord(payload) || !('data' in payload)) {
    if (isRecord(payload) && 'items' in payload) {
      const maybeItems = payload.items
      const data = Array.isArray(maybeItems) ? (maybeItems as T[]) : []
      const pagination = isPaginationInfo(payload.pagination) ? payload.pagination : null

      return {
        data,
        pagination,
      }
    }

    return {
      data: [],
      pagination: null,
    }
  }

  const maybeData = payload.data
  const maybeItems = 'items' in payload ? payload.items : undefined
  const data = Array.isArray(maybeData)
    ? (maybeData as T[])
    : Array.isArray(maybeItems)
      ? (maybeItems as T[])
      : []
  const pagination = isPaginationInfo(payload.pagination) ? payload.pagination : null

  return {
    data,
    pagination,
  }
}
