import { Github, Linkedin } from 'lucide-react'
import FormInput from '@/components/ui/FormInput'
import Button from '@/components/ui/Button'
import Divider from '@/components/ui/divider'
import SocialAccounts from '@/components/auth/SocialAccounts'
import AuthLinks from '@/components/auth/AuthLinks'

export default function Login() {
  return (
    <div className=" flex flex-1 flex-col items-center justify-center text-white">
      <form className="flex w-96 flex-col gap-2 p-8">
        <div className="flex flex-col items-center">
          <h1 className="text-center text-xl font-bold">Login</h1>
        </div>

        <FormInput type="text" id="email" name="email" placeholder="Email" label="Email" />
        <FormInput
          type="password"
          id="password"
          name="password"
          placeholder="Senha"
          label="Senha"
        />

        <Button className="bg-cyan text-black">Entrar</Button>
      </form>

      <div className="flex flex-col gap-6">
        <Divider text="Entre com outras contas" />
        <SocialAccounts providers={[Github, Linkedin]} />
        <AuthLinks />
      </div>
    </div>
  )
}
