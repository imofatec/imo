import ColInputLabel from '../inputs/colinputlabel'
import ColLargeInput from '../inputs/collargeinput'
import { Button } from '../button'
import { SpinnerButton } from '../spinnerButton'

export default function AddLessonForm({
  newLesson,
  onChange,
  onCancel,
  onSubmit,
  isLoading,
  error,
}) {
  return (
    <div className="w-full border border-white rounded-xl p-6 my-6">
      <h2 className="text-xl text-white mb-4">Nova Aula</h2>

      <ColInputLabel
        label="Link da Aula"
        idInput="youtubeLink"
        name="youtubeLink"
        value={newLesson.youtubeLink}
        onChange={onChange}
        placeholder="Cole o link do vídeo"
      />

      <ColInputLabel
        label="Nome da Aula"
        idInput="title"
        name="title"
        value={newLesson.title}
        onChange={onChange}
        placeholder="Digite o nome da aula"
      />

      <ColLargeInput
        label="Descrição da Aula"
        idInput="description"
        name="description"
        value={newLesson.description}
        onChange={onChange}
        placeholder="Descreva o conteúdo da aula"
      />

      <div className="flex justify-end mt-4 gap-4">
        <Button
          type="button"
          onClick={onCancel}
          className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-6 rounded-xl"
        >
          Cancelar
        </Button>

        <SpinnerButton
          onClick={onSubmit}
          isLoading={isLoading}
          className="bg-custom-header-cyan text-black font-bold px-6"
        >
          Salvar Aula
        </SpinnerButton>
      </div>

      {error && (
        <p className="text-red-500 mt-2">{error}</p>
      )}
    </div>
  )
}
