import { Button } from '../button'

export default function Commentary() {
  return (
    <div className="my-6 ml-6 ">
      <div className="flex flex-row">
        <textarea
          className="w-full rounded-lg bg-custom-blue p-6 h-36"
          placeholder="Faça seu comentário"
          type="text"
        ></textarea>
      </div>
      <div className="flex flex-row mt-6">
        <div className="flex flex-col">
          <Button className="bg-custom-header-cyan text-black">Comentar</Button>
        </div>
        <div className="flex flex-col">
          <Button className="bg-custom-dark-blue mx-6">Cancelar</Button>
        </div>
      </div>
    </div>
  )
}
