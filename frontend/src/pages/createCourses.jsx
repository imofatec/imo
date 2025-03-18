import { Button } from '@/components/ui/button'
import { Form, useActionData } from 'react-router-dom'
import ColLargeInput from '@/components/ui/inputs/collargeinput'
import ColInputLabel from '@/components/ui/inputs/colinputlabel'
import NewLesson from '@/components/ui/newlesson'
import { SpinnerButton } from '@/components/ui/spinnerButton'
import { useEffect, useState } from 'react'

export default function CreateCourses() {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const actionData = useActionData()

  useEffect(() => {
    setTimeout(() => {
      if (actionData) {
        setIsLoading(false)
        setError(actionData.error)
      }
    }, 1000)
  }, [actionData])

  return (
    <div className="flex justify-center">
      <div className="w-[70rem]">
        <Form method="post" action="/criar-curso">
          <div className="w-full border-white border rounded-xl p-6 px-10 my-6 ">
            <ColInputLabel
              label={'Nome do Curso'}
              placeholder={'Insira aqui o Nome do Curso'}
              idInput={'name'}
            ></ColInputLabel>

            <ColInputLabel
              label={'Categoria'}
              placeholder={'Insira uma categoria'}
              idInput={'category'}
            ></ColInputLabel>

            <ColLargeInput
              placeholder={'Insira aqui a Descrição do Curso'}
              label={'Descrição do Curso'}
              idInput={'description'}
            ></ColLargeInput>
          </div>

          <NewLesson></NewLesson>
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
  )
}
