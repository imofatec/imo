import { useLessonValidator } from '@/hooks/useLessonValidator'
import { lessonSchema } from '@/schemas/createCourseSchema'
import ColInputLabel from './inputs/colinputlabel'
import ColLargeInput from './inputs/collargeinput'
import Minus from './minus'
import { Plus } from './plus'

export default function NewLesson() {
  const {
    formDataList,
    fieldErrorsList,
    handleChange,
    addLesson,
    removeLesson,
  } = useLessonValidator(lessonSchema)

  return (
    <>
      {formDataList.map((lesson, index) => (
        <div
          className="w-full border-white border rounded-xl p-6 px-10 my-6"
          key={index}
        >
          <ColInputLabel
            label="Link da Aula"
            placeholder="Insira aqui o Link da Aula"
            idInput={`youtubeLink-${index}`}
            name="youtubeLink"
            value={lesson.youtubeLink}
            onChange={(e) => handleChange(index, e)}
            error={fieldErrorsList[index]?.youtubeLink}
          />

          <ColInputLabel
            label="Nome da Aula"
            placeholder="Insira aqui o Nome da Aula"
            idInput={`title-${index}`}
            name="title"
            value={lesson.title}
            onChange={(e) => handleChange(index, e)}
            error={fieldErrorsList[index]?.title}
          />

          <ColLargeInput
            label="Descrição da Aula"
            placeholder="Insira aqui a Descrição da Aula"
            idInput={`descriptionC-${index}`}
            name="descriptionC"
            value={lesson.descriptionC}
            onChange={(e) => handleChange(index, e)}
            error={fieldErrorsList[index]?.descriptionC}
          />

          <div className="flex flex-row justify-between m-6">
            <div
              className="flex-row flex items-center cursor-pointer"
              onClick={() => removeLesson(index)}
            >
              <Minus />
              <label className="ml-6 text-white italic text-sm">
                Clique no “-” para remover uma aula
              </label>
            </div>
            <div
              className="flex-row flex items-center text-white cursor-pointer"
              onClick={addLesson}
            >
              <label className="mr-6 text-white italic text-sm">
                Clique no “+” para adicionar uma aula
              </label>
              <Plus />
            </div>
          </div>
        </div>
      ))}
    </>
  )
}
