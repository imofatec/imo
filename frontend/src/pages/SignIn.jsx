import { InputLabel } from '@/components/ui/inputlabel'
import { Button } from '@/components/ui/button'
import { Form, Link, useActionData } from 'react-router-dom'
import { Logo } from '@/components/ui/logo'
import { Github, Linkedin } from 'lucide-react'
import { Titulo } from '@/components/ui/titulo'

export default function SignIn() {
  const message = useActionData()

  return (
    <>
      <Titulo titulo={'Login / IMO'}></Titulo>

      <div className="flex justify-center items-center bg-custom-dark-purple">
        <div className="h-screen mt-[3.125rem] text-white">
          <Form method="post" action="/login" className="w-96 p-8 space-y-6">
            <div className="flex flex-col items-center">
              <h1 className="text-xl font-bold text-center">Login</h1>
            </div>

            <InputLabel
              type="text"
              id="email"
              name="email"
              placeholder="Email"
              label="Email"
            />

            <InputLabel
              type="password"
              id="password"
              name="password"
              placeholder="Senha"
              label="Senha"
            />

            <Button className="w-full bg-custom-light-blue">Entrar</Button>

            {message && (
              <div className="text-center text-red-500">{message}</div>
            )}

            <div className="flex items-center justify-center h-8">
              <div className="h-[1px] w-12 bg-custom-border-gray"></div>
              <p className="text-custom-text-gray pl-4 pr-4">
                Entre com outras contas
              </p>
              <div className="h-[1px] w-12 bg-custom-border-gray"></div>
            </div>

            <div className="flex justify-center gap-3">
              <Github />
              <Linkedin />
            </div>
            <div className="flex justify-center ">
              <p className="text-custom-text-gray">
                Não tem uma conta?{' '}
                <Link to="/cadastro" className="text-white underline">
                  Cadastrar-se
                </Link>
              </p>
            </div>
          </Form>
        </div>
      </div>
    </>
  )
}
