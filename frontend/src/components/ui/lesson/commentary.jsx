import { Button } from '../button'
import LargeInput from '../inputs/largeinput'

export default function Commentary() {
  return (
    <div className="my-6 ml-6 ">
      <div className="flex flex-row">
        <LargeInput placeholder="Faça seu comentário" />
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
