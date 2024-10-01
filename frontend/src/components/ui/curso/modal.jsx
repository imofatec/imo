import { Dialog, DialogContent, DialogHeader } from './dialog'
import Avaliacao from './avaliacao'
import { Link } from 'react-router-dom'

let codigoCurso = '4'

export default function Modal({
  aberto,
  fechado,
  notaCurso,
  avaliacoesCurso,
  nomeCurso,
  descricao,
  fotoTemp,
  codigoCurso,
  qtd,
  codigoAula
}) {
  return (
    <Dialog open={aberto} onOpenChange={fechado}>
      <DialogContent className="bg-custom-header-dark-purple border-none text-white">
        <DialogHeader>
          <div className="flex flex-row ">
            <div className="flex flex-col items-center text-center p-6">
              <h2 className="font-bold text-lg">{nomeCurso}</h2>
              <div className="flex flex-col items-center max-w-64">
                <img className="m-2 " src={fotoTemp} alt=""></img>
                <Avaliacao
                  notaCurso={notaCurso}
                  avaliacoesCurso={avaliacoesCurso}
                ></Avaliacao>
              </div>
              <p className="text-gray-400 text-sm ">
                Quantidade de aulas: {qtd}
              </p>
              <Link
                to={`/cursos/${codigoCurso}/${codigoAula}`} /*rota por parametros para acessar o vídeo*/
                className="underline mt-8 font-normal text-lg hover:text-gray-600"
              >
                Começe agora!
              </Link>
            </div>

            <div className="flex flex-col p-6 ml-5">
              <h1 className="font-medium text-lg">Descrição</h1>
              <p className="p-3 ml-4 text-justify">{descricao}</p>
            </div>
          </div>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  )
}
