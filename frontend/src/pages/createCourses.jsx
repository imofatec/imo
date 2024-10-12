import { Button } from '@/components/ui/button'
import { Form } from 'react-router-dom'
import ColLargeInput from '@/components/ui/inputs/collargeinput'
import ColInputLabel from '@/components/ui/inputs/colinputlabel'
import { SelectList } from '@/components/ui/selectlist'
import NewLesson from '@/components/ui/newlesson'

export default function CreateCourses() {
  const categories = [
    /*lista do shadcn pronta com poucas modificações, por favor apagar e por a requisição*/
    {
      value: 'next.js',
      label: 'React',
    },
    {
      value: 'sveltekit',
      label: 'Angular',
    },
    {
      value: 'nuxt.js',
      label: 'Rust',
    },
    {
      value: 'remix',
      label: 'Delphi',
    },
    {
      value: 'astro',
      label: 'PHP',
    },
  ]

  return (
    <>
      <Form method="post" action="/criarcurso">
        <div className="w-full border-white border rounded-xl p-6 px-10 my-6 ">
          <ColInputLabel
            label={'Nome do Curso'}
            placeholder={'Insira aqui o Nome do Curso'}
            idInput={'name'}
          ></ColInputLabel>

          <div className="flex flex-row">
            <div className="flex flex-col w-1/4">
              <label className="font-semibold">Categoria</label>
            </div>
            <div className="flex flex-col w-3/4">
              <SelectList
                categories={categories}
                idInput={'category'}
              ></SelectList>
            </div>
          </div>

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
    </>
  )
}
