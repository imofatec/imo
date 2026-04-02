import { zodResolver } from '@hookform/resolvers/zod'
import { useFieldArray, useForm } from 'react-hook-form'
import FormInput from '@/components/ui/FormInput'
import SelectInput from '@/components/CreateCourses/SelectInput'
import TextBoxInput from '@/components/CreateCourses/TextBoxInput'
import LessonFormCard from '@/components/CreateCourses/LessonForm'
import Button from '@/components/ui/Button'
import { createCourseSchema, type CreateCourseData } from '@/schemas/CreateCourseSchema'

const levelOptions = [
  { label: 'Iniciante', value: 'beginner' },
  { label: 'Intermediário', value: 'intermediate' },
  { label: 'Avançado', value: 'advanced' },
]

const categoryOptions = [
  { label: 'Inteligência artificial', value: 'AI' },
  { label: 'Dados', value: 'DATA' },
  { label: 'Computação em nuvem', value: 'CLOUD' },
  { label: 'Desenvolvimento web', value: 'DEV_WEB' },
  { label: 'Segurança', value: 'SECURITY' },
  { label: 'Desenvolvimento mobile', value: 'DEV_MOBILE' },
]

export default function CreateCoursePage() {
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

  function handleCreateCourse(data: CreateCourseData) {
    console.log(data)
  }

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="w-full px-4 py-5">
          <h1 className="text-2xl font-bold text-white">Criar curso</h1>
        </div>
      </section>

      <section className="mx-auto w-full max-w-5xl px-4 py-8">
        <form onSubmit={handleSubmit(handleCreateCourse)} className="space-y-8">
          <div className="rounded-3xl border border-white/10 bg-[#14082f] p-6">
            <h2 className="mb-4 text-xl font-semibold text-white">Informações do curso</h2>

            <div className="grid gap-5 md:grid-cols-2">
              <FormInput
                label="Nome do curso"
                placeholder="Curso de Python"
                className="h-12 rounded-xl bg-white/5"
                error={errors.nameCourse?.message}
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
                label="Nível"
                options={levelOptions}
                placeholder="Selecione um nível"
                className="h-12 rounded-xl bg-white/5"
                error={errors.level?.message}
                {...register('level')}
              />
            </div>

            <div className="mt-5">
              <TextBoxInput
                id="description"
                label="Descrição"
                placeholder="Descrição do curso..."
                error={errors.description?.message}
                {...register('description')}
              />
            </div>
          </div>

          <div className="rounded-3xl border border-white/10 bg-[#14082f] p-6">
            <div className="mb-6 flex justify-between">
              <h2 className="text-xl font-semibold text-white">Aulas</h2>

              <div className="flex gap-3">
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
            </div>

            {errors.lessons?.message && (
              <p className="mb-4 text-sm text-red-500">{errors.lessons.message}</p>
            )}

            <div className="space-y-5">
              {fields.map((field, index) => (
                <LessonFormCard
                  key={field.id}
                  index={index}
                  register={register}
                  nameLessonError={errors.lessons?.[index]?.nameLesson?.message}
                  linkError={errors.lessons?.[index]?.link?.message}
                  descriptionError={errors.lessons?.[index]?.descriptionL?.message}
                />
              ))}
            </div>
          </div>

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
