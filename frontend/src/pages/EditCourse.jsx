import { useParams } from 'react-router-dom'
import { useState } from 'react'
import { Titulo } from '@/components/ui/titulo'
import { Button } from '@/components/ui/button'
import { useCourseById } from '@/hooks/useCourseById'
import EditCourseForm from '@/components/ui/courses/editCourseForm'
import AddLessonForm from '@/components/ui/lesson/addLessonForm'
import EditLesson from '@/components/ui/lesson/editlesson'
import { useEditCourseLogic } from '@/hooks/useEditCourseLogic'

export default function EditCourse() {
  const { id } = useParams()
  const { courseById, loading, fetchCourseById } = useCourseById(id)

  const [confirmDelete, setConfirmDelete] = useState(false)

  const {
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
  } = useEditCourseLogic(id, fetchCourseById)

  return (
    <div className="flex justify-center">
      <Titulo titulo="IMO / Editar Curso" />
      <div className="w-[70rem]">
        <h1 className="text-3xl font-bold text-white my-6 text-center">
          Editar Curso
        </h1>

        <EditCourseForm
          courseData={courseById}
          formData={formData}
          onChange={handleCourseChange}
          onSave={updateCourse}
          isLoading={isLoading}
        />

        {!loading && courseById.lessons.map((lesson) => (
          <EditLesson
            key={lesson.id}
            lessonInfo={lesson}
            refetchCourse={fetchCourseById}
          />
        ))}

        <div className="flex flex-row justify-end w-full gap-3">
          {!showLessonForm ? (
            <Button
              type="button"
              onClick={() => setShowLessonForm(true)}
              className="bg-custom-header-cyan hover:bg-custom-header-cyan/80 text-black font-bold py-2 px-10 rounded-xl"
            >
              Adicionar Aula
            </Button>
          ) : (
            <AddLessonForm
              newLesson={newLesson}
              onChange={handleNewLessonChange}
              onCancel={() => setShowLessonForm(false)}
              onSubmit={addLesson}
              isLoading={isAddingLesson}
              error={lessonError}
            />
          )}

          {!confirmDelete ? (
            <Button
              type="button"
              className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-10 rounded-xl"
              onClick={() => setConfirmDelete(true)}
            >
              Excluir Curso
            </Button>
          ) : (
            <div className="flex items-center gap-2">
              <Button
                type="button"
                className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-6 rounded-xl"
                onClick={deleteCourse}
              >
                Confirmar Exclusão
              </Button>
              <Button
                type="button"
                className="bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-6 rounded-xl"
                onClick={() => setConfirmDelete(false)}
              >
                Cancelar
              </Button>
            </div>
          )}
        </div>

        <div className="flex justify-center py-5">
          {error && <p className="text-red-500">{error}</p>}
        </div>
      </div>
    </div>
  )
}
