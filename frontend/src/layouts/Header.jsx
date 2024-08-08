import headerlogoinicial from "@/assets/headerlogoinicial.svg"
import headerlogoazul from "@/assets/headerlogoazul.svg"

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input"
import { Search } from "lucide-react";
import { Outlet } from "react-router-dom";
import { useState } from "react"
import { Link } from "react-router-dom";

export default function Header() {
    const [logo, setLogo] = useState(headerlogoinicial)

    function handleLogoHover(e) {
        setLogo(e.type === 'mouseenter' ? headerlogoazul : headerlogoinicial)
    }

    return (
        <>
            <header className="flex justify-around items-center h-28 bg-custom-gray">
                <div className="inline-flex space-x-20">
                    <img src={logo} onMouseEnter={handleLogoHover} onMouseOut={handleLogoHover} className="cursor-pointer"></img>
                    <div className="inline-flex items-center w-96 -space-x-8">
                        <Search className="z-10" />
                        <Input type="search" placeholder="Pesquise por qualquer coisa" className="text-center rounded-full shadow-md shadow-gray-500" />
                    </div>
                </div>
                <div className="space-x-6">
                    <Button className="border border-white">Todos os cursos</Button>
                    <Button className="border border-white">Fazer login</Button>
                    <Link to="/cadastro">
                        <Button className="text-black bg-white">Cadastrar-se</Button>
                    </Link>
                </div>
            </header>
            <Outlet />
        </>
    )
}