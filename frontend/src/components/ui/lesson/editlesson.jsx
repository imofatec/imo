import { useLessonValidator } from '@/hooks/useLessonValidator'
import { lessonSchema } from '@/schemas/createCourseSchema'
import ColInputLabel from '../inputs/colinputlabel'
import ColLargeInput from '../inputs/collargeinput'
import Minus from '../minus'
import { Plus } from '../plus'
import { useEffect, useState, useRef } from 'react'
import { Form, useActionData } from 'react-router-dom'
import { updateLesson } from '@/requests/lesson/updateLessons'
import { SpinnerButton } from '../spinnerButton'
import { Disc } from 'lucide-react'
import { safeAwait } from '@/lib/safeAwait'
import authAxiosInstance from '@/api/authAxiosInstance'

export default function EditLesson({ lessonInfo }) {
  const {
    formDataList,
    fieldErrorsList,
    handleChange,
    removeLesson,
  } = useLessonValidator(lessonSchema)

  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const [formData, setFormData] = useState({
    title: '',
    descriptionC: '',
    youtubeLink: '',
  })

  const updateLesson = async () => {
    const fieldsToUpdate = Object.keys(formData).filter((key) => formData[key] != '')

    if (!fieldsToUpdate) return

    let requestBody = {}

    fieldsToUpdate.forEach((field) => (
      requestBody = { ...requestBody, [field]: formData[field] }
    ))

    const [error] = await safeAwait(
      authAxiosInstance.put(`/api/courses/lessons/${lessonInfo.id}`, requestBody)
    )

    if (error) {
      setError(error.response.data.message)
      setIsLoading(false)
      return
    }

    setFormData({
      title: '',
      descriptionC: '',
      youtubeLink: '',
    })
    setError(null)
    setIsLoading(false)
  }

  const handleUpdateChange = (e) => {
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
      {formDataList.map((lesson, index) => (
        <div
          className="w-full border-white border rounded-xl p-6 px-10 my-6"
          key={index}
        >
          <ColInputLabel
            label="Link da Aula"
            placeholder={lessonInfo.youtubeLink}
            idInput={`youtubeLink-${index}`}
            name="youtubeLink"
            value={lesson.youtubeLink ?? ''}
            onChange={(e) => {
              handleChange(index, e)
              handleUpdateChange(index, e)
            }}
            error={fieldErrorsList[index]?.youtubeLink}
          />

          <ColInputLabel
            label="Nome da Aula"
            placeholder={lessonInfo.title}
            idInput={`title-${index}`}
            name="title"
            value={lesson.title ?? ''}
            onChange={(e) => {
              handleChange(index, e)
              handleUpdateChange(index, e)
            }}
            error={fieldErrorsList[index]?.title}
          />

          <ColLargeInput
            label="Descrição da Aula"
            placeholder={lessonInfo.description}
            idInput={`descriptionC-${index}`}
            name="descriptionC"
            value={lesson.descriptionC ?? ''}
            onChange={(e) => {
              handleChange(index, e)
              handleUpdateChange(index, e)
            }}
            error={fieldErrorsList[index]?.descriptionC}
          />

          <div className="flex flex-row justify-between m-6">
            <div
              className="flex-row flex items-center cursor-pointer"
              onClick={() => removeLesson(index)}
            >
              <Minus />
              <label className="ml-6 text-white italic text-sm">
                Clique no “-” para remover uma aula
              </label>
            </div>
            <div
              className="flex-row flex items-center text-white cursor-pointer"
            >

              <Disc onClick={() => {
                updateLesson()
              }} />

              {error && error}
            </div>
          </div>
        </div>
      ))}
    </>
  )
}
