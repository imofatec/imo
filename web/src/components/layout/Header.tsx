import LogoIMO from '@/assets/LogoIMO.svg'
import SearchBar from '@/components/layout/SearchBar'
import LinkButton from '@/components/ui/LinkButton'
import UserAvatar from '@/components/ui/UserAvatar'
import { useAuth } from '@/contexts/AuthContext'
import { useUser } from '@/contexts/UserContext'
import { ChevronDown } from 'lucide-react'
import { useEffect, useRef, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

type ProfileMenuItem = {
  label: string
  to: string
}

const profileMenuItems: ProfileMenuItem[] = [
  { label: 'Editar perfil', to: '/user/configuracoes' },
  { label: 'Conquistas', to: '/user/conquistas' },
  { label: 'Meu aprendizado', to: '/user/cursos' },
  { label: 'Todos os cursos', to: '/categorias' },
  { label: 'Criar curso', to: '/criar-curso' },
  { label: 'Editar curso', to: '/user/cursos?filtro=CONTRIBUICAO' },
  { label: 'Validar certificado', to: '/home' },
]

export default function Header() {
  const { isAuthenticated, logout } = useAuth()
  const { user } = useUser()
  const navigate = useNavigate()
  const [isProfileOpen, setIsProfileOpen] = useState(false)
  const profileMenuRef = useRef<HTMLDivElement | null>(null)

  useEffect(() => {
    if (!isProfileOpen) return

    function handleOutsideClick(event: MouseEvent) {
      if (profileMenuRef.current && !profileMenuRef.current.contains(event.target as Node)) {
        setIsProfileOpen(false)
      }
    }

    function handleEscape(event: KeyboardEvent) {
      if (event.key === 'Escape') {
        setIsProfileOpen(false)
      }
    }

    document.addEventListener('mousedown', handleOutsideClick)
    document.addEventListener('keydown', handleEscape)

    return () => {
      document.removeEventListener('mousedown', handleOutsideClick)
      document.removeEventListener('keydown', handleEscape)
    }
  }, [isProfileOpen])

  function handleToggleProfileMenu() {
    setIsProfileOpen((current) => !current)
  }

  function handleCloseProfileMenu() {
    setIsProfileOpen(false)
  }

  function handleLogout() {
    handleCloseProfileMenu()
    logout()
    navigate('/login')
  }

  const userName = user?.name?.trim() || 'Usuario'
  const profileImageSrc = user?.profilePicturePath ?? null

  return (
    <header className="flex flex-col">
      <div className="bg-dark-purple flex min-h-14 items-center justify-around py-2">
        <Link to="/">
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <SearchBar />

        {isAuthenticated ? (
          <div ref={profileMenuRef} className="relative flex w-1/7 justify-end">
            <button
              type="button"
              onClick={handleToggleProfileMenu}
              className="border-cyan/30 bg-cyan/10 hover:bg-cyan/20 flex items-center gap-2 rounded-xl border px-3 py-2 text-white transition"
              aria-haspopup="menu"
              aria-expanded={isProfileOpen}
            >
              <UserAvatar
                imageSrc={profileImageSrc}
                name={userName}
                fallback="icon"
                sizeClassName="h-8 w-8"
                iconClassName="text-cyan"
              />
              <span className="text-smx2 hidden max-w-26 truncate md:block">{userName}</span>
              <ChevronDown
                size={22}
                className={`text-cyan transition-transform ${isProfileOpen ? 'rotate-180' : ''}`}
              />
            </button>

            {isProfileOpen ? (
              <div className="absolute top-12 right-0 z-50 w-60 overflow-hidden rounded-2xl border border-white/10 bg-[#14082f] shadow-2xl">
                <div className="border-b border-white/10 px-4 py-3">
                  <p className="truncate text-sm font-semibold text-white">{userName}</p>
                  <p className="truncate text-xs text-white/50">{user?.email ?? 'E-mail'}</p>
                </div>

                <div className="p-2">
                  {profileMenuItems.map((item) => (
                    <Link
                      key={item.label}
                      to={item.to}
                      onClick={handleCloseProfileMenu}
                      className="block rounded-xl px-3 py-2 text-sm text-white/80 transition hover:bg-white/10 hover:text-white"
                    >
                      {item.label}
                    </Link>
                  ))}

                  <button
                    type="button"
                    onClick={handleLogout}
                    className="mt-1 block w-full rounded-xl px-3 py-2 text-left text-sm text-red-300 transition hover:bg-red-500/15 hover:text-red-200"
                  >
                    Sair
                  </button>
                </div>
              </div>
            ) : null}
          </div>
        ) : (
          <div className="flex w-1/7 justify-end gap-4">
            <LinkButton variant="cyanOutline" to="/login" className="bg-cyan text-cyan mt-0! w-25">
              Entrar
            </LinkButton>
            <LinkButton
              variant="cyanOutline"
              to="/cadastro"
              className="bg-cyan text-cyan mt-0! w-25"
            >
              Cadastrar
            </LinkButton>
          </div>
        )}
      </div>

      <div className="bg-cyan flex min-h-10 flex-wrap items-center justify-center gap-9 text-black">
        <LinkButton variant="default" to="/home" className="bg-cyan font-semibold text-black">
          Página inicial
        </LinkButton>
        <LinkButton variant="default" to="/categorias" className="bg-cyan font-semibold text-black">
          Todos os cursos
        </LinkButton>
        <LinkButton
          variant="default"
          to="/user/cursos"
          className="bg-cyan font-semibold text-black"
        >
          Meus cursos
        </LinkButton>
        <LinkButton
          variant="default"
          to="/criar-curso"
          className="bg-cyan font-semibold text-black"
        >
          Contribuir com curso
        </LinkButton>
        <LinkButton variant="default" to="/home" className="bg-cyan font-semibold text-black">
          Validar certificado
        </LinkButton>
      </div>
    </header>
  )
}
