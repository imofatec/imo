import PaginationControls from '@/components/AllCourses/PaginationControls'
import MyCoursesContent from '@/components/MyCourses/MyCoursesContent'
import MyCoursesFilter from '@/components/MyCourses/MyCoursesFilter'
import { useMyCoursesPage } from '@/hooks/useMyCoursesPage'

export default function MyCoursesPage() {
  const {
    title,
    page,
    pageSize,
    selectedFilter,
    setSelectedFilter,
    isContributionFilter,
    contributedCourses,
    filteredProgress,
    currentLoading,
    currentError,
    isPrevDisabled,
    isNextDisabled,
    goToPreviousPage,
    goToNextPage,
  } = useMyCoursesPage()

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="max-w-8xl mx-auto flex w-full items-center px-4 py-5 sm:px-6 lg:px-8">
          <h1 className="pl-3 text-2xl font-bold text-white">{title}</h1>
          <MyCoursesFilter selectedFilter={selectedFilter} onSelect={setSelectedFilter} />
        </div>
      </section>

      <section className="flex justify-center px-4 py-8 sm:px-6 lg:px-8">
        <div className="max-w-8xl w-full items-center justify-center">
          <div className="min-h-130 items-center justify-center">
            {isContributionFilter ? (
              <MyCoursesContent
                items={contributedCourses}
                loading={currentLoading}
                error={currentError}
                pageSize={pageSize}
                mode="courses"
              />
            ) : (
              <MyCoursesContent
                items={filteredProgress}
                loading={currentLoading}
                error={currentError}
                pageSize={pageSize}
                mode="progress"
              />
            )}
          </div>

          <PaginationControls
            page={page + 1}
            isPrevDisabled={isPrevDisabled}
            isNextDisabled={isNextDisabled}
            onPrev={goToPreviousPage}
            onNext={goToNextPage}
          />
        </div>
      </section>
    </main>
  )
}
