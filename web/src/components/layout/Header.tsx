import LogoIMO from '@/assets/LogoIMO.svg'
import { useAuth } from '@/contexts/AuthContext'
import { Link } from 'react-router-dom'
import Button from '@/components/ui/Button'
import LinkButton from '@/components/ui/LinkButton'

export default function Header() {
  const { isAuthenticated, logout } = useAuth()

  return (
    <header className="flex flex-col">
      <div className="bg-dark-purple flex h-12.5 items-center justify-around">
        <Link to={'/'}>
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <div className="relative flex w-196 items-center">
          <p>dropdownsearch</p>
        </div>
        {isAuthenticated ? (
          <Button className="bg-cyan my-5 w-25 text-black" onClick={logout}>
            Sair
          </Button>
        ) : (
          <>
            <div className="flex w-1/7 justify-between">
              <LinkButton to={'/login'} className="bg-cyan w-25 text-black">
                Login
              </LinkButton>
              <LinkButton to={'/cadastrar'} className="bg-cyan w-25 text-black">
                Registro
              </LinkButton>
            </div>
          </>
        )}
      </div>

      <div className="bg-cyan mb-3 flex min-h-10 flex-wrap items-center justify-center gap-9 text-black">
        <p>Header Item 1</p>
        <p>Header Item 2</p>
        <p>Header Item 3</p>
      </div>
    </header>
  )
}
