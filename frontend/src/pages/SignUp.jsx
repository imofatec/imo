import { Button } from "@/components/ui/button"
import { InputLabel } from "@/components/ui/inputlabel"
import { Link } from "react-router-dom"
import { Logo } from "@/components/ui/logo"
import { Github, Linkedin } from "lucide-react"
import { Titulo } from "@/components/ui/titulo"

export default function SignUp() {

    return (
        <>
        <Titulo titulo={"Cadastro"}></Titulo>

        <div className="flex justify-center items-center bg-custom-gray ">
            <div className="w-96 p-8 space-y-6 text-white bg-custom-blue rounded-xl my-16 ">

                <div className="flex flex-col items-center">
                    <Logo />
                    <h1 className="text-xl font-bold text-center">
                        Cadastro
                    </h1>
                </div>

                <InputLabel type="text" id="email" name="email" placeholder="Email" label="Email" />

                {/* <InputLabel type="text" id="email" name="email" placeholder="Email" label="Email" classname="text-black border border-custom-border-gray focus:border-custom-light-blue" /> */}

                <InputLabel type="text" id="username" name="username" placeholder="Username" label="Username" />

                <InputLabel type="password" id="password" name="password" placeholder="Senha" label="Senha" />

                <InputLabel type="password" id="password-confirm" name="password-confirm" placeholder="Confirmar senha" label="Confirmar senha" />

                <Button className="w-full bg-custom-light-blue">Cadastrar-se</Button>


                <div className="flex items-center justify-center h-8">
                    <div className="h-[1px] w-12 bg-custom-border-gray"></div>
                    <p className="text-custom-text-gray pl-4 pr-4">Entre com outras contas</p>
                    <div className="h-[1px] w-12 bg-custom-border-gray"></div>
                </div>

                <div className="flex justify-center gap-3">
                    <Github />
                    <Linkedin />
                </div>
                <div className="flex justify-center ">
                    <p className="text-custom-text-gray">Ja tem uma conta? <Link to="/login" className="text-white ">Fazer login</Link></p>
                </div>
            </div>
        </div>
        </>

    )
}