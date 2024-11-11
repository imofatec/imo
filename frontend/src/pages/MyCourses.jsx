import CardCurso from '@/components/ui/curso/cardcurso'
import CategorySelector from '@/components/ui/mycourses/CategorySelector'
import { useMyCourses } from '@/hooks/useMyCourses'
import { DropdownSelect } from '@/components/ui/dropdownselect'
import Pagination from '@/components/ui/pagination'
import StatusMessage from '@/components/ui/statusMessage'
import { Titulo } from '@/components/ui/titulo'
import { useState } from 'react'
import { useSortedCourses } from '@/hooks/useSortedCourses'

export default function MyCourses() {
  const size = 8
  const [selectedCategory, setSelectedCategory] = useState(null)
  const [page, setPage] = useState(0)
  const [order, setOrder] = useState(null)
  const { myCourses, loading, error, hasMoreCourses } = useMyCourses(
    selectedCategory,
    page,
    size,
  )
  const sortedCourses = useSortedCourses(myCourses, order)

  const handleShowAllCourses = () => {
    setSelectedCategory(null)
    setPage(0)
    window.history.pushState({}, '', '/user/cursos')
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

  const tipoCurso = 'Meus Cursos'

  return (
    <>
      <Titulo titulo={`IMO / Meus Cursos`} />
      <div className="min-h-screen flex flex-row w-full">
        <div className="w-1/3 p-12">
          <CategorySelector
            selectedCategory={selectedCategory}
            onCategorySelect={handleCategorySelect}
            onShowAllCourses={handleShowAllCourses}
          />
        </div>

        <div className="w-2/3 p-12">
          <div className="flex flex-row w-full">
            <div className="flex flex-col w-1/2">
              <h5 className="font-semibold text-xl mb-10 text-white">
                {tipoCurso}
              </h5>
            </div>
            <div className="flex flex-col w-1/2 items-end">
              <DropdownSelect
                onSelectOrder={handleSelectOrder}
              ></DropdownSelect>
            </div>
          </div>

          <div className="flex flex-row flex-wrap p-18 w-auto max-w-full">
            <StatusMessage loading={loading} error={error} />
            {!loading &&
              !error &&
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
                  //onStart={handleStartCourse}
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
