import CardCurso from "@/components/ui/curso/cardcurso";
import { Dropdown } from "@/components/ui/dropdown/dropdown";
import { Titulo } from "@/components/ui/titulo";
import React from "react";

import fotoTemp from '../assets/thumb.jpg'
let tipoCurso = "Todos os cursos"

export default function Cursos() {

    const infoCurso = [
        { nomeCurso: "Metodologia Ágil", notaCurso: "5.0", avaliacoesCurso: "13" },
        { nomeCurso: "Banco de Dados", notaCurso: "4.0", avaliacoesCurso: "42" },
        { nomeCurso: "JavaScript", notaCurso: "4.9", avaliacoesCurso: "1964" },
        { nomeCurso: "React", notaCurso: "2.6", avaliacoesCurso: "2907" },
        { nomeCurso: "Excel", notaCurso: "3.8", avaliacoesCurso: "1606" },
        { nomeCurso: "Cloud Computing", notaCurso: "4.2", avaliacoesCurso: "2024" },
        { nomeCurso: "Git e GitHub", notaCurso: "4.0", avaliacoesCurso: "80" },
        { nomeCurso: "Adobe Ilustrator", notaCurso: "4.6", avaliacoesCurso: "3" }
    ]
    let descricao = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias maxime culpa officia eveniet. Natus tempore facilis soluta distinctio, ipsam libero, consequatur sit, pariatur iure voluptate aliquam deleniti ea adipisci culpa. Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias maxime culpa officia eveniet. Natus tempore facilis soluta distinctio, ipsam libero, consequatur sit, pariatur iure voluptate aliquam deleniti ea adipisci culpa."
    let conteudo = "Lorem ipsum"

    return (
        <>
            <Titulo titulo={tipoCurso} />
            <div className="flex flex-row w-full">
                <div className="w-1/3 p-12">
                    <Dropdown></Dropdown>
                </div>

                <div className="w-2/3 p-12">
                    <h5 className="font-semibold text-xl mb-10 text-white">
                        {tipoCurso}
                    </h5>
                    <div className="flex flex-row flex-wrap p-18 w-auto max-w-full">
                        {infoCurso.map((dado, i) => {
                            return (
                                <>
                                    <CardCurso key={i} nomeCurso={dado.nomeCurso} notaCurso={dado.notaCurso} avaliacoesCurso={dado.avaliacoesCurso} fotoCurso={fotoTemp} descricaoCurso={descricao} conteudo={conteudo}></CardCurso>
                                </>
                            )
                        })
                        }
                    </div>
                </div>
            </div>
        </>
    )
}