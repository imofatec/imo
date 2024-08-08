import headerlogoinicial from "../assets/headerlogoinicial.svg"
import headerlogoazul from "../assets/headerlogoazul.svg"

import { InputLabel } from "@/components/ui/inputlabel"
import { Button } from "@/components/ui/button"
import { Link } from "react-router-dom"
import { useState } from "react"


export default function SignUp() {
    const [logo, setLogo] = useState(headerlogoinicial)

    function handleLogoHover(e) {
        setLogo(e.type === 'mouseenter' ? headerlogoazul : headerlogoinicial)
    }

    return (
        <div className="flex justify-center items-center h-screen bg-custom-gray">
            <div className="flex flex-col items-center w-1/3 p-20 space-y-6 text-white bg-custom-blue rounded">
                <div>
                    <Link to="/">
                        <img src={logo} onMouseEnter={handleLogoHover} onMouseOut={handleLogoHover} className="mb-4 cursor-pointer"></img>
                    </Link>

                    <h1 className="text-2xl font-bold text-center">
                        Cadastro
                    </h1>
                </div>
                <InputLabel type="text" id="email" name="email" placeholder="Email" label="Email" />

                <InputLabel type="text" id="username" name="username" placeholder="Username" label="Username" />

                <InputLabel type="password" id="password" name="password" placeholder="Senha" label="Senha" />

                <InputLabel type="password" id="password-confirm" name="password-confirm" placeholder="Confirmar senha" label="Confirmar senha" />

                <Button className="w-full bg-custom-light-blue">Cadastrar-se</Button>

                <span>
                    Ja tem uma conta? <Link to="/login" className="text-blue-500 underline">fazer login</Link>
                </span>
            </div>
        </div>
    )
}