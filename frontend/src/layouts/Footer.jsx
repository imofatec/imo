import { Link } from "react-router-dom"


export default function Footer() {
    return (
        <footer className=" flex flex-col bg-custom-footer-dark  px-32 text-white mx-auto w-full " >
            <div className=" mb-24 ">

                <div className=" flex flex-col mt-24 ">
                    <label className=" text-xl font-semibold ">
                        Menu
                    </label>
                    <div className=" mt-6 ">

                    <Link to = {""}>
                    <a className=" underline ">
                        Quem somos
                    </a>
                    </Link>
                    <Link to = {""}>
                    <a className=" underline px-3 ">
                        Termo de uso
                    </a>
                    </Link>
                    <Link to = {""}>
                    <a className=" underline ">
                        FAQ
                    </a>
                    </Link>
                </div>
                </div>

                <div className=" mt-14 ">
                    <label className=" text-xl font-semibold ">
                        Tecnologias
                    </label>
                    <p className=" mt-6 ">
                        HTML ● CSS ● JavaScript ● Node.js ● React ● Next.js  ● Vue.js ● Tailwind ● TypeScript ● Angular ●
                        Lua ● Python ● Go ● C ● C++ ● C# ● Java ● Spring ● PHP ● Laravel ● Kotlin ● Flutter ● Rust ● Delphi ● 
                        SQL ● Docker ● Git ● Linux ● SSH
                    </p>
                </div>

                <div className=" mt-14 ">
                <label className=" text-xl font-semibold ">
                    Cursos
                    </label>
                    <p className=" mt-6 ">
                        Algoritimos e lógica de programação ● Desenvolvimento web ● Design Digital ● Banco de dados relacional e não relacional ●
                        Técnicas de programação ● Estrutura de dados ● Programação mobile ● Sistemas operacionais ● Redes de computadores ●
                        Computação em nuvem ● Engenharia de Software ● Gestão ágil de projeto de software ● Matemática para computação
                    </p>
                </div>

            </div>
        </footer>
    )
}