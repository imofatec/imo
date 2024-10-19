import { useState, useEffect } from 'react'
import { Button } from '@/components/ui/button'
import SettingsIcon from './icons/settingsIcon'
import Certificate from './icons/certificate'
import CoursesIcon from './icons/coursesIcon'
import PlusHeader from './icons/plusheader'
import DropdownTriangle from './icons/dropdownTriangle'
import LogoutHeader from './icons/logoutHeader'
import { Link } from 'react-router-dom'
import api from '@/api/api'
import useFetchUserInfo from '@/hooks/useFetchUserInfo'
export default function DropdownHeader({ isLoggedIn }) {
  const [showDropdown, setShowDropdown] = useState(false)
  const [hideTimeout, setHideTimeout] = useState(null)
  const { urlImage, fetchUserInfo } = useFetchUserInfo()

  useEffect(() => {
    if (isLoggedIn) {
      fetchUserInfo()
    }
  }, [isLoggedIn, fetchUserInfo])

  const handleMouseEnter = () => {
    if (hideTimeout) {
      clearTimeout(hideTimeout)
    }
    setShowDropdown(true)
  }

  const handleMouseLeave = () => {
    const timeout = setTimeout(() => {
      setShowDropdown(false)
    }, 200)
    setHideTimeout(timeout)
  }

  return (
    <div className="flex align-middle items-center relative">
      {isLoggedIn ? (
        <>
          <div
            className="w-10 h-10 bg-white rounded-full cursor-pointer flex items-center justify-center"
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
          >
            <img
              src={urlImage}
              alt="Avatar do usuario"
              className="w-full h-full rounded-full"
            />
          </div>
          {showDropdown && (
            <>
              <div className='absolute top-[3rem] right-[0.75rem]'>
                <DropdownTriangle />
              </div>
              <div
                className="absolute top-full right-[-2rem] mt-6 w-48 bg-custom-header-dark-purple border border-white rounded-lg shadow-lg"
                onMouseEnter={handleMouseEnter}
                onMouseLeave={handleMouseLeave}
              >
                <ul>
                  <Link to="/user/configurar-conta">
                    <li className="flex items-center gap-1 px-4 py-2 hover:bg-custom-header-cyan hover:text-black transition-colors duration-300 ease-in-out">
                      <SettingsIcon />
                      Configurações
                    </li>
                  </Link>

                  <Link to={'/'}>
                    <li className="flex items-center gap-1 px-4 py-2 hover:bg-custom-header-cyan hover:text-black transition-colors duration-300 ease-in-out">
                      <Certificate />
                      Meus Certificados
                    </li>
                  </Link>

                  <Link to={'/'}>
                    <li className="flex items-center gap-1 px-4 py-2 hover:bg-custom-header-cyan hover:text-black transition-colors duration-300 ease-in-out">
                      <CoursesIcon />
                      Meus Cursos
                    </li>
                  </Link>

                  <Link to={'/criar-curso'}>
                    <li className="flex items-center gap-1 px-4 py-2 hover:bg-custom-header-cyan hover:text-black transition-colors duration-300 ease-in-out">
                      <PlusHeader />
                      Submeter Curso
                    </li>
                  </Link>
                  <li
                    className="flex items-center gap-1 px-4 py-2 hover:bg-custom-header-cyan hover:text-black transition-colors duration-300 ease-in-out cursor-pointer"
                    onClick={() => {
                      localStorage.clear('token')
                      window.location.reload()
                    }}
                  >
                    <LogoutHeader />
                    Sair
                  </li>
                </ul>
              </div>
            </>
          )}
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
  )
}
