import CardCurso from '@/components/ui/curso/cardcurso'
import { Dropdown } from '@/components/ui/dropdown/dropdown'
import { Titulo } from '@/components/ui/titulo'
import { useState, useEffect } from 'react'
import axios from 'axios'
import React from 'react'

let tipoCurso = 'Todos os cursos'

export default function Cursos() {
  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)
  const [categories, setCategories] = useState([])

  const fetchCategory = async () => {
    setError(false)
    setLoading(true)
    try {
      const response = await axios.get('/api/categories/get-all')
      setCategories(response.data)
    } catch (error) {
      setError(true)
      console.error(error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchCategory()
  }, [])

  console.log(categories)

  let notaCurso = '5.0'
  let avaliacoesCurso = '80'

  return (
    <>
      <Titulo titulo={tipoCurso} />
      <div className="flex flex-row w-full">
        <div className="w-1/3 p-12">
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
              categories.map((categoria) =>
                categoria.courses.map((curso) => (
                  <CardCurso
                    key={curso.id}
                    nomeCurso={curso.name}
                    notaCurso={notaCurso}
                    avaliacoesCurso={avaliacoesCurso}
                    fotoCurso={`https://img.youtube.com/vi/${curso.firstLessonYoutubeId}/hqdefault.jpg`}
                    descricaoCurso={curso.description}
                    conteudo={categoria.name}
                    quantidade={curso.totalLessons}
                  />
                )),
              )
            )}
          </div>
        </div>
      </div>
    </>
  )
}
