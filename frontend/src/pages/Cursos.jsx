import CardCurso from '@/components/ui/curso/cardcurso'
import api from "@/api/api";
import Pagination from '@/components/ui/pagination'
import StatusMessage from '@/components/ui/statusMessage'
import { Dropdown } from '@/components/ui/dropdown/dropdown'
import { Titulo } from '@/components/ui/titulo'
import { useState, useEffect } from 'react'
import axios from 'axios'
import React from 'react'
import { useParams } from 'react-router-dom'

let tipoCurso = 'Todos os cursos'

export default function Cursos() {
  const { slug } = useParams()

  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)
  const [categories, setCategories] = useState([])
  const [courses, setCourses] = useState([])
  const [page, setPage] = useState(0)
  const [hasMoreCourses, setHasMoreCourses] = useState(true)
  const size = 10

  const fetchData = async (slug) => {
    setError(false)
    setLoading(true)
    try {
      const responseCategories = await axios.get(
        '/api/courses/get-all/categories',
      )
      setCategories(responseCategories.data)

      const responseCourses = slug
        ? await axios.get(`/api/courses/pagination/get-all/overviews/${slug}`, {params: {page, size}}) 
        : await axios.get('/api/courses/pagination/get-all/overviews', {params: {page, size}})

      setCourses(responseCourses.data)

      if (responseCourses.data.length < size) {
        setHasMoreCourses(false) 
      } else {
        setHasMoreCourses(true) 
      }

    } catch (error) {
      setError(true)
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  const fetchStartCouse = async (id) => {
    setError(false)
    setLoading(true)
    try {
      await api.put(`/api/user/update-progress/${id}`)
    } catch (error) {
      setError(true)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchData(slug)
  }, [slug,page])

  const handleShowAllCourses = () => {
    fetchData(null)
    window.history.pushState({}, '', '/categorias')
  }

  
  const handlePreviousPage = () => {
    if (page > 0) {
      setPage((prevPage) => prevPage - 1)
    }
  }
  
  const handleNextPage = () => {
    setPage((prevPage) => prevPage + 1)
  }

  const handleStartCourse = (id) => {
    fetchStartCouse(id)
  }
  return (
    <>
      <Titulo titulo={tipoCurso} />
      <div className="min-h-screen flex flex-row w-full">
        <div className="w-1/3 p-12">
          <button
            onClick={handleShowAllCourses}
            className=" px-4 py-4 w-full text-white border-t border-white"
          >
            Ver Todos os Cursos
          </button>
          <Dropdown categorias={categories}></Dropdown>
        </div>
        <div className="w-2/3 p-12">
          <h5 className="font-semibold text-xl mb-10 text-white">
            {tipoCurso}
          </h5>
          <div className="flex flex-row flex-wrap p-18 w-auto max-w-full">
            <StatusMessage loading={loading} error={error} />
            {!loading && !error && courses.map((curso) => (
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
            onNext={handleNextPage}/>
        </div>
      </div>
    </>
  )
}
