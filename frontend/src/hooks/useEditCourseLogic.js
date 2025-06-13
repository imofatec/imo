import { useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

export function useEditCourseLogic(courseId, fetchCourseById) {
    const [formData, setFormData] = useState({
        name: '',
        category: '',
        level: '',
        description: ''
    })

    const [newLesson, setNewLesson] = useState({
        title: '',
        description: '',
        youtubeLink: ''
    })

    const [isAddingLesson, setIsAddingLesson] = useState(false)
    const [showLessonForm, setShowLessonForm] = useState(false)
    const [lessonError, setLessonError] = useState(null)
    const [isLoading, setIsLoading] = useState(false)
    const [error, setError] = useState(null)

    const handleCourseChange = (e) => {
        const { name, value } = e.target
        const field = name.split('-')[0]
        setFormData((prev) => ({ ...prev, [field]: value }))
    }

    const handleNewLessonChange = (e) => {
        const { name, value } = e.target
        setNewLesson((prev) => ({ ...prev, [name]: value }))
    }

    const updateCourse = async () => {
        setIsLoading(true)
        const fieldsToUpdate = Object.keys(formData).filter((key) => formData[key] !== '')
        if (!fieldsToUpdate.length) return

        const body = fieldsToUpdate.reduce((acc, key) => {
            acc[key] = formData[key]
            return acc
        }, {})

        const [err] = await safeAwait(authAxiosInstance.put(`/api/courses/${courseId}`, body))
        if (err) {
            setError(err.response?.data?.message || 'Erro ao atualizar curso')
            setIsLoading(false)
            return
        }

        setError(null)
        setFormData({ name: '', category: '', level: '', description: '' })
        setIsLoading(false)
        fetchCourseById(courseId)
    }

    const addLesson = async () => {
        setIsAddingLesson(true)

        const body = [{
            title: newLesson.title,
            description: newLesson.description,
            youtubeLink: newLesson.youtubeLink
        }]

        const [err] = await safeAwait(authAxiosInstance.put(`/api/courses/${courseId}/lessons`, body))

        if (err) {
            setLessonError(err.response?.data?.message || 'Erro ao adicionar aula')
            setIsAddingLesson(false)
            return
        }

        setNewLesson({ title: '', description: '', youtubeLink: '' })
        setLessonError(null)
        setIsAddingLesson(false)
        setShowLessonForm(false)
        fetchCourseById(courseId)
    }

    const deleteCourse = async () => {
        
        const [err] = await safeAwait(
            authAxiosInstance.patch(`/api/courses/${courseId}`)
        )

        if (err) {
            alert(err.response?.data?.message || 'Erro ao excluir curso')
            return
        }

        console.log('finge que funciona')
    }


    return {
        formData,
        newLesson,
        isAddingLesson,
        isLoading,
        showLessonForm,
        lessonError,
        error,
        setShowLessonForm,
        handleCourseChange,
        handleNewLessonChange,
        updateCourse,
        addLesson,
        deleteCourse
    }
}
