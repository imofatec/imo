import LogoIMO from '@/assets/LogoIMO.svg'
import { useAuth } from '@/contexts/AuthContext'
import { Link } from 'react-router-dom'
import Button from '@/components/ui/Button'
import LinkButton from '@/components/ui/LinkButton'

export default function Header() {
  const { isAuthenticated, logout } = useAuth()

  return (
    <header className="flex flex-col">
      <div className="bg-dark-purple flex min-h-14 items-center justify-around py-2">
        <Link to={'/'}>
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <div className="relative flex w-196 items-center">
          <p>dropdownsearch</p>
        </div>
        {isAuthenticated ? (
          <Button className="bg-cyan text-cyan my-5 w-25" onClick={logout}>
            Sair
          </Button>
        ) : (
          <>
            <div className="flex w-1/7 justify-end gap-4">
              <LinkButton variant="cyanOutline" to={'/login'} className="bg-cyan text-cyan w-25">
                Entrar
              </LinkButton>
              <LinkButton variant="cyanOutline" to={'/register'} className="bg-cyan text-cyan w-25">
                Cadastrar
              </LinkButton>
            </div>
          </>
        )}
      </div>

      <div className="bg-cyan mb-3 flex min-h-10 flex-wrap items-center justify-center gap-9 text-black">
        <LinkButton variant="default" to={'/home'} className="bg-cyan w-25 text-black">
          Página inicial
        </LinkButton>
        <LinkButton variant="default" to={'/allcourses'} className="bg-cyan w-25 text-black">
          Todos os cursos
        </LinkButton>
        <LinkButton variant="default" to={'/mycourses'} className="bg-cyan w-25 text-black">
          Meus cursos
        </LinkButton>
        <LinkButton variant="default" to={'/home'} className="bg-cyan w-25 text-black">
          Contribuir com curso
        </LinkButton>
        <LinkButton variant="default" to={'/home'} className="bg-cyan w-25 text-black">
          Validar certificado
        </LinkButton>
      </div>
    </header>
  )
}
