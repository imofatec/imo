import { useState } from 'react'
import ColInputLabel from './inputs/colinputlabel'
import ColLargeInput from './inputs/collargeinput'
import Minus from './minus'
import { Plus } from './plus'

export default function NewLesson() {
  /* 
    Acertar a validação segundo o backend
    Validação de quantidade de aulas no front feito para testes
*/
  const [newLesson, setNewLesson] = useState([1]) /*minimo 1*/

  const moreLessons = () => {
    if (newLesson.length >= 100) {
      /*remover validação no front*/
      console.log('Limite alcançado')
    } else {
      setNewLesson([...newLesson, newLesson.length + 1])
    }
  }

  const lessLessons = (index) => {
    if (newLesson.length > 1) {
      /*acrescentar feedback pro usuário caso tenha resposta do backend no caso de tentar remover a unica aula*/
      setNewLesson(newLesson.filter((_, i) => i !== index))
    }
  }

  return (
    <>
      {newLesson.map((_, index) => (
        <div
          className="w-full border-white border rounded-xl p-6 px-10 my-6"
          key={index}
        >
          <ColInputLabel
            label={'Link da Aula'}
            placeholder={'Insira aqui o Link da Aula'}
            idInput={`youtubeLink-${index}`}
          ></ColInputLabel>

          <ColInputLabel
            label={'Nome da Aula'}
            placeholder={'Insira aqui o Nome da Aula'}
            idInput={`title-${index}`}
          ></ColInputLabel>

          <ColLargeInput
            placeholder={'Insira aqui a Descrição da Aula'}
            label={'Descrição da Aula'}
            idInput={`descriptionC-${index}`}
          ></ColLargeInput>

          <div className="flex flex-row justify-between m-6">
            <div
              className="flex-row flex items-center cursor-pointer"
              onClick={() => lessLessons(index)}
            >
              <Minus></Minus>
              <label className="ml-6 text-white italic text-sm">
                Clique no “-” para remover uma aula
              </label>
            </div>
            <div
              className="flex-row flex items-center text-white cursor-pointer"
              onClick={moreLessons}
            >
              <label className="mr-6 text-white italic text-sm">
                Clique no “+” para adicionar uma aula
              </label>
              <Plus></Plus>
            </div>
          </div>
        </div>
      ))}
    </>
  )
}
