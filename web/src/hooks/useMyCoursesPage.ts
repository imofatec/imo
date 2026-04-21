import { myCoursesFilters, type MyCoursesFilter } from '@/components/MyCourses/MyCoursesFilter'
import { useUser } from '@/contexts/UserContext'
import { useCourses } from '@/hooks/useCourses'
import { useUserProgress } from '@/hooks/useUserProgress'
import type { Course } from '@/types/course'
import { useEffect, useMemo, useState } from 'react'
import { useSearchParams } from 'react-router-dom'

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
  const [searchParams, setSearchParams] = useSearchParams()
  const queryFilter = searchParams.get('filtro')
  const selectedFilter = myCoursesFilters.find((filter) => filter === queryFilter) ?? 'TODOS'

  const [page, setPage] = useState(0)
  const { user, loading: userLoading } = useUser()

  const {
    userProgress,
    pagination: userProgressPagination,
    loading: userProgressLoading,
    error: userProgressError,
  } = useUserProgress({
    page,
    size: PAGE_SIZE,
  })

  const {
    courses: contributedCourses,
    pagination: contributedCoursesPagination,
    loading: contributedCoursesLoading,
    error: contributedCoursesError,
  } = useCourses({
    contributorId: user?.id,
    page,
    size: PAGE_SIZE,
    enabled: ['TODOS', 'CONTRIBUICAO'].includes(selectedFilter) && Boolean(user?.id),
  })

  useEffect(() => {
    // eslint-disable-next-line
    setPage(0)
  }, [selectedFilter])

  function setSelectedFilter(filter: MyCoursesFilter) {
    if (filter === 'TODOS') {
      setSearchParams({})
      return
    }

    setSearchParams({ filtro: filter })
  }

  const filteredProgress = useMemo(() => {
    if (selectedFilter === 'FINALIZADOS') {
      return userProgress.filter((item) => item.progress.status === 'FINISHED')
    }

    if (selectedFilter === 'EM_PROGRESSO') {
      return userProgress.filter((item) => item.progress.status === 'IN_PROGRESS')
    }

    return userProgress
  }, [selectedFilter, userProgress])

  const allOwnedCourses = useMemo(() => {
    const uniqueCoursesById = new Map<string, Course>()

    userProgress.forEach((item) => {
      uniqueCoursesById.set(item.course.id, item.course)
    })

    contributedCourses.forEach((course) => {
      uniqueCoursesById.set(course.id, course)
    })

    return Array.from(uniqueCoursesById.values())
  }, [userProgress, contributedCourses])

  const isAllFilter = selectedFilter === 'TODOS'
  const isContributionFilter = selectedFilter === 'CONTRIBUICAO'
  const isCoursesMode = isAllFilter || isContributionFilter

  const currentLoading = isAllFilter
    ? userLoading || userProgressLoading || contributedCoursesLoading
    : isContributionFilter
      ? userLoading || contributedCoursesLoading
      : userProgressLoading

  const currentError = isAllFilter
    ? userProgressError ?? contributedCoursesError
    : isContributionFilter
      ? contributedCoursesError
      : userProgressError

  const hasNextForUserProgress = userProgressPagination
    ? page + 1 < userProgressPagination.totalPages
    : userProgress.length >= PAGE_SIZE

  const hasNextForContributedCourses = contributedCoursesPagination
    ? page + 1 < contributedCoursesPagination.totalPages
    : contributedCourses.length >= PAGE_SIZE

  const isNextDisabled = isAllFilter
    ? !(hasNextForUserProgress || hasNextForContributedCourses)
    : isContributionFilter
      ? !hasNextForContributedCourses
      : !hasNextForUserProgress

  const totalPages = isAllFilter
    ? Math.max(userProgressPagination?.totalPages ?? 1, contributedCoursesPagination?.totalPages ?? 1)
    : isContributionFilter
      ? contributedCoursesPagination?.totalPages ?? 1
      : userProgressPagination?.totalPages ?? 1

  return {
    title: getTitle(selectedFilter),
    page,
    totalPages,
    pageSize: PAGE_SIZE,
    selectedFilter,
    setSelectedFilter,
    isAllFilter,
    isContributionFilter,
    isCoursesMode,
    allOwnedCourses,
    contributedCourses,
    filteredProgress,
    currentLoading,
    currentError,
    isPrevDisabled: page === 0,
    isNextDisabled,
    goToPage: (nextPage: number) => setPage(Math.max(nextPage, 0)),
    goToPreviousPage: () => setPage((currentPage) => Math.max(currentPage - 1, 0)),
    goToNextPage: () => setPage((currentPage) => currentPage + 1),
  }
}
