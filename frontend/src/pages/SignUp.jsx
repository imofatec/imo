import { Button } from "@/components/ui/button";
import InputLabel from "@/components/ui/inputlabel";
import { Link } from "react-router-dom";


export default function SignUp() {
    return (
        <div className="flex justify-center items-center h-screen">
            <div className="p-20 space-y-6 text-white bg-custom-blue rounded">
                <h1 className="text-xl font-bold text-center">
                    Cadastro
                </h1>

                <InputLabel type="text" id="email" name="email" placeholder="Email" label="Email" />

                <InputLabel type="text" id="username" name="username" placeholder="Username" label="Username" />

                <InputLabel type="password" id="password" name="password" placeholder="Senha" label="Senha" />

                <InputLabel type="password" id="password-confirm" name="password-confirm" placeholder="Confirmar senha" label="Confirmar senha" />

                <Button className="w-full bg-custom-light-blue">Cadastrar-se</Button>

                Ja tem uma conta? <Link to="/login" className="text-blue-500 underline">fazer login</Link>
            </div>
        </div>
    )
}