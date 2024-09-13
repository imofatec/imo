import EstrelaIcon from "./estrelaicon"

export default function ({ notaCurso, avaliacoesCurso }) {
    return (
        <div className="flex flex-row text-center  px-7 max-w-full">
            <p className=''>
                {notaCurso}
            </p>
            {/* 
> Implementar logica das estrelas baseado noa avaliação
> Escolha estética de estrelas com a equipe
*/}
            <EstrelaIcon></EstrelaIcon>
            <EstrelaIcon></EstrelaIcon>
            <EstrelaIcon></EstrelaIcon>
            <EstrelaIcon></EstrelaIcon>
            <EstrelaIcon desativado={true}></EstrelaIcon>
            <p className='text-gray-400'>
                {/* 
quantidade de avaliações
*/}
                ({avaliacoesCurso})
            </p>
        </div>
    )
}