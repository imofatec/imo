import { InputLabel } from "@/components/ui/inputlabel"
import { Button } from "@/components/ui/button"
import { Form, Link } from "react-router-dom"
import { Logo } from "@/components/ui/logo"
import { Github, Linkedin } from "lucide-react"
import { Titulo } from "@/components/ui/titulo"

export default function SignIn() {

    return (
        <>
            <Titulo titulo={"Login"}></Titulo>

            <div className="flex justify-center items-center bg-custom-gray">
                <div className="p-8 space-y-6 w-96 text-white bg-custom-blue rounded-xl my-16 ">

                    <div className="flex flex-col items-center">
                        <Logo />
                        <h1 className="text-xl font-bold text-center">
                            Login
                        </h1>
                    </div>

                    <Form action="/login" method="post">
                        <InputLabel type="text" id="email" name="email" placeholder="Email" label="Email" />
                        <InputLabel type="password" id="password" name="password" placeholder="Senha" label="Senha" />
                        <Button className="w-full bg-custom-light-blue">Entrar</Button>
                    </Form>

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
                        <p className="text-custom-text-gray">
                            Não tem uma conta?
                            <Link to="/cadastro" className="text-white ">
                                Cadastrar-se
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </>
    )
}
