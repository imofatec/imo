import type { MyCoursesFilter } from '@/components/MyCourses/MyCoursesFilter'
import { useUser } from '@/contexts/UserContext'
import { useCourses } from '@/hooks/useCourses'
import { useUserProgress } from '@/hooks/useUserProgress'
import { useEffect, useMemo, useState } from 'react'

const PAGE_SIZE = 10

function getTitle(filter: MyCoursesFilter) {
  switch (filter) {
    case 'FINALIZADOS':
      return 'Cursos Finalizados'
    case 'EM_PROGRESSO':
      return 'Cursos em Progresso'
    case 'CONTRIBUICAO':
      return 'Cursos em Contribuição'
    default:
      return 'Meus Cursos'
  }
}

export function useMyCoursesPage() {
  const [page, setPage] = useState(0)
  const [selectedFilter, setSelectedFilter] = useState<MyCoursesFilter>('TODOS')
  const { user, loading: userLoading } = useUser()

  const {
    userProgress,
    loading: userProgressLoading,
    error: userProgressError,
  } = useUserProgress({
    page,
    size: PAGE_SIZE,
  })

  const {
    courses: contributedCourses,
    loading: contributedCoursesLoading,
    error: contributedCoursesError,
  } = useCourses({
    contributorId: user?.id,
    page,
    size: PAGE_SIZE,
    enabled: selectedFilter === 'CONTRIBUICAO' && Boolean(user?.id),
  })

  useEffect(() => {
    // eslint-disable-next-line
    setPage(0)
  }, [selectedFilter])

  const filteredProgress = useMemo(() => {
    if (selectedFilter === 'FINALIZADOS') {
      return userProgress.filter((item) => item.progress.status === 'FINISHED')
    }

    if (selectedFilter === 'EM_PROGRESSO') {
      return userProgress.filter((item) => item.progress.status === 'IN_PROGRESS')
    }

    return userProgress
  }, [selectedFilter, userProgress])

  const isContributionFilter = selectedFilter === 'CONTRIBUICAO'
  const currentLoading = isContributionFilter
    ? userLoading || contributedCoursesLoading
    : userProgressLoading
  const currentError = isContributionFilter ? contributedCoursesError : userProgressError
  const currentItemsLength = isContributionFilter
    ? contributedCourses.length
    : filteredProgress.length

  return {
    title: getTitle(selectedFilter),
    page,
    pageSize: PAGE_SIZE,
    selectedFilter,
    setSelectedFilter,
    isContributionFilter,
    contributedCourses,
    filteredProgress,
    currentLoading,
    currentError,
    isPrevDisabled: page === 0,
    isNextDisabled: currentItemsLength < PAGE_SIZE,
    goToPreviousPage: () => setPage((currentPage) => Math.max(currentPage - 1, 0)),
    goToNextPage: () => setPage((currentPage) => currentPage + 1),
  }
}
