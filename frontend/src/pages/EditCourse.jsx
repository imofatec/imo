import SkeletonCreateCourses from '@/components/skeletons/SkeletonCreateCourses'
import ColInputLabel from '@/components/ui/inputs/colinputlabel'
import ColLargeInput from '@/components/ui/inputs/collargeinput'
import ColSelectLabel from '@/components/ui/inputs/colselectlabel'
import { Titulo } from '@/components/ui/titulo'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { useEffect, useRef, useState } from 'react'
import { Form, useParams } from 'react-router-dom'
import { Button } from '@/components/ui/button'
import { useCourseById } from '@/hooks/useCourseById'
import { updateCourse } from '@/requests/courses/updateCourse'
import EditLesson from '@/components/ui/lesson/editlesson'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export default function EditCourse() {
  const { id } = useParams()
  const { courseById, loading, fetchCourseById } = useCourseById(id)
  const [error, setError] = useState(null)
  const [isLoading, setIsLoading] = useState(false)

  const [formData, setFormData] = useState({
    name: '',
    category: '',
    level: '',
    description: ''
  })

  const updateCourse = async () => {
    const fieldsToUpdate = Object.keys(formData).filter((key) => formData[key] != '')

    if (!fieldsToUpdate) return

    let requestBody = {}

    fieldsToUpdate.forEach((field) => (
      requestBody = { ...requestBody, [field]: formData[field] }
    ))

    const [error] = await safeAwait(
      authAxiosInstance.put(`/api/courses/${id}`, requestBody),
    )
    if (error) {
      setError(error.response.data.message)
      setIsLoading(false)
      return
    }

    setError(null)
    fetchCourseById(id)
    setFormData({
      name: '',
      category: '',
      level: '',
      description: ''
    })
    setIsLoading(false)
  }

  const handleChange = (e) => {
    const { name, value } = e.target

    const fieldName = name.split('-')[0]

    const updatedLesson = {
      ...formData,
      [fieldName]: value
    }

    setFormData(updatedLesson)
  }

  return (
    <>
      {/* {isPageLoading ? (
        <SkeletonCreateCourses />
      ) : ( */}
      <div className="flex justify-center">
        <Titulo titulo="IMO / Editar Curso" />
        <div className="w-[70rem]">
          <h1 className="text-3xl font-bold text-white my-6 text-center">
            Editar Curso
          </h1>

          <div className="w-full border-white border rounded-xl p-6 px-10 my-6">
            <ColInputLabel
              label="Nome do Curso"
              placeholder={courseById?.name}
              idInput="name"
              value={formData.name}
              onChange={(e) => handleChange(e)}
            />

            <ColInputLabel
              label="Categoria"
              placeholder={courseById?.category}
              idInput="category"
              value={formData.category}
              onChange={(e) => handleChange(e)}
            />

            <ColSelectLabel
              label="Nível"
              placeholder={courseById?.level}
              idInput="level"
              value={formData.level}
              onChange={(e) => handleChange(e)}
            />

            <ColLargeInput
              placeholder={courseById?.description}
              label="Descrição do Curso"
              idInput="description"
              value={formData.description}
              onChange={(e) => handleChange(e)}
            />

            <input type="hidden" name="idCourse" id="idCourse" value={id} />
          </div>

          {!loading && courseById.lessons.map((lesson) => (
            <EditLesson
              key={lesson.id}
              lessonInfo={lesson}
            />
          ))}

          <div className="flex flex-row justify-end w-full">
            <SpinnerButton
              children="Salvar Alterações"
              isLoading={isLoading}
              onClick={() => updateCourse()}
              className="bg-custom-header-cyan text-black font-bold px-10 mx-10"
            />
            <Button
              type="button"
              className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-10 rounded-xl"
              onClick={() => {
                if (confirm("Tem certeza que deseja excluir este curso? Essa ação não pode ser desfeita.")) {
                  console.log("Curso excluído (simulação)")
                }
              }}
            >
              Excluir Aula
            </Button>
          </div>

          <div className="flex justify-center py-5">
            {error && <p className="text-red-500">{error}</p>}
          </div>
        </div>
      </div>
      {/* )} */}
    </>
  )
}