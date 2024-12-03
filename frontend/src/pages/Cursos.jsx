import CardCurso from '@/components/ui/curso/cardcurso'
import Pagination from '@/components/ui/pagination'
import { Seletor } from '@/components/ui/dropdown/seletor'
import StatusMessage from '@/components/ui/statusMessage'
import { Dropdown } from '@/components/ui/dropdown/dropdown'
import { Titulo } from '@/components/ui/titulo'
import { useState, useEffect } from 'react'
import SkeletonLoading from '@/components/ui/curso/skeletonLoading'
import { useCoursesData } from '@/hooks/useCoursesData'
import { useCoursesProgress } from '@/hooks/useCourseProgress'
import { useParams } from 'react-router-dom'

const tipoCurso = 'Todos os cursos'

export default function Cursos() {
  const { slug } = useParams()
  const [page, setPage] = useState(0)
  const [selectedCategory, setSelectedCategory] = useState(null)
  const size = 8
  const {
    categories,
    courses,
    hasMoreCourses,
    loading,
    error,
    fetchData,
    setCurrentSlug,
  } = useCoursesData(slug, page, size, setPage)
  const { fetchStartCourse } = useCoursesProgress()

  const handleShowAllCourses = () => {
    setSelectedCategory(null)
    setCurrentSlug(null)
    setPage(0)
    window.history.pushState({}, '', '/categorias')
  }

  const handleCategorySelect = (slug) => {
    setSelectedCategory(slug)
    setPage(0)
    window.history.pushState({}, '', `/categorias/${slug}`)
  }

  const handleStartCourse = (id) => {
    console.log('ID: ', id)
    fetchStartCourse(id, page, size)
  }

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage((prevPage) => prevPage - 1)
    }
  }

  const handleNextPage = () => {
    setPage((prevPage) => prevPage + 1)
  }

  useEffect(() => {
    setSelectedCategory(slug)
  }, [slug])

  return (
    <>
      <Titulo titulo={`IMO / ${tipoCurso}`} />
      <div className="min-h-screen flex flex-row w-full">
        <div className="w-1/3 p-12">
          <Seletor
            id="allLessons"
            label={'text-xl font-semibold'}
            conteudo={'Todos os cursos'}
            onShowAllCourses={handleShowAllCourses}
            isSelected={!selectedCategory}
          ></Seletor>
          <Dropdown
            categorias={categories}
            onCategorySelect={handleCategorySelect}
            selectedCategory={selectedCategory}
            url="categorias"
          ></Dropdown>
        </div>
        <div className="w-2/3 p-12">
          <h5 className="font-semibold text-xl mb-10 text-white">
            {tipoCurso}
          </h5>
          <div className="flex flex-row flex-wrap p-18 w-auto max-w-full">
            <StatusMessage error={error} />
            {loading && (
              Array.from({ length: size }).map((index) => (
                <SkeletonLoading key={index} />
              ))
            )}
            {!loading &&
              !error &&
              courses.map((curso) => (
                <CardCurso
                  key={curso.id}
                  idCurso={curso.id}
                  nomeCurso={curso.name}
                  notaCurso="5.0"
                  avaliacoesCurso="80"
                  fotoCurso={`https://img.youtube.com/vi/${curso.firstLessonYoutubeId}/maxresdefault.jpg`}
                  descricaoCurso={curso.description}
                  conteudo={curso.name}
                  quantidade={curso.totalLessons}
                  codigo={curso.slugCourse}
                  codAula={curso.firstLessonYoutubeId}
                  onStart={handleStartCourse}
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
