import FormSection from '@/components/CreateCourses/FormSection'
import CourseFormEdit from '@/components/EditCourse/CourseFormEdit'
import DeleteCourseButton from '@/components/EditCourse/DeleteCourseButton'
import DeleteCourseModal from '@/components/EditCourse/DeleteCourseModal'
import EditCourseLessonsSection from '@/components/EditCourse/EditCourseLessonsSection'
import { useEditCoursePage } from '@/hooks/useEditCoursePage'

export default function EditCoursePage() {
  const {
    course,
    register,
    errors,
    isSubmitting,
    statusMessage,
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
  } = useEditCoursePage()

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
              course={course}
              onSaveCourse={handleSaveCourse}
            />
          </FormSection>

          <EditCourseLessonsSection
            course={course}
            register={register}
            errors={errors}
            newLessonIndex={newLessonIndex}
            newLessonError={newLessonError}
            onSaveLesson={handleSaveLesson}
            onDeleteLesson={handleDeleteLesson}
            onShowNewLessonForm={showNewLessonForm}
            onHideNewLessonForm={hideNewLessonForm}
            onCreateNewLesson={handleCreateNewLesson}
          />

          <FormSection title="Zona de perigo">
            <DeleteCourseButton onDelete={() => setIsDeleteModalOpen(true)} />
          </FormSection>

          {statusMessage ? <p className="text-cyan text-sm">{statusMessage}</p> : null}
          {isSubmitting ? <p className="text-sm text-white/60">Salvando...</p> : null}
        </form>
      </section>

      <DeleteCourseModal
        isOpen={isDeleteModalOpen}
        courseName={course?.course.name.name}
        onClose={() => setIsDeleteModalOpen(false)}
        onConfirm={handleDeleteCourse}
      />
    </main>
  )
}
