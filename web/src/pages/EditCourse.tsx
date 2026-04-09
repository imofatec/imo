import CourseFormEdit from '@/components/EditCourse/CourseFormEdit'
import DeleteCourseButton from '@/components/EditCourse/DeleteCourseButton'
import LessonListEdit, { type LessonPreview } from '@/components/EditCourse/LessonListEdit'
import NewLessonForm from '@/components/EditCourse/NewLessonForm'
import type { EditCourseData } from '@/types/edit'
import FormSection from '@/components/CreateCourses/FormSection'
import Button from '@/components/ui/Button'
import { useMemo, useState } from 'react'
import { useFieldArray, useForm } from 'react-hook-form'
import { useParams } from 'react-router-dom'

const mockedLessons: LessonPreview[] = [
  {
    id: 'lesson-1',
    title: 'Introdução do curso',
    youtubeLink: 'https://youtube.com/watch?v=aaaaaaaaaaa',
    description: 'Visão geral do curso e objetivos principais.',
  },
  {
    id: 'lesson-2',
    title: 'Preparando o ambiente',
    youtubeLink: 'https://youtube.com/watch?v=bbbbbbbbbbb',
    description: 'Configuração de ferramentas e primeiros ajustes.',
  },
]

export default function EditCoursePage() {
  const { courseId } = useParams()
  const [statusMessage, setStatusMessage] = useState<string | null>(null)
  const [lessonsPreview, setLessonsPreview] = useState<LessonPreview[]>(mockedLessons)
  const [newLessonIndex, setNewLessonIndex] = useState<number | null>(null)

  const coursePreview = useMemo(
    () => ({
      id: courseId ?? 'curso-demo',
      name: 'React para web na prática',
      category: 'Desenvolvimento web',
      level: 'Intermediário',
      description: 'Curso focado em React com abordagem prática para projetos reais.',
    }),
    [courseId]
  )

  const {
    register,
    control,
    getValues,
    formState: { errors },
  } = useForm<EditCourseData>({
    defaultValues: {
      nameCourse: '',
      category: '',
      level: '',
      description: '',
      lessons: mockedLessons.map(() => ({
        nameLesson: '',
        link: '',
        descriptionL: '',
      })),
    },
  })

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'lessons',
  })

  function handleSaveCourse() {
    setStatusMessage('Alterações do curso salvas')
  }

  function handleSaveLesson(lessonId: string, index: number) {
    setStatusMessage(`Alterações da aula ${index + 1} salvas (${lessonId}).`)
  }

  function handleDeleteLesson(lessonId: string) {
    const lessonIndex = lessonsPreview.findIndex((lesson) => lesson.id === lessonId)
    if (lessonIndex < 0) return

    setLessonsPreview((previous) => previous.filter((lesson) => lesson.id !== lessonId))
    remove(lessonIndex)

    if (newLessonIndex !== null && newLessonIndex > lessonIndex) {
      setNewLessonIndex(newLessonIndex - 1)
    }
  }

  function showNewLessonForm() {
    if (newLessonIndex !== null) return

    append({
      nameLesson: '',
      link: '',
      descriptionL: '',
    })
    setNewLessonIndex(fields.length)
  }

  function hideNewLessonForm() {
    if (newLessonIndex === null) return

    remove(newLessonIndex)
    setNewLessonIndex(null)
  }

  function handleCreateNewLesson() {
    if (newLessonIndex === null) return

    const lessonData = getValues(`lessons.${newLessonIndex}` as const)

    setLessonsPreview((previous) => [
      ...previous,
      {
        id: `lesson-${Date.now()}`,
        title: lessonData?.nameLesson || 'Nova aula',
        youtubeLink: lessonData?.link || 'https://youtube.com/watch?v=novo',
        description: lessonData?.descriptionL || 'Descrição da aula',
      },
    ])

    setStatusMessage('Nova aula adicionada.')
    setNewLessonIndex(null)
  }

  function handleDeleteCourse() {
    setStatusMessage('Curso excluído.')
  }

  const lessonErrors = Array.isArray(errors.lessons) ? errors.lessons : []

  const lessonsWithErrors: LessonPreview[] = lessonsPreview.map((lesson, index) => {
    const lessonError = lessonErrors[index] as
      | {
          nameLesson?: { message?: string }
          link?: { message?: string }
          descriptionL?: { message?: string }
        }
      | undefined

    return {
      ...lesson,
      nameError: lessonError?.nameLesson?.message,
      linkError: lessonError?.link?.message,
      descriptionError: lessonError?.descriptionL?.message,
    }
  })

  const newLessonError =
    newLessonIndex !== null
      ? (lessonErrors[newLessonIndex] as
          | {
              nameLesson?: { message?: string }
              link?: { message?: string }
              descriptionL?: { message?: string }
            }
          | undefined)
      : undefined

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="flex justify-center border-b border-white/10">
        <div className="px-4 py-5">
          <h1 className="text-2xl font-bold text-white">Editar curso</h1>
        </div>
      </section>

      <section className="mx-auto w-full max-w-5xl px-4 py-8">
        <form className="space-y-8">
          <FormSection title="Informacoes do curso">
            <CourseFormEdit
              register={register}
              errors={errors}
              course={coursePreview}
              onSaveCourse={handleSaveCourse}
            />
          </FormSection>

          <FormSection title="Aulas">
            <LessonListEdit
              lessons={lessonsWithErrors}
              register={register}
              onSaveLesson={handleSaveLesson}
              onDeleteLesson={handleDeleteLesson}
            />

            {newLessonIndex !== null ? (
              <div className="mt-6">
                <NewLessonForm
                  index={newLessonIndex}
                  register={register}
                  nameError={newLessonError?.nameLesson?.message}
                  linkError={newLessonError?.link?.message}
                  descriptionError={newLessonError?.descriptionL?.message}
                  onCancel={hideNewLessonForm}
                  onCreate={handleCreateNewLesson}
                />
              </div>
            ) : (
              <div className="mt-6 flex justify-end">
                <Button
                  type="button"
                  onClick={showNewLessonForm}
                  variant="cyanOutline"
                  className="border-cyan/40 text-cyan w-auto rounded-full border px-4 py-2 text-sm"
                >
                  Adicionar aula
                </Button>
              </div>
            )}
          </FormSection>

          <FormSection title="Zona de perigo">
            <DeleteCourseButton onDelete={handleDeleteCourse} />
          </FormSection>

          {statusMessage ? <p className="text-cyan text-sm">{statusMessage}</p> : null}
        </form>
      </section>
    </main>
  )
}
