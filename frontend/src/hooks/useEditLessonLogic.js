import { useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export function useEditLessonLogic(lessonId, refetchCourse) {
  const [formData, setFormData] = useState({
    title: '',
    descriptionC: '',
    youtubeLink: '',
  })
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)

  const handleUpdateChange = (e) => {
    const { name, value } = e.target
    const fieldName = name.split('-')[0]
    setFormData(prev => ({ ...prev, [fieldName]: value }))
  }

  const updateLesson = async () => {
    const fieldsToUpdate = Object.keys(formData).filter((key) => formData[key] !== '')
    if (!fieldsToUpdate.length) return

    const requestBody = fieldsToUpdate.reduce((acc, key) => {
      acc[key] = formData[key]
      return acc
    }, {})

    setIsLoading(true)
    const [err] = await safeAwait(
      authAxiosInstance.put(`/api/courses/lessons/${lessonId}`, requestBody)
    )

    if (err) {
      setError(err.response?.data?.message || 'Erro ao atualizar a aula')
      setIsLoading(false)
      return
    }

    setFormData({ title: '', descriptionC: '', youtubeLink: '' })
    setError(null)
    setIsLoading(false)
    await refetchCourse()
  }

  const deleteLesson = async () => {
    
    setIsLoading(true)
    const [err] = await safeAwait(
      authAxiosInstance.delete(`/api/courses/lessons/${lessonId}`)
    )

    if (err) {
      setError(err.response?.data?.message || 'Erro ao excluir a aula')
      setIsLoading(false)
      return
    }

    setError(null)
    setIsLoading(false)
    await refetchCourse()
  }

  return {
    formData,
    setFormData,
    isLoading,
    error,
    handleUpdateChange,
    updateLesson,
    deleteLesson
  }
}
