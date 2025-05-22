import SkeletonCreateCourses from '@/components/skeletons/SkeletonCreateCourses'
import ColInputLabel from '@/components/ui/inputs/colinputlabel'
import ColLargeInput from '@/components/ui/inputs/collargeinput'
import ColSelectLabel from '@/components/ui/inputs/colselectlabel'
import NewLesson from '@/components/ui/newlesson'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { useAuth } from '@/context/useAuth'
import { useFormValidator } from '@/hooks/useFormValidator'
import { createCourseBaseSchema } from '@/schemas/createCourseSchema'
import { useEffect, useState } from 'react'
import { Form, useActionData } from 'react-router-dom'

export default function CreateCourses() {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const actionData = useActionData()
  const { authLoading } = useAuth()

  const { formData, fieldErrors, handleChange, setFormData, setFieldErrors } =
    useFormValidator(createCourseBaseSchema)

  useEffect(() => {
      if (actionData) {
        setIsLoading(false)
        setError(actionData.error)
      }
  }, [actionData])

  return (
    <>
      {authLoading ? (
        <SkeletonCreateCourses />
      ) : (
        <div className="flex justify-center ">
          <div className="w-[70rem]">
            <Form method="post" action="/criar-curso">
              <div className="w-full border-white border rounded-xl p-6 px-10 my-6 ">
                <ColInputLabel
                  label={'Nome do Curso'}
                  placeholder={'Insira aqui o Nome do Curso'}
                  idInput={'name'}
                  value={formData.name || ''}
                  onChange={handleChange}
                  error={fieldErrors.name}
                />

                <ColInputLabel
                  label={'Categoria'}
                  placeholder={'Insira uma categoria'}
                  idInput={'category'}
                  value={formData.category || ''}
                  onChange={handleChange}
                  error={fieldErrors.category}
                />

                <ColSelectLabel
                  label={'Nível'}
                  placeholder={'Selecione a dificuldade do curso'}
                  idInput={'level'}
                  value={formData.level || ''}
                  onChange={handleChange}
                  error={fieldErrors.level}
                />

                <ColLargeInput
                  placeholder={'Insira aqui a Descrição do Curso'}
                  label={'Descrição do Curso'}
                  idInput={'description'}
                  value={formData.description || ''}
                  onChange={handleChange}
                  error={fieldErrors.description}
                />
              </div>

              <NewLesson />

              <div className="flex flex-row justify-end w-full">
                <SpinnerButton
                  children="Adicionar"
                  isLoading={isLoading}
                  onClick={() => setIsLoading(true)}
                  className="bg-custom-header-cyan text-black font-bold px-10 mx-10"
                />
              </div>
            </Form>

            <div className="flex justify-center py-5">
              {error && <p className="text-red-500">{actionData.error}</p>}
            </div>
          </div>
        </div>
      )}
    </>
  )
}
