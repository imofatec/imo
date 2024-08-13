import LogoIMO from "@/assets/LogoIMO.svg"

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input"
import { Search } from "lucide-react";
import { Outlet } from "react-router-dom";
import { Link } from "react-router-dom";

const headerItens = ["Data Science", "Devops", "Hardware", "Design", "Back End", "Front End"] // contem todos os botoes do header ciano

// import { Logo } from "@/components/ui/logo"  || talvez utilizar depois caso a logo seja decidida XD

export default function Header() {

    return (
        <header className="h-[8.5rem] flex flex-col">

            <div className="bg-custom-header-dark-purple h-20 flex justify-around items-center">

                <Link to="/">
                    <img src={LogoIMO} className="cursor-pointer"></img>
                </Link>

                <div className=" w-[49rem] relative flex items-center ">
                    <Search className="absolute left-3 text-white" />
                    <div className="absolute left-12 w-[1px] h-5 bg-white"></div>
                    <Input type="search"
                        placeholder="Pesquise por qualquer coisa"
                        className="pl-14 bg-custom-search-dark border-custom-search-dark text-white w-full placeholder:text-white"
                    />
                </div>

                <div className="flex align-middle items-center">
                    <Button className="">
                        Criar Conta
                    </Button>
                    <Button className="">
                        Fazer Login
                    </Button>
                </div>

            </div>

            <div className="bg-custom-header-cyan min-h-14 flex flex-wrap justify-center items-center gap-9">

                {
                    // CODIGO PARA PEGAR TODOS OS ITENS DA CONSTANTE E RETORNA OS BOTOES

                    headerItens.map(function (item) {
                        return (
                            <Button className="text-black">{item}</Button>
                        )
                    })
                }

            </div>
        </header>
    )
}