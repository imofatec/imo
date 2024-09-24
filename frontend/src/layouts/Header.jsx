import api from '@/api/api'
import LogoIMO from '@/assets/LogoIMO.svg'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { LogOut, Search } from 'lucide-react'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

const headerItens = ['Data Science', 'Redes', 'Gestão', 'Design', 'Programação'] // contem todos os botoes do header ciano

export default function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false)

  useEffect(() => {
    const validate = async () => {
      const token = localStorage.getItem('token')
      setIsLoggedIn(token ? true : false)
    }
    validate()
  }, [])

  return (
    <header className="flex flex-col">
      <div className="bg-custom-dark-purple h-[3.125rem] flex justify-around items-center">
        <Link to={'/'}>
          <img src={LogoIMO} className="cursor-pointer" />
        </Link>

        <div className=" w-[49rem] relative flex items-center ">
          <Search className="absolute left-3 text-white" />
          <div className="absolute left-12 w-[1px] h-5 bg-white"></div>
          <Input
            type="search"
            placeholder="Pesquise por qualquer coisa"
            className="pl-14 bg-custom-search-dark border-custom-search-dark text-white w-full placeholder:text-white h-[2.375rem]"
          />
        </div>

        <div className="flex align-middle items-center">
          {isLoggedIn ? (
            <>
              <Link to={'#'}>
                <div className="w-10 h-10 bg-white rounded-full" />
              </Link>

              <Button className="">
                <LogOut
                  onClick={() => {
                    localStorage.clear('token'), window.location.reload()
                  }}
                />
              </Button>
            </>
          ) : (
            <>
              <Button className="">
                <Link to={'/cadastro'}>Criar Conta</Link>
              </Button>

              <Button className="">
                <Link to={'/login'}>Fazer Login</Link>
              </Button>
            </>
          )}
        </div>
      </div>

      <div className="bg-custom-header-cyan min-h-[2.5rem] flex flex-wrap justify-center items-center gap-9">
        {
          // CODIGO PARA PEGAR TODOS OS ITENS DA CONSTANTE E RETORNA OS BOTOES

          headerItens.map(function (item) {
            return <Button className="text-black">{item}</Button>
          })
        }
      </div>
    </header>
  )
}
