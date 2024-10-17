import LogoIMO from '@/assets/LogoIMO.svg'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/inputs/input'
import { LogOut, Search } from 'lucide-react'
import DropdownHeader from '@/components/ui/header/dropdwnHeader'
import { useEffect, useMemo, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'

const headerItens = ['Data Science', 'Redes', 'Gestão', 'Design', 'Programação']

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

  const navigate = useNavigate()

  const handleSearch = (e) => {
    if (e.key === 'Enter') {
      const searchTerm = e.target.value
        .trim()
        .toLowerCase()
        .replace(/[^\w\s]|_/g, '')
        .replace(/\s+/g, '-')
      navigate(`/categorias/${searchTerm}`)
      e.preventDefault()
    }
  }
  return (
    <header className="flex flex-col">
      <div className="bg-custom-dark-purple h-[3.125rem] flex justify-around items-center">
        <Link to={'/'}>
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <div className="w-[49rem] relative flex items-center ">
          <Search className="absolute left-3 text-white" />
          <div className="absolute left-12 w-[1px] h-5 bg-white"></div>
          <Input
            type="search"
            placeholder="Pesquise por qualquer coisa"
            className="pl-14 bg-custom-search-dark border-custom-search-dark text-white w-full placeholder:text-white h-[2.375rem]"
            onKeyDown={handleSearch}
          />
        </div>
        <DropdownHeader isLoggedIn={isLoggedIn}/>
      </div>

      <div className="bg-custom-header-cyan min-h-[2.5rem] flex flex-wrap justify-center items-center gap-9 mb-3">
        {headerItens.map(function (item, index) {
          return (
            <Button key={index} className="text-black">
              {item}
            </Button>
          )
        })}
      </div>
    </header>
  )
}
