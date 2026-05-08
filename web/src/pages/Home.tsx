import CourseCard from '@/components/AllCourses/CourseCard'
import SkeletonCourseCard from '@/components/AllCourses/SkeletonCourseCard'
import CategoryChip from '@/components/Home/CategoryChip'
import ContinueLearningCard from '@/components/Home/ContinueLearningCard'
import ContinueLearningSkeleton from '@/components/Home/ContinueLearningSkeleton'
import HomeSectionHeader from '@/components/Home/HomeSectionHeader'
import { categoryOptions } from '@/constants/courseOptions'
import { useContinueProgress } from '@/hooks/useContinueProgress'
import { useCourses } from '@/hooks/useCourses'
import { useRecommendations } from '@/hooks/useRecommendations'
import LinkButton from '@/components/ui/LinkButton'
import { useMemo } from 'react'

function toSlug(value: string) {
  return value
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .toLowerCase()
    .replace(/[^a-z0-9\s-]/g, '')
    .trim()
    .replace(/[\s_]+/g, '-')
}

function getCategoryTerm(value: string) {
  const option = categoryOptions.find((category) => category.value === value)
  const name = option?.label ?? value

  return {
    name,
    slug: toSlug(name),
  }
}

function getCategoryPath(value: string) {
  return `/categorias/${getCategoryTerm(value).slug}`
}

export default function HomePage() {
  const { continueProgress, loading: continueLoading } = useContinueProgress()
  const { courses, loading: coursesLoading, error: coursesError } = useCourses({ page: 0, size: 6 })
  const {
    courses: recommendedCourses,
    loading: recommendationsLoading,
    error: recommendationsError,
    isAuthenticated,
  } = useRecommendations()

  const continueCourse = useMemo(() => {
    if (!continueProgress) {
      return null
    }

    const firstLesson = continueProgress.lessons[0]
    const lessonLink = firstLesson?.youtubeLink || continueProgress.course.firstLessonYoutubeLink
    const thumbnailUrl = `https://img.youtube.com/vi/${continueProgress.course.firstLessonYoutubeLink}/maxresdefault.jpg`

    return {
      title: continueProgress.course.name.name,
      instructor: continueProgress.course.category.name,
      progress: continueProgress.summary.completionPercentage,
      imageUrl: thumbnailUrl,
      to: lessonLink ? `/cursos/${continueProgress.course.id}/${lessonLink}` : '/user/cursos',
    }
  }, [continueProgress])

  const trendingCourses = useMemo(() => courses.slice(0, 6), [courses])
  const shouldShowCoursesSkeleton = coursesLoading || Boolean(coursesError)
  const shouldShowRecommendationsFallback = Boolean(recommendationsError)

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10 bg-linear-to-r from-[#130738] via-[#0E0530] to-[#0B0326]">
        <div className="mx-auto w-full max-w-6xl px-4 py-10 sm:px-6 lg:px-8">
          <p className="text-cyan text-xs font-medium tracking-[0.2em] uppercase">Início</p>
          <h1 className="mt-3 max-w-2xl text-3xl leading-tight font-bold text-white md:text-4xl">
            Seu aprendizado continua aqui
          </h1>
          <p className="mt-3 max-w-2xl text-sm text-white/70 md:text-base">
            Explore cursos, continue seu aprendizado em andamento e descubra conteúdos de alta
            qualidade!
          </p>
          <div className="mt-6">
            <LinkButton
              to="/categorias"
              variant="cyanOutline"
              className="text-cyan border-cyan/40 mt-0! w-auto rounded-full border px-5 py-2.5"
            >
              Ver todos os cursos
            </LinkButton>
          </div>
        </div>
      </section>

      <section className="mx-auto w-full max-w-6xl space-y-10 px-4 py-8 sm:px-6 lg:px-8">
        {continueLoading ? (
          <div>
            <HomeSectionHeader title="Continue seu aprendizado" />
            <ContinueLearningSkeleton />
          </div>
        ) : continueCourse ? (
          <div>
            <HomeSectionHeader title="Continue seu aprendizado" />
            <ContinueLearningCard {...continueCourse} />
          </div>
        ) : null}

        <div>
          <HomeSectionHeader title="Categorias" to="/categorias" />
          <div className="flex flex-wrap gap-2">
            {categoryOptions.map((category) => (
              <CategoryChip
                key={category.value}
                label={category.label}
                to={getCategoryPath(category.value)}
              />
            ))}
          </div>
        </div>

        <div>
          <HomeSectionHeader title="Cursos recomendados" to="/categorias" />

          {recommendationsLoading ? (
            <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
              {Array.from({ length: 4 }).map((_, index) => (
                <SkeletonCourseCard key={`recommended-skeleton-${index}`} />
              ))}
            </div>
          ) : recommendedCourses.length > 0 ? (
            <div className="home-trending-scrollbar flex gap-4 overflow-x-auto pb-2">
              {recommendedCourses.map((course) => (
                <div key={course.id} className="w-72 shrink-0">
                  <CourseCard
                    course={course}
                    actionLabel="Ver mais"
                    modalActionLabel="Inscrever-se"
                  />
                </div>
              ))}
            </div>
          ) : !isAuthenticated ? (
            <div className="rounded-2xl border border-white/10 bg-white/5 p-5">
              <p className="text-sm text-white/75">
                Entre na sua conta para receber recomendações alinhadas com suas skills e
                interesses.
              </p>
              <LinkButton
                to="/login"
                variant="cyanOutline"
                className="text-cyan border-cyan/40 mt-4! w-auto rounded-full border px-4 py-2"
              >
                Fazer login
              </LinkButton>
            </div>
          ) : shouldShowRecommendationsFallback ? (
            <div className="rounded-2xl border border-white/10 bg-white/5 p-5">
              <p className="text-sm text-white/75">
                Não foi possível carregar suas recomendações agora. Enquanto isso, você pode
                explorar o catálogo completo.
              </p>
              <LinkButton
                to="/categorias"
                variant="cyanOutline"
                className="text-cyan border-cyan/40 mt-4! w-auto rounded-full border px-4 py-2"
              >
                Explorar catálogo
              </LinkButton>
            </div>
          ) : (
            <div className="rounded-2xl border border-white/10 bg-white/5 p-5">
              <p className="text-sm text-white/75">
                Ainda não temos recomendações para você. Explore o catálogo para encontrar cursos e
                ajudar o sistema a entender melhor seus interesses.
              </p>
              <LinkButton
                to="/categorias"
                variant="cyanOutline"
                className="text-cyan border-cyan/40 mt-4! w-auto rounded-full border px-4 py-2"
              >
                Explorar catálogo
              </LinkButton>
            </div>
          )}
        </div>

        <div>
          <HomeSectionHeader title="Em alta" to="/categorias" />

          {shouldShowCoursesSkeleton ? (
            <div className="home-trending-scrollbar flex gap-4 overflow-x-auto pb-2">
              {Array.from({ length: 4 }).map((_, index) => (
                <div key={`trending-skeleton-${index}`} className="w-72 shrink-0">
                  <SkeletonCourseCard />
                </div>
              ))}
            </div>
          ) : trendingCourses.length > 0 ? (
            <div className="home-trending-scrollbar flex gap-4 overflow-x-auto pb-2">
              {trendingCourses.map((course) => (
                <div key={course.id} className="w-72 shrink-0">
                  <CourseCard
                    course={course}
                    actionLabel="Ver mais"
                    modalActionLabel="Inscrever-se"
                  />
                </div>
              ))}
            </div>
          ) : (
            <p className="text-sm text-white/70">Nenhum curso encontrado.</p>
          )}
        </div>

        <section className="border-cyan/20 bg-cyan/10 rounded-3xl border p-6">
          <p className="text-sm text-white/80">Quer explorar mais conteúdos e cursos completos?</p>
          <div className="mt-4">
            <LinkButton
              to="/categorias"
              variant="cyanOutline"
              className="text-cyan border-cyan/40 mt-0! w-auto rounded-full border px-4 py-2"
            >
              Acesse o catálogo completo de todos os cursos!
            </LinkButton>
          </div>
        </section>
      </section>
    </main>
  )
}
