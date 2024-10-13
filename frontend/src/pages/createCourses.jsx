import { Button } from '@/components/ui/button'
import { Form } from 'react-router-dom'
import ColLargeInput from '@/components/ui/inputs/collargeinput'
import ColInputLabel from '@/components/ui/inputs/colinputlabel'
import NewLesson from '@/components/ui/newlesson'

export default function CreateCourses() {
  return (
    <div className='flex justify-center'>
      <div className="w-[70rem]">
        <Form method="post" action="/criarcurso">
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
          <div className="flex flex-row justify-end">
            <Button
              type="submit"
              className="bg-custom-header-cyan text-black font-bold px-10 mx-10"
            >
              Adicionar
            </Button>
          </div>
        </Form>
      </div>
    </div>
  )
}
