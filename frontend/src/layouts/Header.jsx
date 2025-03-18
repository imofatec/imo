import LogoIMO from '@/assets/LogoIMO.svg'
import CyanLink from '@/components/ui/header/cyanLink'
import DropdownHeader from '@/components/ui/header/dropdwnHeader'
import { useEffect, useMemo, useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'
import DropdownSearch from '@/components/ui/header/dropdownSearch'

const headerItens = [
  { name: 'Página Inicial', to: '/' },
  { name: 'Cursos em Alta', to: '/' },
  { name: 'Todos os Cursos', to: '/categorias' },
  { name: 'Contribuir com Curso', to: '/criar-curso' },
  { name: 'Validar Certificado', to: '/validar-certificado' },
]

export default function Header() {
  const [token, setToken] = useState(localStorage.getItem('token'))
  const location = useLocation()

  const validateTime = (token) => {
    const { exp } = jwtDecode(token)
    return exp * 1000 > Date.now() ? true : false
  }

  const isLoggedIn = useMemo(() => {
    return token ? validateTime(token) : false
  }, [token])

  useEffect(() => {
    const storedToken = localStorage.getItem('token')
    setToken(storedToken)
  }, [location])

  return (
    <header className="flex flex-col">
      <div className="bg-custom-dark-purple h-[3.125rem] flex justify-around items-center">
        <Link to={'/'}>
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <div className="w-[49rem] relative flex items-center ">
          <DropdownSearch />
        </div>
        <DropdownHeader isLoggedIn={isLoggedIn} />
      </div>

      <div className="bg-custom-header-cyan min-h-[2.5rem] flex flex-wrap justify-center items-center gap-9 mb-3">
        {headerItens.map(function (item, index) {
          return <CyanLink key={index} to={item.to} name={item.name} />
        })}
      </div>
    </header>
  )
}
