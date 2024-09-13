import fotoCurso from '@/assets/thumb.jpg'
import React, { useState } from 'react';
import Modal from './modal';
import Avaliacao from './avaliacao';

export default function CardCurso({ nomeCurso, notaCurso, avaliacoesCurso, descricaoCurso, conteudo }) {

    const [aberto, setAberto] = useState(false);
    const abrir = () => setAberto(true);
    const fechar = () => setAberto(false);

    return <>
        <div onClick={abrir}
            className="flex flex-col text-center max-w-64 font-semibold px-5 cursor-pointer mb-12 hover:bg-slate-600 text-white">
            <img className="max-w-thumb max-h-thumb" src={fotoCurso} alt=''></img>
            <h6 className="">
                {nomeCurso}
            </h6>
            <Avaliacao notaCurso={notaCurso} avaliacoesCurso={avaliacoesCurso}></Avaliacao>
            <Modal aberto={aberto} fechado={fechar} notaCurso={notaCurso} avaliacoesCurso={avaliacoesCurso} nomeCurso={nomeCurso} descricao={descricaoCurso} conteudo={conteudo} />
        </div>
    </>
}