import { useState, useEffect } from 'react'
import CoursesContent from '@/components/AllCourses/CoursesContent'
import FilterDrawer from '@/components/AllCourses/FilterDrawer'
import PaginationControls from '@/components/AllCourses/PaginationControls'
import { useCourses } from '@/hooks/useCourses'
import { useCategories } from '@/hooks/useCategories'
import { useRequestErrorToast } from '@/lib/requestToast'
import { getCategorySlugFromSearchTerm } from '@/lib/categorySearch'
import { useParams, useSearchParams } from 'react-router-dom'

const PAGE_SIZE = 10

export default function AllCoursesPage() {
  const [page, setPage] = useState(0)
  const { categorySlug } = useParams()
  const [searchParams] = useSearchParams()
  const searchName = searchParams.get('name') ?? undefined
  const categoryBySearchName = searchName ? getCategorySlugFromSearchTerm(searchName) : undefined
  const resolvedCategorySlug = categorySlug ?? categoryBySearchName
  const resolvedSearchName = resolvedCategorySlug ? undefined : searchName
  const { categories, error: categoriesError } = useCategories()
  const { courses, loading, error } = useCourses({
    matchType: resolvedSearchName ? 'CONTAINS' : undefined,
    categorySlug: resolvedCategorySlug,
    name: resolvedSearchName,
    page,
    size: PAGE_SIZE,
  })

  useRequestErrorToast(error, { id: 'all-courses-error' })
  useRequestErrorToast(categoriesError, { id: 'categories-error' })

  const isPrevDisabled = page === 0
  const isNextDisabled = courses.length < PAGE_SIZE

  useEffect(() => {
    // eslint-disable-next-line
    setPage(0)
  }, [resolvedCategorySlug, resolvedSearchName])

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }, [page])

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="max-w-8xl mx-auto flex w-full items-center px-4 py-5 sm:px-6 lg:px-8">
          <h1 className="pl-3 text-2xl font-bold text-white">Cursos</h1>
          <FilterDrawer categories={categories} selectedCategory={resolvedCategorySlug || null} />
        </div>
      </section>

      <section className="flex justify-center px-4 py-8 sm:px-6 lg:px-8">
        <div className="max-w-8xl w-full items-center justify-center">
          <div className="min-h-130 items-center justify-center">
            <CoursesContent
              courses={courses}
              loading={loading}
              error={error}
              pageSize={PAGE_SIZE}
            />
          </div>

          <PaginationControls
            page={page + 1}
            isPrevDisabled={isPrevDisabled}
            isNextDisabled={isNextDisabled}
            onPrev={() => setPage((currentPage) => Math.max(currentPage - 1, 0))}
            onNext={() => setPage((currentPage) => currentPage + 1)}
          />
        </div>
      </section>
    </main>
  )
}
