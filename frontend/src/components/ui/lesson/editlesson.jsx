import { useLessonValidator } from '@/hooks/useLessonValidator'
import { lessonSchema } from '@/schemas/createCourseSchema'
import { useState } from 'react'
import ColInputLabel from '../inputs/colinputlabel'
import ColLargeInput from '../inputs/collargeinput'
import { Save } from 'lucide-react';
import { Trash } from 'lucide-react';
import { useEditLessonLogic } from '@/hooks/useEditLessonLogic'

export default function EditLesson({ lessonInfo, refetchCourse }) {
  const [confirmDelete, setConfirmDelete] = useState(false)

  const {
    formDataList,
    fieldErrorsList,
    handleChange,
    removeLesson,
  } = useLessonValidator(lessonSchema)

  const {
    formData,
    handleUpdateChange,
    updateLesson,
    deleteLesson,
    isLoading,
    error,
  } = useEditLessonLogic(lessonInfo.id, refetchCourse)

  return (
    <>
      {formDataList.map((lesson, index) => (
        <div className="w-full border-white border rounded-xl p-6 px-10 my-6" key={index}>
          <ColInputLabel
            label="Link da Aula"
            placeholder={lessonInfo.youtubeLink}
            idInput={`youtubeLink-${index}`}
            name="youtubeLink"
            value={lesson.youtubeLink ?? ''}
            onChange={(e) => {
              handleChange(index, e)
              handleUpdateChange(e)
            }}
            error={fieldErrorsList[index]?.youtubeLink}
          />

          <ColInputLabel
            label="Nome da Aula"
            placeholder={lessonInfo.title}
            idInput={`title-${index}`}
            name="title"
            value={lesson.title ?? ''}
            onChange={(e) => {
              handleChange(index, e)
              handleUpdateChange(e)
            }}
            error={fieldErrorsList[index]?.title}
          />

          <ColLargeInput
            label="Descrição da Aula"
            placeholder={lessonInfo.description}
            idInput={`descriptionC-${index}`}
            name="descriptionC"
            value={lesson.descriptionC ?? ''}
            onChange={(e) => {
              handleChange(index, e)
              handleUpdateChange(e)
            }}
            error={fieldErrorsList[index]?.descriptionC}
          />

          <div className="flex flex-row justify-between m-6">
            {!confirmDelete ? (
              <div
                className="flex items-center text-red-500 cursor-pointer"
                onClick={() => setConfirmDelete(true)}
              >
                <Trash className="w-5 h-5" />
                <span className="ml-2 text-sm italic">Excluir aula</span>
              </div>
            ) : (
              <div className="flex gap-3 items-center">
                <button
                  onClick={deleteLesson}
                  className="bg-red-600 hover:bg-red-700 text-white text-sm px-4 py-1 rounded"
                >
                  Confirmar Exclusão
                </button>
                <button
                  onClick={() => setConfirmDelete(false)}
                  className="text-gray-400 hover:text-gray-200 text-sm"
                >
                  Cancelar
                </button>
              </div>
            )}

            <div
              className="flex-row flex items-center gap-2 text-white cursor-pointer"
              onClick={updateLesson}
            >
              <Save className="w-5 h-5" />
              Salvar Alterações
              {error && <span className="text-red-500 text-sm ml-2">{error}</span>}
            </div>
          </div>
        </div>
      ))}
    </>
  )
}
