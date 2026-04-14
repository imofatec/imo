import { zodResolver } from '@hookform/resolvers/zod'
import { useFieldArray, useForm } from 'react-hook-form'
import { useNavigate } from 'react-router-dom'
import FormInput from '@/components/ui/FormInput'
import SelectInput from '@/components/CreateCourses/SelectInput'
import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import LessonFormCard from '@/components/CreateCourses/LessonForm'
import FormSection from '@/components/CreateCourses/FormSection'
import { useUser } from '@/contexts/UserContext'
import Button from '@/components/ui/Button'
import { categoryOptions, levelOptions } from '@/constants/courseOptions'
import { showRequestErrorToast } from '@/lib/requestToast'
import { createCourseSchema, type CreateCourseData } from '@/schemas/courses/CreateCourseSchema'
import { createCourseRequest } from '@/services/course/createCourse'
import { toast } from 'sonner'

export default function CreateCoursePage() {
  const { user } = useUser()
  const navigate = useNavigate()

  const {
    register,
    handleSubmit,
    control,
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
      lessons: [{ nameLesson: '', link: '', descriptionL: '' }],
    },
  })

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'lessons',
  })

  function addLesson() {
    append({ nameLesson: '', link: '', descriptionL: '' })
  }

  function removeLesson() {
    if (fields.length > 1) {
      remove(fields.length - 1)
    }
  }

  async function handleCreateCourse(data: CreateCourseData) {
    if (!user?.id) {
      toast.error('Ocorreu um erro', {
        id: 'create-course-user-error',
        description: 'Usuário não identificado.',
        duration: 5000,
      })
      return
    }

    try {
      await createCourseRequest(data, user.id)
      navigate('/categorias')
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar criar o curso. Por favor, tente novamente.',
        { id: 'create-course-error' }
      )
    }
  }

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="flex justify-center border-b border-white/10">
        <div className="px-4 py-5">
          <h1 className="text-2xl font-bold text-white">Criar curso</h1>
        </div>
      </section>

      <section className="mx-auto w-full max-w-5xl px-4 py-8">
        <form onSubmit={handleSubmit(handleCreateCourse)} className="space-y-8">
          <FormSection title="Informacoes do curso">
            <div className="grid gap-5 md:grid-cols-2">
              <FormInput
                label="Nome do curso"
                placeholder="Curso de Python"
                className="h-12 rounded-xl bg-white/5"
                error={errors.nameCourse?.message}
                minLength={15}
                {...register('nameCourse')}
              />

              <SelectInput
                id="category"
                label="Categoria"
                options={categoryOptions}
                placeholder="Selecione uma categoria"
                className="h-12 rounded-xl bg-white/5"
                error={errors.category?.message}
                {...register('category')}
              />
            </div>

            <div className="mt-5">
              <SelectInput
                id="level"
                label="Nivel"
                options={levelOptions}
                placeholder="Selecione um nivel"
                className="h-12 rounded-xl bg-white/5"
                error={errors.level?.message}
                {...register('level')}
              />
            </div>

            <div className="mt-5">
              <TextBoxInput
                id="description"
                label="Descricao"
                placeholder="Descricao do curso..."
                error={errors.description?.message}
                minLength={25}
                maxLength={500}
                {...register('description')}
              />
            </div>
          </FormSection>

          <FormSection title="Aulas">
            {errors.lessons?.message && (
              <p className="mb-4 text-sm text-red-500">{errors.lessons.message}</p>
            )}

            <div className="space-y-5">
              {fields.map((field, index) => (
                <div key={field.id}>
                  {index > 0 ? <div className="mb-5 h-px w-full bg-white/10" aria-hidden /> : null}

                  <LessonFormCard
                    index={index}
                    register={register}
                    nameLessonError={errors.lessons?.[index]?.nameLesson?.message}
                    linkError={errors.lessons?.[index]?.link?.message}
                    descriptionError={errors.lessons?.[index]?.descriptionL?.message}
                  />
                </div>
              ))}
            </div>

            <div className="mt-6 flex justify-end gap-3">
              <Button
                type="button"
                onClick={addLesson}
                variant="cyanOutline"
                className="border-cyan/40 text-cyan rounded-full border px-4 py-2 text-sm"
              >
                Adicionar
              </Button>

              <Button
                type="button"
                onClick={removeLesson}
                variant="cyanOutline"
                className="border-cyan/40 text-cyan rounded-full border px-4 py-2 text-sm"
              >
                Remover
              </Button>
            </div>
          </FormSection>

          <div className="flex justify-end">
            <Button
              type="submit"
              variant="cyanOutline"
              className="text-cyan mt-auto!"
              disabled={isSubmitting}
            >
              {isSubmitting ? 'Criando...' : 'Criar Curso'}
            </Button>
          </div>
        </form>
      </section>
    </main>
  )
}
