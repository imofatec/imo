import { categoryOptions, levelOptions } from '@/constants/courseOptions'
import { useCurrentCourse } from '@/hooks/useCurrentCourse'
import {
  createCourseSchema,
  lessonSchema,
  type CreateCourseData,
} from '@/schemas/courses/CreateCourseSchema'
import { addLessonRequest } from '@/services/course/lessons/addLesson'
import { deleteLessonRequest } from '@/services/course/lessons/deleteLesson'
import { updateLessonRequest } from '@/services/course/lessons/updateLesson'
import { editCourseRequest } from '@/services/course/editCourse'
import { toggleCourseRequest } from '@/services/course/toggleCourse'
import { zodResolver } from '@hookform/resolvers/zod'
import { useEffect, useState } from 'react'
import { useFieldArray, useForm } from 'react-hook-form'
import { useNavigate, useParams } from 'react-router-dom'

function normalizeOptionValue(value: string) {
  return value
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .replace(/[\s-]/g, '_')
    .toUpperCase()
}

export function useEditCoursePage() {
  const { courseId } = useParams()
  const navigate = useNavigate()
  const { course, refetch } = useCurrentCourse(courseId ?? '')
  const [statusMessage, setStatusMessage] = useState<string | null>(null)
  const [errorMessage, setErrorMessage] = useState<string | null>(null)
  const [newLessonIndex, setNewLessonIndex] = useState<number | null>(null)
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false)

  const {
    register,
    control,
    getValues,
    reset,
    resetField,
    trigger,
    formState: { errors, isSubmitting },
  } = useForm<CreateCourseData>({
    resolver: zodResolver(createCourseSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
    defaultValues: {
      nameCourse: '',
      category: '',
      level: '',
      description: '',
      lessons: [],
    },
  })

  const { append, remove } = useFieldArray({
    control,
    name: 'lessons',
  })

  useEffect(() => {
    if (!course) return

    const selectedCategory =
      categoryOptions.find(
        (option) =>
          normalizeOptionValue(option.value) ===
            normalizeOptionValue(course.course.category.slug) ||
          normalizeOptionValue(option.label) === normalizeOptionValue(course.course.category.name)
      )?.value ?? ''

    const selectedLevel =
      levelOptions.find(
        (option) =>
          normalizeOptionValue(option.value) === normalizeOptionValue(course.course.level.slug) ||
          normalizeOptionValue(option.label) === normalizeOptionValue(course.course.level.name)
      )?.value ?? ''

    reset({
      nameCourse: '',
      category: selectedCategory,
      level: selectedLevel,
      description: '',
      lessons: [],
    })
  }, [course, reset])

  async function handleSaveCourse() {
    setStatusMessage(null)
    setErrorMessage(null)

    if (!courseId || !course) return

    const data = getValues()
    const validations = [trigger(['category', 'level'])]

    if (data.nameCourse.trim()) {
      validations.push(trigger('nameCourse'))
    }

    if (data.description.trim()) {
      validations.push(trigger('description'))
    }

    const validationResults = await Promise.all(validations)
    const isValid = validationResults.every(Boolean)
    if (!isValid) return

    const nameCourse = data.nameCourse.trim() || course.course.name.name
    const description = data.description.trim() || course.course.description

    try {
      await editCourseRequest(courseId, {
        name: nameCourse,
        category: data.category,
        level: data.level,
        description,
      })

      await refetch()
      setStatusMessage('Informações do curso atualizadas com sucesso.')
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar atualizar o curso. Por favor, tente novamente.'
      )
    }
  }

  async function handleSaveLesson(lessonId: string, index: number) {
    setStatusMessage(null)
    setErrorMessage(null)

    const currentLesson = course?.lessons[index]
    if (!currentLesson) return

    const lesson = getValues(`lessons.${index}`)
    const lessonData = {
      nameLesson: lesson?.nameLesson?.trim() || currentLesson.title,
      link: lesson?.link?.trim() || currentLesson.youtubeLink,
      descriptionL: lesson?.descriptionL?.trim() || currentLesson.description,
    }

    const validation = lessonSchema.safeParse(lessonData)
    if (!validation.success) {
      await trigger([
        `lessons.${index}.nameLesson`,
        `lessons.${index}.link`,
        `lessons.${index}.descriptionL`,
      ])
      return
    }

    try {
      await updateLessonRequest(lessonId, {
        title: lessonData.nameLesson,
        youtubeLink: lessonData.link,
        description: lessonData.descriptionL,
      })

      await refetch()
      setStatusMessage('Aula atualizada com sucesso.')
      resetField(`lessons.${index}.nameLesson`, { defaultValue: '' })
      resetField(`lessons.${index}.link`, { defaultValue: '' })
      resetField(`lessons.${index}.descriptionL`, { defaultValue: '' })
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar atualizar a aula. Por favor, tente novamente.'
      )
    }
  }

  async function handleDeleteLesson(lessonId: string) {
    setStatusMessage(null)
    setErrorMessage(null)

    try {
      await deleteLessonRequest(lessonId)
      await refetch()
      setStatusMessage('Aula excluída com sucesso.')
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar excluir a aula. Por favor, tente novamente.'
      )
    }
  }

  function showNewLessonForm() {
    const lessonIndex = course?.lessons.length ?? 0

    append({ nameLesson: '', link: '', descriptionL: '' })
    resetField(`lessons.${lessonIndex}.nameLesson`, { defaultValue: '' })
    resetField(`lessons.${lessonIndex}.link`, { defaultValue: '' })
    resetField(`lessons.${lessonIndex}.descriptionL`, { defaultValue: '' })
    setNewLessonIndex(lessonIndex)
  }

  function hideNewLessonForm() {
    if (newLessonIndex === null) return

    remove(newLessonIndex)
    setNewLessonIndex(null)
  }

  async function handleCreateNewLesson() {
    setStatusMessage(null)
    setErrorMessage(null)

    if (!courseId || newLessonIndex === null) return

    const isValid = await trigger([
      `lessons.${newLessonIndex}.nameLesson`,
      `lessons.${newLessonIndex}.link`,
      `lessons.${newLessonIndex}.descriptionL`,
    ])

    if (!isValid) return

    const lesson = getValues(`lessons.${newLessonIndex}`)
    const lessonData = {
      nameLesson: lesson?.nameLesson?.trim() || '',
      link: lesson?.link?.trim() || '',
      descriptionL: lesson?.descriptionL?.trim() || '',
    }

    const validation = lessonSchema.safeParse(lessonData)
    if (!validation.success) {
      await trigger([
        `lessons.${newLessonIndex}.nameLesson`,
        `lessons.${newLessonIndex}.link`,
        `lessons.${newLessonIndex}.descriptionL`,
      ])
      return
    }

    try {
      await addLessonRequest(courseId, {
        title: lessonData.nameLesson,
        youtubeLink: lessonData.link,
        description: lessonData.descriptionL,
      })

      remove(newLessonIndex)
      setNewLessonIndex(null)
      await refetch()
      setStatusMessage('Aula criada com sucesso.')
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar criar a aula. Por favor, tente novamente.'
      )
    }
  }

  async function handleDeleteCourse() {
    setStatusMessage(null)
    setErrorMessage(null)

    if (!courseId) return

    try {
      await toggleCourseRequest(courseId)
      setIsDeleteModalOpen(false)
      navigate('/user/cursos')
    } catch (error: any) {
      setErrorMessage(
        error.response?.data?.message ||
          'Ocorreu um erro ao tentar excluir o curso. Por favor, tente novamente.'
      )
    }
  }

  const newLessonError = newLessonIndex !== null ? errors.lessons?.[newLessonIndex] : undefined

  return {
    course,
    register,
    errors,
    isSubmitting,
    statusMessage,
    errorMessage,
    newLessonIndex,
    newLessonError,
    isDeleteModalOpen,
    setIsDeleteModalOpen,
    handleSaveCourse,
    handleSaveLesson,
    handleDeleteLesson,
    showNewLessonForm,
    hideNewLessonForm,
    handleCreateNewLesson,
    handleDeleteCourse,
  }
}
