import CardCurso from '@/components/ui/curso/cardcurso'
import SkeletonLoading from '@/components/ui/curso/skeletonLoading'
import { DropdownSelect } from '@/components/ui/dropdownselect'
import CategorySelector from '@/components/ui/mycourses/CategorySelector'
import Pagination from '@/components/ui/pagination'
import StatusMessage from '@/components/ui/statusMessage'
import { Titulo } from '@/components/ui/titulo'
import { useAuth } from '@/context/useAuth'
import { useMyCourses } from '@/hooks/useMyCourses'
import { useSortedCourses } from '@/hooks/useSortedCourses'
import { useUserContributions } from '@/hooks/useUserContributions'
import { useState } from 'react'

export default function MyCourses() {
  const size = 8
  const [selectedCategory, setSelectedCategory] = useState(null)
  const [page, setPage] = useState(0)
  const [order, setOrder] = useState(null)
  const [viewMode, setViewMode] = useState('courses')
  const { myCourses, loading, error, hasMoreCourses } = useMyCourses(
    selectedCategory,
    page,
    size,
  )
  const sortedCourses = useSortedCourses(myCourses, order)

  const contributions = useUserContributions(page, size)

  const { authLoading } = useAuth()

  const handleShowAllCourses = () => {
    setSelectedCategory(null)
    setPage(0)
    setViewMode('courses')
    window.history.pushState({}, '', '/user/cursos')
  }

  const handleShowContributions = () => {
    setSelectedCategory(null)
    setPage(0)
    setViewMode('contributions')
    window.history.pushState({}, '', '/user/cursos/submissoes')
  }

  const handleCategorySelect = (slug) => {
    setSelectedCategory(slug)
    setPage(0)
  }

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage((prevPage) => prevPage - 1)
    }
  }

  const handleNextPage = () => {
    setPage((prevPage) => prevPage + 1)
  }

  const handleSelectOrder = (order) => {
    setOrder(order)
  }

  return (
    <>
      <Titulo titulo={`IMO / Meus Cursos`} />
      <div className="min-h-screen flex flex-row w-full">
        <div className="w-1/3 p-12">
          <CategorySelector
            selectedCategory={selectedCategory}
            onCategorySelect={handleCategorySelect}
            onShowAllCourses={handleShowAllCourses}
            onShowContributions={handleShowContributions}
            viewMode={viewMode}
          />
        </div>

        <div className="w-2/3 p-12">
          <div className="flex flex-row w-full">
            <div className="flex flex-col w-1/2">
              <h5 className="font-semibold text-xl mb-10 text-white">
                {viewMode === 'courses' ? 'Meus Cursos' : 'Minhas Contribuições'}
              </h5>
            </div>
            <div className="flex flex-col w-1/2 items-end">
              <DropdownSelect
                onSelectOrder={handleSelectOrder}
              ></DropdownSelect>
            </div>
          </div>

          <div className="flex flex-row flex-wrap p-18 w-auto max-w-full">
            <StatusMessage error={error} />
            {(loading || authLoading) &&
              Array.from({ length: size }).map((index) => (
                <SkeletonLoading key={index} />
              ))}
            {viewMode === 'courses' && !loading && !error &&
              sortedCourses.map((curso) => (
                <CardCurso
                  key={curso.courseOverview.id}
                  idCurso={curso.courseOverview.id}
                  nomeCurso={curso.courseOverview.name}
                  notaCurso="5.0"
                  avaliacoesCurso="80"
                  fotoCurso={`https://img.youtube.com/vi/${curso.courseOverview.firstLessonYoutubeId}/maxresdefault.jpg`}
                  descricaoCurso={curso.courseOverview.description}
                  conteudo={curso.courseOverview.name}
                  quantidade={curso.courseOverview.totalLessons}
                  codigo={curso.courseOverview.slugCourse}
                  codAula={curso.courseOverview.firstLessonYoutubeId}
                  nameButton="Retomar curso"
                  isEditing={false}
                />
              ))}

            {viewMode === 'contributions' && !loading && !error &&
              contributions.map((curso) => (
                <CardCurso
                  key={curso.id}
                  idCurso={curso.id}
                  nomeCurso={curso.name}
                  notaCurso="5.0"
                  avaliacoesCurso="80"
                  fotoCurso={`https://img.youtube.com/vi/${curso.lessons[0]?.youtubeLink}/maxresdefault.jpg`}
                  descricaoCurso={curso.description}
                  conteudo={curso.name}
                  quantidade={curso.totalLessons}
                  codigo={curso.slugCourse}
                  codAula={curso.lessons[0]?.youtubeLink}
                  nameButton="Ver Contribuição"
                  isEditing={true}
                />
              ))}
          </div>

          <Pagination
            page={page}
            hasMoreCourses={hasMoreCourses}
            onPrevious={handlePreviousPage}
            onNext={handleNextPage}
          />
        </div>
      </div>
    </>
  )
}
