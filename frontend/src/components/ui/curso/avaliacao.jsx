import { useEffect , useState} from "react"
import EstrelaIcon from "./estrelaicon"

export default function Avaliacao ({ notaCurso, avaliacoesCurso }) {
    const [estrelas , setEstrelas] = useState([]); 

    useEffect(() => {
        let listaEstrelas = []

        for(let i = 1 ; i <= 5 ; i++){
            if(i <= notaCurso){
                listaEstrelas.push({estado: false})
            }else{
                listaEstrelas.push({estado: true})
            }
        }
        setEstrelas(listaEstrelas)
    } , [notaCurso]) 

    return (
        <div className="flex flex-row text-center  px-7 max-w-full">
            <p className=''>
                {notaCurso}
            </p>

            <>
            {estrelas.map((dados, i) => (
                <EstrelaIcon key={i} desativado={dados.estado} />
            ))}
        </>

            <p className='text-gray-400'>
                {/* 
quantidade de avaliações
*/}
                ({avaliacoesCurso})
            </p>
        </div>
    )
}