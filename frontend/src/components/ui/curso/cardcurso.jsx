import React, { useState } from 'react'
import Modal from './modal'
import Avaliacao from './avaliacao'

export default function CardCurso({
  nomeCurso,
  notaCurso,
  avaliacoesCurso,
  descricaoCurso,
  conteudo,
  quantidade,
  codigo,
  fotoCurso,
  codAula,
  onStart,
  idCurso
}) {
  const [aberto, setAberto] = useState(false)
  const abrir = () => setAberto(true)
  const fechar = () => setAberto(false)

  return (
    <>
      <div
        onClick={abrir}
        className="flex flex-col text-center max-w-64 font-semibold px-5 cursor-pointer mb-12 hover:scale-105 hover:duration-200 text-white"
      >
        <img className="max-w-thumb max-h-thumb" src={fotoCurso} alt={nomeCurso + " "+ descricaoCurso}></img>
        <h6 className="">{nomeCurso}</h6>
        <Avaliacao
          notaCurso={notaCurso}
          avaliacoesCurso={avaliacoesCurso}
        ></Avaliacao>
      </div>
      <Modal
        aberto={aberto}
        fechado={fechar}
        notaCurso={notaCurso}
        avaliacoesCurso={avaliacoesCurso}
        nomeCurso={nomeCurso}
        descricao={descricaoCurso}
        conteudo={conteudo}
        qtd={quantidade}
        fotoTemp={fotoCurso}
        codigoCurso={codigo}
        codigoAula={codAula}
        onStart={onStart}
        idCurso={idCurso}
      />
    </>
  )
}
