import CardCurso from '@/components/ui/curso/cardcurso'
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

  const fetchData = async (slug) => {
    setError(false)
    setLoading(true)
    try {
      const responseCategories = await axios.get(
        '/api/courses/get-all/categories',
      )
      setCategories(responseCategories.data)

      const responseCourses = slug
        ? await axios.get(`/api/courses/get-all/overviews/${slug}`)
        : await axios.get('/api/courses/get-all/overviews')

      setCourses(responseCourses.data)
    } catch (error) {
      setError(true)
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchData(slug)
  }, [slug])

  const handleShowAllCourses = () => {
    window.history.pushState({}, '', '/categorias')
    fetchData(null)
  }

  let notaCurso = '5.0'
  let avaliacoesCurso = '80'

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
            {loading ? (
              <p className="text-white text-center mt-10">Carregando...</p>
            ) : error ? (
              <p className="text-white text-center mt-10">
                O servidor encontrou um erro ao exibir os cursos.
              </p>
            ) : (
              courses.map((curso) => (
                <CardCurso
                  key={curso.id}
                  nomeCurso={curso.name}
                  notaCurso={notaCurso}
                  avaliacoesCurso={avaliacoesCurso}
                  fotoCurso={`https://img.youtube.com/vi/${curso.firstLessonYoutubeId}/maxresdefault.jpg`}
                  descricaoCurso={curso.description}
                  conteudo={curso.name}
                  quantidade={curso.totalLessons}
                  codigo={curso.slugCourse}
                  codAula={curso.firstLessonYoutubeId}
                />
              ))
            )}
          </div>
        </div>
      </div>
    </>
  )
}
